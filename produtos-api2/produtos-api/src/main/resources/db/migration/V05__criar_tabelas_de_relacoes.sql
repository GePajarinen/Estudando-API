CREATE TABLE fornecedor_produtos (
	
	fornecedor_id BIGINT(20),
	produtos_id BIGINT(20),
	
	FOREIGN KEY (fornecedor_id) REFERENCES fornecedormini(id),
	FOREIGN KEY (produtos_id) REFERENCES produto(id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE venda_fornecedor (
	
	venda_id BIGINT(20),
	fornecedor_id BIGINT(20),
	
	FOREIGN KEY (venda_id) REFERENCES venda(id),
	FOREIGN KEY (fornecedor_id) REFERENCES fornecedormini(id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE venda_produtos (
	
	venda_id BIGINT(20),
	produtos_id BIGINT(20),
	
	FOREIGN KEY (venda_id) REFERENCES venda(id),
	FOREIGN KEY (produtos_id) REFERENCES produto(id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (1, 1);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (1, 4);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (1, 5);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (2, 6);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (4, 7);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (5, 8);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (7, 3);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (8, 2);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (8, 9);
INSERT INTO fornecedor_produtos (fornecedor_id, produtos_id) values (8, 10);

INSERT INTO venda_fornecedor (venda_id, fornecedor_id) values (1, 1);
INSERT INTO venda_fornecedor (venda_id, fornecedor_id) values (2, 1);

INSERT INTO venda_produtos (venda_id, produtos_id) values (1, 1);
INSERT INTO venda_produtos (venda_id, produtos_id) values (2, 1);
