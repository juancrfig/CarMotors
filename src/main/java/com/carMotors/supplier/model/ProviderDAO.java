package com.carMotors.supplier.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Provider Data Access Object
public interface ProviderDAO {
    void addProvider(Provider provider) throws SQLException;
    Provider getProviderById(int id) throws SQLException;
    List<Provider> getAllProviders() throws SQLException;
    void updateProvider(Provider provider) throws SQLException;
    void deleteProvider(int id) throws SQLException;
    // Add other specific methods if needed, e.g., findByName
}

