package br.com.instafood.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Inrediente {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int idIngrediente;
    
    @Getter @Setter private String nome;
    
    public Ingrediente(String nome) {
        this.nome = nome;
    }
}