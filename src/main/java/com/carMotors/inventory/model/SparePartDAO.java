package com.carMotors.inventory.model;

import com.carMotors.core.util.DatabaseManager;
import com.carMotors.inventory.model.SparePart;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SparePartDAO implements ISparePartDAO {
    private Connection connection;

    // Constructor que recibe la conexión desde fuera
    public SparePartDAO(Connection connection) {
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
    public void create(SparePart sparePart) throws SQLException {
        ensureConnection();
        String sql = "INSERT INTO SpareParts (name, category, brand, model, supplier_id, stock_quantity, minimum_stock_level, entry_date, lifespan_days, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sparePart.getName());
            // Validar categoría
            String category = sparePart.getType();
            if (category == null || !List.of("Mechanical", "Electrical", "Bodywork", "Consumable").contains(category)) {
                throw new IllegalArgumentException("Categoría no válida: " + category);
            }
            stmt.setString(2, category); // Maps to category ENUM
            stmt.setString(3, sparePart.getBrand());
            stmt.setString(4, sparePart.getModel());
            stmt.setInt(5, sparePart.getSupplierId());
            stmt.setInt(6, sparePart.getStockQuantity());
            stmt.setInt(7, sparePart.getMinimumStockLevel());
            stmt.setDate(8, sparePart.getEntryDate() != null ? Date.valueOf(sparePart.getEntryDate()) : null);
            stmt.setInt(9, sparePart.getUsefulLifeDays());
            stmt.setString(10, sparePart.getStatus()); // Maps to status ENUM
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sparePart.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public SparePart read(int id) throws SQLException {
        ensureConnection();
        String sql = "SELECT * FROM SpareParts WHERE part_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSparePart(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<SparePart> readAll() throws SQLException {
        ensureConnection();
        List<SparePart> parts = new ArrayList<>();
        String sql = "SELECT * FROM SpareParts";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                parts.add(mapResultSetToSparePart(rs));
            }
        }
        return parts;
    }

    @Override
    public void update(SparePart sparePart) throws SQLException {
        ensureConnection();
        String sql = "UPDATE SpareParts SET name = ?, category = ?, brand = ?, model = ?, supplier_id = ?, stock_quantity = ?, minimum_stock_level = ?, entry_date = ?, lifespan_days = ?, status = ? WHERE part_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sparePart.getName());
            // Validar categoría
            String category = sparePart.getType();
            if (category == null || !List.of("Mechanical", "Electrical", "Bodywork", "Consumable").contains(category)) {
                throw new IllegalArgumentException("Categoría no válida: " + category);
            }
            stmt.setString(2, category); // Maps to category ENUM
            stmt.setString(3, sparePart.getBrand());
            stmt.setString(4, sparePart.getModel());
            stmt.setInt(5, sparePart.getSupplierId());
            stmt.setInt(6, sparePart.getStockQuantity());
            stmt.setInt(7, sparePart.getMinimumStockLevel());
            stmt.setDate(8, sparePart.getEntryDate() != null ? Date.valueOf(sparePart.getEntryDate()) : null);
            stmt.setInt(9, sparePart.getUsefulLifeDays());
            stmt.setString(10, sparePart.getStatus()); // Maps to status ENUM
            stmt.setInt(11, sparePart.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        ensureConnection();
        String sql = "DELETE FROM SpareParts WHERE part_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private SparePart mapResultSetToSparePart(ResultSet rs) throws SQLException {
        SparePart sparePart = new SparePart();
        sparePart.setId(rs.getInt("part_id"));
        sparePart.setName(rs.getString("name"));
        sparePart.setType(rs.getString("category")); // Maps to category ENUM
        sparePart.setBrand(rs.getString("brand"));
        sparePart.setModel(rs.getString("model"));
        sparePart.setSupplierId(rs.getInt("supplier_id"));
        sparePart.setStockQuantity(rs.getInt("stock_quantity"));
        sparePart.setMinimumStockLevel(rs.getInt("minimum_stock_level"));
        sparePart.setEntryDate(rs.getDate("entry_date") != null ? String.valueOf(rs.getDate("entry_date").toLocalDate()) : null);
        sparePart.setUsefulLifeDays(rs.getInt("lifespan_days"));
        sparePart.setStatus(rs.getString("status")); // Maps to status ENUM
        return sparePart;
    }

    // Método para cerrar la conexión si es necesario
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}