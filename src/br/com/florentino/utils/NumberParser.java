package br.com.florentino.utils;

public class NumberParser {
    public static Double parseDouble(String numberText) {
        numberText = numberText.replace(",", ".");
        return Double.parseDouble(numberText);
    }
}
