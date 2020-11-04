package br.com.instafood.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "receita")
@Getter @Setter
@NoArgsConstructor

public class Receita {
	
	@Id @Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "image")
	private String image;
	
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@Column(name="tipo", columnDefinition="enum('Doce', 'Salgado')")
	private String tipo;

	@Column(name="tempo_preparo")
	private String tempoPreparo;
	
	@Column(name="rendimento")
	private String rendimento;
	
	@Column(name="instrucoes")
	private String instrucoes;
	
	@Column(name="autor_id")
	private int autorId;
	
	@ManyToOne
	@JoinColumn(name = "autor_id", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToMany
	@JoinTable(name="utensilio", joinColumns = {
			@JoinColumn(name="id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="id", nullable=false)
	})
	private List <Utensilio> utensilios;
	
	@ManyToMany
	@JoinTable(name="ingrediente", joinColumns = {
			@JoinColumn(name="id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="id", nullable=false)
	})
	private List <Ingrediente> ingredientes;
	
	@ManyToMany
	@JoinTable(name="tag", joinColumns = {
			@JoinColumn(name="id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="id", nullable=false)
	})
	private List <Tag> tags;
	
}