-- SQL INSERT Statements for Motor Store Database (Colombia)

-- Provider Table (3 Records)
INSERT INTO provider (id, name, identification, contact, numberVisits) VALUES
(1, 'Autopartes SAS', 900123456, 'ventas@autopartessas.com.co', 15),
(2, 'Repuestos La Economía', 800987654, 'contacto@repuestoseconomia.co', 25),
(3, 'Importadora Frenos y Partes', 900555111, 'gerencia@importadorafp.com', 8);

-- SparePart Table (12 Records)
INSERT INTO sparePart (id, name, type, brand, model, cost, lifeSpan, state) VALUES
(101, 'Filtro de Aceite', 'consumable', 'ACDelco', 'Universal', 25000, '2026-12-31', 'available'),
(102, 'Pastillas de Freno Delanteras', 'mechanic', 'Bosch', 'Chevrolet Spark GT', 180000, '2027-06-30', 'available'),
(103, 'Bujía Iridium', 'electric', 'NGK', 'Universal 4T', 45000, '2028-01-15', 'available'),
(104, 'Amortiguador Trasero', 'mechanic', 'Gabriel', 'Renault Duster', 350000, '2029-05-20', 'available'),
(105, 'Batería 12V 60Ah', 'electric', 'MAC', 'Standard', 420000, '2027-11-01', 'reserved'),
(106, 'Aceite Motor 10W-30 Sintético', 'consumable', 'Mobil', 'API SN - 1L', 60000, '2026-08-10', 'available'),
(107, 'Llanta 185/65 R15', 'consumable', 'Michelin', 'Energy Saver', 310000, '2030-03-01', 'available'),
(108, 'Faro Delantero Derecho', 'carBody', 'TYC', 'Mazda 3 Skyactiv', 550000, '2030-12-31', 'available'),
(109, 'Correa de Distribución', 'mechanic', 'Gates', 'Kia Picanto Ion', 120000, '2028-07-01', 'outOfService'),
(110, 'Bomba de Agua', 'mechanic', 'SKF', 'Nissan March', 280000, '2029-02-10', 'available'),
(111, 'Sensor de Oxígeno', 'electric', 'Denso', 'Universal OBDII', 210000, '2028-10-01', 'available'),
(112, 'Guardabarros Delantero Izquierdo', 'carBody', 'Genérico', 'Renault Logan', 150000, '2030-12-31', 'reserved');

-- Package Table (10 Records)
INSERT INTO package (id, entryDate, idProvider) VALUES
(201, '2025-04-10', 1),
(202, '2025-04-11', 2),
(203, '2025-04-12', 1),
(204, '2025-04-15', 3),
(205, '2025-04-18', 2),
(206, '2025-04-20', 1),
(207, '2025-04-22', 3),
(208, '2025-04-25', 2),
(209, '2025-04-28', 1),
(210, '2025-04-29', 2);

-- Inventory Table (12 Records - Assuming initial stock matches spare parts)
INSERT INTO inventory (idSparePart, quantity) VALUES
(101, 50),
(102, 20),
(103, 100),
(104, 15),
(105, 10),
(106, 80),
(107, 40),
(108, 5),
(109, 0), -- Out of service
(110, 12),
(111, 25),
(112, 8);

-- Order Table (10 Records)
INSERT INTO order (id, orderDate) VALUES
(301, '2025-03-01'),
(302, '2025-03-05'),
(303, '2025-03-10'),
(304, '2025-03-15'),
(305, '2025-03-20'),
(306, '2025-03-25'),
(307, '2025-04-01'),
(308, '2025-04-05'),
(309, '2025-04-10'),
(310, '2025-04-15');

-- orderSpare Table (15 Records)
INSERT INTO orderSpare (idOrder, idSparePart, quantity, entryDate) VALUES
(301, 101, 30, '2025-04-10'),
(301, 103, 50, '2025-04-10'),
(302, 102, 10, '2025-04-11'),
(303, 105, 5, '2025-04-12'),
(303, 106, 40, '2025-04-12'),
(304, 108, 3, '2025-04-15'),
(305, 107, 20, '2025-04-18'),
(306, 104, 8, '2025-04-20'),
(306, 110, 6, '2025-04-20'),
(307, 111, 15, '2025-04-22'),
(308, 101, 20, '2025-04-25'),
(308, 106, 40, '2025-04-25'),
(309, 103, 50, '2025-04-28'),
(310, 112, 4, '2025-04-29'),
(310, 102, 10, '2025-04-29');

