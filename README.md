# Automotive Workshop Management System

## Project Overview

This project implements a management system for an automotive workshop, designed to handle operations such as spare parts inventory, maintenance services, customer management, supplier relationships, and billing. The system follows a component-based Model-View-Controller (MVC) architecture and is built using Java with Maven.

## Features (Based on Specifications)

*   **Inventory Management:** Tracks spare parts (type, brand, model, cost, stock), manages batches, handles restocking alerts, and generates inventory reports.
*   **Maintenance Management:** Registers services (preventive/corrective), manages workflows (reception, execution, delivery), tracks work progress, and generates maintenance reports.
*   **Customer Management:** Manages customer data, service history, and includes features for loyalty programs and reminders.
*   **Billing Management:** Generates electronic invoices (visual format like PDF/PNG) based on completed services, including necessary details like CUFE and QR codes (as per Colombian regulations specified).
*   **Supplier Management:** Manages supplier information and tracks supplied products.
*   **Employee Management:** Manages employee details and specialities.
*   **Special Activities:** Supports management of promotional campaigns and specialized inspections.

## Architecture

*   **Language:** Java (JDK 11)
*   **Build Tool:** Maven
*   **Architecture:** Component-based MVC
    *   Each major functional area (Inventory, Maintenance, Customer, Supplier, Billing, Employee) is separated into its own component package.
    *   Each component package contains `model`, `view`, and `controller` subpackages.
    *   A `core` package contains shared utilities like the `DatabaseManager`.
*   **Database:** MySQL (Schema provided separately in `database_schema.sql`)
*   **GUI:** Java Swing (Structure prepared, implementation pending)

## Project Structure

The project follows a standard Maven layout:

```
automotive-workshop/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── workshop/
│   │   │           ├── app/         # Main application entry point (pending)
│   │   │           ├── core/
│   │   │           │   ├── model/     # Shared models (if any)
│   │   │           │   └── util/      # E.g., DatabaseManager.java
│   │   │           ├── inventory/
│   │   │           │   ├── model/     # Inventory POJOs and DAO interfaces
│   │   │           │   ├── view/      # Inventory Swing views (pending)
│   │   │           │   └── controller/ # Inventory controllers (pending)
│   │   │           ├── maintenance/
│   │   │           │   ├── model/
│   │   │           │   ├── view/
│   │   │           │   └── controller/
│   │   │           ├── customer/
│   │   │           │   ├── model/
│   │   │           │   ├── view/
│   │   │           │   └── controller/
│   │   │           ├── supplier/
│   │   │           │   ├── model/
│   │   │           │   ├── view/
│   │   │           │   └── controller/
│   │   │           ├── billing/
│   │   │           │   ├── model/
│   │   │           │   ├── view/
│   │   │           │   └── controller/
│   │   │           └── employee/
│   │   │               ├── model/
│   │   │               ├── view/
│   │   │               └── controller/
│   │   └── resources/ # Resource files (if any)
│   └── test/        # Unit tests (pending)
├── database_schema.sql
├── class_diagram.png
├── er_diagram.png
├── class_diagram_description.md
├── mvc_structure_description.md
├── component_table_mapping.md
├── component_class_mapping.md
└── README.md
```

## Deliverables Included

*   Maven Project Structure: Source code structure with model classes and DAO interfaces.
*   Database Schema: `database_schema.sql` (as provided).
*   ER Diagram: `er_diagram.png`.
*   Class Diagram: `class_diagram.png`.
*   Component-Table Mapping: `component_table_mapping.md`.
*   Component-Class Mapping: `component_class_mapping.md`.
*   Architectural Descriptions: `class_diagram_description.md`, `mvc_structure_description.md`.

## Next Steps / Pending Implementation

*   Implementation of DAO classes (JDBC logic for database interaction).
*   Implementation of Controller classes (application logic).
*   Implementation of View classes (Java Swing GUI).
*   Implementation of the main application entry point.
*   Unit and integration testing.
*   Configuration of database connection details in `DatabaseManager.java`.

This README provides a snapshot based on the generated structure and initial design documents.
