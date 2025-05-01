package com.workshop.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Diálogo para agregar un nuevo repuesto
public class AddSpareDialog extends JDialog {

    // Campos de entrada
    private JTextField nameField;
    private JComboBox<String> tipoCombo;
    private JTextField marcaField;
    private JTextField modeloField;
    private JComboBox<String> proveedorCombo;
    private JSpinner cantidadSpinner;
    private JSpinner nivelMinimoSpinner;
    private JTextField fechaIngresoField;
    private JTextField vidaUtilField;
    private JComboBox<String> estadoCombo;

    public AddSpareDialog(JFrame parent) {
        super(parent, "Agregar Repuesto", true);
        // Establece el tamaño y centra el diálogo
        setSize(400, 500);
        setLocationRelativeTo(parent);

        // Crea un panel con GridBagLayout para el formulario
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Campo Tipo
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        String[] tipos = {"Mecánico", "Eléctrico", "Carrocería", "Consumo"};
        tipoCombo = new JComboBox<>(tipos);
        panel.add(tipoCombo, gbc);

        // Campo Marca
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1;
        marcaField = new JTextField(20);
        panel.add(marcaField, gbc);

        // Campo Modelo
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        modeloField = new JTextField(20);
        panel.add(modeloField, gbc);

        // Campo Proveedor
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        String[] proveedores = {"Proveedor A", "Proveedor B", "Proveedor C"};
        proveedorCombo = new JComboBox<>(proveedores);
        panel.add(proveedorCombo, gbc);

        // Campo Cantidad
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        cantidadSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        panel.add(cantidadSpinner, gbc);

        // Campo Nivel Mínimo
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Nivel mínimo:"), gbc);
        gbc.gridx = 1;
        nivelMinimoSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        panel.add(nivelMinimoSpinner, gbc);

        // Campo Fecha de Ingreso
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Fecha de ingreso (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        fechaIngresoField = new JTextField(20);
        panel.add(fechaIngresoField, gbc);

        // Campo Vida Útil
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Vida útil (días):"), gbc);
        gbc.gridx = 1;
        vidaUtilField = new JTextField(20);
        panel.add(vidaUtilField, gbc);

        // Campo Estado
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        String[] estados = {"Disponible", "Reservado", "Fuera de servicio"};
        estadoCombo = new JComboBox<>(estados);
        panel.add(estadoCombo, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> {
            // Muestra un mensaje (en una aplicación real, guardaría los datos)
            JOptionPane.showMessageDialog(this, "Repuesto guardado (no realmente)", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Agrega los paneles al diálogo
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}