package com.carMotors.billing.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
    private int id;
    private int idService; // Foreign key to Service
    private Date issuance;
    private String cufe; // Unique Electronic Invoice Code (Colombia)
    private String url;  // URL for the electronic invoice (e.g., QR code link)
    private BigDecimal taxes; // Using BigDecimal for precise currency values

    // Default constructor
    public Bill() {
    }

    // Parameterized constructor
    public Bill(int id, int idService, Date issuance, String cufe, String url, BigDecimal taxes) {
        this.id = id;
        this.idService = idService;
        this.issuance = issuance;
        this.cufe = cufe;
        this.url = url;
        this.taxes = taxes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
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

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
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

