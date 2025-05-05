package com.carMotors.customer.controller;

import com.carMotors.customer.model.IVehicleDAO;
import com.carMotors.customer.model.Vehicle;
import java.sql.SQLException;
import java.util.List;

public class VehicleController {

    private final IVehicleDAO vehicleDAO;

    public VehicleController(IVehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO; // Recibe el DAO como parámetro
    }

    public void registerVehicle(Vehicle vehicle) throws Exception {
        validateVehicle(vehicle);
        vehicleDAO.registerVehicle(vehicle);
    }

    public void updateVehicle(Vehicle vehicle) throws Exception {
        validateVehicle(vehicle);
        vehicleDAO.updateVehicle(vehicle);
    }

    public void deleteVehicle(int vehicleId) throws SQLException {
        vehicleDAO.deleteVehicle(vehicleId);
    }

    public Vehicle findVehicleById(int id) throws SQLException {
        return vehicleDAO.findVehicleById(id);
    }

    public List<Vehicle> findVehiclesByClientId(int clientId) throws SQLException {
        return vehicleDAO.listVehiclesByClientId(clientId);
    }

    public List<Vehicle> listAllVehicles() throws SQLException {
        return vehicleDAO.listAllVehicles();
    }

    private void validateVehicle(Vehicle vehicle) throws Exception {
        if (vehicle.getBrand() == null || vehicle.getBrand().trim().isEmpty()) {
            throw new Exception("La marca del vehículo no puede estar vacía.");
        }
        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
            throw new Exception("El modelo del vehículo no puede estar vacío.");
        }
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().trim().isEmpty()) {
            throw new Exception("La placa del vehículo no puede estar vacía.");
        }
        if (vehicle.getType() == null || vehicle.getType().trim().isEmpty()) {
            throw new Exception("El tipo del vehículo no puede estar vacío.");
        }
    }
}