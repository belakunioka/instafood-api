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

import br.com.instafood.api.model.Tag;
import br.com.instafood.api.model.errors.ObjetoJaExisteException;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.TagRepository;
import br.com.instafood.api.validators.NaCriacao;

import br.com.instafood.api.validators.NaAtualizacao;

@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;
    
    @PostMapping
	public ResponseEntity<Tag> createUsuario(@Validated(NaCriacao.class) @RequestBody Tag tag) throws Throwable {
		
		if (tagRepository.findByNome(tag.getNome()) != null)
			throw new ObjetoJaExisteException("Tag com nome '" + tag.getNome() + "' já existe");
		
        tag.setNome(tag.getNome());
        
		Tag novaTag = tagRepository.save(tag);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaTag);
    }
    
    @PatchMapping
	public ResponseEntity<Tag> updateTag(@Validated(NaAtualizacao.class) @RequestBody Tag tagAtualizada) throws Throwable {
		Tag tag = tagRepository.findById(tagAtualizada.getId()).get();
				
		if (tag == null)
			throw new ObjetoNaoEncontradoException("Tag ID " + tagAtualizada.getId() + "não foi encontrada");
	
		tag.setNome(tagAtualizada.getNome());
		tagRepository.save(tag);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(tag);
    }
    
    @DeleteMapping("{id}")
	public ResponseEntity<Object> deleteTag(@PathVariable int id) throws Throwable {
		
		if (!tagRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Tag ID " + id + "não foi encontrada");
		
		tagRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Tag>> findAllTags() {
		Iterable<Tag> tags = tagRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(tags);
    }
    
    @GetMapping("id/{id}")
	public ResponseEntity<Tag> findTagbyId(@PathVariable int id) {

        if(tagRepository.findById(id).isPresent()){
            Tag tag = tagRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        }
            
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @GetMapping("nome/{nome}")
	public ResponseEntity<Tag> findTagbyNome(@PathVariable String nome) {

        if(tagRepository.findByNome(nome) != null){
            Tag tag = tagRepository.findByNome(nome);
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
