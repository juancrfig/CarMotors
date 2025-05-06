package com.carMotors.shared.view;

import com.carMotors.core.util.DatabaseManager;
import com.carMotors.customer.controller.ClientController;
import com.carMotors.customer.controller.VehicleController;
import com.carMotors.customer.model.IClienteDAO;
import com.carMotors.customer.model.IVehicleDAO;
import com.carMotors.customer.model.ClientDAO;
import com.carMotors.customer.model.VehicleDAO;
import com.carMotors.customer.view.ClientePanel;
import com.carMotors.customer.view.VehicleView;
import com.carMotors.inventory.controller.SparePartController;
import com.carMotors.inventory.model.SparePartDAO;
import com.carMotors.inventory.view.SparePartView;
import com.carMotors.maintenance.controller.ServiceController;
import com.carMotors.maintenance.controller.ServiceOrderController;
import com.carMotors.maintenance.model.ServiceDAO;
import com.carMotors.maintenance.model.ServiceOrderDAO;
import com.carMotors.maintenance.view.ServiceView;
import com.carMotors.maintenance.view.ServiceOrderView;
import com.carMotors.supplier.controller.SupplierController;
import com.carMotors.supplier.model.SupplierDAO;
import com.carMotors.supplier.view.SupplierView;

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
    private IClienteDAO clientDAO;
    private IVehicleDAO vehicleDAO;
    private SparePartDAO sparePartDAO;
    private SupplierDAO supplierDAO;
    private ServiceDAO serviceDAO;
    private ServiceOrderDAO serviceOrderDAO;
    private Connection connection;

    public MainMenuView() {
        setTitle("Automotive Workshop Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            // Inicializa la conexión a la base de datos
            connection = DatabaseManager.getConnection();

            // Inicializa DAOs con conexión válida
            clientDAO = new ClientDAO();
            vehicleDAO = new VehicleDAO();
            sparePartDAO = new SparePartDAO(connection);
            supplierDAO = new SupplierDAO(connection);
            serviceDAO = new ServiceDAO(connection);
            serviceOrderDAO = new ServiceOrderDAO(connection, serviceDAO);

            // Inicializa Controllers
            clientController = new ClientController(clientDAO, this);
            vehicleController = new VehicleController(vehicleDAO);
            sparePartController = new SparePartController(sparePartDAO, supplierDAO);
            supplierController = new SupplierController(supplierDAO);
            serviceController = new ServiceController(serviceDAO);
            serviceOrderController = new ServiceOrderController(serviceOrderDAO, vehicleDAO, serviceDAO);

            // Inicializa las Vistas
            clientPanel = new ClientePanel(clientController);
            vehicleView = new VehicleView(vehicleController);
            sparePartView = new SparePartView(sparePartController);
            supplierView = new SupplierView(supplierController);
            maintenancePanel = createMaintenancePanel();
            reportsPanel = createPlaceholderPanel("Reports and Statistics - Under Construction");

            // Configura las pestañas
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Clients", clientPanel);
            tabbedPane.addTab("Vehicles", vehicleView);
            tabbedPane.addTab("Spare Parts", sparePartView);
            tabbedPane.addTab("Suppliers", supplierView);
            tabbedPane.addTab("Maintenance & Repairs", maintenancePanel);
            tabbedPane.addTab("Reports & Statistics", reportsPanel);

            add(tabbedPane, BorderLayout.CENTER);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createMaintenancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane maintenanceTabs = new JTabbedPane();
        ServiceOrderView serviceOrderView = new ServiceOrderView(serviceOrderController, serviceController, vehicleDAO);
        ServiceView serviceView = new ServiceView(serviceController, serviceOrderView);
        maintenanceTabs.addTab("Services", serviceView);
        maintenanceTabs.addTab("Service Orders", serviceOrderView);
        panel.add(maintenanceTabs, BorderLayout.CENTER);
        return panel;
    }

    // Paneles de texto temporal
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.ITALIC, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}