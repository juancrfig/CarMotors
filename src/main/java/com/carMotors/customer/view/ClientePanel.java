package com.carMotors.customer.view;

import com.carMotors.customer.controller.ClientController;
import com.carMotors.customer.model.Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ClientePanel extends JPanel {
    private final ClientController controller;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    // --- Color Palette ---
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

    private final JTextArea areaListado = new JTextArea();

    public ClientePanel(ClientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(0, 10)); // Add gap between center and south
        setBackground(COLOR_BACKGROUND); // Main panel background

        // --- Bottom Menu Panel ---
        JPanel bottomMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Center buttons, add gaps
        bottomMenuPanel.setBackground(COLOR_BACKGROUND); // Match main background or use COLOR_COMPONENT_BG
        bottomMenuPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_COMPONENT_BG)); // Top border line

        JButton btnRegistrar = createMenuButton("📋 Register");
        JButton btnListar = createMenuButton("📑 List");
        JButton btnBuscar = createMenuButton("🔍 Search");
        JButton btnActualizar = createMenuButton("✏️ Update");

        btnRegistrar.addActionListener(e -> cardLayout.show(cardPanel, "Register"));
        btnListar.addActionListener(e -> {
            cardLayout.show(cardPanel, "List");
            cargarClientes();
        });
        btnBuscar.addActionListener(e -> cardLayout.show(cardPanel, "Search"));
        btnActualizar.addActionListener(e -> cardLayout.show(cardPanel, "Update"));

        bottomMenuPanel.add(btnRegistrar);
        bottomMenuPanel.add(btnListar);
        bottomMenuPanel.add(btnBuscar);
        bottomMenuPanel.add(btnActualizar);

        // --- Card Panel for Content ---
        cardPanel.setBackground(COLOR_BACKGROUND); // Match background
        cardPanel.setBorder(BorderFactory.createEmptyBorder()); // Remove default border if any

        cardPanel.add(crearPanelRegistro(), "Register");
        cardPanel.add(crearPanelListado(), "List");
        cardPanel.add(crearPanelBuscar(), "Search");
        cardPanel.add(crearPanelActualizar(), "Update");

        // Original menu on WEST is removed
        // add(menu, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        add(bottomMenuPanel, BorderLayout.SOUTH); // Add menu to the bottom

        // Ensure the first panel is shown
        cardLayout.show(cardPanel, "Register");
    }

    // Style for bottom menu buttons
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_MENU_BUTTON_BG); // Yellowish Cake
        btn.setForeground(COLOR_TEXT_DARK); // Dark text for contrast
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 35)); // Uniform size
        return btn;
    }

    // Style for action buttons within panels
    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(COLOR_ACTION_BUTTON_BG); // Steel Blue
        btn.setForeground(COLOR_ACTION_BUTTON_FG); // White text
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Helper to style JTextField
    private void styleTextField(JTextField field) {
        field.setBackground(COLOR_COMPONENT_BG);
        field.setForeground(COLOR_TEXT_DARK); // Dark text on gray background
        field.setFont(FONT_LABEL);
        field.setBorder(BORDER_COMPONENT);
        field.setCaretColor(COLOR_TEXT_DARK);
    }

    // Helper to style JTextArea
    private void styleTextArea(JTextArea area) {
        area.setBackground(COLOR_COMPONENT_BG);
        area.setForeground(COLOR_TEXT_DARK); // Dark text
        area.setFont(FONT_TEXT_AREA);
        area.setBorder(BORDER_COMPONENT);
        area.setCaretColor(COLOR_TEXT_DARK);
    }

    // Helper to style JLabel
    private void styleLabel(JLabel label) {
        label.setForeground(COLOR_TEXT_LIGHT); // White text on dark background
        label.setFont(FONT_LABEL);
    }

    // Helper to create a styled TitledBorder
    private TitledBorder createStyledTitledBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ACCENT, 1), // Border line color
                title
        );
        titledBorder.setTitleColor(COLOR_TEXT_LIGHT); // Title text color
        titledBorder.setTitleFont(FONT_TITLE);
        return titledBorder;
    }


    private JPanel crearPanelRegistro() {
        JPanel panel = createFormPanel("Register Customer");

        JTextField txtNombre = new JTextField();
        JTextField txtId = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPuntos = new JTextField("0");
        JTextField txtDescuento = new JTextField("0.0");
        JButton btnGuardar = createActionButton("✅ Save");

        styleTextField(txtNombre);
        styleTextField(txtId);
        styleTextField(txtTel);
        styleTextField(txtEmail);
        styleTextField(txtPuntos); txtPuntos.setEditable(false); txtPuntos.setToolTipText("Automatically calculated");
        styleTextField(txtDescuento); txtDescuento.setEditable(false); txtDescuento.setToolTipText("Automatically calculated");

        addFormField(panel, "Name:", txtNombre);
        addFormField(panel, "Identification:", txtId);
        addFormField(panel, "Phone:", txtTel);
        addFormField(panel, "Email:", txtEmail);
        addFormField(panel, "Points:", txtPuntos);
        addFormField(panel, "Discount %:", txtDescuento);

        // Add button with proper alignment if needed (GridLayout handles simple cases)
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                // --- Data validation could be added here ---
                if (txtNombre.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and Indentification are mandatory.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Client cliente = new Client();
                cliente.setName(txtNombre.getText().trim());
                cliente.setIdentification(txtId.getText().trim());
                cliente.setPhone(txtTel.getText().trim());
                cliente.setEmail(txtEmail.getText().trim());
                // Points and Discount are usually managed by the system/controller logic
                cliente.setDiscountPercentage(0.0); // Initial values
                cliente.setRewardPoints(0);

                controller.registerClient(cliente);

                JOptionPane.showMessageDialog(this, "✅ Customer registered with ID: " + cliente.getId(), "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields
                txtNombre.setText(""); txtId.setText(""); txtTel.setText(""); txtEmail.setText("");
                txtPuntos.setText("0"); txtDescuento.setText("0.0");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: Check numeric fields.", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error while registering: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(createStyledTitledBorder("Registered Clients"));
        panel.setBackground(COLOR_BACKGROUND);

        styleTextArea(areaListado);
        areaListado.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(areaListado);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scrollpane border
        scrollPane.getViewport().setBackground(COLOR_COMPONENT_BG); // Background visible through scrollpane

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void cargarClientes() {
        try {
            List<Client> lista = controller.listAllClients();
            areaListado.setText(""); // Clear previous content
            if (lista.isEmpty()) {
                areaListado.setText(" --- There's no registered clients ---");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Client c : lista) {
                    // Apply benefits potentially before displaying, if needed here
                    // controller.aplicarBeneficios(c); // Uncomment if benefits should be shown in list

                    sb.append("ID        : ").append(c.getId()).append("\n");
                    sb.append("Name    : ").append(c.getName()).append("\n");
                    sb.append("Ident.    : ").append(c.getIdentification()).append("\n");
                    sb.append("Phone  : ").append(c.getPhone()).append("\n");
                    sb.append("Email     : ").append(c.getEmail()).append("\n");
                    sb.append("Points    : ").append(c.getRewardPoints()).append("\n");
                    sb.append("Discount : ").append(String.format("%.1f%%", c.getDiscountPercentage())).append("\n");
                    sb.append("------------------------------------\n");
                }
                areaListado.setText(sb.toString());
                areaListado.setCaretPosition(0); // Scroll to top
            }
        } catch (Exception e) {
            areaListado.setText("❌ Error while loading clients: " + e.getMessage());
        }
    }

    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 15)); // Gaps
        panel.setBorder(createStyledTitledBorder("Search Client"));
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                createStyledTitledBorder("Search Client"),
                BORDER_PADDING // Add padding inside the border
        ));


        JTextField txtBuscar = new JTextField();
        JButton btnBuscarAction = createActionButton("🔎Search");
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        styleTextField(txtBuscar);
        styleTextArea(resultado);

        JPanel top = new JPanel(new BorderLayout(10, 0)); // Gap between label/field and button
        top.setBackground(COLOR_BACKGROUND); // Match parent background
        JLabel lblBuscar = new JLabel("Enter client ID:");
        styleLabel(lblBuscar);

        top.add(lblBuscar, BorderLayout.WEST);
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(btnBuscarAction, BorderLayout.EAST);

        btnBuscarAction.addActionListener(e -> {
            try {
                if (txtBuscar.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter an ID.", "Required field", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtBuscar.getText().trim());
                Client c = controller.findClientById(id);
                if (c != null) {
                    controller.aplicarBeneficios(c); // Apply benefits when found
                    resultado.setText("--- Client found ---\n");
                    resultado.append("ID        : " + c.getId() + "\n");
                    resultado.append("Name    : " + c.getName() + "\n");
                    resultado.append("Ident.    : " + c.getIdentification() + "\n");
                    resultado.append("Phone  : " + c.getPhone() + "\n");
                    resultado.append("Email     : " + c.getEmail() + "\n");
                    resultado.append("Points    : " + c.getRewardPoints() + "\n");
                    resultado.append("Discount : " + String.format("%.1f%%", c.getDiscountPercentage()));
                } else {
                    resultado.setText("--- Client with ID " + id + " not found. ---");
                }
            } catch (NumberFormatException ex) {
                resultado.setText("❌ Error: Enter a valid ID.");
            } catch (Exception ex) {
                resultado.setText("❌ Error while searching: " + ex.getMessage());
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
        JPanel panel = createFormPanel("Update Client");

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtIdent = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPuntos = new JTextField();
        JTextField txtDescuento = new JTextField();
        JButton btnCargar = createActionButton("📥 Load Data");
        JButton btnActualizarAction = createActionButton("💾Update");

        styleTextField(txtId);
        styleTextField(txtNombre);
        styleTextField(txtIdent);
        styleTextField(txtTel);
        styleTextField(txtEmail);
        styleTextField(txtPuntos); txtPuntos.setEditable(false); txtPuntos.setToolTipText("No editable");
        styleTextField(txtDescuento); txtDescuento.setEditable(false); txtDescuento.setToolTipText("No editable");

        // Using a slightly different layout for the load button
        JPanel loadPanel = new JPanel(new BorderLayout(5,0));
        loadPanel.setBackground(COLOR_BACKGROUND); // Match form background
        JLabel idLabel = new JLabel("Client ID to update:");
        styleLabel(idLabel);
        loadPanel.add(idLabel, BorderLayout.WEST);
        loadPanel.add(txtId, BorderLayout.CENTER);
        loadPanel.add(btnCargar, BorderLayout.EAST);
        panel.add(loadPanel); // Add this composite panel to the grid
        panel.add(new JLabel()); // Grid placeholder

        addFormField(panel, "Name:", txtNombre);
        addFormField(panel, "Ident.:", txtIdent);
        addFormField(panel, "Phone:", txtTel);
        addFormField(panel, "Email:", txtEmail);
        addFormField(panel, "Points:", txtPuntos); // Label changed for clarity
        addFormField(panel, "Discount %:", txtDescuento); // Label changed

        panel.add(new JLabel()); // Placeholder
        panel.add(btnActualizarAction);

        btnCargar.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter an ID", "Required field", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtId.getText().trim());
                Client c = controller.findClientById(id);
                if (c != null) {
                    controller.aplicarBeneficios(c); // Apply benefits before showing
                    txtNombre.setText(c.getName());
                    txtIdent.setText(c.getIdentification());
                    txtTel.setText(c.getPhone());
                    txtEmail.setText(c.getEmail());
                    txtPuntos.setText(String.valueOf(c.getRewardPoints()));
                    txtDescuento.setText(String.format("%.1f", c.getDiscountPercentage())); // Display raw number for potential future edit logic
                    txtNombre.requestFocusInWindow(); // Focus first editable field
                } else {
                    JOptionPane.showMessageDialog(this, "Client with ID " + id + " not found.", "Not found", JOptionPane.WARNING_MESSAGE);
                    // Clear fields if not found
                    txtNombre.setText(""); txtIdent.setText(""); txtTel.setText(""); txtEmail.setText("");
                    txtPuntos.setText(""); txtDescuento.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: Enter a valid ID.", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error while loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizarAction.addActionListener(e -> {
            try {
                if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and ID are mandatory.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = Integer.parseInt(txtId.getText().trim());
                // Find the client again to ensure it exists before updating
                Client c = controller.findClientById(id);
                if (c != null) {
                    // Update only user-editable fields
                    c.setName(txtNombre.getText().trim());
                    c.setIdentification(txtIdent.getText().trim());
                    c.setPhone(txtTel.getText().trim());
                    c.setEmail(txtEmail.getText().trim());
                    // Do NOT update points/discount here; they are managed by controller logic

                    controller.updateClient(c);
                    JOptionPane.showMessageDialog(this, "✅ Client updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Client with ID " + id + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: ID.", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error while updating: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // Creates the base panel for forms (Registro, Actualizar)
    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 12)); // Rows, Cols, Hgap, Vgap
        panel.setBackground(COLOR_BACKGROUND);
        // Add padding around the grid
        panel.setBorder(BorderFactory.createCompoundBorder(
                createStyledTitledBorder(title),
                BORDER_PADDING // Padding inside the border
        ));
        return panel;
    }

    // Adds a styled label and field to a form panel
    private void addFormField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        styleLabel(label);
        panel.add(label);
        panel.add(field);
    }
}