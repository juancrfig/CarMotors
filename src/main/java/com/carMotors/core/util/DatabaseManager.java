package com.carMotors.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // Database connection details - Replace with your actual credentials and URL
    // Consider using a configuration file or environment variables for sensitive data
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carMotors?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "your_username"; // FIXME: Replace with actual username
    private static final String DB_PASSWORD = "your_password"; // FIXME: Replace with actual password

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DatabaseManager() {}

    /**
     * Gets the singleton database connection.
     * Establishes the connection if it doesn't exist or is closed.
     *
     * @return The database connection.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Ensure the MySQL JDBC driver is loaded
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish the connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found.");
                throw new SQLException("MySQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                System.err.println("Failed to connect to the database: " + e.getMessage());
                throw e; // Re-throw the exception to be handled by the caller
            }
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection: " + e.getMessage());
            }
            connection = null; // Ensure we get a new connection next time
        }
    }
    
    // Optional: Add methods for transaction management (commit, rollback) if needed
}

