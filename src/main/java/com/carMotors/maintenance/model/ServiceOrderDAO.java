package com.carMotors.maintenance.model;

import com.carMotors.core.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOrderDAO implements IServiceOrderDAO {
    private Connection connection;
    private ServiceDAO serviceDAO;

    public ServiceOrderDAO(Connection connection, ServiceDAO serviceDAO) {
        this.connection = connection;
        this.serviceDAO = serviceDAO;
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
    public void create(ServiceOrder serviceOrder) throws SQLException {
        ensureConnection();
        String sql = "INSERT INTO ServiceOrders (vehicle_id, service_id, status, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, serviceOrder.getVehicleId());
            stmt.setInt(2, serviceOrder.getServiceId());
            String status = serviceOrder.getStatus();
            if (status == null || !List.of("Pending", "In progress", "Completed", "Delivered").contains(status)) {
                throw new IllegalArgumentException("Estado no válido: " + status);
            }
            stmt.setString(3, status);
            stmt.setDate(4, serviceOrder.getStartDate());
            stmt.setDate(5, serviceOrder.getEndDate());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    serviceOrder.setId(rs.getInt(1));
                    // Cargar el objeto Service asociado
                    Service service = serviceDAO.read(serviceOrder.getServiceId());
                    serviceOrder.setService(service);
                }
            }
        }
    }

    @Override
    public ServiceOrder read(int id) throws SQLException {
        ensureConnection();
        String sql = "SELECT so.* FROM ServiceOrders so WHERE so.order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ServiceOrder serviceOrder = mapResultSetToServiceOrder(rs);
                    // Cargar el objeto Service asociado
                    Service service = serviceDAO.read(serviceOrder.getServiceId());
                    serviceOrder.setService(service);
                    return serviceOrder;
                }
            }
        }
        return null;
    }

    @Override
    public List<ServiceOrder> readAll() throws SQLException {
        ensureConnection();
        List<ServiceOrder> orders = new ArrayList<>();
        String sql = "SELECT so.* FROM ServiceOrders so";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServiceOrder serviceOrder = mapResultSetToServiceOrder(rs);
                // Cargar el objeto Service asociado
                Service service = serviceDAO.read(serviceOrder.getServiceId());
                serviceOrder.setService(service);
                orders.add(serviceOrder);
            }
        }
        return orders;
    }

    @Override
    public void update(ServiceOrder serviceOrder) throws SQLException {
        ensureConnection();
        String sql = "UPDATE ServiceOrders SET vehicle_id = ?, service_id = ?, status = ?, start_date = ?, end_date = ? WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, serviceOrder.getVehicleId());
            stmt.setInt(2, serviceOrder.getServiceId());
            String status = serviceOrder.getStatus();
            if (status == null || !List.of("Pending", "In progress", "Completed", "Delivered").contains(status)) {
                throw new IllegalArgumentException("Estado no válido: " + status);
            }
            stmt.setString(3, status);
            stmt.setDate(4, serviceOrder.getStartDate());
            stmt.setDate(5, serviceOrder.getEndDate());
            stmt.setInt(6, serviceOrder.getId());
            stmt.executeUpdate();
            // Cargar el objeto Service asociado
            Service service = serviceDAO.read(serviceOrder.getServiceId());
            serviceOrder.setService(service);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        ensureConnection();
        String sql = "DELETE FROM ServiceOrders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private ServiceOrder mapResultSetToServiceOrder(ResultSet rs) throws SQLException {
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(rs.getInt("order_id"));
        serviceOrder.setVehicleId(rs.getInt("vehicle_id"));
        serviceOrder.setServiceId(rs.getInt("service_id"));
        serviceOrder.setStatus(rs.getString("status"));
        serviceOrder.setStartDate(rs.getDate("start_date"));
        serviceOrder.setEndDate(rs.getDate("end_date"));
        return serviceOrder;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}