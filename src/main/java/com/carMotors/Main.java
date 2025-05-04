// Main.java
package com.carMotors;

import javax.swing.SwingUtilities;
import com.carMotors.shared.view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenuView().setVisible(true);
        });
    }
}