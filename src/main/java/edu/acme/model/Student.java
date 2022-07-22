package edu.acme.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Basemodel{
    private String name;
    private String address;
    private Date dateOfBirth;
    private List<Enrollment> enrollmentList;


    public Student(String name, String address, Date dateOfBirth, List<Enrollment> enrollmentList) {
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.enrollmentList = enrollmentList;
    }

    public Student(Long id, String name, String address, Date dateOfBirth, List<Enrollment> enrollmentList) {
        super(id);
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.enrollmentList = enrollmentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", enrollmentList=" + enrollmentList +
                '}';
    }
}
