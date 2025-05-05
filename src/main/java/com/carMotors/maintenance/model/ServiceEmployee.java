package com.carMotors.maintenance.model;

import com.carMotors.employee.model.Employee;

public class ServiceEmployee {
    private Service service;
    private Employee employee;

    // Constructor
    public ServiceEmployee(Service service, Employee employee) {
        this.service = service;
        this.employee = employee;
    }

    // Getters and setters
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "ServiceEmployee{" +
                "service=" + service.getId() + // Muestra solo el ID del servicio
                ", employee=" + employee.getId() + // Muestra solo el ID del empleado
                '}';
    }
}
