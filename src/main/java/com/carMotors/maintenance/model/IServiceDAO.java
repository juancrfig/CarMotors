package com.carMotors.maintenance.model;

import java.sql.SQLException;
import java.util.List;

public interface IServiceDAO {
    void create(Service service) throws SQLException;
    Service read(int id) throws SQLException;
    List<Service> readAll() throws SQLException;
    void update(Service service) throws SQLException;
    void delete(int id) throws SQLException;
    boolean existsById(int id) throws SQLException;
}