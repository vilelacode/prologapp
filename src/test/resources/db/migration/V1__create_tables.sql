CREATE TABLE veiculo (
    placa VARCHAR(7) PRIMARY KEY NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    quilometragem BIGINT NOT NULL,
    status VARCHAR(10) NOT NULL
);

CREATE TABLE pneu (
    numero_fogo VARCHAR(20) PRIMARY KEY NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    pressao_atual DOUBLE PRECISION NOT NULL,
    status VARCHAR(10) NOT NULL
);

CREATE TABLE veiculo_pneu (
    veiculo_placa VARCHAR NOT NULL REFERENCES veiculo(placa),
    pneu_numero_fogo VARCHAR NOT NULL REFERENCES pneu(numero_fogo),
    posicao VARCHAR NOT NULL,
    CONSTRAINT fk_veiculo FOREIGN KEY (veiculo_placa) REFERENCES veiculo(placa),
    CONSTRAINT fk_pneu FOREIGN KEY (pneu_numero_fogo) REFERENCES pneu(numero_fogo),
    CONSTRAINT pk_veiculo_pneu PRIMARY KEY (veiculo_placa, pneu_numero_fogo, posicao)
);

INSERT INTO veiculo (placa, marca, quilometragem, status) VALUES
('PRO1234', 'Toyota', 50000, 'ATIVO'),
('LOG1234', 'Honda', 75000, 'ATIVO'),
('APP1234', 'Ford', 30000, 'INATIVO');

INSERT INTO pneu (numero_fogo, marca, pressao_atual, status) VALUES
('FOGO001', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO002', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO003', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO004', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO005', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO006', 'Michelin', 32.0, 'DISPONIVEL'),
('FOGO007', 'Goodyear', 30.0, 'DISPONIVEL'),
('FOGO008', 'Goodyear', 30.0, 'DISPONIVEL'),
('FOGO009', 'Goodyear', 30.0, 'DISPONIVEL'),
('FOGO010', 'Goodyear', 30.0, 'DISPONIVEL'),
('FOGO011', 'Goodyear', 30.0, 'DISPONIVEL'),
('FOGO012', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO013', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO014', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO015', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO016', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO017', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO018', 'Pirelli', 31.0, 'DISPONIVEL'),
('FOGO019', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO020', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO021', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO022', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO023', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO024', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO025', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO026', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO027', 'Bridgestone', 33.0, 'DISPONIVEL'),
('FOGO028', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO029', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO030', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO031', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO032', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO033', 'Continental', 32.5, 'DISPONIVEL'),
('FOGO034', 'Continental', 32.5, 'DISPONIVEL');


INSERT INTO veiculo_pneu (veiculo_placa, pneu_numero_fogo, posicao) VALUES
('PRO1234', 'FOGO001', 'A'),
('PRO1234', 'FOGO002', 'B'),
('PRO1234', 'FOGO003', 'C'),
('PRO1234', 'FOGO004', 'D'),
('PRO1234', 'FOGO005', 'E'),
('PRO1234', 'FOGO006', 'F'),
('LOG1234', 'FOGO028', 'A'),
('LOG1234', 'FOGO029', 'B'),
('LOG1234', 'FOGO030', 'C'),
('LOG1234', 'FOGO031', 'D');

