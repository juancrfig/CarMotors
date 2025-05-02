package com.workshop.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddSpareDialog extends JDialog {

    private JTextField nameField;
    private JComboBox<String> typeCombo;
    private JTextField brandField;
    private JTextField modelField;
    private JComboBox<String> supplierCombo;
    private JSpinner quantitySpinner;
    private JSpinner minimumLevelSpinner;
    private JTextField entryDateField;
    private JTextField lifespanField;
    private JComboBox<String> statusCombo;

    private boolean darkMode = false;

    // Minimalist Corporate Palette
    private final Color LIGHT_BG = new Color(0xF5F5F5); // Light Gray
    private final Color LIGHT_FG = new Color(0x333333); // Dark Gray
    private final Color DARK_BG = new Color(0x222222);  // Dark Gray
    private final Color DARK_FG = new Color(0xEEEEEE);  // Light Gray
    private final Color ACCENT_COLOR = new Color(0x007ACC); // Corporate Blue (Microsoft Blue)
    private final Color ACCENT_HOVER = ACCENT_COLOR.brighter();
    private final Color INPUT_BG_LIGHT = new Color(0xFFFFFF);
    private final Color INPUT_BG_DARK = new Color(0x444444);

    public AddSpareDialog(JFrame parent) {
        super(parent, "Add Spare Part", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        String[] types = {"Mechanical", "Electrical", "Bodywork", "Consumable"};
        typeCombo = new JComboBox<>(types);
        panel.add(typeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        brandField = new JTextField(20);
        panel.add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1;
        modelField = new JTextField(20);
        panel.add(modelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 1;
        String[] suppliers = {"Supplier A", "Supplier B", "Supplier C"};
        supplierCombo = new JComboBox<>(suppliers);
        panel.add(supplierCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Minimum Level:"), gbc);
        gbc.gridx = 1;
        minimumLevelSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        panel.add(minimumLevelSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Entry Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        entryDateField = new JTextField(20);
        panel.add(entryDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Lifespan (days):"), gbc);
        gbc.gridx = 1;
        lifespanField = new JTextField(20);
        panel.add(lifespanField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        String[] statuses = {"Available", "Reserved", "Out of Service"};
        statusCombo = new JComboBox<>(statuses);
        panel.add(statusCombo, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Spare part saved (not really)", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        applyTheme(false); // Apply initial theme
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
        applyTheme(darkMode);
    }

    public void applyTheme(boolean isDarkMode) {
        Color background = isDarkMode ? DARK_BG : LIGHT_BG;
        Color foreground = isDarkMode ? DARK_FG : LIGHT_FG;
        Color accent = ACCENT_COLOR;
        Color inputBg = isDarkMode ? INPUT_BG_DARK : INPUT_BG_LIGHT;

        getContentPane().setBackground(background);
        getContentPane().setForeground(foreground);

        applyComponentStyle(this, background, foreground, accent, inputBg);
    }

    private void applyComponentStyle(Component component, Color bg, Color fg, Color accent, Color inputBg) {
        component.setBackground(bg);
        component.setForeground(fg);

        if (component instanceof JLabel) {
            ((JLabel) component).setForeground(fg);
        } else if (component instanceof JTextField) {
            ((JTextField) component).setBackground(inputBg);
            ((JTextField) component).setForeground(fg);
        } else if (component instanceof JComboBox) {
            ((JComboBox) component).setBackground(inputBg);
            ((JComboBox) component).setForeground(fg);
        } else if (component instanceof JSpinner) {
            ((JSpinner) component).setBackground(inputBg);
            ((JSpinner) component).setForeground(fg);
        } else if (component instanceof JButton) {
            ((JButton) component).setBackground(accent);
            ((JButton) component).setForeground(Color.WHITE);
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
                applyComponentStyle(child, bg, fg, accent, inputBg);
            }
        }
        repaint();
    }
}