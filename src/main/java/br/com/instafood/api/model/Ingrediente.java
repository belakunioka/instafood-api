package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Ingrediente {
	
	@Id @Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false)
	@Getter @Setter private Produto produto;
	
	@Getter @Setter private int quantidade;
	
	@Getter @Setter private String unidade;
	
}
