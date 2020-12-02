CREATE TABLE venda (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	valor DECIMAL(10,2),
	datacompra DATE,
	
	cliente BIGINT(20) NOT NULL,
	FOREIGN KEY (cliente) REFERENCES cliente(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO venda (valor, datacompra, cliente) values(10.22, '2017-06-10', 1);
INSERT INTO venda (valor, datacompra, cliente) values(10.22, '2017-06-10', 2);
