-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS carMotors;

-- Crear nueva base de datos con soporte para UTF-8 extendido
CREATE DATABASE carMotors CHARACTER SET utf8mb4;

-- Usar la base de datos
USE carMotors;

-- Tabla: clients
CREATE TABLE client (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    identification BIGINT,
    phone BIGINT,
    email VARCHAR(50),
    address VARCHAR(50),
    idVehicle int unsigned,
	FOREIGN KEY (idVehicle) REFERENCES vehicle(id) ON DELETE CASCADE
);

-- Tabla: vehicles
CREATE TABLE vehicle (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(20),
    model VARCHAR(30),
    plate VARCHAR(10),
    type ENUM('automobile', 'SUV', 'motorbike')
);

-- Tabla: employees
CREATE TABLE employee (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    phone BIGINT,
    speciality VARCHAR(20)
);

-- Tabla: services
CREATE TABLE service (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type ENUM('preventive', 'corrective'),
    description VARCHAR(100),
    estimatedTime INT,
    actualTime INT,
    stateService ENUM('pending', 'underway', 'finished')
);

-- Tabla: spareServices
create table spareService(
	idSpare int unsigned,
	idService int unsigned,
	quantity int,
	FOREIGN KEY (idSpare) REFERENCES sparePart(id) ON DELETE CASCADE,
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE CASCADE,
    PRIMARY KEY (idSpare, idService)
);

-- tabla: employeeService (relacion N:M)
create table employeeService (
	idEmployee int unsigned,
	idService int unsigned,
	FOREIGN KEY (idEmployee) REFERENCES employee(id) ON DELETE CASCADE,
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE CASCADE,
    PRIMARY KEY (idEmployee, idService)
);

-- Tabla: serviceVehicle (relación N:M)
CREATE TABLE serviceVehicle (
    idService INT UNSIGNED,
    idVehicle INT UNSIGNED,
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE CASCADE,
    FOREIGN KEY (idVehicle) REFERENCES vehicle(id) ON DELETE CASCADE,
    PRIMARY KEY (idService, idVehicle)
);

-- Tabla: bills
CREATE TABLE bill (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    idService INT UNSIGNED,
    idClient int unsigned,
    issuance DATE,
    cufe VARCHAR(100),
    url VARCHAR(100),
    taxes DECIMAL(10,2),
    discount double,
    cost double,
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE cascade,
    FOREIGN KEY (idClient) REFERENCES client(id) ON DELETE CASCADE
);

-- Tabla: providers
CREATE TABLE provider (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    identification BIGINT,
    contact VARCHAR(50),
    numberVisits INT
);

-- Tabla: spareParts
CREATE TABLE sparePart (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    type ENUM('mechanic', 'electric', 'carBody', 'consumable'),
    brand VARCHAR(20),
    model VARCHAR(20),
    cost DOUBLE,
    lifeSpan INT,
    state ENUM('available', 'reserved', 'outOfService')
);

-- Tabla: spareProvider (relación N:M)
CREATE TABLE spareProvider (
    idSparePart INT UNSIGNED,
    idProvider INT UNSIGNED,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id),
    FOREIGN KEY (idProvider) REFERENCES provider(id),
    PRIMARY KEY (idSparePart, idProvider)
);

-- Tabla: orders
CREATE TABLE orders (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    orderDate DATE,
    idPackage int unsigned,
    FOREIGN KEY (idPackage) REFERENCES package(id) ON DELETE CASCADE
);

-- Tabla: packages
CREATE TABLE package (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    idProvider INT UNSIGNED,
    idOrder INT UNSIGNED,
    FOREIGN KEY (idProvider) REFERENCES provider(id) ON DELETE CASCADE
);

-- Tabla: inventory
CREATE TABLE inventory (
    idSparePart INT UNSIGNED,
    idPackage INT UNSIGNED,
    initialQuantity INT,
    currentQuantity INT,
    entryDate DATE,
    expirationDate DATE,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id) ON DELETE CASCADE,
    FOREIGN KEY (idPackage) REFERENCES package(id) ON DELETE CASCADE,
    PRIMARY KEY (idSparePart, idPackage)
);

-- Tabla: orderSpare (relación N:M)
CREATE TABLE orderSpare (
    idOrder INT UNSIGNED,
    idSparePart INT UNSIGNED,
    quantity INT,
    entryDate DATE,
    FOREIGN KEY (idOrder) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id) ON DELETE CASCADE,
    PRIMARY KEY (idOrder, idSparePart)
);
