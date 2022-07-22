package edu.acme.model;

public class Enrollment extends Basemodel{
    private double grade;
    private Unit unit;

    public Enrollment() {
    }

    public Enrollment(Long id, double grade) {
        super(id);
        this.grade = grade;
    }

    public Enrollment(double grade, Unit unit) {
        this.grade = grade;
        this.unit = unit;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "grade=" + grade +
                ", unit=" + unit +
                '}';
    }
}
