package br.com.instafood.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Receita;

public interface ReceitaRepository extends CrudRepository<Receita, Integer> {
	Receita findById(int id);
	
	@Query("SELECT DISTINCT r.id, r.titulo, r.imagem FROM receita r\n"
			+ "	INNER JOIN ingrediente i \n"
			+ "		INNER JOIN produto p ON p.id = i.produto_id\n"
			+ "    ON i.receita_id = r.id\n"
			+ "	INNER JOIN receita_utensilio ru \n"
			+ "		INNER JOIN utensilio u \n"
			+ "	ON r.id = ru.receita_id \n"
			+ "	INNER JOIN receita_tag rt \n"
			+ "		INNER JOIN tag t ON t.id = rt.tag_id\n"
			+ "	ON r.id = ru.receita_id\n"
			+ "WHERE p.id IN :ingredientes \n"
			+ "	AND u.id IN :utensilios\n"
			+ "    AND t.id IN :tags;")
	public List<Receita> findByParams(List<Integer> Ingredients, List<Integer> Utensilions, List<Integer> Tags);
}
