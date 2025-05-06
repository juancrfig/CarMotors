package com.carMotors.customer.view;

import com.carMotors.customer.controller.VehicleController;
import com.carMotors.customer.model.Vehicle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class VehicleView extends JPanel {
    private final VehicleController controller;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    // --- Color Palette (Consistent with ClientePanel) ---
    private static final Color COLOR_BACKGROUND = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_COMPONENT_BG = new Color(0xA9, 0xA9, 0xA9); // Dark Gray
    private static final Color COLOR_ACCENT = new Color(0x46, 0x82, 0xB4); // Steel Blue
    private static final Color COLOR_TEXT_LIGHT = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_MENU_BUTTON_BG = new Color(0xFA, 0xFA, 0xD2); // LightGoldenrodYellow ("Yellowish Cake")
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
            new EmptyBorder(3, 5, 3, 5) // Padding inside text field
    );

    private final JTextArea areaListado = new JTextArea(); // Shared JTextArea for listing

    public VehicleView(VehicleController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 10)); // Gap
        setBackground(COLOR_BACKGROUND);

        // --- Bottom Menu Panel ---
        JPanel bottomMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomMenuPanel.setBackground(COLOR_BACKGROUND);
        bottomMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_COMPONENT_BG)); // Top line

        JButton btnRegistrar = createMenuButton("🚗 Registrar");
        JButton btnListar = createMenuButton("📋 Listar");
        JButton btnBuscar = createMenuButton("🔎 Buscar");
        JButton btnActualizar = createMenuButton("🛠️ Actualizar");

        btnRegistrar.addActionListener(e -> cardLayout.show(cardPanel, "registrar"));
        btnListar.addActionListener(e -> {
            cardLayout.show(cardPanel, "listar");
            cargarVehiculos(); // Load data when showing the list panel
        });
        btnBuscar.addActionListener(e -> cardLayout.show(cardPanel, "buscar"));
        btnActualizar.addActionListener(e -> cardLayout.show(cardPanel, "actualizar"));

        bottomMenuPanel.add(btnRegistrar);
        bottomMenuPanel.add(btnListar);
        bottomMenuPanel.add(btnBuscar);
        bottomMenuPanel.add(btnActualizar);


        // --- Card Panel for Content ---
        cardPanel.setBackground(COLOR_BACKGROUND);
        cardPanel.setBorder(BorderFactory.createEmptyBorder());

        cardPanel.add(crearPanelRegistro(), "registrar");
        cardPanel.add(crearPanelListado(), "listar");
        cardPanel.add(crearPanelBuscar(), "buscar");
        cardPanel.add(crearPanelActualizar(), "actualizar");

        // Add components to the main VehicleView panel
        add(cardPanel, BorderLayout.CENTER);
        add(bottomMenuPanel, BorderLayout.SOUTH);

        // Show initial panel
        cardLayout.show(cardPanel, "registrar");
    }

    // --- Style Helpers (Copied from ClientePanel for consistency) ---

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_MENU_BUTTON_BG);
        btn.setForeground(COLOR_TEXT_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 35));
        return btn;
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_ACTION_BUTTON_BG);
        btn.setForeground(COLOR_ACTION_BUTTON_FG);
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(COLOR_COMPONENT_BG);
        field.setForeground(COLOR_TEXT_DARK);
        field.setFont(FONT_LABEL);
        field.setBorder(BORDER_COMPONENT);
        field.setCaretColor(COLOR_TEXT_DARK);
    }

    private void styleTextArea(JTextArea area) {
        area.setBackground(COLOR_COMPONENT_BG);
        area.setForeground(COLOR_TEXT_DARK);
        area.setFont(FONT_TEXT_AREA);
        area.setBorder(BORDER_COMPONENT);
        area.setCaretColor(COLOR_TEXT_DARK);
    }

    private void styleLabel(JLabel label) {
        label.setForeground(COLOR_TEXT_LIGHT);
        label.setFont(FONT_LABEL);
    }

    private TitledBorder createStyledTitledBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT, 1),
                title
        );
        titledBorder.setTitleColor(COLOR_TEXT_LIGHT);
        titledBorder.setTitleFont(FONT_TITLE);
        return titledBorder;
    }

    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 12)); // Rows, Cols, Hgap, Vgap
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                createStyledTitledBorder(title),
                BORDER_PADDING
        ));
        return panel;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        styleLabel(label);
        panel.add(label);
        panel.add(field);
    }

    // --- Panel Creation Methods ---

    private JPanel crearPanelRegistro() {
        JPanel panel = createFormPanel("Registrar Vehículo");

        JTextField txtPlaca = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtClienteId = new JTextField();
        JButton btnGuardar = createActionButton("✅ Guardar Vehículo");

        styleTextField(txtPlaca);
        styleTextField(txtMarca);
        styleTextField(txtModelo);
        styleTextField(txtTipo);
        styleTextField(txtClienteId);

        addFormField(panel, "Placa:", txtPlaca);
        addFormField(panel, "Marca:", txtMarca);
        addFormField(panel, "Modelo:", txtModelo);
        addFormField(panel, "Tipo:", txtTipo);
        addFormField(panel, "ID Cliente Propietario:", txtClienteId);

        panel.add(new JLabel()); // Placeholder
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                // Basic Validation
                if (txtPlaca.getText().trim().isEmpty() || txtClienteId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Placa e ID Cliente son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Vehicle v = new Vehicle();
                v.setLicensePlate(txtPlaca.getText().trim().toUpperCase()); // Standardize plate format
                v.setBrand(txtMarca.getText().trim());
                v.setModel(txtModelo.getText().trim());
                v.setType(txtTipo.getText().trim());
                v.setClientId(Integer.parseInt(txtClienteId.getText().trim())); // Validate number format

                controller.registerVehicle(v);
                JOptionPane.showMessageDialog(this, "✅ Vehículo registrado con ID: " + v.getId(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                txtPlaca.setText(""); txtMarca.setText(""); txtModelo.setText("");
                txtTipo.setText(""); txtClienteId.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: ID Cliente debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }


    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(createStyledTitledBorder("Vehículos Registrados"));
        panel.setBackground(COLOR_BACKGROUND);

        styleTextArea(areaListado); // Use the shared text area
        areaListado.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(areaListado);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(COLOR_COMPONENT_BG);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void cargarVehiculos() {
        try {
            List<Vehicle> lista = controller.listAllVehicles();
            areaListado.setText(""); // Clear
            if (lista.isEmpty()) {
                areaListado.setText(" --- No hay vehículos registrados ---");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Vehicle v : lista) {
                    sb.append("ID        : ").append(v.getId()).append("\n");
                    sb.append("Placa     : ").append(v.getLicensePlate()).append("\n");
                    sb.append("Marca     : ").append(v.getBrand()).append("\n");
                    sb.append("Modelo    : ").append(v.getModel()).append("\n");
                    sb.append("Tipo      : ").append(v.getType()).append("\n");
                    sb.append("ID Cliente: ").append(v.getClientId()).append("\n");
                    sb.append("------------------------------------\n");
                }
                areaListado.setText(sb.toString());
                areaListado.setCaretPosition(0); // Scroll top
            }
        } catch (Exception e) {
            areaListado.setText("❌ Error al cargar vehículos: " + e.getMessage());
        }
    }


    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                createStyledTitledBorder("Buscar Vehículo"),
                BORDER_PADDING
        ));

        JTextField txtBuscar = new JTextField();
        JButton btnBuscarAction = createActionButton("🔎 Buscar");
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        styleTextField(txtBuscar);
        styleTextArea(resultado);

        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setBackground(COLOR_BACKGROUND);
        JLabel lblBuscar = new JLabel("Ingrese ID Vehículo:");
        styleLabel(lblBuscar);

        top.add(lblBuscar, BorderLayout.WEST);
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(btnBuscarAction, BorderLayout.EAST);

        btnBuscarAction.addActionListener(e -> {
            try {
                if (txtBuscar.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un ID para buscar.", "Entrada Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtBuscar.getText().trim());
                Vehicle v = controller.findVehicleById(id);
                if (v != null) {
                    resultado.setText("--- Vehículo Encontrado ---\n");
                    resultado.append("ID        : " + v.getId() + "\n");
                    resultado.append("Placa     : " + v.getLicensePlate() + "\n");
                    resultado.append("Marca     : " + v.getBrand() + "\n");
                    resultado.append("Modelo    : " + v.getModel() + "\n");
                    resultado.append("Tipo      : " + v.getType() + "\n");
                    resultado.append("ID Cliente: " + v.getClientId());
                } else {
                    resultado.setText("--- Vehículo con ID " + id + " no encontrado. ---");
                }
            } catch (NumberFormatException ex) {
                resultado.setText("❌ Error: Ingrese un ID numérico válido.");
            } catch (Exception ex) {
                resultado.setText("❌ Error al buscar: " + ex.getMessage());
            }
        });

        JScrollPane scrollPane = new JScrollPane(resultado);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(COLOR_COMPONENT_BG);

        panel.add(top, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private JPanel crearPanelActualizar() {
        JPanel panel = createFormPanel("Actualizar Vehículo"); // Using helper now

        JTextField txtId = new JTextField();
        JTextField txtPlaca = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtClienteId = new JTextField();
        JButton btnCargar = createActionButton("📥 Cargar Datos");
        JButton btnActualizarAction = createActionButton("💾 Actualizar Vehículo");

        styleTextField(txtId);
        styleTextField(txtPlaca);
        styleTextField(txtMarca);
        styleTextField(txtModelo);
        styleTextField(txtTipo);
        styleTextField(txtClienteId);

        // Composite panel for loading by ID
        JPanel loadPanel = new JPanel(new BorderLayout(5, 0));
        loadPanel.setBackground(COLOR_BACKGROUND);
        JLabel idLabel = new JLabel("ID Vehículo a Actualizar:");
        styleLabel(idLabel);
        loadPanel.add(idLabel, BorderLayout.WEST);
        loadPanel.add(txtId, BorderLayout.CENTER);
        loadPanel.add(btnCargar, BorderLayout.EAST);
        panel.add(loadPanel); // Add to grid
        panel.add(new JLabel()); // Placeholder

        // Add remaining fields using helper
        addFormField(panel, "Placa:", txtPlaca);
        addFormField(panel, "Marca:", txtMarca);
        addFormField(panel, "Modelo:", txtModelo);
        addFormField(panel, "Tipo:", txtTipo);
        addFormField(panel, "ID Cliente Propietario:", txtClienteId);

        panel.add(new JLabel()); // Placeholder
        panel.add(btnActualizarAction);

        btnCargar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese un ID para cargar.", "Entrada Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtId.getText().trim());
                Vehicle v = controller.findVehicleById(id);
                if (v != null) {
                    txtPlaca.setText(v.getLicensePlate());
                    txtMarca.setText(v.getBrand());
                    txtModelo.setText(v.getModel());
                    txtTipo.setText(v.getType());
                    txtClienteId.setText(String.valueOf(v.getClientId()));
                    txtPlaca.requestFocusInWindow(); // Focus first editable field
                } else {
                    JOptionPane.showMessageDialog(this, "Vehículo con ID " + id + " no encontrado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
                    // Clear fields if not found
                    txtPlaca.setText(""); txtMarca.setText(""); txtModelo.setText("");
                    txtTipo.setText(""); txtClienteId.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: Ingrese un ID numérico válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al cargar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizarAction.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty() || txtPlaca.getText().trim().isEmpty() || txtClienteId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Se requiere ID, Placa e ID Cliente para actualizar.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtId.getText().trim());
                Vehicle v = controller.findVehicleById(id); // Find again before update
                if (v != null) {
                    v.setLicensePlate(txtPlaca.getText().trim().toUpperCase());
                    v.setBrand(txtMarca.getText().trim());
                    v.setModel(txtModelo.getText().trim());
                    v.setType(txtTipo.getText().trim());
                    v.setClientId(Integer.parseInt(txtClienteId.getText().trim())); // Validate number

                    controller.updateVehicle(v);
                    JOptionPane.showMessageDialog(this, "✅ Vehículo actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Vehículo con ID " + id + " no encontrado. No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: ID y/o ID Cliente deben ser numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}