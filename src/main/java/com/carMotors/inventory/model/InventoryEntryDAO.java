package com.carMotors.inventory.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// Interface for InventoryEntry Data Access Object
public interface InventoryEntryDAO {
    // Note: The inventory table doesn't have a single primary key in the SQL.
    // Operations might need to identify entries by composite key (idSparePart, idPackage).

    void addInventoryEntry(InventoryEntry entry) throws SQLException;
    InventoryEntry getInventoryEntry(int idSparePart, int idPackage) throws SQLException;
    List<InventoryEntry> getAllInventoryEntries() throws SQLException;
    List<InventoryEntry> getInventoryBySparePart(int idSparePart) throws SQLException;
    List<InventoryEntry> getInventoryByPackage(int idPackage) throws SQLException;
    List<InventoryEntry> getInventoryNearExpiration(Date thresholdDate) throws SQLException;
    List<InventoryEntry> getInventoryBelowStockLevel(int minLevel) throws SQLException; // Requires joining with spareParts? Or add minLevel to InventoryEntry?
    void updateInventoryEntry(InventoryEntry entry) throws SQLException;
    // Deletion might be tricky without a single PK, maybe delete by composite key?
    void deleteInventoryEntry(int idSparePart, int idPackage) throws SQLException;
}

