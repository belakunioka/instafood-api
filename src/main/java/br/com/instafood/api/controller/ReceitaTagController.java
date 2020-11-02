package br.com.instafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.ReceitaTag;
import br.com.instafood.api.repository.ReceitaTagRepository;

@RestController
@RequestMapping(path = "receitatag")
public class ReceitaTagController {
	
	@Autowired
	public ReceitaTagRepository receitaTagRepository;
	
	@GetMapping()
	public Iterable<ReceitaTag> findAllReceitas(){
		return receitaTagRepository.findAll();
	}
	
	@GetMapping("/{tagId}")
	public List<ReceitaTag> getReceitaTag(@PathVariable int tagId) {
		return receitaTagRepository.findByTagId(tagId);
	}

}
