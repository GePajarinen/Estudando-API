CREATE TABLE produto (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	codigo_produto VARCHAR(20),
	valor DECIMAL(10,2) NOT NULL,
	promocao  BOOLEAN NOT NULL,
	valor_promo DECIMAL(10,2) NOT NULL,
	imagem VARCHAR (100),
	quantidade  BIGINT(20) NOT NULL,
	categoria VARCHAR(20) NOT NULL,
	fornecedor_id BIGINT(20) NOT NULL,
	
	FOREIGN KEY (fornecedor_id) REFERENCES fornecedormini(id)

	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Agenda', '1234', 10.22, false, 8.99, null, 50, 'Papelaria', 1);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Vinho tinto', '1258', 25.22, true, 18.99, null, 90, 'Bebidas', 8);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Chocolate', '1458', 5.22, false, 3.99, null, 150, 'Alimentação', 7);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Caderno', '3256', 10.22, true, 8.99, null, 100, 'Papelaria', 1);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Caneta', '2589', 2.22, false, 1.99, null, 500, 'Papelaria', 1);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Repelente', '2365', 10.00, true, 7.99, null, 110, 'Outros', 2);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Sabonete', '5879', 6.22, true, 4.99, null, 600, 'Higiene', 4);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Biscoito', '9874', 6.50, false, 3.99, null, 350, 'Alimentação', 5);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Vinho branco', '8596', 30.22, true, 16.99, null, 40, 'Bebidas', 8);
INSERT INTO produto  (nome, codigo_produto, valor, promocao, valor_promo, imagem, quantidade, categoria, fornecedor_id) values ('Gin', '3223', 40.22, false, 28.99, null, 50, 'Bebidas', 8);

