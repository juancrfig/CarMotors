package com.carMotors.customer.view;

import com.carMotors.customer.controller.VehicleController;
import com.carMotors.customer.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VehicleView extends JPanel {
    private final VehicleController controller;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    public VehicleView(VehicleController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        JPanel menu = new JPanel(new GridLayout(5, 1, 10, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menu.setBackground(new Color(230, 230, 250));

        JButton btnRegistrar = new JButton("🚗 Registrar");
        JButton btnListar = new JButton("📋 Listar");
        JButton btnBuscar = new JButton("🔎 Buscar");
        JButton btnActualizar = new JButton("🛠️ Actualizar");

        for (JButton btn : new JButton[]{btnRegistrar, btnListar, btnBuscar, btnActualizar}) {
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 255, 255));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            menu.add(btn);
        }

        btnRegistrar.addActionListener(e -> cardLayout.show(cardPanel, "registrar"));
        btnListar.addActionListener(e -> {
            cardLayout.show(cardPanel, "listar");
            cargarVehiculos();
        });
        btnBuscar.addActionListener(e -> cardLayout.show(cardPanel, "buscar"));
        btnActualizar.addActionListener(e -> cardLayout.show(cardPanel, "actualizar"));

        cardPanel.add(crearPanelRegistro(), "registrar");
        cardPanel.add(crearPanelListado(), "listar");
        cardPanel.add(crearPanelBuscar(), "buscar");
        cardPanel.add(crearPanelActualizar(), "actualizar");

        add(menu, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "registrar");
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar Vehículo"));
        panel.setBackground(Color.WHITE);

        JTextField txtPlaca = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtClienteId = new JTextField();
        JButton btnGuardar = new JButton("✅ Guardar");

        panel.add(new JLabel("Placa:")); panel.add(txtPlaca);
        panel.add(new JLabel("Marca:")); panel.add(txtMarca);
        panel.add(new JLabel("Modelo:")); panel.add(txtModelo);
        panel.add(new JLabel("Tipo:")); panel.add(txtTipo);
        panel.add(new JLabel("ID Cliente:")); panel.add(txtClienteId);
        panel.add(new JLabel()); panel.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                Vehicle v = new Vehicle();
                v.setLicensePlate(txtPlaca.getText());
                v.setBrand(txtMarca.getText());
                v.setModel(txtModelo.getText());
                v.setType(txtTipo.getText());
                v.setClientId(Integer.parseInt(txtClienteId.getText()));

                controller.registerVehicle(v);
                JOptionPane.showMessageDialog(this, "✅ Vehículo registrado con ID: " + v.getId());

                txtPlaca.setText(""); txtMarca.setText(""); txtModelo.setText("");
                txtTipo.setText(""); txtClienteId.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JTextArea areaListado = new JTextArea();

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Vehículos Registrados"));
        areaListado.setEditable(false);
        panel.add(new JScrollPane(areaListado), BorderLayout.CENTER);
        return panel;
    }

    private void cargarVehiculos() {
        try {
            List<Vehicle> lista = controller.listAllVehicles();
            areaListado.setText("");
            for (Vehicle v : lista) {
                areaListado.append("ID: " + v.getId() +
                        ", Placa: " + v.getLicensePlate() +
                        ", Marca: " + v.getBrand() +
                        ", Modelo: " + v.getModel() +
                        ", Tipo: " + v.getType() +
                        ", Cliente ID: " + v.getClientId() + "\n");
            }
        } catch (Exception e) {
            areaListado.setText("❌ Error: " + e.getMessage());
        }
    }

    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar Vehículo"));

        JTextField txtBuscar = new JTextField();
        JButton btnBuscar = new JButton("🔎 Buscar");
        JTextArea resultado = new JTextArea();
        resultado.setEditable(false);

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Ingrese ID:"), BorderLayout.WEST);
        top.add(txtBuscar, BorderLayout.CENTER);
        top.add(btnBuscar, BorderLayout.EAST);

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtBuscar.getText());
                Vehicle v = controller.findVehicleById(id);
                if (v != null) {
                    resultado.setText("ID: " + v.getId() +
                            "\nPlaca: " + v.getLicensePlate() +
                            "\nMarca: " + v.getBrand() +
                            "\nModelo: " + v.getModel() +
                            "\nTipo: " + v.getType() +
                            "\nCliente ID: " + v.getClientId());
                } else {
                    resultado.setText("Vehículo no encontrado.");
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
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Actualizar Vehículo"));

        JTextField txtId = new JTextField();
        JTextField txtPlaca = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtClienteId = new JTextField();
        JButton btnCargar = new JButton("📥 Cargar");
        JButton btnActualizar = new JButton("💾 Actualizar");

        panel.add(new JLabel("ID Vehículo:")); panel.add(txtId);
        panel.add(new JLabel()); panel.add(btnCargar);
        panel.add(new JLabel("Placa:")); panel.add(txtPlaca);
        panel.add(new JLabel("Marca:")); panel.add(txtMarca);
        panel.add(new JLabel("Modelo:")); panel.add(txtModelo);
        panel.add(new JLabel("Tipo:")); panel.add(txtTipo);
        panel.add(new JLabel("ID Cliente:")); panel.add(txtClienteId);
        panel.add(new JLabel()); panel.add(btnActualizar);

        btnCargar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Vehicle v = controller.findVehicleById(id);
                if (v != null) {
                    txtPlaca.setText(v.getLicensePlate());
                    txtMarca.setText(v.getBrand());
                    txtModelo.setText(v.getModel());
                    txtTipo.setText(v.getType());
                    txtClienteId.setText(String.valueOf(v.getClientId()));
                } else {
                    JOptionPane.showMessageDialog(this, "Vehículo no encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                Vehicle v = controller.findVehicleById(id);
                if (v != null) {
                    v.setLicensePlate(txtPlaca.getText());
                    v.setBrand(txtMarca.getText());
                    v.setModel(txtModelo.getText());
                    v.setType(txtTipo.getText());
                    v.setClientId(Integer.parseInt(txtClienteId.getText()));
                    controller.updateVehicle(v);
                    JOptionPane.showMessageDialog(this, "✅ Vehículo actualizado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        return panel;
    }
}