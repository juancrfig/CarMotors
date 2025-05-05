-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS railway;

-- Crear nueva base de datos con soporte para UTF-8 extendido
CREATE DATABASE railway CHARACTER SET utf8mb4;

-- Usar la base de datos
USE railway;

-- Table: Clients
CREATE TABLE Clients (
    client_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    identification VARCHAR(20) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- Table: Vehicles
CREATE TABLE Vehicles (
    vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    brand VARCHAR(50),
    model VARCHAR(50),
    license_plate VARCHAR(20) UNIQUE,
    type VARCHAR(20),
    FOREIGN KEY (client_id) REFERENCES Clients(client_id)
);

-- Table: Suppliers
CREATE TABLE Suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    tax_id VARCHAR(20),
    contact VARCHAR(100),
    visit_frequency VARCHAR(50)
);

-- Table: Spare Parts
CREATE TABLE SpareParts (
    part_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    category ENUM('Mechanical', 'Electrical', 'Bodywork', 'Consumable'),
    brand VARCHAR(50),
    model VARCHAR(50),
    supplier_id INT,
    stock_quantity INT,
    minimum_stock_level INT,
    entry_date DATE,
    lifespan_days INT,
    status ENUM('Available', 'ReservedForJob', 'OutOfService'),
    FOREIGN KEY (supplier_id) REFERENCES Suppliers(supplier_id)
);

-- Table: Spare Part Batches
CREATE TABLE Batches (
    batch_id INT PRIMARY KEY AUTO_INCREMENT,
    part_id INT,
    entry_date DATE,
    quantity INT,
    FOREIGN KEY (part_id) REFERENCES SpareParts(part_id)
);

-- Table: Maintenance Services
CREATE TABLE Services (
    service_id INT PRIMARY KEY AUTO_INCREMENT,
    type ENUM('Preventive', 'Corrective'),
    description TEXT,
    labor_cost DECIMAL(10,2),
    estimated_time INT
);

-- Table: Service Orders (record of performed maintenance)
CREATE TABLE ServiceOrders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id INT,
    service_id INT,
    status ENUM('Pending', 'In progress', 'Completed', 'Delivered'),
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id)
);

-- Table: Spare Parts Used in Services
CREATE TABLE UsedParts (
    used_part_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    part_id INT,
    quantity INT,
    batch_id INT,
    FOREIGN KEY (order_id) REFERENCES ServiceOrders(order_id),
    FOREIGN KEY (part_id) REFERENCES SpareParts(part_id),
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id)
);

-- Table: Technicians
CREATE TABLE Technicians (
    technician_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    specialty VARCHAR(100)
);

-- Table: Assignment of Technicians to Orders
CREATE TABLE Order_Technicians (
    order_id INT,
    technician_id INT,
    PRIMARY KEY (order_id, technician_id),
    FOREIGN KEY (order_id) REFERENCES ServiceOrders(order_id),
    FOREIGN KEY (technician_id) REFERENCES Technicians(technician_id)
);

-- Table: Invoices
CREATE TABLE Invoices (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    issue_date DATE,
    subtotal DECIMAL(10,2),
    tax DECIMAL(10,2),
    total DECIMAL(10,2),
    cufe VARCHAR(100),
    qr_url TEXT,
    FOREIGN KEY (order_id) REFERENCES ServiceOrders(order_id)
);

-- Table: Special Activities
CREATE TABLE SpecialActivities (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    type ENUM('Campaign', 'Inspection'),
    description TEXT,
    start_date DATE,
    end_date DATE
);

-- Table: Participation of Vehicles in Special Activities
CREATE TABLE VehicleActivities (
    activity_id INT,
    vehicle_id INT,
    result VARCHAR(100), -- Approved, Requires repairs, Rejected
    PRIMARY KEY (activity_id, vehicle_id),
    FOREIGN KEY (activity_id) REFERENCES SpecialActivities(activity_id),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id)
);

-- Table: Supplier Evaluations
CREATE TABLE SupplierEvaluations (
    evaluation_id INT PRIMARY KEY AUTO_INCREMENT,
    supplier_id INT,
    punctuality INT,
    quality INT,
    cost INT,
    comment TEXT,
    date DATE,
    FOREIGN KEY (supplier_id) REFERENCES Suppliers(supplier_id)
);

