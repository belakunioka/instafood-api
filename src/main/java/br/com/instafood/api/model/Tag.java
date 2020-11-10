package br.com.instafood.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
//@Table(name = "tag") 
@NoArgsConstructor
public class Tag {

	@Id @Column(name = "id") 
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int id;
	
	@Column (name = "nome")
	@Getter @Setter private String nome;
}	
