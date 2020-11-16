package br.com.instafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.Ingrediente;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.IngredienteRepository;
import br.com.instafood.api.validators.NaAtualizacao;
import br.com.instafood.api.validators.NaCriacao;

@RestController
@RequestMapping("ingredientes")
public class IngredienteController{
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @PostMapping
	public ResponseEntity<Ingrediente> createIngrediente(@Validated(NaCriacao.class) @RequestBody Ingrediente ingrediente) throws Throwable {
		
        ingrediente.setProduto(ingrediente.getProduto());
        ingrediente.setQuantidade(ingrediente.getQuantidade());
        ingrediente.setUnidade(ingrediente.getUnidade());
        
		Ingrediente novoIngrediente = ingredienteRepository.save(ingrediente);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoIngrediente);
    }
    
    @PatchMapping
	public ResponseEntity<Ingrediente> updateIngrediente(@Validated(NaAtualizacao.class) @RequestBody Ingrediente ingredienteAtualizado) throws Throwable {
		Ingrediente ingrediente = ingredienteRepository.findById(ingredienteAtualizado.getId()).get();
				
		if (ingrediente == null)
			throw new ObjetoNaoEncontradoException("Ingrediente ID " + ingredienteAtualizado.getId() + "não foi encontrado");
	
        ingrediente.setProduto(ingredienteAtualizado.getProduto());
        ingrediente.setQuantidade(ingredienteAtualizado.getQuantidade());
        ingrediente.setUnidade(ingredienteAtualizado.getUnidade());

		ingredienteRepository.save(ingrediente);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ingrediente);
    }
    
    @DeleteMapping("{id}")
	public ResponseEntity<Object> deleteIngrediente(@PathVariable int id) throws Throwable {
		
		if (!ingredienteRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Ingrediente ID " + id + "não foi encontrado");
		
		ingredienteRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Ingrediente>> findAllIngredientes() {
		Iterable<Ingrediente> ingredientes = ingredienteRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(ingredientes);
    }
    
    @GetMapping("id/{id}")
	public ResponseEntity<Ingrediente> findIngredientesbyId(@PathVariable int id) {

        if(ingredienteRepository.findById(id).isPresent()){
            Ingrediente ingrediente = ingredienteRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(ingrediente);
        }
            
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
