package br.com.instafood.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Usuario findById(int id);
	
	Usuario findByEmail(String email);
	
	boolean existsByEmail(String email);
}
