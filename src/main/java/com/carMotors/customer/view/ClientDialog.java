package com.carMotors.customer.view;

import com.carMotors.customer.model.Client;

import javax.swing.*;
import java.awt.*;

public class ClientDialog extends JDialog {
    private JTextField nameField, idField, phoneField, emailField, addressField;
    private JButton okButton, cancelButton;
    private Client client; // Holds the client being edited, or null for new client
    private boolean confirmed = false;

    public ClientDialog(Frame owner, String title, Client clientToEdit) {
        super(owner, title, true); // Modal dialog
        this.client = clientToEdit;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(owner);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels and Fields
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        nameField = new JTextField(20); formPanel.add(nameField, gbc);
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Identification:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        idField = new JTextField(20); formPanel.add(idField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        phoneField = new JTextField(20); formPanel.add(phoneField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20); formPanel.add(emailField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        addressField = new JTextField(20); formPanel.add(addressField, gbc);
        gbc.fill = GridBagConstraints.NONE;

        // Populate fields if editing
        if (client != null) {
            nameField.setText(client.getName());
            idField.setText(String.valueOf(client.getIdentification()));
            phoneField.setText(String.valueOf(client.getPhone()));
            emailField.setText(client.getEmail());
            addressField.setText(client.getAddress());
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Add Action Listeners
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        // Basic validation (more robust validation needed in real app)
        if (nameField.getText().trim().isEmpty() || idField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Identification, and Phone cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            long identification = Long.parseLong(idField.getText().trim());
            long phone = Long.parseLong(phoneField.getText().trim());

            if (client == null) { // Creating new client
                client = new Client();
            }
            client.setName(nameField.getText().trim());
            client.setIdentification(identification);
            client.setPhone(phone);
            client.setEmail(emailField.getText().trim());
            client.setAddress(addressField.getText().trim());

            confirmed = true;
            setVisible(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Identification and Phone must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        confirmed = false;
        setVisible(false);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Client getClient() {
        return client;
    }
}

