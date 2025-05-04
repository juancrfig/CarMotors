package com.carMotors.inventory.model;

public class Package {
    private int id;
    private int idProvider; // Foreign key to Provider
    private int idOrder;    // Foreign key to Order

    // Default constructor
    public Package() {
    }

    // Parameterized constructor
    public Package(int id, int idProvider, int idOrder) {
        this.id = id;
        this.idProvider = idProvider;
        this.idOrder = idOrder;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public String toString() {
        return "Package{" +
               "id=" + id +
               ", idProvider=" + idProvider +
               ", idOrder=" + idOrder +
               "}";
    }
}

