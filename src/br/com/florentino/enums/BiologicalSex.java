package br.com.florentino.enums;

import br.com.florentino.exceptions.InvalidBiologicalSexException;
import br.com.florentino.utils.StringFormatter;

public enum BiologicalSex {
    MALE ("Macho"),
    FEMALE ("Femea");

    private final String biologicalSex;

    BiologicalSex(String biologicalSex) {
        this.biologicalSex = biologicalSex;
    }

    public static BiologicalSex matchBiologicalSex(String biologicalSex) throws InvalidBiologicalSexException {
        if (biologicalSex == null) {
            throw new InvalidBiologicalSexException("Campo de sexo biológico não pode ser nulo.");
        }

        for (BiologicalSex petBiologicalSex : BiologicalSex.values()) {
            String formattedType = StringFormatter.removeAccents(biologicalSex);

            if (petBiologicalSex.getBiologicalSex().equalsIgnoreCase(formattedType)){
                return petBiologicalSex;
            }
        }

        throw new InvalidBiologicalSexException("Sexo biológico inválido. Digite \"macho\" ou \"fêmea\".");
    }

    public String getBiologicalSex() {
        return biologicalSex;
    }
}
