package br.com.instafood.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.instafood.api.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
    
    Produto findByNome(String nome);
}
