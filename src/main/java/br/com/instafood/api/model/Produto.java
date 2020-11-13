package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Produto {

    @Id
    @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;
    
    @Column(unique = true)
    @NotNull
    @Getter @Setter private String nome;
    
    public Produto(String nome) {
        this.nome = nome;
    }
}