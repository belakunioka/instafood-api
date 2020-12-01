package br.com.instafood.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Receita;

public interface ReceitaRepository extends CrudRepository<Receita, Integer> {
	Receita findById(int id);
	
	Iterable<Receita> findAll();
	
	List<Receita> findByTipo(String tipo);	
}
