CREATE TABLE medicamento (
	codigo BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	concentracao VARCHAR(30) NOT NULL,
	classe VARCHAR (20),
	preco DECIMAL(10,2) NOT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO medicamento (nome, concentracao, classe, preco ) values ('Abretia', '30 mg','Referência', 89.00);
INSERT INTO medicamento (nome, concentracao, classe, preco ) values ('Algicod', '500 mg 30 mg', 'Referência', 65.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Alprazolam', '0,5 mg', 'Genérico', 35.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Alprazolam', '0,25 mg', 'Genérico', 32.00);
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Alprazolam', '1 mg', 'Genérico', 11.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Amato', '100 mg', 'Similar', 101.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Amato', '25 mg', 'Similar', 88.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Bup', '150 mg', 'Similar', 55.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Bup', '150 mg', 'Similar', 120.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Coglive', '16 mg',  null, 18.00 );
INSERT INTO medicamento (nome, concentracao, classe, preco )  values ('Coglive', '24 mg', null, 24.00);
