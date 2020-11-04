package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class ReceitaIngrediente {
	
	@Id @Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private int idReceitaIngrediente;
	
	@JoinColumn(name = "receita_id", nullable = false)
	@Getter @Setter private Receita receita;
	
	@JoinColumn(name = "ingrediente_id", nullable = false)
	@Getter @Setter private Ingrediente ingrediente;
	
	@Getter @Setter private int quantidade;
	
	@Getter @Setter private String unidade;
	
}
