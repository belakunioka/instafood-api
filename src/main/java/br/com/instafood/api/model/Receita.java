package br.com.instafood.api.model;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor

public class Receita {
	
	@Id @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private int id;	
	
	@Column(name = "titulo")
	@NotNull
	private String titulo;
	
	@Column(name = "imagem")
	@NotNull
	private String imagem;
	
	@Column(name="data_criacao", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
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
	
	@Column(name="instrucoes", columnDefinition="TEXT")
	@NotNull
	private String instrucoes;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "usuario_id", insertable=false, updatable=false)
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="receita_utensilio", joinColumns = {
			@JoinColumn(name="receita_id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="utensilio_id", nullable=false)
	})
	private List <Utensilio> utensilios = new ArrayList<Utensilio>();
	
	@OneToMany(mappedBy = "receita")
	@JsonManagedReference
	private List <Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name="receita_tag", joinColumns = {
			@JoinColumn(name="receita_id", nullable=false)}, inverseJoinColumns = {
					@JoinColumn(name="tag_id", nullable=false)
	})
	private List <Tag> tags = new ArrayList<Tag>();
		
}