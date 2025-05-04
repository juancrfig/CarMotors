package com.carMotors.customer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// Mock implementation of VehicleDAO using in-memory data structures
public class MockVehicleDAO implements VehicleDAO {

    private final Map<Integer, Vehicle> vehicles = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public MockVehicleDAO() {
        // Add some initial mock data
        try {
            addVehicle(new Vehicle(0, "Toyota", "Camry", "ABC-123", Vehicle.VehicleType.AUTOMOBILE));
            addVehicle(new Vehicle(0, "Honda", "CRV", "XYZ-789", Vehicle.VehicleType.SUV));
            addVehicle(new Vehicle(0, "Yamaha", "MT-07", "MOTO-1", Vehicle.VehicleType.MOTORBIKE));
        } catch (SQLException e) {
            System.err.println("Error initializing mock vehicle data: " + e.getMessage());
        }
    }

    @Override
    public synchronized void addVehicle(Vehicle vehicle) throws SQLException {
        if (vehicle.getId() == 0) {
            vehicle.setId(idCounter.incrementAndGet());
        }
        if (vehicles.containsKey(vehicle.getId())) {
            throw new SQLException("Vehicle with ID " + vehicle.getId() + " already exists.");
        }
        // Check for duplicate plate (optional)
        if (getVehicleByPlate(vehicle.getPlate()) != null) {
            throw new SQLException("Vehicle with plate " + vehicle.getPlate() + " already exists.");
        }
        vehicles.put(vehicle.getId(), vehicle);
        System.out.println("MockDAO: Added vehicle: " + vehicle);
    }

    @Override
    public synchronized Vehicle getVehicleById(int id) throws SQLException {
        return vehicles.get(id);
    }

    @Override
    public synchronized Vehicle getVehicleByPlate(String plate) throws SQLException {
        return vehicles.values().stream()
                .filter(v -> v.getPlate().equalsIgnoreCase(plate))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Vehicle> getAllVehicles() throws SQLException {
        return new ArrayList<>(vehicles.values());
    }

    @Override
    public synchronized List<Vehicle> getVehiclesByBrand(String brand) throws SQLException {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.getBrand().equalsIgnoreCase(brand)) {
                result.add(v);
            }
        }
        return result;
    }

    @Override
    public synchronized List<Vehicle> getVehiclesByType(Vehicle.VehicleType type) throws SQLException {
         List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.getType() == type) {
                result.add(v);
            }
        }
        return result;
    }

    @Override
    public synchronized void updateVehicle(Vehicle vehicle) throws SQLException {
        if (!vehicles.containsKey(vehicle.getId())) {
            throw new SQLException("Vehicle with ID " + vehicle.getId() + " not found for update.");
        }
        // Check if plate is being changed to one that already exists (excluding self)
        Vehicle existingByPlate = getVehicleByPlate(vehicle.getPlate());
        if (existingByPlate != null && existingByPlate.getId() != vehicle.getId()) {
            throw new SQLException("Cannot update vehicle. Plate " + vehicle.getPlate() + " already used by another vehicle.");
        }
        vehicles.put(vehicle.getId(), vehicle);
        System.out.println("MockDAO: Updated vehicle: " + vehicle);
    }

    @Override
    public synchronized void deleteVehicle(int id) throws SQLException {
        if (vehicles.remove(id) == null) {
            throw new SQLException("Vehicle with ID " + id + " not found for deletion.");
        }
        // In a real scenario, might need to handle cascading deletes or checks in join tables
        System.out.println("MockDAO: Deleted vehicle with ID: " + id);
    }
}

