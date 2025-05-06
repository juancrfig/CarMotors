# Car Workshop Management System
This project is a comprehensive Java-based application designed to optimize the daily operations of a car workshop. It provides tools for managing inventory, customer relations, maintenance services, billing, and supplier interactions, aiming to enhance productivity and organization within the workshop.
Features

**Inventory Management**: Track and manage spare parts with details like name, type, brand, supplier, stock levels, and more.
Customer Management: Register clients, their vehicles, and service history, with automatic reminders for preventive maintenance.
Maintenance Services: Manage service orders, assign tasks to technicians, and track the progress of repairs and maintenance work.
Billing: Generate electronic invoices compliant with local regulations, including automatic calculation of taxes and totals.
Supplier Management: Maintain supplier information, track supplied products, and evaluate supplier performance.

## Technologies Used

**Java**: The core programming language for the backend logic.
**MVC Architecture**: Organized into models, views, and controllers for better maintainability.
**Database**: Utilizes a relational database for persistent storage (configured via DatabaseManager).
**UI Framework**: Swing

### Project Structure
The project is organized into the following packages:

**billing**: Handles invoice generation and management.
**core**: Contains utility classes like DatabaseManager.
**customer**: Manages client and vehicle data.
**inventory**: Deals with spare parts inventory.
**maintenance**: Manages service orders and maintenance tasks.
**shared**: Shared views, like the main menu.
**supplier**: Manages supplier information and interactions.

#### Setup Instructions

**Java Development Kit (JDK)**: Ensure you have JDK 17 or higher installed.
**Database**: Set up a relational database MySQL.
**Build**: Compile the project using your preferred IDE or build tool (e.g., Maven, Gradle).

##### Usage

1. Run the Application: Execute the Main class to start the application.
2. Navigate the Menu: Use the main menu to access different modules like inventory, customers, maintenance, etc.
3. Perform Operations: Follow the on-screen instructions to manage inventory, register clients, create service orders, generate invoices, and more.
***
