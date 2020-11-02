package br.com.instafood.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.ReceitaTag;

public interface ReceitaTagRepository extends CrudRepository <ReceitaTag, Integer>{
	public List<ReceitaTag> findByTagId(int tagId);
}
