package com.carMotors.customer.model;

import com.carMotors.maintenance.model.ServiceOrder;
import com.carMotors.core.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClienteDAO {

    @Override
    public void registerClient(Client client) throws SQLException {
        if (client == null) throw new SQLException("Client cannot be null");

        String sql = "INSERT INTO Clients (name, identification, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getIdentification());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getEmail());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Client> listAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients ORDER BY name";

        // Paso 1: Obtener todos los clientes sin cargar el historial
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("client_id"));
                client.setName(rs.getString("name"));
                client.setIdentification(rs.getString("identification"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                clients.add(client);
            }
        }

        // Paso 2: Cargar el historial de servicios para cada cliente
        for (Client client : clients) {
            client.setServiceHistory(getServiceHistory(client.getId()));
        }

        return clients;
    }

    @Override
    public void updateClient(Client client) throws SQLException {
        if (client == null) throw new SQLException("Client cannot be null");

        String sql = "UPDATE Clients SET name = ?, identification = ?, phone = ?, email = ? WHERE client_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getIdentification());
            pstmt.setString(3, client.getPhone());
            pstmt.setString(4, client.getEmail());
            pstmt.setInt(5, client.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Client findClientById(int id) throws SQLException {
        String sql = "SELECT * FROM Clients WHERE client_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("client_id"));
                    client.setName(rs.getString("name"));
                    client.setIdentification(rs.getString("identification"));
                    client.setPhone(rs.getString("phone"));
                    client.setEmail(rs.getString("email"));
                    client.setServiceHistory(getServiceHistory(id));
                    return client;
                }
            }
        }
        return null;
    }

    private List<ServiceOrder> getServiceHistory(int clientId) throws SQLException {
        List<ServiceOrder> history = new ArrayList<>();
        String sql = """
            SELECT so.*, s.type, s.description, s.labor_cost
            FROM ServiceOrders so
            JOIN Vehicles v ON so.vehicle_id = v.vehicle_id
            JOIN Services s ON so.service_id = s.service_id
            WHERE v.client_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ServiceOrder order = new ServiceOrder();
                    order.setId(rs.getInt("order_id"));
                    order.setVehicleId(rs.getInt("vehicle_id"));
                    order.setClientId(clientId);
                    order.setMaintenanceType(rs.getString("type"));
                    order.setDescription(rs.getString("description"));
                    order.setLaborCost(rs.getDouble("labor_cost"));
                    order.setStatus(rs.getString("status"));
                    order.setStartDate(rs.getDate("start_date"));
                    order.setEndDate(rs.getDate("end_date"));
                    history.add(order);
                }
            }
        }
        return history;
    }
}