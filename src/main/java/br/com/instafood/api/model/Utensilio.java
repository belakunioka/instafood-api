package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Utensilio {

    @Id @Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;
    
    @Getter @Setter private String nome;
    
    public Utensilio(String nome) {
        this.nome = nome;
    }
}