-- spareProvider Table (15 Records)
INSERT INTO spareProvider (idSparePart, idProvider) VALUES
(101, 1), (101, 2),
(102, 1), (102, 3),
(103, 2),
(104, 3),
(105, 1), (105, 2),
(106, 1), (106, 2),
(107, 2), (107, 3),
(108, 3),
(110, 1),
(111, 2);
-- Note: Not all parts might be available from all providers, and some might not have a provider listed yet.

-- Employee Table (10 Records)
INSERT INTO employee (id, name, phone, speciality) VALUES
(401, 'Carlos Rodriguez', 3101234567, 'Mecánico Senior'),
(402, 'Maria Fernanda Gómez', 3119876543, 'Electricista Auto'),
(403, 'Andrés Pérez', 3121112233, 'Latoneiro'), -- Bodywork specialist
(404, 'Sofia Castro', 3134445566, 'Asesora Servicio'), -- Service Advisor
(405, 'Javier Ramirez', 3147778899, 'Mecánico Junior'),
(406, 'Laura Valentina Diaz', 3151212121, 'Técnico Llantas'), -- Tire Technician
(407, 'Diego Hernandez', 3163434343, 'Jefe de Taller'), -- Workshop Manager
(408, 'Camila Vargas', 3175656565, 'Recepcionista'),
(409, 'Luis Miguel Ortiz', 3187878787, 'Mecánico Frenos'), -- Brake Specialist
(410, 'Ana María Silva', 3199090909, 'Asesora Repuestos'); -- Parts Advisor

-- Service Table (10 Records)
INSERT INTO service (id, type, description, estimatedTime, actualTime, cost, discount, stateService, idEmployee) VALUES
(501, 'preventive', 'Cambio de aceite y filtro', 60, 55, 150000, 0, 'finished', 401),
(502, 'corrective', 'Reemplazo pastillas de freno delanteras', 120, 130, 280000, 10, 'finished', 409),
(503, 'preventive', 'Alineación y balanceo', 90, 85, 120000, 0, 'underway', 406),
(504, 'corrective', 'Diagnóstico sistema eléctrico', 180, NULL, 90000, 0, 'pending', 402),
(505, 'corrective', 'Reparación golpe guardabarros izq', 240, 250, 450000, 5, 'finished', 403),
(506, 'preventive', 'Revisión Tecnomecánica Previa', 120, 110, 80000, 0, 'finished', 407),
(507, 'corrective', 'Cambio de batería', 30, 25, 480000, 0, 'underway', 402),
(508, 'preventive', 'Sincronización motor', 180, NULL, 320000, 15, 'pending', 401),
(509, 'corrective', 'Cambio amortiguadores traseros', 150, 160, 850000, 0, 'finished', 405),
(510, 'corrective', 'Reemplazo bomba de agua', 240, NULL, 500000, 0, 'pending', 401);

-- Vehicle Table (10 Records)
INSERT INTO vehicle (id, brand, model, plate, type) VALUES
(601, 'Chevrolet', 'Spark GT', 'FQK123', 'automobile'),
(602, 'Renault', 'Duster', 'HGL456', 'SUV'),
(603, 'Mazda', '3 Skyactiv', 'JMN789', 'automobile'),
(604, 'Kia', 'Picanto Ion', 'KLP012', 'automobile'),
(605, 'Nissan', 'March', 'MVN345', 'automobile'),
(606, 'Yamaha', 'NMAX', 'XYZ78A', 'motorbike'),
(607, 'Chevrolet', 'Onix Turbo', 'BDE901', 'automobile'),
(608, 'Renault', 'Logan', 'CDE234', 'automobile'),
(609, 'Suzuki', 'Vitara', 'EFG567', 'SUV'),
(610, 'AKT', 'NKD 125', 'HIJ89B', 'motorbike');

-- serviceVehicle Table (10 Records - Linking services to vehicles)
INSERT INTO serviceVehicle (idService, idVehicle) VALUES
(501, 601),
(502, 603),
(503, 602),
(504, 607),
(505, 608),
(506, 604),
(507, 605),
(508, 601),
(509, 602),
(510, 605);

