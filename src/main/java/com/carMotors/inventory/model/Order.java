package com.carMotors.inventory.model;

import java.util.Date;

public class Order {
    private int id;
    private Date orderDate;

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(int id, Date orderDate) {
        this.id = id;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", orderDate=" + orderDate +
               "}";
    }
}

