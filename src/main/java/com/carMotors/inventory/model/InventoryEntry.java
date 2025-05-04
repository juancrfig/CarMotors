package com.carMotors.inventory.model;

import java.util.Date;

// Represents an entry in the inventory, linking a spare part to a package
// Corresponds to the `inventory` table
public class InventoryEntry {
    private int idSparePart;    // Foreign key to SparePart
    private int idPackage;      // Foreign key to Package
    private int initialQuantity;
    private int currentQuantity;
    private Date entryDate;
    private Date expirationDate;

    // Default constructor
    public InventoryEntry() {
    }

    // Parameterized constructor
    public InventoryEntry(int idSparePart, int idPackage, int initialQuantity, int currentQuantity, Date entryDate, Date expirationDate) {
        this.idSparePart = idSparePart;
        this.idPackage = idPackage;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public int getIdSparePart() {
        return idSparePart;
    }

    public void setIdSparePart(int idSparePart) {
        this.idSparePart = idSparePart;
    }

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "InventoryEntry{" +
               "idSparePart=" + idSparePart +
               ", idPackage=" + idPackage +
               ", initialQuantity=" + initialQuantity +
               ", currentQuantity=" + currentQuantity +
               ", entryDate=" + entryDate +
               ", expirationDate=" + expirationDate +
               "}";
    }
}

