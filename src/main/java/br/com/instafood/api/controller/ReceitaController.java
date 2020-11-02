package br.com.instafood.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.Receita;
import br.com.instafood.api.repository.ReceitaRepository;

@RestController
@RequestMapping(path = "receitas")
public class ReceitaController {
	
	@Autowired
	public ReceitaRepository receitaRepository;
	
	@GetMapping
	public Iterable<Receita> findAllReceitas(){
		return receitaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Receita> getReceita(@PathVariable int id) {
		return receitaRepository.findById(id);
	}
	
	@PostMapping
	public Receita createReceita(@RequestBody Receita receita) {
		return receitaRepository.save(receita);
	}
	
	@PatchMapping("/{id}")
	public Receita updateReceita(@RequestBody Receita receita) {
		return receitaRepository.save(receita);
	}
	
	@DeleteMapping("/{id}")
	public void deleteReceita(@PathVariable int id) {
		receitaRepository.deleteById(id);
	}
}
