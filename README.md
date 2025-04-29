# CarMotors: Automotive Workshop Management System

## Description
This project aims to develop a comprehensive system to optimize the daily operations of an automotive workshop. It is designed to manage inventory of spare parts, maintenance processes, customer service, billing, and supplier relationships, ensuring efficient and organized workshop management.
Features and Modules
1. Inventory Management of Spare Parts

Registration of Spare Parts: Register and organize spare parts with attributes including name, type (mechanical, electrical, bodywork, consumables), brand and model compatibility, associated supplier, stock quantity, minimum level for alerts, entry date, estimated lifespan, and status (available, reserved, out of service).
Batch and Traceability Management: Assign unique identification to batches linked to entry date and supplier, record usage in each job, and generate alerts for products nearing expiration or depletion.
Restocking Control: Automatically generate purchase orders based on minimum stock levels and track pending orders with suppliers.
Inventory Reports: Produce detailed lists organized by type, brand, or status, consumption analysis over periods, and alerts for products exceeding their lifespan.

2. Maintenance Services Management

Service Registration: Register services such as preventive or corrective maintenance, capturing vehicle details (brand, model, plate, type), work description, parts used, estimated time, labor cost, and service status (pending, in progress, completed, delivered).
Workflow Management:
Vehicle Reception: Register client and vehicle data with an initial inspection.
Maintenance Execution: Assign tasks to technicians and track progress in real-time.
Vehicle Delivery: Verify completed work and generate a signed delivery order automatically.


Maintenance Reports: Analyze most requested services by vehicle type, technician productivity, and detailed maintenance history per client or vehicle.

3. Customer Management

Customer Registration: Store customer data (name, ID, phone, email), service history (vehicles serviced, maintenance performed, billing), and automate reminders for preventive maintenance or service renewals.
Loyalty Programs: Offer discounts for frequent services and rewards for regular customers.

4. Electronic Billing

Invoice Generation: Automatically create visual invoices in PDF or PNG format, compliant with Colombian DIAN regulations (Resolution 042 of 2020). Invoices include workshop details (e.g., "Motores & Ruedas" NIT, address, contact), customer information, service description, parts used, labor costs, subtotals, totals, invoice number, issue date, CUFE, and a QR code linking to the fiscal receipt with a digital signature.
Automation and Distribution: Pull data from inventory and maintenance modules, calculate taxes per Colombian norms, and enable downloading or emailing invoices to customers.

5. Supplier Management

Supplier Registration: Record supplier details (name, NIT, contact, visit frequency) and products supplied (types, quantities, dates).
Supplier Evaluation: Assess suppliers based on punctuality, product quality, and costs, generating performance reports for future negotiations.

6. Special Activities Management

Preventive Maintenance Campaigns: Register promotions, schedule appointments automatically for frequent customers, and generate campaign success reports.
Specialized Technical Inspections: Record specific inspections, results (approved, repairs needed, rejected), and schedule future reviews.

System Architecture
The system is developed in Java using Maven, adhering to the Model-View-Controller (MVC) architecture. Each component (e.g., inventory, maintenance, customers) is organized in separate packages, each with its own MVC structure. The design is optimized for mobile access, enabling technicians and administrators to consult real-time data.
Technologies Used

Java
Maven
MySQL

Installation and Setup

Clone the repository from GitHub.
Ensure Java and Maven are installed on your system.
Set up the MySQL database using the provided SQL files.
Configure database connection settings in the application.
Build the project using Maven.
Run the application.

Usage
The system features a user-friendly interface with the following menus:

Inventory Management
Maintenance and Repairs
Customers and Billing
Suppliers and Purchases
Reports and Statistics

Users can navigate these menus to perform operations specific to each module.
Deliverables

ER Database Diagram: Complete diagram located at docs/er_diagram.png.
Class Diagram: Complete diagram located at docs/class_diagram.png.
SQL Files: MySQL database scripts in the sql/ directory.
Database Tables per Component: List available at docs/tables_per_component.md.
Classes per Component: List available at docs/classes_per_component.md.

Note
This project is developed in Java with Maven, using an MVC architecture. All code, commits, and documentation are in English, fulfilling the assignment requirements. The GitHub repository contains the source code, database diagrams, SQL files, and additional documentation as specified.

