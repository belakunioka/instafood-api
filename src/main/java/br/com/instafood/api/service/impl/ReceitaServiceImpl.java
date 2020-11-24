package br.com.instafood.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.HTML.Tag;

import org.springframework.stereotype.Service;

import br.com.instafood.api.model.CriteriosDeBuscaDTO;
import br.com.instafood.api.model.Produto;
import br.com.instafood.api.model.Receita;
import br.com.instafood.api.model.Utensilio;
import br.com.instafood.api.service.ReceitaService;

@Service
public class ReceitaServiceImpl implements ReceitaService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Receita> buscarReceitas(CriteriosDeBuscaDTO criterios) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Receita> receitaQuery = builder.createQuery(Receita.class);
		Root<Receita> receita = receitaQuery.from(Receita.class);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		if(!criterios.getUtensilios().isEmpty()) {
			Join<Receita, Utensilio> utensilio = receita.join("utensilios");
			In<Integer> param = builder.in(utensilio.get("id"));
			
			for (Integer id : criterios.getUtensilios())
				param.value(id);
			
			predicados.add(param);
		}
		
		if(!criterios.getTags().isEmpty()) {
			Join<Receita, Tag> tag = receita.join("tags");
			In<Integer> param = builder.in(tag.get("id"));
			
			for (Integer id : criterios.getTags())
				param.value(id);
			
			predicados.add(param);
		}
		
		if(!criterios.getProdutos().isEmpty()) {
			Join<Receita, Produto> produto = receita.join("ingredientes").join("produto");
			In<Integer> param = builder.in(produto.get("id"));
			
			for (Integer id : criterios.getProdutos())
				param.value(id);
			
			predicados.add(param);
			
		}
		
		Predicate [] predicatesArray = new Predicate[predicados.size()];
		receitaQuery.select(receita).where(predicados.toArray(predicatesArray));
		
		TypedQuery<Receita> query = entityManager.createQuery(receitaQuery);
		return query.getResultList();
	}
}
