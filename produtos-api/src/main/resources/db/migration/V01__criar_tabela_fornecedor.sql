CREATE TABLE fornecedor (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	cnpj VARCHAR(60) NOT NULL
	
			
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO fornecedor (nome, cnpj) values ('GFT', '07.174.743/0001-27');
INSERT INTO fornecedor (nome, cnpj) values ('Bayer', '10.174.743/0001-34');
INSERT INTO fornecedor (nome, cnpj) values ('EMS', '27.174.743/0001-99');
INSERT INTO fornecedor (nome, cnpj) values ('Medley', '27.174.743/0001-88');
INSERT INTO fornecedor (nome, cnpj) values ('Nestle', '37.174.743/0001-77');
INSERT INTO fornecedor (nome, cnpj) values ('Padma', '47.174.743/0001-66');
INSERT INTO fornecedor (nome, cnpj) values ('Mars', '57.174.743/0001-55');
INSERT INTO fornecedor (nome, cnpj) values ('AmBev', '08.174.743/0001-44');

CREATE TABLE fornecedormini (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50),
	cnpj VARCHAR(60)
	
			
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO fornecedormini (codigo, nome, cnpj) values (1, 'GFT', '07.174.743/0001-27');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (2, 'Bayer', '10.174.743/0001-34');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (3, 'EMS', '27.174.743/0001-99');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (4, 'Medley', '27.174.743/0001-88');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (5, 'Nestle', '37.174.743/0001-77');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (6, 'Padma', '47.174.743/0001-66');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (7, 'Mars', '57.174.743/0001-55');
INSERT INTO fornecedormini (codigo, nome, cnpj) values (8, 'AmBev', '08.174.743/0001-44');

