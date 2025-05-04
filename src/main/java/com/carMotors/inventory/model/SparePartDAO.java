package com.carMotors.inventory.model;

import java.sql.SQLException;
import java.util.List;

// Interface for SparePart Data Access Object
public interface SparePartDAO {
    void addSparePart(SparePart sparePart) throws SQLException;
    SparePart getSparePartById(int id) throws SQLException;
    List<SparePart> getAllSpareParts() throws SQLException;
    List<SparePart> getSparePartsByType(SparePart.SparePartType type) throws SQLException;
    List<SparePart> getSparePartsByState(SparePart.SparePartState state) throws SQLException;
    void updateSparePart(SparePart sparePart) throws SQLException;
    void deleteSparePart(int id) throws SQLException;
    // Add other specific methods, e.g., findByName, findByBrandAndModel
}

