package com.carMotors.supplier.model;

import com.carMotors.core.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO implements ISupplierDAO {

    private Connection connection;

    // Constructor que recibe la conexión desde fuera
    public SupplierDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para obtener o renovar la conexión
    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DatabaseManager.getConnection(); // Obtiene una nueva conexión
                connection.setAutoCommit(true); // Asegura que las transacciones se confirmen automáticamente
            } catch (SQLException e) {
                System.err.println("❌ Error al obtener la conexión con la base de datos: " + e.getMessage());
                throw new SQLException("No se pudo establecer la conexión con la base de datos.", e);
            }
        }
    }

    @Override
    public void create(Supplier supplier) throws SQLException {
        ensureConnection();

        String sql = "INSERT INTO Suppliers (name, tax_id, contact, visit_frequency) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getTaxId());
            stmt.setString(3, supplier.getContact());
            stmt.setString(4, supplier.getVisitFrequency());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                supplier.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Supplier read(int id) throws SQLException {
        ensureConnection();

        String sql = "SELECT * FROM Suppliers WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSupplier(rs);
            }
        }
        return null;
    }

    @Override
    public List<Supplier> readAll() throws SQLException {
        ensureConnection();

        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Suppliers";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }
        }
        return suppliers;
    }

    @Override
    public void update(Supplier supplier) throws SQLException {
        ensureConnection();

        String sql = "UPDATE Suppliers SET name = ?, tax_id = ?, contact = ?, visit_frequency = ? WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getTaxId());
            stmt.setString(3, supplier.getContact());
            stmt.setString(4, supplier.getVisitFrequency());
            stmt.setInt(5, supplier.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        ensureConnection();

        String sql = "DELETE FROM Suppliers WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean existsById(int id) throws SQLException {
        ensureConnection();

        String sql = "SELECT 1 FROM Suppliers WHERE supplier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("supplier_id"));
        supplier.setName(rs.getString("name"));
        supplier.setTaxId(rs.getString("tax_id"));
        supplier.setContact(rs.getString("contact"));
        supplier.setVisitFrequency(rs.getString("visit_frequency"));
        return supplier;
    }

    // Método para cerrar la conexión si es necesario
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}