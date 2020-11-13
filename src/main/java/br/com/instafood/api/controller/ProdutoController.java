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

import br.com.instafood.api.model.Produto;
import br.com.instafood.api.model.errors.ObjetoJaExisteException;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.ProdutoRepository;
import br.com.instafood.api.validators.NaCriacao;

import br.com.instafood.api.validators.NaAtualizacao;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @PostMapping
	public ResponseEntity<Produto> createUsuario(@Validated(NaCriacao.class) @RequestBody Produto produto) throws Throwable {
		
		if (produtoRepository.findByNome(produto.getNome()) != null)
			throw new ObjetoJaExisteException("Produto com nome '" + produto.getNome() + "' já existe");
		
        produto.setNome(produto.getNome());
        
		Produto novoProduto = produtoRepository.save(produto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
    
    @PatchMapping
	public ResponseEntity<Produto> updateProduto(@Validated(NaAtualizacao.class) @RequestBody Produto produtoAtualizado) throws Throwable {
		Produto produto = produtoRepository.findById(produtoAtualizado.getId()).get();
				
		if (produto == null)
			throw new ObjetoNaoEncontradoException("Produto ID " + produtoAtualizado.getId() + "não foi encontrado");
	
		produto.setNome(produtoAtualizado.getNome());	
		produtoRepository.save(produto);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(produto);
    }
    
    @DeleteMapping("{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable int id) throws Throwable {
		
		if (!produtoRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Produto ID " + id + "não foi encontrado");
		
		produtoRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Produto>> findAllProdutos() {
		Iterable<Produto> produtos = produtoRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }
    
    @GetMapping("{id}")
	public ResponseEntity<Produto> findProdutosbyId(@PathVariable int id) {

        if(produtoRepository.findById(id).isPresent()){
            Produto produto = produtoRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(produto);
        }
            
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @GetMapping("{nome}")
	public ResponseEntity<Produto> findProdutobyNome(@PathVariable String nome) {

        if(produtoRepository.findByNome(nome) != null){
            Produto produto = produtoRepository.findByNome(nome);
            return ResponseEntity.status(HttpStatus.OK).body(produto);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
