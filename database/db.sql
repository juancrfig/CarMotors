-- Ensure a clean initial state when running the database creation script
DROP DATABASE IF EXISTS carMotors;
-- Sets the character encoding to UTF-8 to support special characters and emojis
CREATE DATABASE carMotors CHARACTER SET utf8mb4;

USE carMotors;


CREATE TABLE providers (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    identification BIGINT,
    contact VARCHAR(50),
    numberVisits INT
);

CREATE TABLE spareParts (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    type ENUM('mechanic', 'electric', 'carBody', 'consumable'),
    brand VARCHAR(20),
    model VARCHAR(20),
    cost DOUBLE,
    lifeSpan INT,
    state ENUM('available', 'reserved', 'outOfService')
);

CREATE TABLE packages (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    idProvider INT,
    idOrder INT,
    FOREIGN KEY (idProvider) REFERENCES provider(id) ON DELETE CASCADE,
    FOREIGN KEY (idOrder) REFERENCES order(id) ON DELETE CASCADE
);

CREATE TABLE inventory (
    idSparePart INT,
    idPackage INT,
    initialQuantity INT,
    currentQuantity INT,
    entryDate DATE,
    expirationDate DATE,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id) ON DELETE CASCADE,
    FOREIGN KEY (idPackage) REFERENCES package(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    orderDate DATE
);

CREATE TABLE orderSpare (
    idOrder INT,
    idSparePart INT,
    quantity INT,
    entryDate DATE,
    FOREIGN KEY (idOrder) REFERENCES `order`(id) ON DELETE CASCADE,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id) ON DELETE CASCADE,
    PRIMARY KEY (idOrder, idSparePart)
);

CREATE TABLE spareProvider (
    idSparePart INT,
    idProvider INT,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id),
    FOREIGN KEY (idProvider) REFERENCES provider(id),
    PRIMARY KEY (idSparePart, idProvider)
);

CREATE TABLE employees (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    phone BIGINT,
    speciality VARCHAR(20)
);

CREATE TABLE services (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type ENUM('preventive', 'corrective'),
    description VARCHAR(100),
    estimatedTime INT,
    actualTime INT,
    cost DOUBLE,
    discount INT,
    stateService ENUM('pending', 'underway', 'finished'),
    idEmployee INT,
    idClient INT,
    FOREIGN KEY (idClient) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (idEmployee) REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE vehicles (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(20),
    model VARCHAR(30),
    plate VARCHAR(10),
    type ENUM('automobile', 'SUV', 'motorbike')
);  

CREATE TABLE serviceVehicle (
    idService INT,
    idVehicle INT,
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE CASCADE,
    FOREIGN KEY (idVehicle) REFERENCES vehicle(id) ON DELETE CASCADE,
    PRIMARY KEY (idService, idVehicle)
);

CREATE TABLE clients (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    identification BIGINT,
    phone BIGINT,
    email VARCHAR(50),
    address VARCHAR(50)
);

CREATE TABLE clientVehicle (
    idClient INT,
    idVehicle INT,
    FOREIGN KEY (idClient) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (idVehicle) REFERENCES vehicle(id) ON DELETE CASCADE,
    PRIMARY KEY (idClient, idVehicle)
);  

CREATE TABLE bills (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    idService INT,
    issuance DATE,
    cufe VARCHAR(100),
    url VARCHAR(100),
    taxes DECIMAL(10,2),
    FOREIGN KEY (idService) REFERENCES service(id) ON DELETE CASCADE
);
