package br.com.florentino.validator;

import br.com.florentino.entity.Address;
import br.com.florentino.exceptions.InvalidAddressException;
import br.com.florentino.exceptions.InvalidAgeException;
import br.com.florentino.exceptions.InvalidNameException;
import br.com.florentino.exceptions.InvalidRaceException;
import br.com.florentino.exceptions.InvalidWeightException;
import br.com.florentino.utils.Constants;
import br.com.florentino.utils.StringFormatter;

public class PetValidator {
    public static void validateName(String name) throws InvalidNameException {
        String pattern = "^[A-Za-z-]+( [A-Za-z-]+)+$";

        if (!name.matches(pattern)) {
            throw new InvalidNameException("Nome inválido. Digite nome e sobrenome completos do pet, contendo somente letras de A-Z.");
        }
    }

    public static void validateRace(String race) throws InvalidRaceException {
        String pattern = "^[A-Za-z-]+( [A-Za-z-]+)*$";

        if (!race.equals(Constants.NOT_INFORMED) && !race.matches(pattern)) {
            throw new InvalidRaceException("Raça inválida. Digite somente letras de A-Z.");
        }
    }

    public static void validateAge(Double age) throws InvalidAgeException {
        if (age <= Constants.MINIMUM_AGE || age > Constants.MAXIMUM_AGE) {
            throw new InvalidAgeException("Idade não pode ser menor ou igual a " + StringFormatter.formatNumber(Constants.MINIMUM_AGE) + " ou maior que " + StringFormatter.formatNumber(Constants.MAXIMUM_AGE) + ".");
        }
    }

    public static void validateWeight(Double weight) throws InvalidWeightException {
        if (weight < Constants.MINIMUM_WEIGHT || weight > Constants.MAXIMUM_WEIGHT) {
            throw new InvalidWeightException("Peso não pode ser menor que " + StringFormatter.formatNumber(Constants.MINIMUM_WEIGHT) + " kg ou maior que " + StringFormatter.formatNumber(Constants.MAXIMUM_WEIGHT) + " kg.");
        }
    }

    public static void validateAddress(Address address) throws InvalidAddressException {
        String patternA = "^[A-Za-z]+( [A-Za-z]+)*$";
        String patternB = "^[0-9A-Za-z-]+$";

        if (!address.getCity().matches(patternA)) {
            throw new InvalidAddressException("Cidade inválida. Digite somente letras de A-Z.");
        }

        if (!address.getStreet().matches(patternA)) {
            throw new InvalidAddressException("Rua inválida. Digite somente letras de A-Z.");
        }

        if (!address.getNumber().equals(Constants.NOT_INFORMED) && !address.getNumber().matches(patternB)) {
            throw new InvalidAddressException("Número inválido. Digite somente letras de A-Z, números de 0-9, com ou sem hífen.");
        }
    }

}
