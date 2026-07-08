package br.com.florentino.enums;

import br.com.florentino.exceptions.InvalidBiologicalSexException;
import br.com.florentino.utils.StringFormatter;

public enum BiologicalSex {
    MALE ("Macho"),
    FEMALE ("Femea");

    private final String biologicalSexValue;

    BiologicalSex(String biologicalSex) {
        this.biologicalSexValue = biologicalSex;
    }

    public static BiologicalSex matchBiologicalSex(String biologicalSex) throws InvalidBiologicalSexException {
        if (biologicalSex == null) {
            throw new InvalidBiologicalSexException("Campo de sexo biológico não pode ser nulo.");
        }

        for (BiologicalSex petBiologicalSex : BiologicalSex.values()) {
            String formattedType = StringFormatter.removeAccents(biologicalSex);

            if (petBiologicalSex.getBiologicalSexValue().equalsIgnoreCase(formattedType)){
                return petBiologicalSex;
            }
        }

        throw new InvalidBiologicalSexException("Sexo biológico inválido. Digite \"macho\" ou \"fêmea\".");
    }

    public String getBiologicalSexValue() {
        return biologicalSexValue;
    }
}
