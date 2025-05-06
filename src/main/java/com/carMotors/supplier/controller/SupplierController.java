package com.carMotors.supplier.controller;

import com.carMotors.supplier.model.Supplier;
import com.carMotors.supplier.model.SupplierDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller to manage Supplier operations.
 */
public class SupplierController {

    private final SupplierDAO supplierDAO;

    public SupplierController(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO; // Recibe el DAO como parámetro
    }

    /**
     * Adds a new supplier to the system.
     */
    public void addSupplier(Supplier supplier) throws SQLException {
        validateSupplier(supplier); // Validar proveedor antes de agregar
        supplierDAO.create(supplier);
    }

    /**
     * Retrieves a supplier by ID.
     */
    public Supplier getSupplier(int id) throws SQLException {
        return supplierDAO.read(id);
    }

    /**
     * Retrieves all suppliers.
     */
    public List<Supplier> getAllSuppliers() throws SQLException {
        return supplierDAO.readAll();
    }

    /**
     * Updates the data of an existing supplier.
     */
    public void updateSupplier(Supplier supplier) throws SQLException {
        validateSupplier(supplier); // Validar proveedor antes de actualizar
        supplierDAO.update(supplier);
    }

    /**
     * Deletes a supplier by ID.
     */
    public void deleteSupplier(int id) throws SQLException {
        supplierDAO.delete(id);
    }

    /**
     * Validates supplier data.
     */
    private void validateSupplier(Supplier supplier) throws SQLException {
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new SQLException("Supplier name cannot be empty.");
        }
        if (supplier.getContact() == null || supplier.getContact().trim().isEmpty()) {
            throw new SQLException("Supplier contact information cannot be empty.");
        }
    }
}
