package com.carMotors.maintenance.model;

import java.sql.Date;

public class ServiceOrder {

    private int id;
    private int vehicleId;
    private int serviceId;
    private String status;
    private Date startDate;
    private Date endDate;

    public ServiceOrder() {}

    public ServiceOrder(int id, int vehicleId, int serviceId, String status, Date startDate, Date endDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.serviceId = serviceId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // Unimplemented methods
    public void setClientId(int clientId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setMaintenanceType(String maintenanceType) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setDescription(String description) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void setLaborCost(double laborCost) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

