package com.carMotors.maintenance.view;

import com.carMotors.customer.model.Vehicle;
import com.carMotors.maintenance.controller.ServiceController;
import com.carMotors.maintenance.controller.ServiceOrderController;
import com.carMotors.maintenance.model.Service;
import com.carMotors.maintenance.model.ServiceOrder;
import com.carMotors.customer.model.IVehicleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceOrderView extends JPanel {
    private ServiceOrderController serviceOrderController;
    private ServiceController serviceController;
    private IVehicleDAO vehicleDAO;
    private JTextField idField, startDateField, endDateField, clientIdField;
    private JComboBox<Integer> vehicleComboBox;
    private JComboBox<Integer> serviceComboBox;
    private JComboBox<String> statusComboBox;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ServiceOrderView(ServiceOrderController serviceOrderController, ServiceController serviceController, IVehicleDAO vehicleDAO) {
        this.serviceOrderController = serviceOrderController;
        this.serviceController = serviceController;
        this.vehicleDAO = vehicleDAO;
        setLayout(new BorderLayout());
        initializeUI();
        loadOrders();
        loadVehicles();
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
        formPanel.add(new JLabel("Order ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        idField.setEditable(false);
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Vehicle ID:"), gbc);
        gbc.gridx = 1;
        vehicleComboBox = new JComboBox<>();
        formPanel.add(vehicleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Service ID:"), gbc);
        gbc.gridx = 1;
        serviceComboBox = new JComboBox<>();
        formPanel.add(serviceComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Client ID:"), gbc);
        gbc.gridx = 1;
        clientIdField = new JTextField(10);
        clientIdField.setEditable(false);
        formPanel.add(clientIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusComboBox = new JComboBox<>(new String[]{"Pending", "In progress", "Completed", "Delivered"});
        formPanel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        startDateField = new JTextField(10);
        formPanel.add(startDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        endDateField = new JTextField(10);
        formPanel.add(endDateField, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addOrder());
        updateButton.addActionListener(e -> updateOrder());
        deleteButton.addActionListener(e -> deleteOrder());
        clearButton.addActionListener(e -> clearForm());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Tabla de órdenes
        String[] columnNames = {"Order ID", "Vehicle ID", "Service ID", "Client ID", "Status", "Start Date", "End Date", "Service Type", "Description", "Labor Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateForm();
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(orderTable);

        // Añadir componentes al panel principal
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Actualizar clientId cuando se seleccione un vehículo
        vehicleComboBox.addActionListener(e -> updateClientId());
    }

    private void addOrder() {
        try {
            ServiceOrder order = new ServiceOrder();
            order.setVehicleId((Integer) vehicleComboBox.getSelectedItem());
            order.setServiceId((Integer) serviceComboBox.getSelectedItem());
            order.setStatus((String) statusComboBox.getSelectedItem());
            order.setStartDate(parseDate(startDateField.getText()));
            order.setEndDate(parseDate(endDateField.getText()));

            serviceOrderController.addServiceOrder(order);
            loadOrders();
            clearForm();
            JOptionPane.showMessageDialog(this, "Service order added successfully.");
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error adding service order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOrder() {
        try {
            int id = Integer.parseInt(idField.getText());
            ServiceOrder order = new ServiceOrder();
            order.setId(id);
            order.setVehicleId((Integer) vehicleComboBox.getSelectedItem());
            order.setServiceId((Integer) serviceComboBox.getSelectedItem());
            order.setStatus((String) statusComboBox.getSelectedItem());
            order.setStartDate(parseDate(startDateField.getText()));
            order.setEndDate(parseDate(endDateField.getText()));

            serviceOrderController.updateServiceOrder(order);
            loadOrders();
            clearForm();
            JOptionPane.showMessageDialog(this, "Service order updated successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid order ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error updating service order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        try {
            int id = Integer.parseInt(idField.getText());
            serviceOrderController.deleteServiceOrder(id);
            loadOrders();
            clearForm();
            JOptionPane.showMessageDialog(this, "Service order deleted successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error deleting service order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        vehicleComboBox.setSelectedIndex(-1);
        serviceComboBox.setSelectedIndex(-1);
        clientIdField.setText("");
        statusComboBox.setSelectedIndex(0);
        startDateField.setText("");
        endDateField.setText("");
        orderTable.clearSelection();
    }

    private void loadOrders() {
        try {
            List<ServiceOrder> orders = serviceOrderController.getAllServiceOrders();
            tableModel.setRowCount(0);
            for (ServiceOrder order : orders) {
                tableModel.addRow(new Object[]{
                        order.getId(),
                        order.getVehicleId(),
                        order.getServiceId(),
                        order.getClientId(),
                        order.getStatus(),
                        order.getStartDate(),
                        order.getEndDate(),
                        order.getService() != null ? order.getService().getType() : "",
                        order.getService() != null ? order.getService().getDescription() : "",
                        order.getService() != null ? order.getService().getLaborCost() : ""
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading service orders: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadVehicles() {
        try {
            List<Vehicle> vehicles = vehicleDAO.listAllVehicles();
            vehicleComboBox.removeAllItems();
            for (Vehicle vehicle : vehicles) {
                vehicleComboBox.addItem(vehicle.getId());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading vehicles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadServices() {
        try {
            List<Service> services = serviceController.getAllServices();
            serviceComboBox.removeAllItems();
            for (Service service : services) {
                serviceComboBox.addItem(service.getId());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading services: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshServices() {
        loadServices();
    }

    private void updateClientId() {
        Integer vehicleId = (Integer) vehicleComboBox.getSelectedItem();
        if (vehicleId != null) {
            try {
                Integer clientId = serviceOrderController.getClientIdByVehicleId(vehicleId);
                System.out.println("Vehicle ID: " + vehicleId + ", Client ID: " + clientId); // Log para depuración
                clientIdField.setText(clientId != null ? clientId.toString() : "");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error loading client ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            clientIdField.setText("");
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return new Date(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private void populateForm() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            vehicleComboBox.setSelectedItem(Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()));
            serviceComboBox.setSelectedItem(Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString()));
            clientIdField.setText(tableModel.getValueAt(selectedRow, 3) != null ? tableModel.getValueAt(selectedRow, 3).toString() : "");
            statusComboBox.setSelectedItem(tableModel.getValueAt(selectedRow, 4));
            startDateField.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
            Object endDate = tableModel.getValueAt(selectedRow, 6);
            endDateField.setText(endDate != null ? endDate.toString() : "");
        }
    }
}