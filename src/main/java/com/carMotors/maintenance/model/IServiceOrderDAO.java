package com.carMotors.maintenance.model;

import java.sql.SQLException;
import java.util.List;

public interface IServiceOrderDAO {
    void create(ServiceOrder serviceOrder) throws SQLException;
    ServiceOrder read(int id) throws SQLException;
    List<ServiceOrder> readAll() throws SQLException;
    void update(ServiceOrder serviceOrder) throws SQLException;
    void delete(int id) throws SQLException;
}