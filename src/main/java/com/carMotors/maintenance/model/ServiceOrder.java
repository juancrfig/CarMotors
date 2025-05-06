package com.carMotors.maintenance.model;

import java.sql.Date;

public class ServiceOrder {
    private int id;
    private int vehicleId;
    private int serviceId;
    private String status;
    private Date startDate;
    private Date endDate;
    private Integer clientId; // Opcional, derivado de Vehicles
    private Service service; // Referencia al servicio asociado

    public ServiceOrder() {}

    public ServiceOrder(int id, int vehicleId, int serviceId, String status, Date startDate, Date endDate,
                        Integer clientId, Service service) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.serviceId = serviceId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clientId = clientId;
        this.service = service;
    }

    // Getters y setters
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        if (service != null && service.getId() != 0) {
            this.serviceId = service.getId();
        }
    }

    // Métodos delegados para propiedades del servicio
    public String getMaintenanceType() {
        return service != null ? service.getType() : null;
    }

    public void setMaintenanceType(String type) {
        if (service == null) {
            service = new Service();
        }
        service.setType(type);
    }

    public String getDescription() {
        return service != null ? service.getDescription() : null;
    }

    public void setDescription(String description) {
        if (service == null) {
            service = new Service();
        }
        service.setDescription(description);
    }

    public Double getLaborCost() {
        return service != null ? service.getLaborCost() : null;
    }

    public void setLaborCost(Double laborCost) {
        if (service == null) {
            service = new Service();
        }
        service.setLaborCost(laborCost != null ? laborCost : 0.0);
    }

    public Integer getEstimatedTime() {
        return service != null ? service.getEstimatedTime() : null;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        if (service == null) {
            service = new Service();
        }
        service.setEstimatedTime(estimatedTime != null ? estimatedTime : 0);
    }
}