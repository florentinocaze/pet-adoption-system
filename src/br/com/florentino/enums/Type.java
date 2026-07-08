package br.com.florentino.enums;

import br.com.florentino.exceptions.InvalidTypeException;
import br.com.florentino.utils.StringFormatter;

public enum Type {
    CAT("Gato"),
    DOG("Cachorro");

    private final String typeValue;

    Type(String type) {
        this.typeValue = type;
    }

    public static Type matchType(String type) throws InvalidTypeException {
        if (type == null) {
            throw new InvalidTypeException("Campo de tipo não pode ser nulo.");
        }

        for (Type petType : Type.values()) {
            String formattedType = StringFormatter.removeAccents(type);

            if (petType.getTypeValue().equalsIgnoreCase(formattedType)){
                return petType;
            }
        }

        throw new InvalidTypeException("Tipo inválido.");
    }

    public String getTypeValue() {
        return this.typeValue;
    }
}
