package com.carMotors.billing.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
    private int id;
    private Date issuance;
    private String cufe; // Unique Electronic Invoice Code (Colombia)
    private String url;  // URL for the electronic invoice (e.g., QR code link)
    private int discount;
    private BigDecimal taxes; // Using BigDecimal for precise currency values
    private double cost;
    private int idClient; // Foreign key to client
    private int idService; // Foreign key to Service

    // Default constructor
    public Bill() {
    }

    // Parameterized constructor
    public Bill(int id, Date issuance, String cufe, String url, int discount, BigDecimal taxes, double cost, int idClient, int idService) {
        this.id = id;
        this.issuance = issuance;
        this.cufe = cufe;
        this.url = url;
        this.discount = discount;
        this.taxes = taxes;
        this.cost = cost;
        this.idClient = idClient;
        this.idService = idService;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssuance() {
        return issuance;
    }

    public void setIssuance(Date issuance) {
        this.issuance = issuance;
    }

    public String getCufe() {
        return cufe;
    }

    public void setCufe(String cufe) {
        this.cufe = cufe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Bill{" +
               "id=" + id +
               ", idService=" + idService +
               ", issuance=" + issuance +
               ", cufe=\"" + cufe + "\"" +
               ", url=\"" + url + "\"" +
               ", taxes=" + taxes +
               "}";
    }
}