-- Client Table (10 Records)
INSERT INTO client (id, name, identification, phone, email, address) VALUES
(701, 'Juan Pérez', 1098765432, 3001112233, 'juan.perez@email.com', 'Cra 27 # 45-67, Bucaramanga'),
(702, 'Ana García', 1098123456, 3012223344, 'ana.garcia@email.com', 'Calle 56 # 18-90, Floridablanca'),
(703, 'Pedro Martínez', 1097654321, 3023334455, 'pedro.martinez@email.com', 'Av Quebrada Seca # 30-12, Bucaramanga'),
(704, 'Lucía Rodríguez', 63456789, 3034445566, 'lucia.r@email.com', 'Carrera 33 # 52-05, Cabecera, Bucaramanga'),
(705, 'Miguel Sánchez', 1095987654, 3045556677, 'miguel.sanchez@email.com', 'Calle 105 # 25-11, Provenza, Bucaramanga'),
(706, 'Isabela Gómez', 1096112233, 3206667788, 'isagomez@email.com', 'Diagonal 15 # 60-30, Bucaramanga'),
(707, 'Ricardo López', 63987123, 3217778899, 'ricardo.lopez@email.com', 'Calle 34 # 22-45, Centro, Bucaramanga'),
(708, 'Valeria Castro', 1094556677, 3228889900, 'valeria.castro@email.com', 'Anillo Vial, Girón'),
(709, 'Daniel Morales', 1093334455, 3199990011, 'daniel.m@email.com', 'Carrera 21 # 55-10, Sotomayor, Bucaramanga'),
(710, 'Gabriela Torres', 63123987, 3180001122, 'gabi.torres@email.com', 'Calle 48 # 28-70, Bucaramanga');

-- clientVehicle Table (12 Records - Linking clients to vehicles, some clients might have multiple vehicles)
INSERT INTO clientVehicle (idClient, idVehicle) VALUES
(701, 601),
(702, 602),
(703, 603),
(704, 604),
(705, 605),
(706, 606),
(707, 607),
(708, 608),
(709, 609),
(710, 610),
(701, 607), -- Juan Pérez also owns the Onix
(703, 609); -- Pedro Martínez also owns the Vitara

-- clientService Table (10 Records - Linking clients to specific services performed)
-- Assuming one client per service for simplicity here, but could be different
INSERT INTO clientService (id, idClient, idService) VALUES
(801, 701, 501),
(802, 703, 502),
(803, 702, 503),
(804, 707, 504),
(805, 708, 505),
(806, 704, 506),
(807, 705, 507),
(808, 701, 508), -- Service for Juan Pérez's Spark
(809, 702, 509), -- Service for Ana García's Duster
(810, 705, 510); -- Service for Miguel Sánchez's March

-- Bill Table (10 Records - Generating bills for finished or underway services)
INSERT INTO bill (id, idClientService, issuance, cufe, url, taxes) VALUES
(901, 801, '2025-04-20 10:30:00', 'CUFE_Placeholder_901ABC...', 'https://dian.gov.co/facturas/901', (150000 * 0.19)), -- Bill for service 501
(902, 802, '2025-04-21 14:00:00', 'CUFE_Placeholder_902DEF...', 'https://dian.gov.co/facturas/902', ((280000 * (1 - 0.10)) * 0.19)), -- Bill for service 502 (with discount)
(903, 803, '2025-04-29 11:00:00', 'CUFE_Placeholder_903GHI...', 'https://dian.gov.co/facturas/903', (120000 * 0.19)), -- Bill for service 503 (underway)
-- No bill yet for 504 (pending)
(905, 805, '2025-04-23 16:45:00', 'CUFE_Placeholder_905JKL...', 'https://dian.gov.co/facturas/905', ((450000 * (1- 0.05)) * 0.19)), -- Bill for service 505 (with discount)
(906, 806, '2025-04-24 09:15:00', 'CUFE_Placeholder_906MNO...', 'https://dian.gov.co/facturas/906', (80000 * 0.19)), -- Bill for service 506
(907, 807, '2025-04-29 12:00:00', 'CUFE_Placeholder_907PQR...', 'https://dian.gov.co/facturas/907', (480000 * 0.19)), -- Bill for service 507 (underway)
-- No bill yet for 508 (pending)
(909, 809, '2025-04-28 17:00:00', 'CUFE_Placeholder_909STU...', 'https://dian.gov.co/facturas/909', (850000 * 0.19)), -- Bill for service 509
-- No bill yet for 510 (pending)
(911, 801, '2025-04-29 12:30:00', 'CUFE_Placeholder_911VWX...', 'https://dian.gov.co/facturas/911', (0 * 0.19)), -- Example: Bill for a warranty check (zero cost) linked to clientService 801
(912, 802, '2025-04-29 12:35:00', 'CUFE_Placeholder_912YZA...', 'https://dian.gov.co/facturas/912', (50000 * 0.19)), -- Example: Bill for misc small part linked to clientService 802
(913, 805, '2025-04-29 12:40:00', 'CUFE_Placeholder_913BCD...', 'https://dian.gov.co/facturas/913', (25000 * 0.19)); -- Example: Bill for fluid top-up linked to clientService 805


