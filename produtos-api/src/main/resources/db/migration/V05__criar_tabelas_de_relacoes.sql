CREATE TABLE fornecedor_produtos (
	
	fornecedor_codigo BIGINT(20),
	produtos_codigo BIGINT(20),
	
	FOREIGN KEY (fornecedor_codigo) REFERENCES fornecedormini(codigo),
	FOREIGN KEY (produtos_codigo) REFERENCES produto(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE venda_fornecedor (
	
	venda_codigo BIGINT(20),
	fornecedor_codigo BIGINT(20),
	
	FOREIGN KEY (venda_codigo) REFERENCES venda(codigo),
	FOREIGN KEY (fornecedor_codigo) REFERENCES fornecedormini(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE venda_produtos (
	
	venda_codigo BIGINT(20),
	produtos_codigo BIGINT(20),
	
	FOREIGN KEY (venda_codigo) REFERENCES venda(codigo),
	FOREIGN KEY (produtos_codigo) REFERENCES produto(codigo)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO fornecedor_produtos (fornecedor_codigo, produtos_codigo) values (1, 1);
INSERT INTO fornecedor_produtos (fornecedor_codigo, produtos_codigo) values (1, 4);
INSERT INTO fornecedor_produtos (fornecedor_codigo, produtos_codigo) values (1, 5);

INSERT INTO venda_fornecedor (venda_codigo, fornecedor_codigo) values (1, 1);
INSERT INTO venda_fornecedor (venda_codigo, fornecedor_codigo) values (2, 1);

INSERT INTO venda_produtos (venda_codigo, produtos_codigo) values (1, 1);
INSERT INTO venda_produtos (venda_codigo, produtos_codigo) values (2, 1);
