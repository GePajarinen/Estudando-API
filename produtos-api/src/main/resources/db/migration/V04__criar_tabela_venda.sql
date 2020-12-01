CREATE TABLE venda (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	valor DECIMAL(10,2) NOT NULL,
	datacompra DATE NOT NULL,
	
	cliente BIGINT(20) NOT NULL,
	fornecedor BIGINT(20) NOT NULL,
	produto BIGINT(20) NOT NULL,
	
	FOREIGN KEY (cliente) REFERENCES cliente(codigo),
	FOREIGN KEY (fornecedor) REFERENCES fornecedor(codigo),
	FOREIGN KEY (produto) REFERENCES produto(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO venda (valor, datacompra, cliente, fornecedor, produto) values(10.22, '2017-06-10', 1, 1, 1);
INSERT INTO venda (valor, datacompra, cliente, fornecedor, produto) values(10.22, '2017-06-10', 2, 1, 1);
