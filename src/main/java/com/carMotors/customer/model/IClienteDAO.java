package com.carMotors.customer.model;

import java.sql.SQLException;
import java.util.List;

public interface IClienteDAO {
    void registerClient(Client client) throws SQLException;
    List<Client> listAllClients() throws SQLException;
    void updateClient(Client client) throws SQLException;
    Client findClientById(int id) throws SQLException;
}

