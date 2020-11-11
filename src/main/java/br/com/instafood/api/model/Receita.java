package br.com.instafood.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@Getter private int id;	
	
	@Column(name = "titulo")
	@NotNull
	private String titulo;
	
	@Column(name = "image")
	@NotNull
	private String image;
	
	@Column(name="data_criacao")
	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dataCriacao;
	
	@Column(name="tipo", columnDefinition="enum('Doce', 'Salgado')")
	@NotNull
	private String tipo;

	@Column(name="tempo_preparo")
	@NotNull
	private String tempoPreparo;
	
	@Column(name="rendimento")
	@NotNull
	private String rendimento;
	
	@Column(name="instrucoes")
	@NotNull
	private String instrucoes;
	
	@Column(name="autor_id")
	@NotNull
	private int autorId;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "autor_id", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="receita_utensilio", joinColumns = {
			@JoinColumn(name="receita_id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="utensilio_id", nullable=false)
	})
	private List <Utensilio> utensilios;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="receita_ingrediente", joinColumns = {
			@JoinColumn(name="receita_id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="ingrediente_id", nullable=false)
	})
	private List <Produto> ingredientes;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="receita_tag", joinColumns = {
			@JoinColumn(name="receita_id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="tag_id", nullable=false)
	})
	private List <Tag> tags;
		
}