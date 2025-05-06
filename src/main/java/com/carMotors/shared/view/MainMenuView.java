package view.mainMenu;

import controller.client.ClientController;
import controller.maintenance.ServiceController;
import controller.maintenance.ServiceOrderController;
import controller.spareParts.SparePartController;
import controller.supplier.SupplierController;
import controller.vehicle.VehicleController;
import dao.client.ClientDAO;
import dao.maintenance.ServiceDAO;
import dao.maintenance.ServiceOrderDAO;
import dao.spareParts.SparePartDAO;
import dao.supplier.SupplierDAO;
import dao.vehicle.VehicleDAO;
import model.DatabaseManager;
import view.client.ClientePanel;
import view.maintenance.MaintenancePanel;
import view.spareParts.SparePartView;
import view.supplier.SupplierView;
import view.vehicle.VehicleView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class MainMenuView extends JFrame {

    private JTabbedPane tabbedPane;

    private ClientePanel clientPanel;
    private VehicleView vehicleView;
    private SparePartView sparePartView;
    private SupplierView supplierView;
    private JPanel maintenancePanel;
    private JPanel reportsPanel;

    private ClientController clientController;
    private VehicleController vehicleController;
    private SparePartController sparePartController;
    private SupplierController supplierController;
    private ServiceController serviceController;
    private ServiceOrderController serviceOrderController;

    private Connection connection;

    private static final Color COLOR_BACKGROUND = new Color(245, 245, 245);
    private static final Color COLOR_TAB_BACKGROUND = new Color(220, 220, 220);
    private static final Color COLOR_TAB_FOREGROUND = new Color(50, 50, 50);
    private static final Color COLOR_TAB_SELECTED = new Color(100, 149, 237);
    private static final Color COLOR_TAB_SELECTED_FOREGROUND = Color.WHITE;

    public MainMenuView() {
        setTitle("Car Motors Workshop System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Look & Feel y estilos UI ---
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Nimbus L&F not found, using default.");
        }

        // Estilos para el tabbedPane
        UIManager.put("TabbedPane.background", COLOR_TAB_BACKGROUND);
        UIManager.put("TabbedPane.foreground", COLOR_TAB_FOREGROUND);
        UIManager.put("TabbedPane.selected", COLOR_TAB_SELECTED);
        UIManager.put("TabbedPane.selectHighlight", COLOR_TAB_SELECTED);
        UIManager.put("TabbedPane.focus", COLOR_TAB_SELECTED);
        UIManager.put("TabbedPane.contentAreaColor", COLOR_BACKGROUND);
        UIManager.put("TabbedPane.selectedForeground", COLOR_TAB_SELECTED_FOREGROUND);
        UIManager.put("TabbedPane.font", new Font("SansSerif", Font.BOLD, 12));
        UIManager.put("Panel.background", COLOR_BACKGROUND);

        try {
            connection = DatabaseManager.getConnection();

            clientDAO = new ClientDAO();
            vehicleDAO = new VehicleDAO();
            sparePartDAO = new SparePartDAO(connection);
            supplierDAO = new SupplierDAO(connection);
            serviceDAO = new ServiceDAO(connection);
            serviceOrderDAO = new ServiceOrderDAO(connection, serviceDAO);

            clientController = new ClientController(clientDAO, this);
            vehicleController = new VehicleController(vehicleDAO);
            sparePartController = new SparePartController(sparePartDAO, supplierDAO);
            supplierController = new SupplierController(supplierDAO);
            serviceController = new ServiceController(serviceDAO);
            serviceOrderController = new ServiceOrderController(serviceOrderDAO, vehicleDAO, serviceDAO);

            clientPanel = new ClientePanel(clientController);
            vehicleView = new VehicleView(vehicleController);
            sparePartView = new SparePartView(sparePartController);
            supplierView = new SupplierView(supplierController);
            maintenancePanel = new MaintenancePanel(serviceOrderController);
            reportsPanel = createPlaceholderPanel("Reports and Statistics - Under Construction");

            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Clients", clientPanel);
            tabbedPane.addTab("Vehicles", vehicleView);
            tabbedPane.addTab("Spare Parts", sparePartView);
            tabbedPane.addTab("Suppliers", supplierView);
            tabbedPane.addTab("Maintenance & Repairs", maintenancePanel);
            tabbedPane.addTab("Reports & Statistics", reportsPanel);

            getContentPane().setBackground(COLOR_BACKGROUND);
            add(tabbedPane, BorderLayout.CENTER);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.DARK_GRAY);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuView().setVisible(true));
    }
}
