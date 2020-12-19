package com.gft.produtos.api.repository.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.gft.produtos.api.model.Produto;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	public List<Order> ordemCrescente(){
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		
		CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		query.select(root);
		query.orderBy(cb.asc(root.get("nome")));
		
		List<Order> listaAsc =  query.getOrderList();
		
		return listaAsc;
		
	}
}
