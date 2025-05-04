package com.carMotors.inventory.model;

import java.util.Date;

// Represents the details of a spare part within an order (association class for orderSpare table)
public class OrderSpareDetail {
    private int idOrder;        // Foreign key to Order
    private int idSparePart;    // Foreign key to SparePart
    private int quantity;
    private Date entryDate; // Note: SQL has entryDate here, seems redundant if orderDate exists in Order?
                          // Keeping it as per SQL schema.

    // Default constructor
    public OrderSpareDetail() {
    }

    // Parameterized constructor
    public OrderSpareDetail(int idOrder, int idSparePart, int quantity, Date entryDate) {
        this.idOrder = idOrder;
        this.idSparePart = idSparePart;
        this.quantity = quantity;
        this.entryDate = entryDate;
    }

    // Getters and Setters
    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdSparePart() {
        return idSparePart;
    }

    public void setIdSparePart(int idSparePart) {
        this.idSparePart = idSparePart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        return "OrderSpareDetail{" +
               "idOrder=" + idOrder +
               ", idSparePart=" + idSparePart +
               ", quantity=" + quantity +
               ", entryDate=" + entryDate +
               "}";
    }
}

