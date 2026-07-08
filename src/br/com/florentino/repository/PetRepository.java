package br.com.florentino.repository;

import br.com.florentino.entity.Pet;
import br.com.florentino.utils.Constants;
import br.com.florentino.utils.StringFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PetRepository {
    public void save(Pet pet) {
        File registeredPetsDirectory = new File(Constants.REGISTERED_PETS_DIRECTORY_PATH);

        if (!(registeredPetsDirectory.exists() && registeredPetsDirectory.isDirectory())) {
            registeredPetsDirectory.mkdirs();
        }

        String date = StringFormatter.formatDate();
        String name = StringFormatter.formatName(pet.getName());
        String fileName = StringFormatter.formatRegisteredPetFileName(date, name);

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
        } catch (IOException e) {
            System.out.println("Falha ao salvar o pet.");
        }
    }
}
