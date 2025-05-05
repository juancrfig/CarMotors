package com.carMotors.core.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Dotenv dotenv = Dotenv.configure().load();

    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static Connection connection = null;

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✅ Conexión exitosa a la base de datos.");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Driver JDBC de MySQL no encontrado.");
                throw new SQLException("MySQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                System.err.println("❌ Falló la conexión a la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("🔒 Conexión cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
            connection = null;
        }
    }
}
