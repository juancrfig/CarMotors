package com.carMotors.maintenance.model;

public class Service {
    private int id;
    private String type;
    private String description;
    private double laborCost;
    private int estimatedTime;

    public Service() {}

    public Service(int id, String type, String description, double laborCost, int estimatedTime) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.laborCost = laborCost;
        this.estimatedTime = estimatedTime;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(double laborCost) {
        this.laborCost = laborCost;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}