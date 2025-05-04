package com.carMotors.customer.view;

import com.carMotors.customer.model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerManagementPanel extends JPanel {

    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton; // Added refresh button

    public CustomerManagementPanel() {
        setLayout(new BorderLayout(10, 10)); // Add gaps between components
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Table setup
        String[] columnNames = {"ID", "Name", "Identification", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        clientTable = new JTable(tableModel);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        clientTable.getTableHeader().setReorderingAllowed(false); // Prevent column reordering

        JScrollPane scrollPane = new JScrollPane(clientTable);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        addButton = new JButton("Add Client");
        editButton = new JButton("Edit Selected");
        deleteButton = new JButton("Delete Selected");
        refreshButton = new JButton("Refresh List");

        // Disable edit/delete initially until a row is selected
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Add components to the panel
        add(new JLabel("Customer Management", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listener to enable/disable buttons based on selection
        clientTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = clientTable.getSelectedRow() != -1;
            editButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });
    }

    // Method to update the table data (will be called by the controller)
    public void setTableData(List<Client> clients) {
        tableModel.setRowCount(0); // Clear existing data
        if (clients != null) {
            for (Client client : clients) {
                tableModel.addRow(new Object[]{
                        client.getId(),
                        client.getName(),
                        client.getIdentification(),
                        client.getPhone(),
                        client.getEmail(),
                        client.getAddress()
                });
            }
        }
    }

    // Getters for components that the controller needs to interact with
    public JTable getClientTable() {
        return clientTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
    
    public JButton getRefreshButton() {
        return refreshButton;
    }
}

