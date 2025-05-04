package com.carMotors.supplier.model;

public class Provider {
    private int id;
    private String name;
    private long identification;
    private String contact;
    private int numberVisits;

    // Default constructor
    public Provider() {
    }

    // Parameterized constructor
    public Provider(int id, String name, long identification, String contact, int numberVisits) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.contact = contact;
        this.numberVisits = numberVisits;
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

    public long getIdentification() {
        return identification;
    }

    public void setIdentification(long identification) {
        this.identification = identification;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getNumberVisits() {
        return numberVisits;
    }

    public void setNumberVisits(int numberVisits) {
        this.numberVisits = numberVisits;
    }

    @Override
    public String toString() {
        return "Provider{" +
               "id=" + id +
               ", name=\"" + name + "\"" +
               ", identification=" + identification +
               ", contact=\"" + contact + "\"" +
               ", numberVisits=" + numberVisits +
               "}";
    }
}

