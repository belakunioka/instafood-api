package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Ingrediente {
	
	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@JsonBackReference
	@Getter @Setter private Receita receita;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	@Getter @Setter private Produto produto;
	
	@Column(columnDefinition = "DECIMAL(5, 2) DEFAULT 0.0")
	@Getter @Setter private int quantidade;
	
	@Getter @Setter private String unidade;
	
}
