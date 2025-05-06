package com.carMotors.maintenance.model;

import com.carMotors.core.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO implements IServiceDAO {
    private Connection connection;

    public ServiceDAO(Connection connection) {
        this.connection = connection;
    }

    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DatabaseManager.getConnection();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("❌ Error al obtener la conexión con la base de datos: " + e.getMessage());
                throw new SQLException("No se pudo establecer la conexión con la base de datos.", e);
            }
        }
    }

    @Override
    public void create(Service service) throws SQLException {
        ensureConnection();
        String sql = "INSERT INTO Services (type, description, labor_cost, estimated_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Validar type
            String type = service.getType();
            if (type == null || !List.of("Preventive", "Corrective").contains(type)) {
                throw new IllegalArgumentException("Tipo de servicio no válido: " + type);
            }
            stmt.setString(1, type);
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getLaborCost());
            stmt.setInt(4, service.getEstimatedTime());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    service.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Service read(int id) throws SQLException {
        ensureConnection();
        String sql = "SELECT * FROM Services WHERE service_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToService(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Service> readAll() throws SQLException {
        ensureConnection();
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Services";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        }
        return services;
    }

    @Override
    public void update(Service service) throws SQLException {
        ensureConnection();
        String sql = "UPDATE Services SET type = ?, description = ?, labor_cost = ?, estimated_time = ? WHERE service_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Validar type
            String type = service.getType();
            if (type == null || !List.of("Preventive", "Corrective").contains(type)) {
                throw new IllegalArgumentException("Tipo de servicio no válido: " + type);
            }
            stmt.setString(1, type);
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getLaborCost());
            stmt.setInt(4, service.getEstimatedTime());
            stmt.setInt(5, service.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        ensureConnection();
        String sql = "DELETE FROM Services WHERE service_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean existsById(int id) throws SQLException {
        ensureConnection();
        String sql = "SELECT 1 FROM Services WHERE service_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getInt("service_id"));
        service.setType(rs.getString("type"));
        service.setDescription(rs.getString("description"));
        service.setLaborCost(rs.getDouble("labor_cost"));
        service.setEstimatedTime(rs.getInt("estimated_time"));
        return service;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}