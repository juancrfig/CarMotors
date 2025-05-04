package com.carMotors.billing.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Bill Data Access Object
public interface BillDAO {
    void addBill(Bill bill) throws SQLException;
    Bill getBillById(int id) throws SQLException;
    Bill getBillByServiceId(int serviceId) throws SQLException; // Assuming one bill per service
    List<Bill> getAllBills() throws SQLException;
    void updateBill(Bill bill) throws SQLException;
    void deleteBill(int id) throws SQLException;
    // Add other specific methods if needed, e.g., find by date range
}

