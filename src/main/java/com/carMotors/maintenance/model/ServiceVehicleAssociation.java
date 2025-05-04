package com.carMotors.maintenance.model;

// Represents the many-to-many relationship between Service and Vehicle
// Corresponds to the `serviceVehicle` join table
public class ServiceVehicleAssociation {
    private int idService; // Foreign key to Service
    private int idVehicle; // Foreign key to Vehicle

    // Default constructor
    public ServiceVehicleAssociation() {
    }

    // Parameterized constructor
    public ServiceVehicleAssociation(int idService, int idVehicle) {
        this.idService = idService;
        this.idVehicle = idVehicle;
    }

    // Getters and Setters
    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    @Override
    public String toString() {
        return "ServiceVehicleAssociation{" +
               "idService=" + idService +
               ", idVehicle=" + idVehicle +
               "}";
    }
}

