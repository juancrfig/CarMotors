package com.carMotors.supplier.view;

import com.carMotors.supplier.controller.SupplierController;
import com.carMotors.supplier.model.Supplier;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SupplierView extends JPanel {
    private SupplierController controller;
    private JTextField nameField, contactField, visitFrequencyField;
    private JTextArea suppliersArea;
    private JButton addButton, updateButton, deleteButton, viewAllButton;

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

    public SupplierView(SupplierController controller) {
        this.controller = controller;
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
        addFormField(formPanel, "Contact:", contactField = new JTextField(), 0, 1);
        addFormField(formPanel, "Visit Frequency:", visitFrequencyField = new JTextField(), 0, 2);

        // Text Area
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        suppliersArea = new JTextArea(5, 40);
        suppliersArea.setEditable(false);
        styleTextArea(suppliersArea);
        JScrollPane scrollPane = new JScrollPane(suppliersArea);
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
                Supplier supplier = new Supplier();
                supplier.setName(nameField.getText());
                supplier.setContact(contactField.getText());
                supplier.setVisitFrequency(visitFrequencyField.getText());
                controller.addSupplier(supplier);
                JOptionPane.showMessageDialog(this, "Supplier added with ID: " + supplier.getId());
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding supplier: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Please enter valid values.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
            }
        });

        viewAllButton.addActionListener(e -> {
            try {
                List<Supplier> suppliers = controller.getAllSuppliers();
                suppliersArea.setText("");
                for (Supplier s : suppliers) {
                    suppliersArea.append(s.getId() + ", " + s.getName() + ", " + s.getContact() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading suppliers: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the supplier ID to update:"));
                Supplier supplier = controller.getSupplier(id);
                if (supplier != null) {
                    supplier.setName(nameField.getText());
                    supplier.setContact(contactField.getText());
                    supplier.setVisitFrequency(visitFrequencyField.getText());
                    controller.updateSupplier(supplier);
                    JOptionPane.showMessageDialog(this, "Supplier updated with ID: " + supplier.getId());
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Supplier not found with ID: " + id);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating supplier: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID must be a valid number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the supplier ID to delete:"));
                controller.deleteSupplier(id);
                JOptionPane.showMessageDialog(this, "Supplier deleted with ID: " + id);
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting supplier: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: ID must be a valid number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
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
        contactField.setText("");
        visitFrequencyField.setText("");
    }
}