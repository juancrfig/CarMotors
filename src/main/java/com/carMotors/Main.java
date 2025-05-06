// Main.java
package com.carMotors;

import javax.swing.SwingUtilities;
import com.carMotors.shared.view.MainMenuView;
import com.carMotors.core.util.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        // Iniciar interfaz Swing
        SwingUtilities.invokeLater(() -> {
            new MainMenuView().setVisible(true);
        });
    }
}
