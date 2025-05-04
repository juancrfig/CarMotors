package com.carMotors.customer.controller;

import com.carMotors.customer.model.Client;
import com.carMotors.customer.model.ClientDAO;
import com.carMotors.customer.view.ClientDialog;
import com.carMotors.customer.view.CustomerManagementPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    private final CustomerManagementPanel view;
    private final ClientDAO clientDAO;
    private JFrame mainFrame; // Reference to the main application frame for dialog ownership

    public CustomerController(CustomerManagementPanel view, ClientDAO clientDAO, JFrame mainFrame) {
        this.view = view;
        this.clientDAO = clientDAO;
        this.mainFrame = mainFrame;

        // Add listeners to the view components
        this.view.getAddButton().addActionListener(this::addClientAction);
        this.view.getEditButton().addActionListener(this::editClientAction);
        this.view.getDeleteButton().addActionListener(this::deleteClientAction);
        this.view.getRefreshButton().addActionListener(this::refreshClientListAction);

        // Load initial data
        refreshClientList();
    }

    private void refreshClientList() {
        try {
            List<Client> clients = clientDAO.getAllClients();
            view.setTableData(clients);
        } catch (SQLException e) {
            showErrorDialog("Error loading clients: " + e.getMessage());
        }
    }

    private void refreshClientListAction(ActionEvent e) {
        refreshClientList();
    }

    private void addClientAction(ActionEvent e) {
        ClientDialog dialog = new ClientDialog(mainFrame, "Add New Client", null);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            Client newClient = dialog.getClient();
            try {
                clientDAO.addClient(newClient);
                refreshClientList(); // Update the table
            } catch (SQLException ex) {
                showErrorDialog("Error adding client: " + ex.getMessage());
            }
        }
    }

    private void editClientAction(ActionEvent e) {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a client to edit.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get client ID from the selected row (assuming ID is in the first column)
        int clientId = (int) view.getTableModel().getValueAt(selectedRow, 0);

        try {
            Client clientToEdit = clientDAO.getClientById(clientId);
            if (clientToEdit == null) {
                showErrorDialog("Selected client not found in database.");
                refreshClientList(); // Refresh list in case of inconsistency
                return;
            }

            ClientDialog dialog = new ClientDialog(mainFrame, "Edit Client", clientToEdit);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                Client updatedClient = dialog.getClient();
                clientDAO.updateClient(updatedClient);
                refreshClientList(); // Update the table
            }
        } catch (SQLException ex) {
            showErrorDialog("Error editing client: " + ex.getMessage());
        }
    }

    private void deleteClientAction(ActionEvent e) {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a client to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int clientId = (int) view.getTableModel().getValueAt(selectedRow, 0);
        String clientName = (String) view.getTableModel().getValueAt(selectedRow, 1);

        int confirmation = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete client \"" + clientName + "\" (ID: " + clientId + ")?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                clientDAO.deleteClient(clientId);
                refreshClientList(); // Update the table
            } catch (SQLException ex) {
                showErrorDialog("Error deleting client: " + ex.getMessage());
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(view, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

