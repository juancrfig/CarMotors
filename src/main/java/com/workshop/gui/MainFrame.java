package com.workshop.gui;

import javax.swing.*;
import java.awt.*;

// Clase que representa la ventana principal de la aplicación
public class MainFrame extends JFrame {

    public MainFrame() {
        // Establece el título de la ventana
        setTitle("Taller Automotriz - Sistema de Gestión");
        // Define el tamaño de la ventana
        setSize(800, 600);
        // Centra la ventana en la pantalla
        setLocationRelativeTo(null);
        // Cierra la aplicación al cerrar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea la barra de menús
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");
        // Acción para cerrar la aplicación
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Menú Gestión de Inventarios
        JMenu inventoryMenu = new JMenu("Gestión de Inventarios");
        JMenuItem addRepuestoItem = new JMenuItem("Agregar Repuesto");
        // Abre el diálogo para agregar un repuesto
        addRepuestoItem.addActionListener(e -> {
            AddSpareDialog dialog = new AddSpareDialog(MainFrame.this);
            dialog.setVisible(true);
        });
        inventoryMenu.add(addRepuestoItem);
        menuBar.add(inventoryMenu);

        // Menú Mantenimiento y Reparaciones
        JMenu servicesMenu = new JMenu("Mantenimiento y Reparaciones");
        menuBar.add(servicesMenu);

        // Menú Clientes y Facturación
        JMenu customersMenu = new JMenu("Clientes y Facturación");
        menuBar.add(customersMenu);

        // Menú Proveedores y Compras
        JMenu suppliersMenu = new JMenu("Proveedores y Compras");
        menuBar.add(suppliersMenu);

        // Menú Reportes y Estadísticas
        JMenu reportsMenu = new JMenu("Reportes y Estadísticas");
        menuBar.add(reportsMenu);

        // Menú Ayuda
        JMenu helpMenu = new JMenu("Ayuda");
        menuBar.add(helpMenu);

        // Establece la barra de menús en la ventana
        setJMenuBar(menuBar);

        // Agrega una etiqueta de bienvenida al área de contenido
        JLabel label = new JLabel("Bienvenido al Sistema de Gestión de Taller Automotriz", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        // Asegura que la GUI se cree en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}