CREATE TABLE provider (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    identification BIGINT,
    contact VARCHAR(50),
    numberVisits INT
);

CREATE TABLE sparePart (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    type ENUM('mechanic', 'electric', 'carBody', 'consumable'),
    brand VARCHAR(20),
    model VARCHAR(20),
    cost DOUBLE,
    lifeSpan DATE,
    state ENUM('available', 'reserved', 'outOfService')
);

CREATE TABLE package (
    id INT PRIMARY KEY,
    entryDate DATE,
    idProvider INT,
    FOREIGN KEY (idProvider) REFERENCES provider(id)
);

CREATE TABLE inventory (
    idSparePart INT PRIMARY KEY,
    quantity INT,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id)
);

CREATE TABLE order (
    id INT PRIMARY KEY,
    orderDate DATE
);

CREATE TABLE orderSpare (
    idOrder INT,
    idSparePart INT,
    quantity INT,
    entryDate DATE,
    FOREIGN KEY (idOrder) REFERENCES order(id),
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id),
    PRIMARY KEY (idOrder, idSparePart)
);

CREATE TABLE spareProvider (
    idSparePart INT,
    idProvider INT,
    FOREIGN KEY (idSparePart) REFERENCES sparePart(id),
    FOREIGN KEY (idProvider) REFERENCES provider(id),
    PRIMARY KEY (idSparePart, idProvider)
);

CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    phone BIGINT,
    speciality VARCHAR(20)
);

CREATE TABLE service (
    id INT PRIMARY KEY,
    type ENUM('preventive', 'corrective'),
    description VARCHAR(100),
    estimatedTime INT,
    actualTime INT,
    cost DOUBLE,
    discount INT,
    stateService ENUM('pending', 'underway', 'finished'),
    idEmployee INT,
    FOREIGN KEY (idEmployee) REFERENCES employee(id)
);

CREATE TABLE vehicle (
    id INT PRIMARY KEY,
    brand VARCHAR(20),
    model VARCHAR(30),
    plate VARCHAR(10),
    type ENUM('automobile', 'SUV', 'motorbike')
);  

CREATE TABLE serviceVehicle (
    idService INT,
    idVehicle INT,
    FOREIGN KEY (idService) REFERENCES service(id),
    FOREIGN KEY (idVehicle) REFERENCES vehicle(id),
    PRIMARY KEY (idService, idVehicle)
);

CREATE TABLE client (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    identification BIGINT,
    phone BIGINT,
    email VARCHAR(50),
    address VARCHAR(50)
);

CREATE TABLE clientVehicle (
    idClient INT,
    idVehicle INT,
    FOREIGN KEY (idClient) REFERENCES client(id),
    FOREIGN KEY (idVehicle) REFERENCES vehicle(id),
    PRIMARY KEY (idClient, idVehicle)
);  

CREATE TABLE clientService (
    id INT PRIMARY KEY,
    idClient INT,
    idService INT,
    FOREIGN KEY (idClient) REFERENCES client(id),
    FOREIGN KEY (idService) REFERENCES service(id)
);

CREATE TABLE bill (
    id INT PRIMARY KEY,
    idClientService INT,
    issuance TIMESTAMP,
    cufe VARCHAR(100),
    url VARCHAR(100),
    taxes DECIMAL(10,2),
    FOREIGN KEY (idClientService) REFERENCES clientService(id)
);
