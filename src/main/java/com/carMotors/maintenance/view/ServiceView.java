package com.carMotors.maintenance.view;

import com.carMotors.maintenance.controller.ServiceController;
import com.carMotors.maintenance.model.Service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ServiceView extends JPanel {
    private ServiceController serviceController;
    private ServiceOrderView serviceOrderView; // Referencia a ServiceOrderView
    private JTextField idField, descriptionField, laborCostField, estimatedTimeField;
    private JComboBox<String> typeComboBox;
    private JTable serviceTable;
    private DefaultTableModel tableModel;

    public ServiceView(ServiceController serviceController, ServiceOrderView serviceOrderView) {
        this.serviceController = serviceController;
        this.serviceOrderView = serviceOrderView;
        setLayout(new BorderLayout());
        initializeUI();
        loadServices();
    }

    private void initializeUI() {
        // Panel del formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos del formulario
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        idField.setEditable(false);
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Preventive", "Corrective"});
        formPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Labor Cost:"), gbc);
        gbc.gridx = 1;
        laborCostField = new JTextField(10);
        formPanel.add(laborCostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Estimated Time (hours):"), gbc);
        gbc.gridx = 1;
        estimatedTimeField = new JTextField(10);
        formPanel.add(estimatedTimeField, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addService());
        updateButton.addActionListener(e -> updateService());
        deleteButton.addActionListener(e -> deleteService());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Tabla de servicios
        String[] columnNames = {"ID", "Type", "Description", "Labor Cost", "Estimated Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        serviceTable = new JTable(tableModel);
        serviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        serviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateForm();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(serviceTable);

        // Añadir componentes al panel principal
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private void addService() {
        try {
            Service service = new Service();
            service.setType((String) typeComboBox.getSelectedItem());
            service.setDescription(descriptionField.getText());
            service.setLaborCost(Double.parseDouble(laborCostField.getText()));
            service.setEstimatedTime(Integer.parseInt(estimatedTimeField.getText()));

            serviceController.addService(service);
            loadServices();
            clearForm();
            if (serviceOrderView != null) {
                serviceOrderView.refreshServices(); // Actualizar ServiceOrderView
            }
            JOptionPane.showMessageDialog(this, "Service added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for labor cost or estimated time.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error adding service: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateService() {
        try {
            int id = Integer.parseInt(idField.getText());
            Service service = new Service();
            service.setId(id);
            service.setType((String) typeComboBox.getSelectedItem());
            service.setDescription(descriptionField.getText());
            service.setLaborCost(Double.parseDouble(laborCostField.getText()));
            service.setEstimatedTime(Integer.parseInt(estimatedTimeField.getText()));

            serviceController.updateService(service);
            loadServices();
            clearForm();
            if (serviceOrderView != null) {
                serviceOrderView.refreshServices(); // Actualizar ServiceOrderView
            }
            JOptionPane.showMessageDialog(this, "Service updated successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for ID, labor cost, or estimated time.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error updating service: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteService() {
        try {
            int id = Integer.parseInt(idField.getText());
            serviceController.deleteService(id);
            loadServices();
            clearForm();
            if (serviceOrderView != null) {
                serviceOrderView.refreshServices(); // Actualizar ServiceOrderView
            }
            JOptionPane.showMessageDialog(this, "Service deleted successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please select a service to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error deleting service: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        typeComboBox.setSelectedIndex(0);
        descriptionField.setText("");
        laborCostField.setText("");
        estimatedTimeField.setText("");
        serviceTable.clearSelection();
    }

    private void loadServices() {
        try {
            List<Service> services = serviceController.getAllServices();
            tableModel.setRowCount(0);
            for (Service service : services) {
                tableModel.addRow(new Object[]{
                        service.getId(),
                        service.getType(),
                        service.getDescription(),
                        service.getLaborCost(),
                        service.getEstimatedTime()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading services: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateForm() {
        int selectedRow = serviceTable.getSelectedRow();
        if (selectedRow >= 0) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            typeComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 1));
            descriptionField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            laborCostField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            estimatedTimeField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }
}