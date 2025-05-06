package com.carMotors.inventory.view;

import com.carMotors.inventory.controller.SparePartController;
import com.carMotors.inventory.model.SparePart;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
    private Integer selectedSparePartId;

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

    public SparePartView(SparePartController controller) {
        this.controller = controller;
        this.selectedSparePartId = null;

        setLayout(new BorderLayout(0, 10));
        setBackground(COLOR_BACKGROUND);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_BACKGROUND);
        formPanel.setBorder(BORDER_PADDING);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Fields
        addFormField(formPanel, "Name:", nameField = new JTextField(), 0, 0);
        addFormField(formPanel, "Type:", typeComboBox = new JComboBox<>(new String[]{"Mechanical", "Electrical", "Bodywork", "Consumable"}), 0, 1);
        addFormField(formPanel, "Brand:", brandField = new JTextField(), 0, 2);
        addFormField(formPanel, "Model:", modelField = new JTextField(), 0, 3);
        addFormField(formPanel, "Supplier ID:", supplierIdField = new JTextField(), 0, 4);
        addFormField(formPanel, "Stock Quantity:", stockQuantityField = new JTextField(), 0, 5);
        addFormField(formPanel, "Min Stock Level:", minStockLevelField = new JTextField(), 0, 6);
        addFormField(formPanel, "Entry Date (YYYY-MM-DD):", entryDateField = new JTextField(), 0, 7);
        addFormField(formPanel, "Useful Life (days):", usefulLifeDaysField = new JTextField(), 0, 8);
        addFormField(formPanel, "Status:", statusComboBox = new JComboBox<>(new String[]{"Available", "ReservedForJob", "OutOfService"}), 0, 9);

        // Text Area
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        sparePartsArea = new JTextArea(10, 40);
        sparePartsArea.setEditable(false);
        sparePartsArea.setText("Click 'View All' to list spare parts.\nSelect a spare part by clicking to edit or delete.");
        styleTextArea(sparePartsArea);
        JScrollPane scrollPane = new JScrollPane(sparePartsArea);
        scrollPane.getViewport().setBackground(COLOR_COMPONENT_BG);
        formPanel.add(scrollPane, gbc);

        // Bottom Menu Panel
        JPanel bottomMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomMenuPanel.setBackground(COLOR_BACKGROUND);
        bottomMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_COMPONENT_BG));

        addButton = createMenuButton("➕ Add");
        updateButton = createMenuButton("✏️ Update");
        deleteButton = createMenuButton("🗑️ Delete");
        viewAllButton = createMenuButton("📋 View All");

        bottomMenuPanel.add(addButton);
        bottomMenuPanel.add(updateButton);
        bottomMenuPanel.add(deleteButton);
        bottomMenuPanel.add(viewAllButton);

        // Add Panels
        add(formPanel, BorderLayout.CENTER);
        add(bottomMenuPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> {
            try {
                SparePart sparePart = new SparePart();
                sparePart.setName(nameField.getText());
                sparePart.setType((String) typeComboBox.getSelectedItem());
                sparePart.setBrand(brandField.getText());
                sparePart.setModel(modelField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());
                try {
                    if (!controller.supplierExists(supplierId)) {
                        JOptionPane.showMessageDialog(this, "Error: Supplier ID " + supplierId + " does not exist in the database.");
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error checking supplier: " + ex.getMessage());
                    return;
                }
                sparePart.setSupplierId(supplierId);
                sparePart.setStockQuantity(Integer.parseInt(stockQuantityField.getText()));
                sparePart.setMinimumStockLevel(Integer.parseInt(minStockLevelField.getText()));
                sparePart.setEntryDate(entryDateField.getText());
                sparePart.setUsefulLifeDays(Integer.parseInt(usefulLifeDaysField.getText()));
                sparePart.setStatus((String) statusComboBox.getSelectedItem());
                try {
                    controller.addSparePart(sparePart);
                    JOptionPane.showMessageDialog(this, "Spare part added with ID: " + sparePart.getId());
                    clearFields();
                    selectedSparePartId = null;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding spare part: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Please enter valid numeric values for Supplier ID, Stock Quantity, Min Stock Level, and Useful Life.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                if (selectedSparePartId == null) {
                    JOptionPane.showMessageDialog(this, "Error: Please select a spare part from the list first.");
                    return;
                }
                SparePart sparePart = new SparePart();
                sparePart.setId(selectedSparePartId);
                sparePart.setName(nameField.getText());
                sparePart.setType((String) typeComboBox.getSelectedItem());
                sparePart.setBrand(brandField.getText());
                sparePart.setModel(modelField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());
                try {
                    if (!controller.supplierExists(supplierId)) {
                        JOptionPane.showMessageDialog(this, "Error: Supplier ID " + supplierId + " does not exist in the database.");
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error checking supplier: " + ex.getMessage());
                    return;
                }
                sparePart.setSupplierId(supplierId);
                sparePart.setStockQuantity(Integer.parseInt(stockQuantityField.getText()));
                sparePart.setMinimumStockLevel(Integer.parseInt(minStockLevelField.getText()));
                sparePart.setEntryDate(entryDateField.getText());
                sparePart.setUsefulLifeDays(Integer.parseInt(usefulLifeDaysField.getText()));
                sparePart.setStatus((String) statusComboBox.getSelectedItem());
                try {
                    controller.updateSparePart(sparePart);
                    JOptionPane.showMessageDialog(this, "Spare part updated with ID: " + selectedSparePartId);
                    clearFields();
                    selectedSparePartId = null;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error updating spare part: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Please enter valid numeric values for Supplier ID, Stock Quantity, Min Stock Level, and Useful Life.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                if (selectedSparePartId == null) {
                    JOptionPane.showMessageDialog(this, "Error: Please select a spare part from the list first.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the spare part with ID " + selectedSparePartId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.deleteSparePart(selectedSparePartId);
                        JOptionPane.showMessageDialog(this, "Spare part deleted with ID: " + selectedSparePartId);
                        clearFields();
                        selectedSparePartId = null;
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting spare part: " + ex.getMessage());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
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
                    sparePartsArea.append("No spare parts registered.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading spare parts: " + ex.getMessage());
            }
        });

        sparePartsArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int caretPosition = sparePartsArea.getCaretPosition();
                try {
                    int lineNumber = sparePartsArea.getLineOfOffset(caretPosition);
                    int start = sparePartsArea.getLineStartOffset(lineNumber);
                    int end = sparePartsArea.getLineEndOffset(lineNumber);
                    String line = sparePartsArea.getText(start, end - start);
                    if (line.trim().isEmpty() || line.contains("No spare parts") || line.contains("Click 'View All'")) {
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
                    JOptionPane.showMessageDialog(SparePartView.this, "Error loading spare part: " + ex.getMessage());
                } catch (Exception ex) {
                    // Ignore invalid click errors
                }
            }
        });
    }

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

    private void styleTextArea(JTextArea area) {
        area.setBackground(COLOR_COMPONENT_BG);
        area.setForeground(COLOR_TEXT_DARK);
        area.setFont(FONT_TEXT_AREA);
        area.setBorder(BORDER_COMPONENT);
        area.setCaretColor(COLOR_TEXT_DARK);
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
        selectedSparePartId = null;
    }
}