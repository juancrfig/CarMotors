package com.carMotors.supplier.view;

import com.carMotors.supplier.controller.SupplierController;
import com.carMotors.supplier.model.Supplier;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SupplierView extends JPanel {
    private SupplierController controller;
    private JTextField nameField, contactField, visitFrequencyField;
    private JTextArea suppliersArea;
    private JButton addButton, updateButton, deleteButton, viewAllButton;

    public SupplierView(SupplierController controller) {
        this.controller = controller;

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
        panel.add(new JLabel("Contact:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contactField = new JTextField();
        panel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Visit Frequency:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        visitFrequencyField = new JTextField();
        panel.add(visitFrequencyField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        addButton = new JButton("Agregar");
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        updateButton = new JButton("Actualizar");
        panel.add(updateButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        deleteButton = new JButton("Eliminar");
        panel.add(deleteButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        viewAllButton = new JButton("Ver Todos");
        panel.add(viewAllButton, gbc);

        // Text Area for displaying suppliers
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        suppliersArea = new JTextArea(5, 40);
        suppliersArea.setEditable(false);
        panel.add(new JScrollPane(suppliersArea), gbc);

        add(panel, BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> {
            try {
                Supplier supplier = new Supplier();
                supplier.setName(nameField.getText());
                supplier.setContact(contactField.getText());
                supplier.setVisitFrequency(visitFrequencyField.getText());
                controller.addSupplier(supplier);
                JOptionPane.showMessageDialog(this, "Proveedor agregado con ID: " + supplier.getId());
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Por favor ingrese valores válidos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
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
                JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del proveedor a actualizar:"));
                Supplier supplier = controller.getSupplier(id);
                if (supplier != null) {
                    supplier.setName(nameField.getText());
                    supplier.setContact(contactField.getText());
                    supplier.setVisitFrequency(visitFrequencyField.getText());
                    controller.updateSupplier(supplier);
                    JOptionPane.showMessageDialog(this, "Proveedor actualizado con ID: " + supplier.getId());
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Proveedor no encontrado con ID: " + id);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar proveedor: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID del proveedor a eliminar:"));
                controller.deleteSupplier(id);
                JOptionPane.showMessageDialog(this, "Proveedor eliminado con ID: " + id);
                clearFields();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar proveedor: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: El ID debe ser un número válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            }
        });
    }

    private void clearFields() {
        nameField.setText("");
        contactField.setText("");
        visitFrequencyField.setText("");
    }
}
