
package br.com.instafood.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Utensilio;

public interface UtensilioRepository extends CrudRepository<Utensilio, Integer> {

    Utensilio findByNome(String nome);
}
