package com.carMotors.inventory.model;

import java.sql.SQLException;
import java.util.List;

// Interface for OrderSpareDetail Data Access Object (handles the orderSpare join table)
public interface OrderSpareDetailDAO {
    void addOrderSpareDetail(OrderSpareDetail detail) throws SQLException;
    // Get details based on composite primary key
    OrderSpareDetail getOrderSpareDetail(int idOrder, int idSparePart) throws SQLException;
    // Get all details for a specific order
    List<OrderSpareDetail> getDetailsForOrder(int idOrder) throws SQLException;
    // Get all orders a specific spare part is included in
    List<OrderSpareDetail> getOrdersForSparePart(int idSparePart) throws SQLException;
    void updateOrderSpareDetail(OrderSpareDetail detail) throws SQLException;
    // Delete based on composite primary key
    void deleteOrderSpareDetail(int idOrder, int idSparePart) throws SQLException;
}

