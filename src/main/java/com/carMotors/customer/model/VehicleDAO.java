package com.carMotors.customer.model;

import com.carMotors.core.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO implements IVehicleDAO {

    @Override
    public void registerVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicles (client_id, brand, model, license_plate, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, vehicle.getClientId());
            pstmt.setString(2, vehicle.getBrand());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getLicensePlate());
            pstmt.setString(5, vehicle.getType());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    vehicle.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE Vehicles SET client_id = ?, brand = ?, model = ?, license_plate = ?, type = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicle.getClientId());
            pstmt.setString(2, vehicle.getBrand());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getLicensePlate());
            pstmt.setString(5, vehicle.getType());
            pstmt.setInt(6, vehicle.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteVehicle(int vehicleId) throws SQLException {
        String sql = "DELETE FROM Vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Vehicle findVehicleById(int id) throws SQLException {
        String sql = "SELECT * FROM Vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("vehicle_id"));
                    vehicle.setClientId(rs.getInt("client_id"));
                    vehicle.setBrand(rs.getString("brand"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setLicensePlate(rs.getString("license_plate"));
                    vehicle.setType(rs.getString("type"));
                    return vehicle;
                }
            }
        }
        return null;
    }

    @Override
    public List<Vehicle> listVehiclesByClientId(int clientId) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles WHERE client_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("vehicle_id"));
                    vehicle.setClientId(rs.getInt("client_id"));
                    vehicle.setBrand(rs.getString("brand"));
                    vehicle.setModel(rs.getString("model"));
                    vehicle.setLicensePlate(rs.getString("license_plate"));
                    vehicle.setType(rs.getString("type"));
                    vehicles.add(vehicle);
                }
            }
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> listAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("vehicle_id"));
                vehicle.setClientId(rs.getInt("client_id"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setType(rs.getString("type"));
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }
}