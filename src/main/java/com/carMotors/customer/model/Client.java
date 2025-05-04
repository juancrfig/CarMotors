package com.carMotors.customer.model;

public class Client {
    private int id;
    private String name;
    private long identification;
    private long phone;
    private String email;
    private String address;
    // Note: The relationship to Vehicle (via clientVehicle) will be handled
    // using collections and managed by DAOs, representing the many-to-many link.

    // Default constructor
    public Client() {
    }

    // Parameterized constructor
    public Client(int id, String name, long identification, long phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
               "id=" + id +
               ", name=\"" + name + "\"" +
               ", identification=" + identification +
               ", phone=" + phone +
               ", email=\"" + email + "\"" +
               ", address=\"" + address + "\"" +
               "}";
    }
}

