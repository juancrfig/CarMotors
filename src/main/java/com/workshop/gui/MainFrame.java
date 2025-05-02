package com.workshop.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private boolean darkMode = false;

    // Minimalist Corporate Palette
    private final Color LIGHT_BG = new Color(0xF5F5F5); // Light Gray
    private final Color LIGHT_FG = new Color(0x333333); // Dark Gray
    private final Color DARK_BG = new Color(0x222222);  // Dark Gray
    private final Color DARK_FG = new Color(0xEEEEEE);  // Light Gray
    private final Color ACCENT_COLOR = new Color(0x007ACC); // Corporate Blue (Microsoft Blue)
    private final Color ACCENT_HOVER = ACCENT_COLOR.brighter();

    public MainFrame() {
        setTitle("Automotive Workshop - Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = createStyledMenu("File");
        JMenuItem exitItem = createStyledMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu inventoryMenu = createStyledMenu("Inventory Management");
        JMenuItem addPartItem = createStyledMenuItem("Add Part");
        addPartItem.addActionListener(e -> {
            AddSpareDialog dialog = new AddSpareDialog(MainFrame.this);
            dialog.setDarkMode(darkMode);
            dialog.applyTheme(darkMode); // Ensure initial theme is applied
            dialog.setVisible(true);
        });
        inventoryMenu.add(addPartItem);
        menuBar.add(inventoryMenu);

        JMenu servicesMenu = createStyledMenu("Maintenance and Repairs");
        JMenuItem servicesItem = createStyledMenuItem("Manage Services");
        servicesItem.addActionListener(e -> showModuleDialog("Maintenance and Repairs"));
        servicesMenu.add(servicesItem);
        menuBar.add(servicesMenu);

        JMenu customersMenu = createStyledMenu("Customers and Billing");
        JMenuItem customersItem = createStyledMenuItem("Manage Customers");
        customersItem.addActionListener(e -> showModuleDialog("Customers and Billing"));
        customersMenu.add(customersItem);
        menuBar.add(customersMenu);

        JMenu suppliersMenu = createStyledMenu("Suppliers and Purchases");
        JMenuItem suppliersItem = createStyledMenuItem("Manage Suppliers");
        suppliersItem.addActionListener(e -> showModuleDialog("Suppliers and Purchases"));
        suppliersMenu.add(suppliersItem);
        menuBar.add(suppliersMenu);

        JMenu reportsMenu = createStyledMenu("Reports and Statistics");
        JMenuItem reportsItem = createStyledMenuItem("Generate Reports");
        reportsItem.addActionListener(e -> showModuleDialog("Reports and Statistics"));
        reportsMenu.add(reportsItem);
        menuBar.add(reportsMenu);

        JMenu helpMenu = createStyledMenu("Help");
        JMenuItem aboutItem = createStyledMenuItem("About");
        aboutItem.addActionListener(e -> showModuleDialog("Help - About"));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        JLabel welcomeLabel = new JLabel("Welcome to the Automotive Workshop Management System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton darkModeButton = new JButton("Toggle Dark Mode");
        darkModeButton.addActionListener(e -> toggleDarkMode());
        add(darkModeButton, BorderLayout.SOUTH);

        applyTheme();
    }

    private JMenu createStyledMenu(String text) {
        JMenu menu = new JMenu(text);
        return menu;
    }

    private JMenuItem createStyledMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        return menuItem;
    }

    private void showModuleDialog(String moduleName) {
        JOptionPane.showMessageDialog(this, "Selected module: " + moduleName, moduleName, JOptionPane.INFORMATION_MESSAGE);
    }

    private void toggleDarkMode() {
        darkMode = !darkMode;
        applyTheme();
        // Update AddSpareDialog if it's open
        for (Window window : Window.getWindows()) {
            if (window instanceof AddSpareDialog) {
                ((AddSpareDialog) window).applyTheme(darkMode);
            }
        }
    }

    private void applyTheme() {
        Color background = darkMode ? DARK_BG : LIGHT_BG;
        Color foreground = darkMode ? DARK_FG : LIGHT_FG;
        Color accent = ACCENT_COLOR;

        getContentPane().setBackground(background);
        getContentPane().setForeground(foreground);

        applyComponentStyle(this, background, foreground, accent);
    }

    private void applyComponentStyle(Component component, Color bg, Color fg, Color accent) {
        component.setBackground(bg);
        component.setForeground(fg);

        if (component instanceof JMenuBar) {
            component.setBackground(accent.darker()); // Use the accent color directly
            component.setForeground(fg);
        } else if (component instanceof JMenu) {
            ((JMenu) component).setForeground(fg);
        } else if (component instanceof JMenuItem) {
            ((JMenuItem) component).setForeground(fg);
            ((JMenuItem) component).setBackground(bg);
        } else if (component instanceof JLabel) {
            ((JLabel) component).setForeground(fg);
        } else if (component instanceof JButton) {
            ((JButton) component).setBackground(accent);
            ((JButton) component).setForeground(Color.WHITE); // Ensure good contrast on accent
            ((JButton) component).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(ACCENT_HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(accent);
                }
            });
        } else if (component instanceof JPanel) {
            component.setBackground(bg);
            component.setForeground(fg);
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyComponentStyle(child, bg, fg, accent);
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}