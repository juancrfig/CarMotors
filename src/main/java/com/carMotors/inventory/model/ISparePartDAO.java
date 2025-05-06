package com.carMotors.inventory.model;

import com.carMotors.inventory.model.SparePart;
import java.sql.SQLException;
import java.util.List;

public interface ISparePartDAO {
    void create(SparePart sparePart) throws SQLException;
    SparePart read(int id) throws SQLException;
    List<SparePart> readAll() throws SQLException;
    void update(SparePart sparePart) throws SQLException;
    void delete(int id) throws SQLException;
}

