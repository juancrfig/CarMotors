package com.carMotors.maintenance.controller;

import com.carMotors.maintenance.model.Service;
import com.carMotors.maintenance.model.ServiceDAO;

import java.sql.SQLException;
import java.util.List;

public class ServiceController {
    private ServiceDAO serviceDAO;

    public ServiceController(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public void addService(Service service) throws SQLException {
        // Validar type
        String type = service.getType();
        if (type == null || !List.of("Preventive", "Corrective").contains(type)) {
            throw new IllegalArgumentException("Tipo de servicio no válido: " + type);
        }
        // Validar laborCost
        if (service.getLaborCost() < 0) {
            throw new IllegalArgumentException("El costo de mano de obra no puede ser negativo.");
        }
        // Validar estimatedTime
        if (service.getEstimatedTime() < 0) {
            throw new IllegalArgumentException("El tiempo estimado no puede ser negativo.");
        }
        // Validar description
        if (service.getDescription() == null || service.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        serviceDAO.create(service);
    }

    public void updateService(Service service) throws SQLException {
        // Validar type
        String type = service.getType();
        if (type == null || !List.of("Preventive", "Corrective").contains(type)) {
            throw new IllegalArgumentException("Tipo de servicio no válido: " + type);
        }
        // Validar laborCost
        if (service.getLaborCost() < 0) {
            throw new IllegalArgumentException("El costo de mano de obra no puede ser negativo.");
        }
        // Validar estimatedTime
        if (service.getEstimatedTime() < 0) {
            throw new IllegalArgumentException("El tiempo estimado no puede ser negativo.");
        }
        // Validar description
        if (service.getDescription() == null || service.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        // Validar que el servicio exista
        Service existingService = serviceDAO.read(service.getId());
        if (existingService == null) {
            throw new IllegalArgumentException("El servicio con ID " + service.getId() + " no existe.");
        }
        serviceDAO.update(service);
    }

    public void deleteService(int id) throws SQLException {
        // Validar que el servicio exista
        Service existingService = serviceDAO.read(id);
        if (existingService == null) {
            throw new IllegalArgumentException("El servicio con ID " + id + " no existe.");
        }
        serviceDAO.delete(id);
    }

    public Service getServiceById(int id) throws SQLException {
        return serviceDAO.read(id);
    }

    public List<Service> getAllServices() throws SQLException {
        return serviceDAO.readAll();
    }

    public boolean serviceExists(int id) throws SQLException {
        return serviceDAO.existsById(id);
    }
}