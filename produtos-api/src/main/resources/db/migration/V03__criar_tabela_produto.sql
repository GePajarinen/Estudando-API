CREATE TABLE produto (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	codigoproduto VARCHAR(20) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	promocao  BOOLEAN NOT NULL,
	valorpromo DECIMAL(10,2) NOT NULL,
	imagem VARCHAR (100),
	quantidade  BIGINT(20) NOT NULL,
	categoria VARCHAR(20) NOT NULL,
	fornecedor BIGINT(20) NOT NULL,
	
	FOREIGN KEY (fornecedor) REFERENCES fornecedormini(codigo)
	
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Agenda', '1234', 10.22, false, 8.99, null, 50, 'Papelaria', 1);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Vinho tinto', '1258', 25.22, true, 18.99, null, 90, 'Bebidas', 8);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Chocolate', '1458', 5.22, false, 3.99, null, 150, 'Alimentação', 7);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Caderno', '3256', 10.22, true, 8.99, null, 100, 'Papelaria', 1);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Caneta', '2589', 2.22, false, 1.99, null, 500, 'Papelaria', 1);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Repelente', '2365', 10.00, true, 7.99, null, 110, 'Outros', 2);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Sabonete', '5879', 6.22, true, 4.99, null, 600, 'Higiene', 4);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Biscoito', '9874', 6.50, false, 3.99, null, 350, 'Alimentação', 5);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Vinho branco', '8596', 30.22, true, 16.99, null, 40, 'Bebidas', 8);
INSERT INTO produto  (nome, codigoProduto, valor, promocao, valorPromo, imagem, quantidade, categoria, fornecedor) values ('Gin', '3223', 40.22, false, 28.99, null, 50, 'Bebidas', 8);
