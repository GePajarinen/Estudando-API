CREATE TABLE cliente (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	logradouro VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Odo', 'Rua do Abacaxi', '10', null, 'Bethânia', '38400-12', 'Uberlândia', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Benjamin Sisko', 'Rua Tucuma', '35', 'apto 102', 'Bethânia', '35164-119', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Jadzia Dax', 'Rua Montevideo', '360', 'apto 666', 'Vila Militar', '35164-000', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Worf', 'Av. Selim José de Sales', '2125', null, 'Vagalume', '35164-911', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Quark', 'Av. Gerasa', '8583',null, 'Vila Celeste', '35164-562', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Julian Bashir', 'Av. José Assis Vasconcelos', '332', 'apto 03', 'Bethânia', '35164-119', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Kira Nerys', 'Rua Orquideas', '102', 'apto 33', 'Canaã', '35164-210', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Nog', 'Rua Filisteu', '88', null ,'Cidade Nova', '35164-058', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Miles OBrien', 'Rua Salermo', '51', 'apto 201', 'Jardim Vitória', '35164-563', 'Ipatinga', 'MG');
INSERT INTO cliente (nome, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Elim Garak', 'Rua Tucuma', '666', 'apto 111', 'Carirú', '35164-666', 'Ipatinga', 'MG');