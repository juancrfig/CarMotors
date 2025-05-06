package com.carMotors.maintenance.view;

import com.carMotors.customer.model.Vehicle;
import com.carMotors.maintenance.controller.ServiceController;
import com.carMotors.maintenance.controller.ServiceOrderController;
import com.carMotors.maintenance.model.Service;
import com.carMotors.maintenance.model.ServiceOrder;
import com.carMotors.customer.model.IVehicleDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

    // --- Color Palette ---
    private static final Color COLOR_BACKGROUND = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_COMPONENT_BG = new Color(0xA9, 0xA9, 0xA9); // Dark Gray
    private static final Color COLOR_ACCENT = new Color(0x46, 0x82, 0xB4); // Steel Blue
    private static final Color COLOR_TEXT_LIGHT = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_MENU_BUTTON_BG = new Color(0xFA, 0xFA, 0xD2); // LightGoldenrodYellow
    private static final Color COLOR_ACTION_BUTTON_BG = COLOR_ACCENT; // Steel Blue
    private static final Color COLOR_ACTION_BUTTON_FG = COLOR_TEXT_LIGHT; // White

    // --- Fonts ---
    private static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FONT_TEXT_AREA = new Font("Consolas", Font.PLAIN, 13);
    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 14);

    // --- Borders ---
    private static final Border BORDER_PADDING = new EmptyBorder(15, 15, 15, 15);
    private static final Border BORDER_COMPONENT = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACCENT, 1),
            new EmptyBorder(3, 5, 3, 5)
    );

    public ServiceOrderView(ServiceOrderController serviceOrderController, ServiceController serviceController, IVehicleDAO vehicleDAO) {
        this.serviceOrderController = serviceOrderController;
        this.serviceController = serviceController;
        this.vehicleDAO = vehicleDAO;
        setLayout(new BorderLayout(0, 10));
        setBackground(COLOR_BACKGROUND);
        initializeUI();
        loadOrders();
        loadVehicles();
        loadServices();
    }

    private void initializeUI() {
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_BACKGROUND);
        formPanel.setBorder(BORDER_PADDING);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Fields
        addFormField(formPanel, "Order ID:", idField = new JTextField(10), 0, 0);
        idField.setEditable(false);
        addFormField(formPanel, "Vehicle ID:", vehicleComboBox = new JComboBox<>(), 0, 1);
        addFormField(formPanel, "Service ID:", serviceComboBox = new JComboBox<>(), 0, 2);
        addFormField(formPanel, "Client ID:", clientIdField = new JTextField(10), 0, 3);
        clientIdField.setEditable(false);
        addFormField(formPanel, "Status:", statusComboBox = new JComboBox<>(new String[]{"Pending", "In progress", "Completed", "Delivered"}), 0, 4);
        addFormField(formPanel, "Start Date (YYYY-MM-DD):", startDateField = new JTextField(10), 0, 5);
        addFormField(formPanel, "End Date (YYYY-MM-DD):", endDateField = new JTextField(10), 0, 6);

        // Table
        String[] columnNames = {"Order ID", "Vehicle ID", "Service ID", "Client ID", "Status", "Start Date", "End Date", "Service Type", "Description", "Labor Cost"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateForm();
            }
        });
        styleTable(orderTable);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.getViewport().setBackground(COLOR_COMPONENT_BG);

        // Bottom Menu Panel
        JPanel bottomMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomMenuPanel.setBackground(COLOR_BACKGROUND);
        bottomMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_COMPONENT_BG));

        JButton addButton = createMenuButton("➕ Add");
        JButton updateButton = createMenuButton("✏️ Update");
        JButton deleteButton = createMenuButton("🗑️ Delete");
        JButton clearButton = createMenuButton("🧹 Clear");

        addButton.addActionListener(e -> addOrder());
        updateButton.addActionListener(e -> updateOrder());
        deleteButton.addActionListener(e -> deleteOrder());
        clearButton.addActionListener(e -> clearForm());

        bottomMenuPanel.add(addButton);
        bottomMenuPanel.add(updateButton);
        bottomMenuPanel.add(deleteButton);
        bottomMenuPanel.add(clearButton);

        // Add Panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomMenuPanel, BorderLayout.SOUTH);

        // Update clientId when vehicle is selected
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

    // Helper methods
    private void addFormField(JPanel panel, String labelText, JComponent field, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 0.0;
        JLabel label = new JLabel(labelText);
        styleLabel(label);
        panel.add(label, gbc);

        gbc.gridx = gridx + 1;
        gbc.weightx = 1.0;
        if (field instanceof JTextField) {
            styleTextField((JTextField) field);
        } else if (field instanceof JComboBox) {
            styleComboBox((JComboBox<?>) field);
        }
        panel.add(field, gbc);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(COLOR_MENU_BUTTON_BG);
        btn.setForeground(COLOR_TEXT_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 35));
        return btn;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(COLOR_COMPONENT_BG);
        field.setForeground(COLOR_TEXT_DARK);
        field.setFont(FONT_LABEL);
        field.setBorder(BORDER_COMPONENT);
        field.setCaretColor(COLOR_TEXT_DARK);
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(COLOR_COMPONENT_BG);
        comboBox.setForeground(COLOR_TEXT_DARK);
        comboBox.setFont(FONT_LABEL);
    }

    private void styleLabel(JLabel label) {
        label.setForeground(COLOR_TEXT_LIGHT);
        label.setFont(FONT_LABEL);
    }

    private void styleTable(JTable table) {
        table.setBackground(COLOR_COMPONENT_BG);
        table.setForeground(COLOR_TEXT_DARK);
        table.setFont(FONT_TEXT_AREA);
        table.setGridColor(COLOR_ACCENT);
        table.setSelectionBackground(COLOR_ACCENT);
        table.setSelectionForeground(COLOR_TEXT_LIGHT);
    }
}