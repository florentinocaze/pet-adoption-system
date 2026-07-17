package br.com.florentino.utils;

import br.com.florentino.entity.Pet;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class StringFormatter {
    public static String formatDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.FILE_DATE_FORMAT);
        return now.format(dateTimeFormatter);
    }

    public static String formatRegisteredPetFileName(String date, String name) {
        name = StringFormatter.removeAccents(name);
        name = name.replace("-", "");
        return Constants.REGISTERED_PETS_DIRECTORY_PATH + "/" + date + "-" + name + ".txt";
    }

    public static String formatPetData(String data) {
        String[] splittedData = data.split(" – ");
        return splittedData[1];
    }

    public static String formatPetExtraData(String data) {
        String[] splittedData = data.split(" – ");
        return splittedData[2];
    }

    public static String formatPetDataForDisplay(int index, Pet pet) {
        StringBuilder petData =
                new StringBuilder(
                        "[" + index + "] " +
                        pet.getName() + " – " +
                        pet.getType().getTypeValue() + " – " +
                        pet.getBiologicalSex().getBiologicalSexValue() + " – " +
                        pet.getAddress().getStreet() + ", " +
                        pet.getAddress().getNumber() + " – " +
                        pet.getAddress().getCity() + " – " +
                        (pet.getAge() == null ? Constants.NOT_INFORMED : (StringFormatter.formatNumber(pet.getAge()) + " anos")) + " – " +
                        (pet.getWeight() == null ? Constants.NOT_INFORMED : (StringFormatter.formatNumber(pet.getWeight()) + " kg")) + " – " +
                        pet.getRace()
                );

        if (!pet.getExtraAnswers().isEmpty()) {
            for (String extraAnswer : pet.getExtraAnswers()) {
                petData.append(" – ").append(extraAnswer);
            }
        }

        return petData.toString();
    }

    public static String formatName(String name) {
        return name.toUpperCase().replace(" ", "");
    }

    public static String formatNumber(double value) {
        DecimalFormat formatter = new DecimalFormat("0.##");

        return formatter.format(value);
    }

    public static String removeAccents(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        text = pattern.matcher(text).replaceAll("");

        return text;
    }
}
