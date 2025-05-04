package com.carMotors.customer.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Vehicle Data Access Object
public interface VehicleDAO {
    void addVehicle(Vehicle vehicle) throws SQLException;
    Vehicle getVehicleById(int id) throws SQLException;
    Vehicle getVehicleByPlate(String plate) throws SQLException;
    List<Vehicle> getAllVehicles() throws SQLException;
    List<Vehicle> getVehiclesByBrand(String brand) throws SQLException;
    List<Vehicle> getVehiclesByType(Vehicle.VehicleType type) throws SQLException;
    void updateVehicle(Vehicle vehicle) throws SQLException;
    void deleteVehicle(int id) throws SQLException;
    // Methods for managing relationships (handled by ClientDAO and ServiceDAO)
}

