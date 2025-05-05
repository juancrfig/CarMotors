package com.carMotors.customer.model;

import com.carMotors.customer.model.Vehicle;
import java.sql.SQLException;
import java.util.List;

public interface IVehicleDAO {
    void registerVehicle(Vehicle vehicle) throws SQLException;
    List<Vehicle> listAllVehicles() throws SQLException;
    List<Vehicle> listVehiclesByClientId(int clientId) throws SQLException;
    Vehicle findVehicleById(int id) throws SQLException;
    void updateVehicle(Vehicle vehicle) throws SQLException;
    void deleteVehicle(int id) throws SQLException;
}
