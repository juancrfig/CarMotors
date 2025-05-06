package com.carMotors.maintenance.controller;

import com.carMotors.maintenance.model.ServiceOrder;
import com.carMotors.maintenance.model.ServiceOrderDAO;
import com.carMotors.maintenance.model.ServiceDAO;
import com.carMotors.customer.model.IVehicleDAO;
import com.carMotors.customer.model.Vehicle;

import java.sql.SQLException;
import java.util.List;

public class ServiceOrderController {
    private ServiceOrderDAO serviceOrderDAO;
    private IVehicleDAO vehicleDAO;
    private ServiceDAO serviceDAO;

    public ServiceOrderController(ServiceOrderDAO serviceOrderDAO, IVehicleDAO vehicleDAO, ServiceDAO serviceDAO) {
        this.serviceOrderDAO = serviceOrderDAO;
        this.vehicleDAO = vehicleDAO;
        this.serviceDAO = serviceDAO;
    }

    public void addServiceOrder(ServiceOrder serviceOrder) throws SQLException {
        // Validar vehicleId
        if (!vehicleExists(serviceOrder.getVehicleId())) {
            throw new IllegalArgumentException("El ID del vehículo " + serviceOrder.getVehicleId() + " no existe.");
        }
        // Validar serviceId
        if (!serviceExists(serviceOrder.getServiceId())) {
            throw new IllegalArgumentException("El ID del servicio " + serviceOrder.getServiceId() + " no existe.");
        }
        // Validar status
        String status = serviceOrder.getStatus();
        if (status == null || !List.of("Pending", "In progress", "Completed", "Delivered").contains(status)) {
            throw new IllegalArgumentException("Estado no válido: " + status);
        }
        // Validar fechas
        if (serviceOrder.getStartDate() == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if (serviceOrder.getEndDate() != null && serviceOrder.getEndDate().before(serviceOrder.getStartDate())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        // Obtener clientId
        serviceOrder.setClientId(getClientIdByVehicleId(serviceOrder.getVehicleId()));
        // Crear la orden
        serviceOrderDAO.create(serviceOrder);
    }

    public void updateServiceOrder(ServiceOrder serviceOrder) throws SQLException {
        // Validar vehicleId
        if (!vehicleExists(serviceOrder.getVehicleId())) {
            throw new IllegalArgumentException("El ID del vehículo " + serviceOrder.getVehicleId() + " no existe.");
        }
        // Validar serviceId
        if (!serviceExists(serviceOrder.getServiceId())) {
            throw new IllegalArgumentException("El ID del servicio " + serviceOrder.getServiceId() + " no existe.");
        }
        // Validar status
        String status = serviceOrder.getStatus();
        if (status == null || !List.of("Pending", "In progress", "Completed", "Delivered").contains(status)) {
            throw new IllegalArgumentException("Estado no válido: " + status);
        }
        // Validar fechas
        if (serviceOrder.getStartDate() == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
        }
        if (serviceOrder.getEndDate() != null && serviceOrder.getEndDate().before(serviceOrder.getStartDate())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        // Validar que la orden exista
        ServiceOrder existingOrder = serviceOrderDAO.read(serviceOrder.getId());
        if (existingOrder == null) {
            throw new IllegalArgumentException("La orden con ID " + serviceOrder.getId() + " no existe.");
        }
        // Obtener clientId
        serviceOrder.setClientId(getClientIdByVehicleId(serviceOrder.getVehicleId()));
        // Actualizar la orden
        serviceOrderDAO.update(serviceOrder);
    }

    public void deleteServiceOrder(int id) throws SQLException {
        // Validar que la orden exista
        ServiceOrder existingOrder = serviceOrderDAO.read(id);
        if (existingOrder == null) {
            throw new IllegalArgumentException("La orden con ID " + id + " no existe.");
        }
        serviceOrderDAO.delete(id);
    }

    public ServiceOrder getServiceOrderById(int id) throws SQLException {
        return serviceOrderDAO.read(id);
    }

    public List<ServiceOrder> getAllServiceOrders() throws SQLException {
        return serviceOrderDAO.readAll();
    }

    public boolean vehicleExists(int vehicleId) throws SQLException {
        return vehicleDAO.findVehicleById(vehicleId) != null;
    }

    public Integer getClientIdByVehicleId(int vehicleId) throws SQLException {
        Vehicle vehicle = vehicleDAO.findVehicleById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("El vehículo con ID " + vehicleId + " no existe.");
        }
        Integer clientId = vehicle.getClientId();
        System.out.println("getClientIdByVehicleId: vehicleId=" + vehicleId + ", clientId=" + clientId); // Log para depuración
        return clientId;
    }

    public boolean serviceExists(int serviceId) throws SQLException {
        return serviceDAO.existsById(serviceId);
    }
}