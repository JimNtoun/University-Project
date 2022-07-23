package edu.acme.model;

import jdk.jshell.Snippet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student extends Basemodel{
    private String name;
    private String address;
    private String phone;
    private List<Enrollment> enrollmentList;


    public Student(String name, String address, String phone, List<Enrollment> enrollmentList) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.enrollmentList = enrollmentList;
    }

    public Student(Long id, String name, String address, String phone, List<Enrollment> enrollmentList) {
        super(id);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.enrollmentList = enrollmentList;
    }

    public Student() {

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                ", phone=" + phone +
                ", enrollmentList=" + enrollmentList +
                '}';
    }
}
