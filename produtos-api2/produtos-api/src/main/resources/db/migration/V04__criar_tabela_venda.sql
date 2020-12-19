CREATE TABLE venda (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	total_compra DECIMAL(10,2),
	data_compra DATE,
	
	cliente BIGINT(20) NOT NULL,
	FOREIGN KEY (cliente) REFERENCES cliente(id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO venda (total_compra, data_compra, cliente) values(10.22, '2017-06-10', 1);
INSERT INTO venda (total_compra, data_compra, cliente) values(10.22, '2017-06-10', 2);
