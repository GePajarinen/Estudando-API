CREATE TABLE venda (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	valor DECIMAL(10,2) NOT NULL,
	dataCompra DATE NOT NULL,
	
	cliente BIGINT(20) NOT NULL,
	FOREIGN KEY (cliente) REFERENCES cliente(codigo),
	
	fornecedor BIGINT(20) NOT NULL,
	FOREIGN KEY (fornecedor) REFERENCES fornecedor(codigo),
	
	produto BIGINT(20),
	FOREIGN KEY (produto) REFERENCES produto(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



