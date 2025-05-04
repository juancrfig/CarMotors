package com.carMotors.customer.model; // Or potentially a shared vehicle model package

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String plate;
    private VehicleType type;
    // Note: The relationship to Client (via clientVehicle) and Service (via serviceVehicle)
    // will be handled using collections and managed by DAOs.

    public enum VehicleType {
        AUTOMOBILE, SUV, MOTORBIKE
    }

    // Default constructor
    public Vehicle() {
    }

    // Parameterized constructor
    public Vehicle(int id, String brand, String model, String plate, VehicleType type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
               "id=" + id +
               ", brand=\"" + brand + "\"" +
               ", model=\"" + model + "\"" +
               ", plate=\"" + plate + "\"" +
               ", type=" + type +
               "}";
    }
}

