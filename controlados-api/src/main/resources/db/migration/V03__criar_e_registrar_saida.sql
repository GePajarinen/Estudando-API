CREATE TABLE saida (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data_saida DATE NOT NULL,
	codigo_medicamento BIGINT(20) NOT NULL,
	codigo_cliente BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_medicamento) REFERENCES medicamento(codigo),
	FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-06-11', 1, 10);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2019-07-11', 1, 10);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-08-18', 1, 10);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-08-10', 2, 5);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2018-06-10', 10, 1);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2019-08-19', 9, 2);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-06-10', 9, 9);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-05-20', 7, 7);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2019-07-10', 2, 4);
INSERT INTO saida (data_saida, codigo_medicamento, codigo_cliente ) values ('2020-06-10', 3, 5);