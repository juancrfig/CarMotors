package com.carMotors.shared.view;

import com.carMotors.customer.controller.ClientController;
import com.carMotors.customer.controller.VehicleController;
import com.carMotors.customer.model.IClienteDAO;
import com.carMotors.customer.model.IVehicleDAO;
import com.carMotors.customer.model.ClientDAO;
import com.carMotors.customer.model.VehicleDAO;
import com.carMotors.customer.view.ClientePanel;
import com.carMotors.customer.view.VehicleView;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    private JTabbedPane tabbedPane;
    private ClientePanel clientPanel;
    private VehicleView vehicleView;
    private JPanel inventoryPanel;
    private JPanel maintenancePanel;
    private JPanel supplierPanel;
    private JPanel reportsPanel;

    private ClientController clientController;
    private VehicleController vehicleController;
    private IClienteDAO clientDAO;
    private IVehicleDAO vehicleDAO;

    public MainMenuView() {
        setTitle("Automotive Workshop Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize DAOs (using real implementations)
        clientDAO = new ClientDAO();
        vehicleDAO = new VehicleDAO();

        // Initialize Controllers
        clientController = new ClientController(clientDAO, this);
        vehicleController = new VehicleController(vehicleDAO);

        // Initialize Panels
        clientPanel = new ClientePanel(clientController);
        vehicleView = new VehicleView(vehicleController);
        inventoryPanel = createPlaceholderPanel("Inventory Management - Under Construction");
        maintenancePanel = createPlaceholderPanel("Maintenance and Repairs - Under Construction");
        supplierPanel = createPlaceholderPanel("Suppliers and Purchasing - Under Construction");
        reportsPanel = createPlaceholderPanel("Reports and Statistics - Under Construction");

        // Setup Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Vehicles", vehicleView);
        tabbedPane.addTab("Inventory Management", inventoryPanel);
        tabbedPane.addTab("Maintenance & Repairs", maintenancePanel);
        tabbedPane.addTab("Suppliers & Purchasing", supplierPanel);
        tabbedPane.addTab("Reports & Statistics", reportsPanel);

        // Add Tabbed Pane to Frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Helper method to create simple placeholder panels
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // No need for a main method here, it's launched from com.carMotors.Main
}