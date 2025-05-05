package com.carMotors.customer.controller;

import com.carMotors.customer.model.IClienteDAO;
import com.carMotors.customer.model.Client;
import com.carMotors.maintenance.model.ServiceOrder;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientController {

    private static final long SIX_MONTHS_IN_MILLIS = 1000L * 60 * 60 * 24 * 180;

    private final IClienteDAO clienteDAO;
    private final Timer reminderTimer;
    private final JFrame parentFrame; // Para manejar diálogos desde MainMenuView

    public ClientController(IClienteDAO clienteDAO, JFrame parentFrame) {
        this.clienteDAO = clienteDAO; // Recibe el DAO como parámetro
        this.reminderTimer = new Timer();
        this.parentFrame = parentFrame;
        scheduleAllReminders();
    }

    public void registerClient(Client client) throws Exception {
        validateClient(client);
        clienteDAO.registerClient(client);
        checkAndScheduleReminder(client);
    }

    public List<Client> listAllClients() throws SQLException {
        return clienteDAO.listAllClients();
    }

    public Client findClientById(int id) throws SQLException {
        return clienteDAO.findClientById(id);
    }

    public void updateClient(Client client) throws Exception {
        validateClient(client);
        clienteDAO.updateClient(client);
        checkAndScheduleReminder(client);
    }

    public void applyDiscount(int clientId, double discountPercentage) throws SQLException {
        Client client = findClientById(clientId);
        if (client != null) {
            client.setDiscountPercentage(discountPercentage);
            clienteDAO.updateClient(client);
        }
    }

    public void addRewardPoints(int clientId, int points) throws SQLException {
        Client client = findClientById(clientId);
        if (client != null) {
            client.setRewardPoints(client.getRewardPoints() + points);
            clienteDAO.updateClient(client);
        }
    }

    public void aplicarBeneficios(Client cliente) {
        int servicios = cliente.getServiceHistory().size();
        int puntos = cliente.getRewardPoints();

        if (servicios >= 10) {
            cliente.setDiscountPercentage(15);
            cliente.setRewardPoints(puntos + 100);
        } else if (servicios >= 5) {
            cliente.setDiscountPercentage(5);
            cliente.setRewardPoints(puntos + 50);
        } else {
            cliente.setDiscountPercentage(0);
        }

        try {
            clienteDAO.updateClient(cliente);
        } catch (SQLException e) {
            e.printStackTrace();  // Podrías lanzar excepción o loguear con un logger
        }
    }

    private void validateClient(Client client) throws Exception {
        if (client.getName() == null || client.getName().trim().isEmpty()) {
            throw new Exception("Client name cannot be empty.");
        }
        if (client.getIdentification() == null || client.getIdentification().trim().isEmpty()) {
            throw new Exception("Client identification cannot be empty.");
        }
    }

    private void checkAndScheduleReminder(Client client) {
        List<ServiceOrder> history = client.getServiceHistory();
        if (history == null || history.isEmpty()) return;

        ServiceOrder lastService = history.get(history.size() - 1);
        Date fechaFin = (Date) lastService.getEndDate();

        if (fechaFin != null) {
            Date reminderDate = new Date(fechaFin.getTime() + SIX_MONTHS_IN_MILLIS);
            if (reminderDate.after(new Date())) {
                scheduleReminder(client.getId(),
                        "Preventive maintenance due soon for client ID: " + client.getId(),
                        reminderDate);
            }
        } else {
            System.out.println("No end date available for last service of client ID: " + client.getId());
        }
    }

    private void scheduleReminder(int clientId, String message, Date date) {
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("[REMINDER] " + message + " on " + new Date());
                // Mostrar un diálogo en la UI
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(parentFrame, message, "Reminder", JOptionPane.INFORMATION_MESSAGE);
                });
            }
        }, date);
    }

    private void scheduleAllReminders() {
        try {
            List<Client> clients = clienteDAO.listAllClients();
            for (Client client : clients) {
                checkAndScheduleReminder(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // En un proyecto más robusto, usar Logger
        }
    }
}