package com.carMotors.shared.view;

import com.carMotors.customer.controller.CustomerController;
import com.carMotors.customer.model.ClientDAO;
import com.carMotors.customer.model.MockClientDAO; // Using Mock DAO
import com.carMotors.customer.view.CustomerManagementPanel;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    private JTabbedPane tabbedPane;
    private CustomerManagementPanel customerPanel;
    // Placeholder panels for other sections
    private JPanel inventoryPanel;
    private JPanel maintenancePanel;
    private JPanel supplierPanel;
    private JPanel reportsPanel;

    private CustomerController customerController;
    private ClientDAO clientDAO; // Using the interface type
    // Add other DAOs and Controllers as needed

    public MainMenuView() {
        setTitle("Automotive Workshop Management System");
        setSize(900, 700); // Increased size slightly
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize DAO (using Mock for now)
        clientDAO = new MockClientDAO();
        // Initialize other DAOs here (e.g., MockInventoryDAO, MockSupplierDAO etc.)

        // Initialize Panels
        customerPanel = new CustomerManagementPanel();
        inventoryPanel = createPlaceholderPanel("Inventory Management - Under Construction");
        maintenancePanel = createPlaceholderPanel("Maintenance and Repairs - Under Construction");
        supplierPanel = createPlaceholderPanel("Suppliers and Purchasing - Under Construction");
        reportsPanel = createPlaceholderPanel("Reports and Statistics - Under Construction");

        // Initialize Controllers
        customerController = new CustomerController(customerPanel, clientDAO, this); // Pass this JFrame for dialog ownership
        // Initialize other controllers here (e.g., InventoryController)

        // Setup Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customers & Billing", customerPanel); // Renamed tab
        tabbedPane.addTab("Inventory Management", inventoryPanel);
        tabbedPane.addTab("Maintenance & Repairs", maintenancePanel);
        tabbedPane.addTab("Suppliers & Purchasing", supplierPanel);
        tabbedPane.addTab("Reports & Statistics", reportsPanel);

        // Add Tabbed Pane to Frame
        add(tabbedPane, BorderLayout.CENTER);

        // Initial data load for customer panel is handled by the controller constructor
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

