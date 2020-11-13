package br.com.instafood.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.Receita;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.ReceitaRepository;

@RestController
@RequestMapping("receitas")
public class ReceitaController {
	
	@Autowired
	public ReceitaRepository receitaRepository;
	
	@PostMapping
	public ResponseEntity<Receita> createReceita(@RequestBody Receita receita) {
		
		receita.setDataCriacao(new Date());
		Receita novaReceita = receitaRepository.save(receita);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita);
	}
	
	@PatchMapping
	public ResponseEntity<Receita> updateReceita(@RequestBody Receita receitaAtualizada) {
		Receita receita = receitaRepository.findById(receitaAtualizada.getId());
		
		receitaRepository.save(receita);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(receita);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteReceita(@PathVariable int id) throws Throwable {
		
		if (!receitaRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Receita ID: " + id + " não foi encontrada.");
		
		receitaRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Receita>> findAllReceitas() {
		Iterable<Receita> receitas = receitaRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(receitas);
	}

	
	@GetMapping("{id}")
	public ResponseEntity<Receita> findReceitabyId(@PathVariable int id) {
		Receita receita = receitaRepository.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(receita);
	}
	
//	@GetMapping("{utensilio}")
//	public ResponseEntity<Receita> findReceitabyUtensilio(@PathVariable String nome) {
//		return;
//	}
//	
//	@GetMapping("{tag}")
//	public ResponseEntity<Receita> findReceitabyTag(@PathVariable String nome) {
//		return;
//	}
	
	
}
