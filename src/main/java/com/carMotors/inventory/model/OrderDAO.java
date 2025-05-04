package com.carMotors.inventory.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// Interface for Order Data Access Object
public interface OrderDAO {
    void addOrder(Order order) throws SQLException;
    Order getOrderById(int id) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
    List<Order> getOrdersByDate(Date date) throws SQLException;
    void updateOrder(Order order) throws SQLException;
    void deleteOrder(int id) throws SQLException;
    // Add methods related to OrderSpareDetail if needed, e.g., getSparePartsForOrder(int orderId)
}

