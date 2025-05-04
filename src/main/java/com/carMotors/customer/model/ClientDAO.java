package com.carMotors.customer.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Client Data Access Object
public interface ClientDAO {
    void addClient(Client client) throws SQLException;
    Client getClientById(int id) throws SQLException;
    Client getClientByIdentification(long identification) throws SQLException;
    List<Client> getAllClients() throws SQLException;
    void updateClient(Client client) throws SQLException;
    void deleteClient(int id) throws SQLException;
    // Methods for managing the Client-Vehicle relationship (many-to-many)
    void addVehicleToClient(int clientId, int vehicleId) throws SQLException;
    void removeVehicleFromClient(int clientId, int vehicleId) throws SQLException;
    List<Vehicle> getVehiclesForClient(int clientId) throws SQLException;
}

