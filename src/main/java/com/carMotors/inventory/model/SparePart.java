package com.carMotors.inventory.model;

public class SparePart {
    private int id;
    private String name;
    private String type;
    private String brand;
    private String model;
    private int supplierId;
    private int stockQuantity;
    private int minimumStockLevel;
    private String entryDate;
    private int usefulLifeDays;
    private String status;

    public SparePart() {}

    public SparePart(int id, String name, String type, String brand, String model,
                     int supplierId, int stockQuantity, int minimumStockLevel,
                     String entryDate, int usefulLifeDays, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.supplierId = supplierId;
        this.stockQuantity = stockQuantity;
        this.minimumStockLevel = minimumStockLevel;
        this.entryDate = entryDate;
        this.usefulLifeDays = usefulLifeDays;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public int getUsefulLifeDays() {
        return usefulLifeDays;
    }

    public void setUsefulLifeDays(int usefulLifeDays) {
        this.usefulLifeDays = usefulLifeDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

