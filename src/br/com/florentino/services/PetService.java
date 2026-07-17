package br.com.florentino.services;

import br.com.florentino.entity.Address;
import br.com.florentino.entity.Pet;
import br.com.florentino.enums.BiologicalSex;
import br.com.florentino.enums.Type;
import br.com.florentino.exceptions.*;
import br.com.florentino.repository.PetRepository;
import br.com.florentino.utils.Constants;
import br.com.florentino.utils.NumberParser;
import br.com.florentino.utils.StringFormatter;
import br.com.florentino.validator.PetValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetService {
    private final PetRepository petRepository;
    private final FormService formService;
    private final Scanner scanner;

    public PetService(PetRepository petRepository, FormService formService, Scanner scanner) {
        this.petRepository = petRepository;
        this.formService = formService;
        this.scanner = scanner;
    }

    public void registerPet() throws IOException {
        List<String> questions = formService.getQuestions();

        String name = askName(questions.get(0));
        Type type = askType(questions.get(1));
        BiologicalSex biologicalSex = askBiologicalSex(questions.get(2));
        Address address = askAddress(questions.get(3));
        Double age = askAge(questions.get(4));
        Double weight = askWeight(questions.get(5));
        String race = askRace(questions.get(6));

        List<String> extraAnswers = new ArrayList<>();

        if (questions.size() > 7) {
            for (int i = 7; i < questions.size(); i++) {
                extraAnswers.add(askExtraQuestion(questions.get(i)));
            }
        }

        Pet pet = new Pet(
                name,
                type,
                biologicalSex,
                address,
                age,
                weight,
                race,
                extraAnswers
        );

        petRepository.save(pet, questions);
    }

    public void updatePet() throws IOException {
        List<Pet> filteredPets = listPetsByCriteria();

        if (filteredPets.isEmpty()) {
            return;
        }

        int index = 1;

        System.out.println("[PETS ENCONTRADOS]");
        for (Pet pet : filteredPets) {
            System.out.println(StringFormatter.formatPetDataForDisplay(index++, pet));
        }
        System.out.println();

        while (true) {
            int petOption;
            Pet pet;

            System.out.print("Selecione o pet que você deseja alterar: ");
            try {
                petOption = Integer.parseInt(scanner.nextLine());
                pet = filteredPets.get(petOption - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Número inválido. Tente novamente.");
                continue;
            }
            System.out.println();

            List<String> questions = formService.getQuestions();
            List<String> extraAnswers = pet.getExtraAnswers();

            System.out.println("Selecione o dado que você deseja alterar:");
            System.out.println("[1] Nome;");
            System.out.println("[2] Cidade;");
            System.out.println("[3] Número;");
            System.out.println("[4] Rua;");
            System.out.println("[5] Idade;");
            System.out.println("[6] Peso;");

            if (extraAnswers.isEmpty()) {
                System.out.println("[7] Raça.");
            } else {
                System.out.println("[7] Raça;");
                System.out.println("[8] Extra.");
            }

            System.out.print("Escolha uma opção: ");
            String alterDataOption = scanner.nextLine();
            System.out.println();

            String term;

            Integer extraAnswerIndex = null;

            switch (alterDataOption) {
                case "1" -> {
                    System.out.print("Insira o novo nome: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());

                    try {
                        PetValidator.validateName(term);
                    } catch (InvalidNameException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                case "2" -> {
                    System.out.print("Insira a nova cidade: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());

                    Address address = new Address(term, pet.getAddress().getStreet(), pet.getAddress().getNumber());

                    try {
                        PetValidator.validateAddress(address);
                    } catch (InvalidAddressException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                case "3" -> {
                    System.out.print("Insira o novo número: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());

                    if (term.isBlank()) {
                        term = Constants.NOT_INFORMED;
                    }

                    Address address = new Address(pet.getAddress().getCity(), pet.getAddress().getStreet(), term);

                    try {
                        PetValidator.validateAddress(address);
                    } catch (InvalidAddressException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                case "4" -> {
                    System.out.print("Insira a nova rua: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());

                    Address address = new Address(pet.getAddress().getCity(), term, pet.getAddress().getNumber());

                    try {
                        PetValidator.validateAddress(address);
                    } catch (InvalidAddressException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                case "5" -> {
                    System.out.print("Insira a nova idade: ");
                    term = scanner.nextLine();

                    if (term.isBlank()) {
                        term = null;
                    }

                    if (term != null) {
                        Double parsedTerm = NumberParser.parseDouble(term);

                        try {
                            PetValidator.validateAge(parsedTerm);
                        } catch (InvalidAgeException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                }
                case "6" -> {
                    System.out.print("Insira o novo peso: ");
                    term = scanner.nextLine();

                    if (term.isBlank()) {
                        term = null;
                    }

                    if (term != null) {
                        Double parsedTerm = NumberParser.parseDouble(term);

                        try {
                            PetValidator.validateWeight(parsedTerm);
                        } catch (InvalidWeightException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                }
                case "7" -> {
                    System.out.print("Insira a nova raça: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());

                    if (term.isBlank()) {
                        term = Constants.NOT_INFORMED;
                    }

                    try {
                        PetValidator.validateRace(term);
                    } catch (InvalidRaceException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                case "8" -> {
                    if (extraAnswers.isEmpty()) {
                        System.out.println("Opção inválida. Tente novamente.");
                        System.out.println();
                        continue;
                    }

                    System.out.println("Insira o índice do dado que deseja alterar: ");
                    for (int i = 7; i < questions.size(); i++) {
                        System.out.println(questions.get(i));
                    }
                    System.out.print("Índice escolhido: ");
                    int extraQuestionOption;

                    try {
                        extraQuestionOption = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Número inválido. Tente novamente.");
                        continue;
                    }

                    extraAnswerIndex = extraQuestionOption - 8;

                    System.out.println();

                    System.out.print("Insira a nova resposta: ");
                    term = StringFormatter.removeAccents(scanner.nextLine());
                    System.out.println();

                    try {
                        extraAnswers.set(extraAnswerIndex, term.isBlank() ? Constants.NOT_INFORMED : term);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Índice de pergunta inválido. Tente novamente.");
                        System.out.println();
                        continue;
                    }
                }
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }
            }

            petRepository.update(pet, alterDataOption, term, questions, extraAnswers, extraAnswerIndex);
            System.out.println();

            System.out.println("Você deseja fazer mais alguma mudança?");
            System.out.println("[1] Sim;");
            System.out.println("[2] Não.");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();

            if (option.equals("2")) {
                break;
            }

            if (!option.equals("1")) {
                System.out.println("Opção inválida.");
            }
        }
    }

    public void deletePet() throws IOException {
        List<Pet> filteredPets = listPetsByCriteria();

        if (filteredPets.isEmpty()) {
            return;
        }

        while (true) {
            int index = 1;

            for (Pet pet : filteredPets) {
                System.out.println(StringFormatter.formatPetDataForDisplay(index++, pet));
            }

            int petOption;
            Pet pet;

            System.out.print("Selecione o pet que você deseja excluir o cadastro: ");

            try {
                petOption = Integer.parseInt(scanner.nextLine());
                pet = filteredPets.get(petOption - 1);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Número inválido. Tente novamente.");
                continue;
            }

            System.out.println("ATENÇÃO! Ao excluir o pet selecionado, todos os dados serão perdidos permanentemente. Deseja confirmar a exclusão?");
            System.out.println("[1] Sim;");
            System.out.println("[2] Não.");
            System.out.print("Confirme sua escolha: ");
            String confirmationOption = scanner.nextLine();

            if (confirmationOption.equals("2")) {
                return;
            }

            if (!confirmationOption.equals("1")) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            petRepository.delete(pet);
            filteredPets.remove(petOption - 1);

            System.out.println("Cadastro deletado com sucesso.");

            System.out.println();

            if (filteredPets.isEmpty()) {
                System.out.println("Retornando ao menu principal.");
                return;
            }

            System.out.println("Você deseja excluir mais algum cadastro?");
            System.out.println("[1] Sim;");
            System.out.println("[2] Não.");
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine();
            System.out.println();

            if (option.equals("2")) {
                System.out.println("Retornando ao menu principal.");
                return;
            }

            if (!option.equals("1")) {
                System.out.println("Opção inválida.");
            }
        }

    }

    public List<Pet> listAllPets() {
        try {
            return petRepository.findAll();
        } catch (IOException e) {
            System.out.println("Pets não encontrados.");
            return new ArrayList<>();
        }
    }

    public List<Pet> listPetsByCriteria() throws IOException {
        Type typeCriteria = askTypeCriteria();
        System.out.println();

        String nameCriteria = null;
        BiologicalSex biologicalSexCriteria = null;
        String cityCriteria = null;
        String numberCriteria = null;
        String streetCriteria = null;
        Double ageCriteria = null;
        Double weightCriteria = null;
        String raceCriteria = null;

        int criteriaChosen = 0;

        while (criteriaChosen < 2) {
            System.out.println("Deseja adicionar outro critério à busca?");
            System.out.println("[1] Sim;");
            System.out.println("[2] Não.");
            System.out.print("Escolha uma opção: ");
            String wantsMore = scanner.nextLine();

            if (wantsMore.equals("2")) {
                break;
            }

            if (!wantsMore.equals("1")) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }

            System.out.println("Selecione um dos critérios abaixo:");
            System.out.println("[1] Nome;");
            System.out.println("[2] Sexo biológico;");
            System.out.println("[3] Cidade;");
            System.out.println("[4] Número;");
            System.out.println("[5] Rua;");
            System.out.println("[6] Idade;");
            System.out.println("[7] Peso;");
            System.out.println("[8] Raça.");
            System.out.print("Escolha uma opção: ");
            String criteriaOption = scanner.nextLine();
            System.out.println();

            switch (criteriaOption) {
                case "1" -> {
                    System.out.print("Digite o nome completo ou parcial: ");
                    nameCriteria = StringFormatter.removeAccents(scanner.nextLine());

                    criteriaChosen++;
                }
                case "2" -> {
                    System.out.print("Digite o sexo biológico: ");
                    try {
                        biologicalSexCriteria = BiologicalSex.matchBiologicalSex(StringFormatter.removeAccents(scanner.nextLine()));
                    } catch (InvalidBiologicalSexException e) {
                        System.out.println(e.getMessage());
                    }

                    criteriaChosen++;
                }
                case "3" -> {
                    System.out.print("Digite a cidade: ");
                    cityCriteria = StringFormatter.removeAccents(scanner.nextLine());

                    criteriaChosen++;
                }
                case "4" -> {
                    System.out.print("Digite o número: ");
                    numberCriteria = StringFormatter.removeAccents(scanner.nextLine());

                    criteriaChosen++;
                }
                case "5" -> {
                    System.out.print("Digite a rua: ");
                    streetCriteria = StringFormatter.removeAccents(scanner.nextLine());

                    criteriaChosen++;
                }
                case "6" -> {
                    System.out.print("Digite a idade: ");
                    try {
                        ageCriteria = NumberParser.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Número inválido.");
                    }

                    criteriaChosen++;
                }
                case "7" -> {
                    System.out.print("Digite o peso: ");
                    try {
                        weightCriteria = NumberParser.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Número inválido.");
                    }

                    criteriaChosen++;
                }
                case "8" -> {
                    System.out.print("Digite a raça: ");
                    raceCriteria = StringFormatter.removeAccents(scanner.nextLine());

                    criteriaChosen++;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        System.out.println();

        return filterPets(typeCriteria, nameCriteria, biologicalSexCriteria, cityCriteria, numberCriteria, streetCriteria, ageCriteria, weightCriteria, raceCriteria);
    }

    private String askName(String question) {
        String name;

        while (true) {
            System.out.print(question + " ");

            name = scanner.nextLine();
            name = StringFormatter.removeAccents(name);

            try {
                PetValidator.validateName(name);
            } catch (InvalidNameException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return name;
        }
    }

    private Type askType(String question) {
        String typeText;
        Type type;

        while (true) {
            System.out.print(question + " ");

            typeText = scanner.nextLine();
            typeText = StringFormatter.removeAccents(typeText);

            try {
                type = Type.matchType(typeText);
            } catch (InvalidTypeException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return type;
        }
    }

    private BiologicalSex askBiologicalSex(String question) {
        String biologicalSexText;
        BiologicalSex biologicalSex;

        while (true) {
            System.out.print(question + " ");

            biologicalSexText = scanner.nextLine();
            biologicalSexText = StringFormatter.removeAccents(biologicalSexText);

            try {
                biologicalSex = BiologicalSex.matchBiologicalSex(biologicalSexText);
            } catch (InvalidBiologicalSexException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return biologicalSex;
        }
    }

    private Address askAddress(String question) {
        String city;
        String street;
        String number;

        while (true) {
            System.out.println(question);

            System.out.print("4.1 – Digite a cidade: ");
            city = scanner.nextLine();
            city = StringFormatter.removeAccents(city);

            System.out.print("4.3 – Digite a rua: ");
            street = scanner.nextLine();
            street = StringFormatter.removeAccents(street);

            System.out.print("4.3 – Digite o número: ");
            number = scanner.nextLine();
            number = StringFormatter.removeAccents(number);

            if (number.isBlank()) {
                number = Constants.NOT_INFORMED;
            }

            Address address = new Address(city, street, number);

            try {
                PetValidator.validateAddress(address);
            } catch (InvalidAddressException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return address;
        }
    }

    private Double askAge(String question) {
        String ageText;
        Double age = null;

        while (true) {
            System.out.println(question);

            System.out.print("5.1 – O pet tem menos de 1 ano de idade? Digite \"1\" para \"SIM\" ou \"2\" para \"NÃO\": ");

            String option = scanner.nextLine();

            if (option.isBlank()) {
                return age;
            }

            if (option.equals("1")) {
                System.out.print("5.2 – Digite a idade aproximada, em meses, do pet: ");
                ageText = scanner.nextLine();

                if (ageText.isBlank()) {
                    return age;
                }

                try {
                    age = NumberParser.parseDouble(ageText);
                    age = age / 12;
                    PetValidator.validateAge(age);
                } catch (NumberFormatException e) {
                    System.out.println("Idade inválida. Digite somente números.");
                    continue;
                } catch (InvalidAgeException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                return age;
            }

            if (option.equals("2")) {
                System.out.print("5.2 – Digite a idade aproximada, em anos, do pet: ");
                ageText = scanner.nextLine();

                if (ageText.isBlank()) {
                    return age;
                }

                try {
                    age = NumberParser.parseDouble(ageText);
                    PetValidator.validateAge(age);
                } catch (NumberFormatException e) {
                    System.out.println("Idade inválida. Digite somente números.");
                    continue;
                } catch (InvalidAgeException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                return age;
            }

            System.out.println("Opção inválida.");
        }
    }

    private Double askWeight(String question) {
        String weightText;
        Double weight = null;

        while (true) {
            System.out.print(question + " ");

            weightText = scanner.nextLine();

            if (weightText.isBlank()) {
                return weight;
            }

            try {
                weight = NumberParser.parseDouble(weightText);
                PetValidator.validateWeight(weight);
            } catch (NumberFormatException e) {
                System.out.println("Peso inválido. Digite somente números.");
                continue;
            } catch (InvalidWeightException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return weight;
        }
    }

    private String askRace(String question) {
        String race;

        while (true) {
            System.out.print(question + " ");

            race = scanner.nextLine();
            race = StringFormatter.removeAccents(race);

            if (race.isBlank()) {
                race = Constants.NOT_INFORMED;
            }

            try {
                PetValidator.validateRace(race);
            } catch (InvalidRaceException e) {
                System.out.println(e.getMessage());
                continue;
            }

            return race;
        }
    }

    private Type askTypeCriteria() {
        while (true) {
            System.out.println("Qual tipo de pet você deseja buscar?");
            System.out.println("[1] Cachorro;");
            System.out.println("[2] Gato.");
            System.out.print("Escolha uma opção: ");

            String option = scanner.nextLine();

            if (option.equals("1")) return Type.DOG;
            if (option.equals("2")) return Type.CAT;

            System.out.println("Opção inválida.");
        }
    }

    private String askExtraQuestion(String question) {
        System.out.print(question + " ");

        String answer = StringFormatter.removeAccents(scanner.nextLine());

        return answer.isBlank() ? Constants.NOT_INFORMED : answer;
    }

    private List<Pet> filterPets(Type type, String name, BiologicalSex biologicalSex, String city, String number, String street, Double age, Double weight, String race) throws IOException {
        List<Pet> allPets = petRepository.findAll();
        List<Pet> filteredPets = new ArrayList<>();

        for (Pet pet : allPets) {
            boolean matchesType = pet.getType() == type;

            boolean matchesName = (name == null) ||
                    (pet.getName() != null &&
                            StringFormatter.removeAccents(pet.getName()).toLowerCase().contains(name.toLowerCase()));

            boolean matchesBiologicalSex = (biologicalSex == null) ||
                    (pet.getBiologicalSex() != null &&
                            pet.getBiologicalSex() == biologicalSex);

            boolean matchesCity = (city == null) ||
                    (pet.getAddress().getCity() != null &&
                            StringFormatter.removeAccents(pet.getAddress().getCity()).toLowerCase().contains(city.toLowerCase()));

            boolean matchesNumber = (number == null) ||
                    (pet.getAddress().getNumber() != null &&
                            StringFormatter.removeAccents(pet.getAddress().getNumber()).toLowerCase().contains(number.toLowerCase()));

            boolean matchesStreet = (street == null) ||
                    (pet.getAddress().getStreet() != null &&
                            StringFormatter.removeAccents(pet.getAddress().getStreet()).toLowerCase().contains(street.toLowerCase()));

            boolean matchesAge = (age == null) ||
                    (pet.getAge() != null &&
                            pet.getAge().equals(age));

            boolean matchesWeight = (weight == null) ||
                    (pet.getWeight() != null &&
                            pet.getWeight().equals(weight));

            boolean matchesRace = (race == null) ||
                    (pet.getRace() != null &&
                            StringFormatter.removeAccents(pet.getRace()).toLowerCase().contains(race.toLowerCase()));

            if (matchesType && matchesName && matchesBiologicalSex && matchesCity && matchesNumber && matchesStreet && matchesAge && matchesWeight && matchesRace) {
                filteredPets.add(pet);
            }
        }

        return filteredPets;
    }
}