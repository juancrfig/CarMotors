package com.carMotors.inventory.view;

import com.carMotors.inventory.controller.SparePartController;
import com.carMotors.inventory.model.SparePart;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SparePartView extends JPanel {
    private SparePartController controller;
    private JTextField nameField, brandField, modelField, supplierIdField,
            stockQuantityField, minStockLevelField, entryDateField, usefulLifeDaysField;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> statusComboBox;
    private JTextArea sparePartsArea;
    private JButton addButton, updateButton, deleteButton, viewAllButton;
    private Integer selectedSparePartId; // Almacena el ID del repuesto seleccionado

    public SparePartView(SparePartController controller) {
        this.controller = controller;
        this.selectedSparePartId = null;

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField();
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Type:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] types = {"Mechanical", "Electrical", "Bodywork", "Consumable"};
        typeComboBox = new JComboBox<>(types);
        panel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Brand:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        brandField = new JTextField();
        panel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Model:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        modelField = new JTextField();
        panel.add(modelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Supplier ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        supplierIdField = new JTextField();
        panel.add(supplierIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Stock Quantity:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        stockQuantityField = new JTextField();
        panel.add(stockQuantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Min Stock Level:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        minStockLevelField = new JTextField();
        panel.add(minStockLevelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Entry Date: (YYYY-MM-DD)"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        entryDateField = new JTextField();
        panel.add(entryDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Useful Life (days):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        usefulLifeDaysField = new JTextField();
        panel.add(usefulLifeDaysField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] statuses = {"Available", "ReservedForJob", "OutOfService"};
        statusComboBox = new JComboBox<>(statuses);
        panel.add(statusComboBox, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        addButton = new JButton("Agregar");
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        updateButton = new JButton("Actualizar");
        panel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        deleteButton = new JButton("Eliminar");
        panel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        viewAllButton = new JButton("Ver Todos");
        panel.add(viewAllButton, gbc);

        // Text Area for displaying spare parts
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        sparePartsArea = new JTextArea(10, 40);
        sparePartsArea.setEditable(false);
        sparePartsArea.setText("Haga clic en 'Ver Todos' para listar los repuestos.\nSeleccione un repuesto haciendo clic para editar o eliminar.");
        panel.add(new JScrollPane(sparePartsArea), gbc);

        add(panel, BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> {
            try {
                SparePart sparePart = new SparePart();
                sparePart.setName(nameField.getText());
                sparePart.setType((String) typeComboBox.getSelectedItem());
                sparePart.setBrand(brandField.getText());
                sparePart.setModel(modelField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());
                // Verificar si el proveedor existe
                try {
                    if (!controller.supplierExists(supplierId)) {
                        JOptionPane.showMessageDialog(this, "Error: El ID Proveedor " + supplierId + " no existe en la base de datos.");
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al verificar el proveedor: " + ex.getMessage());
                    return;
                }
                sparePart.setSupplierId(supplierId);
                sparePart.setStockQuantity(Integer.parseInt(stockQuantityField.getText()));
                sparePart.setMinimumStockLevel(Integer.parseInt(minStockLevelField.getText()));
                sparePart.setEntryDate(entryDateField.getText());
                sparePart.setUsefulLifeDays(Integer.parseInt(usefulLifeDaysField.getText()));
                sparePart.setStatus((String) statusComboBox.getSelectedItem());
                // Agregar el repuesto
                try {
                    controller.addSparePart(sparePart);
                    JOptionPane.showMessageDialog(this, "Repuesto agregado con ID: " + sparePart.getId());
                    clearFields();
                    selectedSparePartId = null; // Resetear selección
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al agregar repuesto: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Por favor ingrese valores numéricos válidos para Supplier ID, Stock Quantity, Min Stock Level y Useful Life.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                if (selectedSparePartId == null) {
                    JOptionPane.showMessageDialog(this, "Error: Por favor seleccione un repuesto de la lista primero.");
                    return;
                }
                SparePart sparePart = new SparePart();
                sparePart.setId(selectedSparePartId);
                sparePart.setName(nameField.getText());
                sparePart.setType((String) typeComboBox.getSelectedItem());
                sparePart.setBrand(brandField.getText());
                sparePart.setModel(modelField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());
                // Verificar si el proveedor existe
                try {
                    if (!controller.supplierExists(supplierId)) {
                        JOptionPane.showMessageDialog(this, "Error: El ID Proveedor " + supplierId + " no existe en la base de datos.");
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al verificar el proveedor: " + ex.getMessage());
                    return;
                }
                sparePart.setSupplierId(supplierId);
                sparePart.setStockQuantity(Integer.parseInt(stockQuantityField.getText()));
                sparePart.setMinimumStockLevel(Integer.parseInt(minStockLevelField.getText()));
                sparePart.setEntryDate(entryDateField.getText());
                sparePart.setUsefulLifeDays(Integer.parseInt(usefulLifeDaysField.getText()));
                sparePart.setStatus((String) statusComboBox.getSelectedItem());
                // Actualizar el repuesto
                try {
                    controller.updateSparePart(sparePart);
                    JOptionPane.showMessageDialog(this, "Repuesto actualizado con ID: " + selectedSparePartId);
                    clearFields();
                    selectedSparePartId = null; // Resetear selección
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar repuesto: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Por favor ingrese valores numéricos válidos para Supplier ID, Stock Quantity, Min Stock Level y Useful Life.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                if (selectedSparePartId == null) {
                    JOptionPane.showMessageDialog(this, "Error: Por favor seleccione un repuesto de la lista primero.");
                    return;
                }
                // Confirmar eliminación
                int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el repuesto con ID " + selectedSparePartId + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.deleteSparePart(selectedSparePartId);
                        JOptionPane.showMessageDialog(this, "Repuesto eliminado con ID: " + selectedSparePartId);
                        clearFields();
                        selectedSparePartId = null; // Resetear selección
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar repuesto: " + ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });

        viewAllButton.addActionListener(e -> {
            try {
                List<SparePart> spareParts = controller.getAllSpareParts();
                sparePartsArea.setText("");
                for (SparePart s : spareParts) {
                    sparePartsArea.append("ID: " + s.getId() + ", Name: " + s.getName() +
                            ", Type: " + s.getType() + ", Brand: " + s.getBrand() +
                            ", Model: " + s.getModel() + ", Supplier ID: " + s.getSupplierId() +
                            ", Stock: " + s.getStockQuantity() + ", Min: " + s.getMinimumStockLevel() +
                            ", Entry Date: " + s.getEntryDate() + ", Life: " + s.getUsefulLifeDays() +
                            ", Status: " + s.getStatus() + "\n");
                }
                if (spareParts.isEmpty()) {
                    sparePartsArea.append("No hay repuestos registrados.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar repuestos: " + ex.getMessage());
            }
        });

        // Agregar funcionalidad para cargar datos al hacer clic en un repuesto
        sparePartsArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int caretPosition = sparePartsArea.getCaretPosition();
                try {
                    int lineNumber = sparePartsArea.getLineOfOffset(caretPosition);
                    int start = sparePartsArea.getLineStartOffset(lineNumber);
                    int end = sparePartsArea.getLineEndOffset(lineNumber);
                    String line = sparePartsArea.getText(start, end - start);
                    if (line.trim().isEmpty() || line.contains("No hay repuestos") || line.contains("Haga clic en")) {
                        return;
                    }
                    String idStr = line.split(",")[0].replace("ID: ", "").trim();
                    int id = Integer.parseInt(idStr);
                    SparePart sparePart = controller.getSparePart(id);
                    if (sparePart != null) {
                        selectedSparePartId = sparePart.getId();
                        nameField.setText(sparePart.getName());
                        typeComboBox.setSelectedItem(sparePart.getType());
                        brandField.setText(sparePart.getBrand());
                        modelField.setText(sparePart.getModel());
                        supplierIdField.setText(String.valueOf(sparePart.getSupplierId()));
                        stockQuantityField.setText(String.valueOf(sparePart.getStockQuantity()));
                        minStockLevelField.setText(String.valueOf(sparePart.getMinimumStockLevel()));
                        entryDateField.setText(sparePart.getEntryDate());
                        usefulLifeDaysField.setText(String.valueOf(sparePart.getUsefulLifeDays()));
                        statusComboBox.setSelectedItem(sparePart.getStatus());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(SparePartView.this, "Error al cargar repuesto: " + ex.getMessage());
                } catch (Exception ex) {
                    // Ignorar errores de clic inválido
                }
            }
        });
    }

    private void clearFields() {
        nameField.setText("");
        typeComboBox.setSelectedIndex(0);
        brandField.setText("");
        modelField.setText("");
        supplierIdField.setText("");
        stockQuantityField.setText("");
        minStockLevelField.setText("");
        entryDateField.setText("");
        usefulLifeDaysField.setText("");
        statusComboBox.setSelectedIndex(0);
        selectedSparePartId = null; // Resetear selección
    }
}