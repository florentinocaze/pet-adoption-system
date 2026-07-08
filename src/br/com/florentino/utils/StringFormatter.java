package br.com.florentino.utils;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringFormatter {
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
