package com.carMotors.supplier.model;

// Represents the many-to-many relationship between SparePart and Provider
// Corresponds to the `spareProvider` join table
public class SpareProviderAssociation {
    private int idSparePart; // Foreign key to SparePart
    private int idProvider;  // Foreign key to Provider

    // Default constructor
    public SpareProviderAssociation() {
    }

    // Parameterized constructor
    public SpareProviderAssociation(int idSparePart, int idProvider) {
        this.idSparePart = idSparePart;
        this.idProvider = idProvider;
    }

    // Getters and Setters
    public int getIdSparePart() {
        return idSparePart;
    }

    public void setIdSparePart(int idSparePart) {
        this.idSparePart = idSparePart;
    }

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    @Override
    public String toString() {
        return "SpareProviderAssociation{" +
               "idSparePart=" + idSparePart +
               ", idProvider=" + idProvider +
               "}";
    }
}

