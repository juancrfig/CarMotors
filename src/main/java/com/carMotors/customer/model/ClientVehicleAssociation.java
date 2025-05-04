package com.carMotors.customer.model;

// Represents the many-to-many relationship between Client and Vehicle
// Corresponds to the `clientVehicle` join table
public class ClientVehicleAssociation {
    private int idClient;  // Foreign key to Client
    private int idVehicle; // Foreign key to Vehicle

    // Default constructor
    public ClientVehicleAssociation() {
    }

    // Parameterized constructor
    public ClientVehicleAssociation(int idClient, int idVehicle) {
        this.idClient = idClient;
        this.idVehicle = idVehicle;
    }

    // Getters and Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }

    @Override
    public String toString() {
        return "ClientVehicleAssociation{" +
               "idClient=" + idClient +
               ", idVehicle=" + idVehicle +
               "}";
    }
}

