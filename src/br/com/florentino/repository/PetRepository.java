package br.com.florentino.repository;

import br.com.florentino.entity.Address;
import br.com.florentino.entity.Pet;
import br.com.florentino.enums.BiologicalSex;
import br.com.florentino.enums.Type;
import br.com.florentino.utils.Constants;
import br.com.florentino.utils.NumberParser;
import br.com.florentino.utils.StringFormatter;
import br.com.florentino.validator.PetValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PetRepository {
    public void save(Pet pet) {
        File registeredPetsDirectory = new File(Constants.REGISTERED_PETS_DIRECTORY_PATH);

        if (!(registeredPetsDirectory.exists() && registeredPetsDirectory.isDirectory())) {
            registeredPetsDirectory.mkdirs();
        }

        String date = StringFormatter.formatDate();
        String name = StringFormatter.formatName(pet.getName());
        String fileName = StringFormatter.formatRegisteredPetFileName(date, name);

        pet.setFilePath(fileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            bufferedWriter.write("1 – " + pet.getName());
            bufferedWriter.newLine();

            bufferedWriter.write("2 – " + pet.getType().getTypeValue());
            bufferedWriter.newLine();

            bufferedWriter.write("3 – " + pet.getBiologicalSex().getBiologicalSexValue());
            bufferedWriter.newLine();

            bufferedWriter.write("4 – " + pet.getAddress().getStreet() + ", " + pet.getAddress().getNumber() + ", " + pet.getAddress().getCity());
            bufferedWriter.newLine();

            if (pet.getAge() == null) {
                bufferedWriter.write("5 – " + Constants.NOT_INFORMED);
                bufferedWriter.newLine();
            } else {
                bufferedWriter.write("5 – " + StringFormatter.formatNumber(pet.getAge()) + " anos");
                bufferedWriter.newLine();
            }

            if (pet.getWeight() == null) {
                bufferedWriter.write("6 – " + Constants.NOT_INFORMED);
                bufferedWriter.newLine();
            } else {
                bufferedWriter.write("6 – " + StringFormatter.formatNumber(pet.getWeight()) + " kg");
                bufferedWriter.newLine();
            }

            bufferedWriter.write("7 – " + pet.getRace());

            bufferedWriter.flush();

            System.out.println("Pet salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Falha ao salvar o pet.");
        }
    }

    public void update(Pet pet, String option, String replacement) {
        Path petFilePath = Paths.get(pet.getFilePath());

        String newLine;

        switch (option) {
            case "1" -> {
                pet.setName(replacement);

                newLine = "1 – " + pet.getName();

                editLine(petFilePath, 0, newLine);
            }
            case "2" -> {
                pet.getAddress().setCity(replacement);

                newLine = "4 – " + pet.getAddress().getStreet() + ", " + pet.getAddress().getNumber() + ", " + pet.getAddress().getCity();

                editLine(petFilePath, 3, newLine);
            }
            case "3" -> {
                if (replacement.isBlank()) {
                    pet.getAddress().setNumber(Constants.NOT_INFORMED);
                } else {
                    pet.getAddress().setNumber(replacement);
                }

                newLine = "4 – " + pet.getAddress().getStreet() + ", " + pet.getAddress().getNumber() + ", " + pet.getAddress().getCity();

                editLine(petFilePath, 3, newLine);
            }
            case "4" -> {
                pet.getAddress().setStreet(replacement);

                newLine = "4 – " + pet.getAddress().getStreet() + ", " + pet.getAddress().getNumber() + ", " + pet.getAddress().getCity();

                editLine(petFilePath, 3, newLine);
            }
            case "5" -> {
                if (replacement == null) {
                    pet.setAge(null);
                } else {
                    pet.setAge(NumberParser.parseDouble(replacement));
                }

                if (pet.getAge() == null) {
                    newLine = "5 – " + Constants.NOT_INFORMED;
                } else {
                    newLine = "5 – " + StringFormatter.formatNumber(pet.getAge()) + " anos";
                }

                editLine(petFilePath, 4, newLine);
            }
            case "6" -> {
                if (replacement == null) {
                    pet.setWeight(null);
                } else {
                    pet.setWeight(NumberParser.parseDouble(replacement));
                }

                if (pet.getWeight() == null) {
                    newLine = "6 – " + Constants.NOT_INFORMED;
                } else {
                    newLine = "6 – " + StringFormatter.formatNumber(pet.getWeight()) + " kg";
                }

                editLine(petFilePath, 5, newLine);
            }
            case "7" -> {
                pet.setRace(replacement);

                newLine = "7 – " + pet.getRace();

                editLine(petFilePath, 6, newLine);
            }
        }
    }

    public void delete(Pet pet) throws IOException {
        Path petFilePath = Paths.get(pet.getFilePath());

        Files.delete(petFilePath);
    }

    public List<Pet> findAll() throws IOException {
        String petsDirectoryPath = Constants.REGISTERED_PETS_DIRECTORY_PATH;

        File petsDirectory = new File(petsDirectoryPath);

        if (petsDirectory.exists() && petsDirectory.isDirectory()) {
            File[] pets = petsDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            List<Pet> petsList = new ArrayList<>();

            if (pets != null) {
                for (File pet : pets) {
                    List<String> lines = Files.readAllLines(Path.of(pet.getPath()));

                    // Name

                    String name = lines.get(0);

                    name = StringFormatter.formatPetData(name);

                    // Type

                    String typeText = lines.get(1);

                    typeText = StringFormatter.formatPetData(typeText);

                    Type type = Type.matchType(typeText);

                    // Biological sex

                    String biologicalSexText = lines.get(2);

                    biologicalSexText = StringFormatter.formatPetData(biologicalSexText);

                    BiologicalSex biologicalSex = BiologicalSex.matchBiologicalSex(biologicalSexText);

                    // Address

                    String fullAddress = lines.get(3);

                    fullAddress = StringFormatter.formatPetData(fullAddress);

                    String[] addresses = fullAddress.split(", ");

                    String street = addresses[0];
                    String number = addresses[1];
                    String city = addresses[2];

                    Address address = new Address(city, street, number);

                    // Age

                    Double age;

                    String ageText = lines.get(4);

                    ageText = StringFormatter.formatPetData(ageText);

                    String[] splittedAgeText = ageText.split(" anos");

                    ageText = splittedAgeText[0];

                    if (ageText.equals(Constants.NOT_INFORMED)) {
                        age = null;
                    } else {
                        age = NumberParser.parseDouble(ageText);
                    }

                    // Weight

                    Double weight;

                    String weightText = lines.get(5);

                    weightText = StringFormatter.formatPetData(weightText);

                    String[] splittedWeightText = weightText.split(" kg");

                    weightText = splittedWeightText[0];

                    if (weightText.equals(Constants.NOT_INFORMED)) {
                        weight = null;
                    } else {
                        weight = NumberParser.parseDouble(weightText);
                    }

                    // Race

                    String race = lines.get(6);
                    race = StringFormatter.formatPetData(race);

                    // New pet

                    Pet petObject = new Pet(name, type, biologicalSex, address, age, weight, race);
                    petObject.setFilePath(pet.getPath());

                    petsList.add(petObject);
                }

                return petsList;
            }
        }

        return new ArrayList<>();
    }

    private void editLine(Path petFilePath, int lineToEdit, String lineToReplace) {
        try {
            List<String> lines = Files.readAllLines(petFilePath, StandardCharsets.UTF_8);

            if (lineToEdit < lines.size()) {
                lines.set(lineToEdit, lineToReplace);
            }

            Files.write(petFilePath, lines, StandardCharsets.UTF_8);

            System.out.println("Dado alterado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao alterar dado.");
        }
    }
}
