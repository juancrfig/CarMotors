package com.carMotors.employee.model;

public class Employee {
    private int id;
    private String name;
    private long phone;
    private String speciality;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor
    public Employee(int id, String name, long phone, String speciality) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.speciality = speciality;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", name=\"" + name + "\"" +
               ", phone=" + phone +
               ", speciality=\"" + speciality + "\"" +
               "}";
    }
}

