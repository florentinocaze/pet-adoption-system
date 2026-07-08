package br.com.florentino.entity;

import br.com.florentino.enums.BiologicalSex;
import br.com.florentino.enums.Type;

public class Pet {
    private String name;
    private final Type type;
    private final BiologicalSex biologicalSex;
    private final Address address;
    private Double age;
    private Double weight;
    private String race;

    public Pet(String name, Type type, BiologicalSex biologicalSex, Address address, Double age, Double weight, String race) {
        this.name = name;
        this.type = type;
        this.biologicalSex = biologicalSex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public BiologicalSex getBiologicalSex() {
        return biologicalSex;
    }

    public Address getAddress() {
        return address;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
