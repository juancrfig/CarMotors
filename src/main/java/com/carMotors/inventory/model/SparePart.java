package com.carMotors.inventory.model;

public class SparePart {
    private int id;
    private String name;
    private SparePartType type;
    private String brand;
    private String model;
    private double cost;
    private int lifeSpan; // Assuming this is in days or a relevant unit
    private SparePartState state;

    public enum SparePartType {
        MECHANIC, ELECTRIC, CARBODY, CONSUMABLE
    }

    public enum SparePartState {
        AVAILABLE, RESERVED, OUTOFSERVICE
    }

    // Default constructor
    public SparePart() {
    }

    // Parameterized constructor
    public SparePart(int id, String name, SparePartType type, String brand, String model, double cost, int lifeSpan, SparePartState state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
        this.lifeSpan = lifeSpan;
        this.state = state;
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

    public SparePartType getType() {
        return type;
    }

    public void setType(SparePartType type) {
        this.type = type;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public SparePartState getState() {
        return state;
    }

    public void setState(SparePartState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SparePart{" +
               "id=" + id +
               ", name=\"" + name + "\"" +
               ", type=" + type +
               ", brand=\"" + brand + "\"" +
               ", model=\"" + model + "\"" +
               ", cost=" + cost +
               ", lifeSpan=" + lifeSpan +
               ", state=" + state +
               "}";
    }
}

