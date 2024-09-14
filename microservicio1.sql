    CREATE TABLE Persona (
        id UUID PRIMARY KEY NOT NULL,
        nombre VARCHAR(100) NOT NULL,
        genero VARCHAR(10) NOT NULL,
        edad INT NOT NULL,
        identificacion VARCHAR(10) NOT NULL,
        direccion VARCHAR(100) NOT NULL,
        telefono VARCHAR(15) NOT NULL
    );

    CREATE TABLE Cliente (
        id UUID PRIMARY KEY NOT NULL,
        contrasena VARCHAR(12) NOT NULL,
        estado BOOLEAN NOT NULL,
        clienteid UUID NOT NULL,
        CONSTRAINT fk_persona FOREIGN KEY (id) REFERENCES Persona(id) ON DELETE CASCADE
    );

INSERT INTO Persona (id, nombre, genero, edad, identificacion, direccion, telefono) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'Juan Perez', 'M', 30, '123456789', 'Quito', '555-1234'),
('550e8400-e29b-41d4-a716-446655440001', 'Maria Lopez', 'F', 25, '987654321', 'Ambato', '555-5678'),
('550e8400-e29b-41d4-a716-446655440002', 'Carlos Gomez', 'M', 40, '112233445', 'Sangolqui', '555-8765'),
('550e8400-e29b-41d4-a716-446655440099', 'Andrea Pasto', 'F', 66, '221133445', 'Sangolqui', '444-8765');

INSERT INTO Cliente (id, contrasena, estado, clienteid) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'pass123', true, '550e8400-e29b-41d4-a716-446655440003'),
('550e8400-e29b-41d4-a716-446655440001', 'pass456', true, '550e8400-e29b-41d4-a716-446655440004'),
('550e8400-e29b-41d4-a716-446655440002', 'pass789', true, '550e8400-e29b-41d4-a716-446655440005'),
('550e8400-e29b-41d4-a716-446655440099', 'pass666', true, '550e8400-e29b-41d4-a716-446655440098');