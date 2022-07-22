package edu.acme.model;

public class Unit extends Basemodel{
    private String name;

    public Unit(String name) {
        this.name = name;
    }

    public Unit(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                '}';
    }
}
