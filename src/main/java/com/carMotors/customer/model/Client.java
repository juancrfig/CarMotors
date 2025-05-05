package com.carMotors.customer.model;

import com.carMotors.maintenance.model.ServiceOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String identification;
    private String phone;
    private String email;
    private double discountPercentage;
    private int rewardPoints;
    private LocalDate purchaseDate;
    private List<ServiceOrder> serviceHistory;

    // Empty constructor
    public Client() {
        this.serviceHistory = new ArrayList<>();
    }

    // Full constructor
    public Client(int id, String name, String identification, String phone, String email,
                  double discountPercentage, int rewardPoints) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.discountPercentage = discountPercentage;
        this.rewardPoints = rewardPoints;
        this.serviceHistory = new ArrayList<>();
    }

    // Compatibility constructor for InvoiceDAO
    public Client(int id, String name, String identification, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.discountPercentage = 0.0;
        this.rewardPoints = 0;
        this.serviceHistory = new ArrayList<>();
    }

    // Getters and setters
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

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<ServiceOrder> getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(List<ServiceOrder> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

}
