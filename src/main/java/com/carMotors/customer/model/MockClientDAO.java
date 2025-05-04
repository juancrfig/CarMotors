package com.carMotors.customer.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// Mock implementation of ClientDAO using in-memory data structures
public class MockClientDAO implements ClientDAO {

    private final Map<Integer, Client> clients = new HashMap<>();
    private final Map<Integer, List<Integer>> clientVehicles = new HashMap<>(); // Client ID -> List of Vehicle IDs
    private final AtomicInteger idCounter = new AtomicInteger(0);
    // We might need a mock VehicleDAO instance later if we need vehicle details
    // private VehicleDAO mockVehicleDAO = new MockVehicleDAO();

    public MockClientDAO() {
        // Add some initial mock data
        try {
            addClient(new Client(0, "John Doe", 123456789, 5551111, "john.doe@email.com", "123 Main St"));
            addClient(new Client(0, "Jane Smith", 987654321, 5552222, "jane.smith@email.com", "456 Oak Ave"));
        } catch (SQLException e) {
            // This shouldn't happen with the mock implementation
            System.err.println("Error initializing mock client data: " + e.getMessage());
        }
    }

    @Override
    public synchronized void addClient(Client client) throws SQLException {
        if (client.getId() == 0) { // Assign new ID if not set
            client.setId(idCounter.incrementAndGet());
        }
        if (clients.containsKey(client.getId())) {
            throw new SQLException("Client with ID " + client.getId() + " already exists.");
        }
        // Check for duplicate identification (optional, but good practice)
        if (getClientByIdentification(client.getIdentification()) != null) {
             throw new SQLException("Client with identification " + client.getIdentification() + " already exists.");
        }
        clients.put(client.getId(), client);
        clientVehicles.putIfAbsent(client.getId(), new ArrayList<>());
        System.out.println("MockDAO: Added client: " + client);
    }

    @Override
    public synchronized Client getClientById(int id) throws SQLException {
        return clients.get(id);
    }

    @Override
    public synchronized Client getClientByIdentification(long identification) throws SQLException {
        return clients.values().stream()
                .filter(c -> c.getIdentification() == identification)
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Client> getAllClients() throws SQLException {
        return new ArrayList<>(clients.values());
    }

    @Override
    public synchronized void updateClient(Client client) throws SQLException {
        if (!clients.containsKey(client.getId())) {
            throw new SQLException("Client with ID " + client.getId() + " not found for update.");
        }
        // Check if identification is being changed to one that already exists (excluding self)
        Client existingByIdentification = getClientByIdentification(client.getIdentification());
        if (existingByIdentification != null && existingByIdentification.getId() != client.getId()) {
            throw new SQLException("Cannot update client. Identification " + client.getIdentification() + " already used by another client.");
        }
        clients.put(client.getId(), client);
        System.out.println("MockDAO: Updated client: " + client);
    }

    @Override
    public synchronized void deleteClient(int id) throws SQLException {
        if (clients.remove(id) == null) {
            throw new SQLException("Client with ID " + id + " not found for deletion.");
        }
        clientVehicles.remove(id); // Also remove vehicle associations
        System.out.println("MockDAO: Deleted client with ID: " + id);
    }

    @Override
    public synchronized void addVehicleToClient(int clientId, int vehicleId) throws SQLException {
        if (!clients.containsKey(clientId)) {
            throw new SQLException("Client with ID " + clientId + " not found.");
        }
        // In a real scenario, check if vehicleId exists in VehicleDAO
        List<Integer> vehicles = clientVehicles.computeIfAbsent(clientId, k -> new ArrayList<>());
        if (!vehicles.contains(vehicleId)) {
            vehicles.add(vehicleId);
            System.out.println("MockDAO: Added vehicle " + vehicleId + " to client " + clientId);
        }
    }

    @Override
    public synchronized void removeVehicleFromClient(int clientId, int vehicleId) throws SQLException {
        if (!clients.containsKey(clientId)) {
            throw new SQLException("Client with ID " + clientId + " not found.");
        }
        List<Integer> vehicles = clientVehicles.get(clientId);
        if (vehicles != null) {
            if (vehicles.remove(Integer.valueOf(vehicleId))) {
                 System.out.println("MockDAO: Removed vehicle " + vehicleId + " from client " + clientId);
            } else {
                 System.out.println("MockDAO: Vehicle " + vehicleId + " not found for client " + clientId);
            }
        }
    }

    @Override
    public synchronized List<Vehicle> getVehiclesForClient(int clientId) throws SQLException {
        if (!clients.containsKey(clientId)) {
            throw new SQLException("Client with ID " + clientId + " not found.");
        }
        List<Integer> vehicleIds = clientVehicles.getOrDefault(clientId, new ArrayList<>());
        
        // This part requires a VehicleDAO to get actual Vehicle objects.
        // For now, returning an empty list or list of dummy vehicles.
        // Example with dummy vehicles (replace with actual VehicleDAO call later):
        List<Vehicle> vehicles = new ArrayList<>();
        // MockVehicleDAO mockVehicleDAO = new MockVehicleDAO(); // Assuming this exists
        // for (int vehicleId : vehicleIds) {
        //     Vehicle v = mockVehicleDAO.getVehicleById(vehicleId);
        //     if (v != null) vehicles.add(v);
        // }
        System.out.println("MockDAO: Retrieving vehicles for client " + clientId + ". Currently returning empty list as MockVehicleDAO is not fully implemented.");
        return vehicles; // Return empty list for now
    }
}

