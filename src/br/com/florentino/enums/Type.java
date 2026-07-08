package br.com.florentino.enums;

import br.com.florentino.exceptions.InvalidTypeException;
import br.com.florentino.utils.StringFormatter;

public enum Type {
    CAT("Gato"),
    DOG("Cachorro");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public static Type matchType(String type) throws InvalidTypeException {
        if (type == null) {
            throw new InvalidTypeException("Campo de tipo não pode ser nulo.");
        }

        for (Type petType : Type.values()) {
            String formattedType = StringFormatter.removeAccents(type);

            if (petType.getType().equalsIgnoreCase(formattedType)){
                return petType;
            }
        }

        throw new InvalidTypeException("Tipo inválido.");
    }

    public String getType() {
        return this.type;
    }
}
