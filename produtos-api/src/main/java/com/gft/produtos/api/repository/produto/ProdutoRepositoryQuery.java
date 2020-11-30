package com.gft.produtos.api.repository.produto;

import java.util.List;

import javax.persistence.criteria.Order;

public interface ProdutoRepositoryQuery {

	public List<Order> ordemCrescente();
}
