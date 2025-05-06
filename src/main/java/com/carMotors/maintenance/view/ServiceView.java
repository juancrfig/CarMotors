package com.carMotors.maintenance.view;

import com.carMotors.maintenance.controller.ServiceController;
import com.carMotors.maintenance.model.Service;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ServiceView extends JPanel {
    private ServiceController serviceController;
    private ServiceOrderView serviceOrderView;
    private JTextField idField, descriptionField, laborCostField, estimatedTimeField;
    private JComboBox<String> typeComboBox;
    private JTable serviceTable;
    private DefaultTableModel tableModel;

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

    public ServiceView(ServiceController serviceController, ServiceOrderView serviceOrderView) {
        this.serviceController = serviceController;
        this.serviceOrderView = serviceOrderView;
        setLayout(new BorderLayout(0, 10));
        setBackground(COLOR_BACKGROUND);
        initializeUI();
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
        addFormField(formPanel, "ID:", idField = new JTextField(10), 0, 0);
        idField.setEditable(false);
        addFormField(formPanel, "Type:", typeComboBox = new JComboBox<>(new String[]{"Preventive", "Corrective"}), 0, 1);
        addFormField(formPanel, "Description:", descriptionField = new JTextField(20), 0, 2);
        addFormField(formPanel, "Labor Cost:", laborCostField = new JTextField(10), 0, 3);
        addFormField(formPanel, "Estimated Time (hours):", estimatedTimeField = new JTextField(10), 0, 4);

        // Table
        String[] columnNames = {"ID", "Type", "Description", "Labor Cost", "Estimated Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        serviceTable = new JTable(tableModel);
        serviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        serviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateForm();
            }
        });
        styleTable(serviceTable);
        JScrollPane tableScrollPane = new JScrollPane(serviceTable);
        tableScrollPane.getViewport().setBackground(COLOR_COMPONENT_BG);

        // Bottom Menu Panel
        JPanel bottomMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomMenuPanel.setBackground(COLOR_BACKGROUND);
        bottomMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_COMPONENT_BG));

        JButton addButton = createMenuButton("➕ Add");
        JButton updateButton = createMenuButton("✏️ Update");
        JButton deleteButton = createMenuButton("🗑️ Delete");
        JButton clearButton = createMenuButton("🧹 Clear");

        addButton.addActionListener(e -> addService());
        updateButton.addActionListener(e -> updateService());
        deleteButton.addActionListener(e -> deleteService());
        clearButton.addActionListener(e -> clearForm());

        bottomMenuPanel.add(addButton);
        bottomMenuPanel.add(updateButton);
        bottomMenuPanel.add(deleteButton);
        bottomMenuPanel.add(clearButton);

        // Add Panels
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomMenuPanel, BorderLayout.SOUTH);
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
                serviceOrderView.refreshServices();
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
                serviceOrderView.refreshServices();
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
                serviceOrderView.refreshServices();
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