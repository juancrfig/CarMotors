package com.carMotors.employee.model;

import java.sql.SQLException;
import java.util.List;

// Interface for Employee Data Access Object
public interface EmployeeDAO {
    void addEmployee(Employee employee) throws SQLException;
    Employee getEmployeeById(int id) throws SQLException;
    List<Employee> getAllEmployees() throws SQLException;
    List<Employee> getEmployeesBySpeciality(String speciality) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException;
    void deleteEmployee(int id) throws SQLException;
    // Add other specific methods if needed
}

