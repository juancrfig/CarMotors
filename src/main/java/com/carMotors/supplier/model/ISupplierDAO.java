package com.carMotors.supplier.model;

import java.sql.SQLException;
import java.util.List;

public interface ISupplierDAO {
    void create(Supplier supplier) throws SQLException;
    Supplier read(int id) throws SQLException;
    List<Supplier> readAll() throws SQLException;
    void update(Supplier supplier) throws SQLException;
    void delete(int id) throws SQLException;
    boolean existsById(int id) throws SQLException;
}

