package com.carMotors.inventory.controller;

import com.carMotors.inventory.model.SparePartDAO;
import com.carMotors.inventory.model.SparePart;
import com.carMotors.supplier.model.SupplierDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller for managing CRUD operations on Spare Parts.
 */
public class SparePartController {

    private final SparePartDAO sparePartDAO;
    private final SupplierDAO supplierDAO;

    public SparePartController(SparePartDAO sparePartDAO, SupplierDAO supplierDAO) {
        this.sparePartDAO = sparePartDAO;
        this.supplierDAO = supplierDAO;
    }

    /**
     * Adds a new spare part to the system.
     */
    public void addSparePart(SparePart sparePart) throws SQLException {
        sparePartDAO.create(sparePart);
    }

    /**
     * Gets a spare part by its ID.
     */
    public SparePart getSparePart(int id) throws SQLException {
        return sparePartDAO.read(id);
    }

    /**
     * Returns all spare parts registered in the system.
     */
    public List<SparePart> getAllSpareParts() throws SQLException {
        return sparePartDAO.readAll();
    }

    /**
     * Updates an existing spare part.
     */
    public void updateSparePart(SparePart sparePart) throws SQLException {
        sparePartDAO.update(sparePart);
    }

    /**
     * Deletes a spare part by ID.
     */
    public void deleteSparePart(int id) throws SQLException {
        sparePartDAO.delete(id);
    }

    /**
     * Checks if a supplier exists by ID.
     * This will be used until the supplier component is fully implemented.
     */
    public boolean supplierExists(int supplierId) throws SQLException {
        return supplierDAO.existsById(supplierId);
    }
}
