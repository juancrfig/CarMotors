package com.carMotors.inventory.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Package Data Access Object
public interface PackageDAO {
    void addPackage(Package pkg) throws SQLException;
    Package getPackageById(int id) throws SQLException;
    List<Package> getAllPackages() throws SQLException;
    List<Package> getPackagesByProvider(int providerId) throws SQLException;
    List<Package> getPackagesByOrder(int orderId) throws SQLException;
    void updatePackage(Package pkg) throws SQLException;
    void deletePackage(int id) throws SQLException;
    // Add other specific methods if needed
}

