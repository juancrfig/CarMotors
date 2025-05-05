package com.carMotors.customer.model;

import java.util.*;

public class Client {
    private int id;
    private String name;
    private long identification;
    private long phone;
    private String email;
    private String address;
    private List<Vehicle> vehicles;

    // Parameterized constructor
    public Client(int id, String name, long identification, long phone, String email, String address, List<Vehicle> vehicles) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();

        // Establece la relación bidireccional
        for (Vehicle vehicle : this.vehicles) {
            vehicle.setOwner(this);
        }
    }

    // Constructor sin vehículos
    public Client(int id, String name, long identification, long phone, String email, String address) {
        this(id, name, identification, phone, email, address, new ArrayList<>());
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

    public long getIdentification() {
        return identification;
    }

    public void setIdentification(long identification) {
        this.identification = identification;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    // Agregar vehículo y establecer relación bidireccional
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setOwner(this);
            this.vehicles.add(vehicle);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client{")
                .append("id=").append(id)
                .append(", name=\"").append(name).append("\"")
                .append(", identification=").append(identification)
                .append(", phone=").append(phone)
                .append(", email=\"").append(email).append("\"")
                .append(", address=\"").append(address).append("\"");

        if (vehicles != null && !vehicles.isEmpty()) {
            sb.append(", vehicles=[");
            for (int i = 0; i < vehicles.size(); i++) {
                sb.append(vehicles.get(i).getPlate());
                if (i < vehicles.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        } else {
            sb.append(", vehicles=[]");
        }

        sb.append("}");
        return sb.toString();
    }

}

