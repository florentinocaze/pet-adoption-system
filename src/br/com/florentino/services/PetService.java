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

        Pet pet = new Pet(
                name,
                type,
                biologicalSex,
                address,
                age,
                weight,
                race
        );

        petRepository.save(pet);
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

                age = NumberParser.parseDouble(ageText);
                age = age / 12;

                try {
                    PetValidator.validateAge(age);
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

                age = NumberParser.parseDouble(ageText);

                try {
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

}
