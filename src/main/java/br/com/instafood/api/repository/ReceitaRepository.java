package br.com.instafood.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Receita;

public interface ReceitaRepository extends CrudRepository<Receita, Integer> {
	Receita findById(int id);
	
	Receita findByUtensilio_nome(String nome);
	Receita findByTag_nome(String nome);
	
}
