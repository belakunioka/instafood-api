package br.com.instafood.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Receita {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReceita;
    
    private String nome;
    
    public Receita(String nome) {
        this.nome = nome;
    }

    public String getIdReceita(){
        return this.idReceita;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}