// Main.java
package com.carMotors;

import javax.swing.SwingUtilities;
import com.carMotors.shared.view.MainMenuView;
import com.carMotors.core.util.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        // Probar conexión antes de abrir la vista
        try {
            DatabaseManager.getConnection(); // Intentar conectar a la base de datos
            System.out.println("✅ Conexión exitosa con la base de datos.");
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            return; // Detener ejecución si no se puede conectar
        }

        // Iniciar interfaz Swing
        SwingUtilities.invokeLater(() -> {
            new MainMenuView().setVisible(true);
        });
    }
}
