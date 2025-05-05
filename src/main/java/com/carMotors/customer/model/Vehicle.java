package com.carMotors.customer.model;

import com.carMotors.customer.model.Client;

public class Vehicle {
    private int id;
    private String plate;
    private String brand;
    private String model;
    private VehicleType type;
    private Client owner;

    public enum VehicleType {
        AUTOMOBILE, SUV, MOTORBIKE
    }

    // Constructor completo
    public Vehicle(int id, String plate, String brand, String model, VehicleType type, Client owner) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.owner = owner;
    }

    // Constructor sin dueño
    public Vehicle(int id, String plate, String brand, String model, VehicleType type) {
        this(id, plate, brand, model, type, null);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type=" + type +
                ", owner=" + (owner != null ? owner.getName() : "No Owner") +
                '}';
    }

}


