package edu.acme.model;

import java.util.List;

public class Department extends Basemodel{
    private String name;
    private List<Unit> units;

    public Department(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    public Department(Long id, String name, List<Unit> units) {
        super(id);
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", units=" + units +
                '}';
    }
}
