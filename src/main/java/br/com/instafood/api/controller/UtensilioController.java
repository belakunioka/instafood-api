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

import br.com.instafood.api.model.Utensilio;
import br.com.instafood.api.model.errors.ObjetoJaExisteException;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.UtensilioRepository;
import br.com.instafood.api.validators.NaCriacao;

import br.com.instafood.api.validators.NaAtualizacao;

@RestController
@RequestMapping("utensilio")
public class UtensilioController {

    @Autowired
    private UtensilioRepository utensilioRepository;
    
//    @PostMapping
//	public ResponseEntity<Utensilio> createUsuario(@Validated(NaCriacao.class) @RequestBody Utensilio utensilio) throws Throwable {
//		
//		if (utensilioRepository.findByNome(utensilio.getNome()) != null)
//			throw new ObjetoJaExisteException("Utensilio com nome '" + .getNome() + "' já existe");
//		
//       utensilio.setNome(utensilio.getNome());
//        
//		Utensilio novoUtensilio = utensilioRepository.save(utensilio);
//		
//		return ResponseEntity.status(HttpStatus.CREATED).body(novoUtensilio);
//    }
//    
    @PatchMapping
	public ResponseEntity<Utensilio> updateUtensilio(@Validated(NaAtualizacao.class) @RequestBody Utensilio utensilioAtualizado) throws Throwable {
		Utensilio utensilio = utensilioRepository.findById(utensilioAtualizado.getId()).get();
				
		if (utensilio == null)
			throw new ObjetoNaoEncontradoException("Utensilio ID " + utensilioAtualizado.getId() + "não foi encontrado");
	
		utensilio.setNome(utensilioAtualizado.getNome());	
		utensilioRepository.save(utensilio);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(utensilio);
    }
    
    @DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUtensilio(@PathVariable int id) throws Throwable {
		
		if (!utensilioRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Utensilio ID " + id + "não foi encontrado");
		
		utensilioRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
//	@GetMapping
//	public ResponseEntity<Iterable<Utensilio>> findAllUtensilios() {
//		Iterable<Utensilio> utensilios = utensilioRepository.findAll();
//		return ResponseEntity.status(HttpStatus.OK).body(utensilio);
//    }
    
    @GetMapping("id/{id}")
	public ResponseEntity<Utensilio> findUtensiliosbyId(@PathVariable int id) {

        if(utensilioRepository.findById(id).isPresent()){
            Utensilio utensilio = utensilioRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(utensilio);
        }
            
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @GetMapping("nome/{nome}")
	public ResponseEntity<Utensilio> findUtensiliobyNome(@PathVariable String nome) {

        if(utensilioRepository.findByNome(nome) != null){
            Utensilio utensilio = utensilioRepository.findByNome(nome);
            return ResponseEntity.status(HttpStatus.OK).body(utensilio);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
