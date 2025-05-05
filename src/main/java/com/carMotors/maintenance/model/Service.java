package com.carMotors.maintenance.model;

public class Service {
    private int id;
    private ServiceType type;
    private String description;
    private int estimatedTime; // in minutes or hours?
    private int actualTime;    // in minutes or hours?
    private ServiceState stateService;

    public enum ServiceType {
        PREVENTIVE, CORRECTIVE
    }

    public enum ServiceState {
        PENDING, UNDERWAY, FINISHED
    }

    // Default constructor
    public Service() {
    }

    // Parameterized constructor
    public Service(int id, ServiceType type, String description, int estimatedTime, int actualTime, double cost, int discount, ServiceState stateService, int idEmployee, int idClient) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.stateService = stateService;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
    }

    public ServiceState getStateService() {
        return stateService;
    }

    public void setStateService(ServiceState stateService) {
        this.stateService = stateService;
    }

    @Override
    public String toString() {
        return "Service{" +
               "id=" + id +
               ", type=" + type +
               ", description=\"" + description + "\"" +
               ", estimatedTime=" + estimatedTime +
               ", actualTime=" + actualTime +
               ", stateService=" + stateService +
               "}";
    }
}

