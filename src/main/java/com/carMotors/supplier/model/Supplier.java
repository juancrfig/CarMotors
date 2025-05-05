package com.carMotors.supplier.model;

public class Supplier {
    private int id;
    private String name;
    private String taxId; // NIT
    private String contact;
    private String visitFrequency;

    public Supplier() {}

    public Supplier(int id, String name, String taxId, String contact, String visitFrequency) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.contact = contact;
        this.visitFrequency = visitFrequency;
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

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVisitFrequency() {
        return visitFrequency;
    }

    public void setVisitFrequency(String visitFrequency) {
        this.visitFrequency = visitFrequency;
    }
}

