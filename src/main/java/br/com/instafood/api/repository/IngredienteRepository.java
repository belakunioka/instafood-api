package br.com.instafood.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Integer> {

}
