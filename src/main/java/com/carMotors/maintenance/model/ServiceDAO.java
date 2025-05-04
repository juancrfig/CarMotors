package com.carMotors.maintenance.model;

import com.carMotors.customer.model.Vehicle; // Import the Vehicle class
import java.sql.SQLException;
import java.util.List;

// Interface for Service Data Access Object
public interface ServiceDAO {
    void addService(Service service) throws SQLException;
    Service getServiceById(int id) throws SQLException;
    List<Service> getAllServices() throws SQLException;
    List<Service> getServicesByClient(int clientId) throws SQLException;
    List<Service> getServicesByEmployee(int employeeId) throws SQLException;
    List<Service> getServicesByState(Service.ServiceState state) throws SQLException;
    void updateService(Service service) throws SQLException;
    void deleteService(int id) throws SQLException;
    // Methods for managing the Service-Vehicle relationship (many-to-many)
    void addVehicleToService(int serviceId, int vehicleId) throws SQLException;
    void removeVehicleFromService(int serviceId, int vehicleId) throws SQLException;
    List<Vehicle> getVehiclesForService(int serviceId) throws SQLException; // Requires Vehicle class, ensure it's accessible
}

