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
import javax.swing.plaf.basic.BasicTabbedPaneUI; // Import for potential UI customization
import java.awt.*;

public class MainMenuView extends JFrame {

    // --- Color Palette ---
    private static final Color COLOR_BACKGROUND = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_TAB_BACKGROUND = new Color(0xA9, 0xA9, 0xA9); // Dark Gray
    private static final Color COLOR_TAB_SELECTED = new Color(0x46, 0x82, 0xB4); // Steel Blue
    private static final Color COLOR_TAB_FOREGROUND = new Color(0x36, 0x45, 0x4F); // Charcoal
    private static final Color COLOR_TAB_SELECTED_FOREGROUND = Color.WHITE;
    private static final Color COLOR_PLACEHOLDER_TEXT = new Color(0x46, 0x82, 0xB4); // Steel Blue
    // private static final Color COLOR_ACCENT = new Color(0xFF, 0x63, 0x47); // Tomato (Unused for now)

    // --- UI Components ---
    private JTabbedPane tabbedPane;
    private ClientePanel clientPanel;
    private VehicleView vehicleView;
    private JPanel inventoryPanel;
    private JPanel maintenancePanel;
    private JPanel supplierPanel;
    private JPanel reportsPanel;

    // --- Controllers and DAOs (Unchanged) ---
    private ClientController clientController;
    private VehicleController vehicleController;
    private IClienteDAO clientDAO;
    private IVehicleDAO vehicleDAO;

    public MainMenuView() {
        setTitle("Automotive Workshop Management System - Modern Style");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Apply Look and Feel Settings (Optional but recommended) ---
        try {
            // Using Nimbus for a more modern base look, if available
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to the default L&F
            System.err.println("Nimbus L&F not found, using default.");
        }

        // --- Customize UI Manager Defaults for TabbedPane ---
        // These settings influence the look provided by the LookAndFeel
        UIManager.put("TabbedPane.background", COLOR_TAB_BACKGROUND); // Background of the tab area
        UIManager.put("TabbedPane.foreground", COLOR_TAB_FOREGROUND); // Text color for unselected tabs
        UIManager.put("TabbedPane.selected", COLOR_TAB_SELECTED); // Background color of the selected tab
        UIManager.put("TabbedPane.selectHighlight", COLOR_TAB_SELECTED); // Border/highlight color for selected tab
        UIManager.put("TabbedPane.focus", COLOR_TAB_SELECTED); // Focus indicator color
        UIManager.put("TabbedPane.contentAreaColor", COLOR_BACKGROUND); // Background behind the tabs' content
        UIManager.put("TabbedPane.selectedForeground", COLOR_TAB_SELECTED_FOREGROUND); // Text color for selected tab
        UIManager.put("TabbedPane.font", new Font("SansSerif", Font.BOLD, 12)); // Font for tabs

        // Control Panel background (used by placeholder panels)
        UIManager.put("Panel.background", COLOR_BACKGROUND);

        // --- Initialize DAOs (Unchanged) ---
        clientDAO = new ClientDAO();
        vehicleDAO = new VehicleDAO();

        // --- Initialize Controllers (Unchanged) ---
        clientController = new ClientController(clientDAO, this);
        vehicleController = new VehicleController(vehicleDAO);

        // --- Initialize Panels ---
        // These panels might need their own internal styling adjusted separately
        // if they don't inherit the UIManager settings as expected.
        clientPanel = new ClientePanel(clientController);
        vehicleView = new VehicleView(vehicleController);

        // Initialize Placeholder Panels using the new helper method
        inventoryPanel = createPlaceholderPanel("Inventory Management - Under Construction");
        maintenancePanel = createPlaceholderPanel("Maintenance and Repairs - Under Construction");
        supplierPanel = createPlaceholderPanel("Suppliers and Purchasing - Under Construction");
        reportsPanel = createPlaceholderPanel("Reports and Statistics - Under Construction");

        // --- Setup Tabbed Pane ---
        tabbedPane = new JTabbedPane();
        // The UIManager settings should style the tabs now.
        // We can still force colors if needed, but UIManager is preferred.
        // tabbedPane.setBackground(COLOR_TAB_BACKGROUND); // Less preferred now
        // tabbedPane.setForeground(COLOR_TAB_FOREGROUND); // Less preferred now

        tabbedPane.addTab("Clients", clientPanel);
        tabbedPane.addTab("Vehicles", vehicleView);
        tabbedPane.addTab("Inventory", inventoryPanel); // Shortened title
        tabbedPane.addTab("Maintenance", maintenancePanel); // Shortened title
        tabbedPane.addTab("Suppliers", supplierPanel); // Shortened title
        tabbedPane.addTab("Reports", reportsPanel); // Shortened title

        // --- Add Tabbed Pane to Frame ---
        // Set main content pane background
        getContentPane().setBackground(COLOR_BACKGROUND);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Helper method to create simple placeholder panels with the new style.
     * Uses the color constants defined in the class.
     *
     * @param text The text to display on the placeholder label.
     * @return A JPanel configured with the placeholder style.
     */
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        // Ensure panel background matches the content area background
        panel.setBackground(COLOR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        // Use a different font and the defined text color
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setForeground(COLOR_PLACEHOLDER_TEXT); // Use Steel Blue for text

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // No need for a main method here, it's launched from com.carMotors.Main
}