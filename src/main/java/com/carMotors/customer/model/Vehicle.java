package com.carMotors.customer.model;

public class Vehicle {
    private int id;
    private int clientId;
    private String brand;
    private String model;
    private String licensePlate;
    private String type;

    public Vehicle() {}

    public Vehicle(int id, int clientId, String brand, String model, String licensePlate, String type) {
        this.id = id;
        this.clientId = clientId;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.type = type;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
