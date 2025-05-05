package com.carMotors.customer.view;

import com.carMotors.customer.controller.ClientController;
import com.carMotors.customer.model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientePanel extends JPanel {
    private final ClientController controller;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final Color bgColor = new Color(250, 250, 250);
    private final Color btnColor = new Color(33, 150, 243);

    private final JTextArea areaListado = new JTextArea();

    public ClientePanel(ClientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // Menu lateral
        JPanel menu = new JPanel(new GridLayout(5, 1, 10, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menu.setBackground(bgColor);

        JButton btnRegistrar = createMenuButton("📋 Registrar");
        JButton btnListar = createMenuButton("📑 Listar");
        JButton btnBuscar = createMenuButton("🔍 Buscar");
        JButton btnActualizar = createMenuButton("✏️ Actualizar");

        btnRegistrar.addActionListener(e -> cardLayout.show(cardPanel, "registrar"));
        btnListar.addActionListener(e -> {
            cardLayout.show(cardPanel, "listar");
            cargarClientes();
        });
        btnBuscar.addActionListener(e -> cardLayout.show(cardPanel, "buscar"));
        btnActualizar.addActionListener(e -> cardLayout.show(cardPanel, "actualizar"));

        menu.add(btnRegistrar);
        menu.add(btnListar);
        menu.add(btnBuscar);
        menu.add(btnActualizar);

        cardPanel.add(crearPanelRegistro(), "registrar");
        cardPanel.add(crearPanelListado(), "listar");
        cardPanel.add(crearPanelBuscar(), "buscar");
        cardPanel.add(crearPanelActualizar(), "actualizar");

        add(menu, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(btnColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = createFormPanel("Registrar Cliente");

        JTextField txtNombre = new JTextField();
        JTextField txtId = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPuntos = new JTextField("0"); txtPuntos.setEditable(false);
        JTextField txtDescuento = new JTextField("0.0"); txtDescuento.setEditable(false);
        JButton btnGuardar = new JButton("✅ Guardar");

        addFormField(panel, "Nombre:", txtNombre);
        addFormField(panel, "Identificación:", txtId);
        addFormField(panel, "Teléfono:", txtTel);
        addFormField(panel, "Email:", txtEmail);
        addFormField(panel, "Puntos:", txtPuntos);
        addFormField(panel, "Descuento %:", txtDescuento);
        panel.add(new JLabel()); panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                Client cliente = new Client();
                cliente.setName(txtNombre.getText());
                cliente.setIdentification(txtId.getText());
                cliente.setPhone(txtTel.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setDiscountPercentage(0.0);
                cliente.setRewardPoints(0);

                controller.registerClient(cliente);

                JOptionPane.showMessageDialog(this, "✅ Cliente registrado con ID: " + cliente.getId());

                txtNombre.setText(""); txtId.setText(""); txtTel.setText(""); txtEmail.setText("");
                txtPuntos.setText("0"); txtDescuento.setText("0.0");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Clientes Registrados"));
        areaListado.setEditable(false);
        areaListado.setFont(new Font("Consolas", Font.PLAIN, 13));
        panel.add(new JScrollPane(areaListado), BorderLayout.CENTER);
        return panel;
    }

    private void cargarClientes() {
        try {
            List<Client> lista = controller.listAllClients();
            areaListado.setText("");
            for (Client c : lista) {
                areaListado.append("ID: " + c.getId() +
                        ", Nombre: " + c.getName() +
                        ", Tel: " + c.getPhone() +
                        ", Email: " + c.getEmail() +
                        ", Puntos: " + c.getRewardPoints() +
                        ", Descuento: " + c.getDiscountPercentage() + "%\n");
            }
        } catch (Exception e) {
            areaListado.setText("❌ Error: " + e.getMessage());
        }
    }

    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Cliente"));

        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("🔎 Buscar");
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Ingrese ID:"), BorderLayout.WEST);
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(btnBuscar, BorderLayout.EAST);

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtBuscar.getText());
                Client c = controller.findClientById(id);
                if (c != null) {
                    controller.aplicarBeneficios(c);
                    resultado.setText("ID: " + c.getId() +
                            "\nNombre: " + c.getName() +
                            "\nTeléfono: " + c.getPhone() +
                            "\nEmail: " + c.getEmail() +
                            "\nPuntos: " + c.getRewardPoints() +
                            "\nDescuento: " + c.getDiscountPercentage() + "%");
                } else {
                    resultado.setText("Cliente no encontrado.");
                }
            } catch (Exception ex) {
                resultado.setText("❌ Error: " + ex.getMessage());
            }
        });

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultado), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelActualizar() {
        JPanel panel = createFormPanel("Actualizar Cliente");

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtIdent = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtPuntos = new JTextField(); txtPuntos.setEditable(false);
        JTextField txtDescuento = new JTextField(); txtDescuento.setEditable(false);
        JButton btnCargar = new JButton("📥 Cargar");
        JButton btnActualizar = new JButton("💾 Actualizar");

        addFormField(panel, "ID Cliente:", txtId); panel.add(new JLabel()); panel.add(btnCargar);
        addFormField(panel, "Nombre:", txtNombre);
        addFormField(panel, "Identificación:", txtIdent);
        addFormField(panel, "Teléfono:", txtTel);
        addFormField(panel, "Email:", txtEmail);
        addFormField(panel, "Puntos:", txtPuntos);
        addFormField(panel, "Descuento %:", txtDescuento);
        panel.add(new JLabel()); panel.add(btnActualizar);

        btnCargar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Client c = controller.findClientById(id);
                if (c != null) {
                    controller.aplicarBeneficios(c);
                    txtNombre.setText(c.getName());
                    txtIdent.setText(c.getIdentification());
                    txtTel.setText(c.getPhone());
                    txtEmail.setText(c.getEmail());
                    txtPuntos.setText(String.valueOf(c.getRewardPoints()));
                    txtDescuento.setText(c.getDiscountPercentage() + "%");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Client c = controller.findClientById(id);
                if (c != null) {
                    c.setName(txtNombre.getText());
                    c.setIdentification(txtIdent.getText());
                    c.setPhone(txtTel.getText());
                    c.setEmail(txtEmail.getText());
                    controller.updateClient(c);
                    JOptionPane.showMessageDialog(this, "✅ Cliente actualizado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createFormPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void addFormField(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }
}