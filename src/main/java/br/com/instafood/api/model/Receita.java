package br.com.instafood.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "receita")
@NoArgsConstructor
public class Receita {
	
	@Id @Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int id;	
	
	@Column(name = "titulo")
	@Getter @Setter private String titulo;
	
	@Column(name = "image")
	@Getter @Setter private String image;
	
	@Column(name="data_criacao")
	@Getter @Setter private Date dataCriacao;
	
	@Column(name="tipo", columnDefinition="enum('Doce', 'Salgado')")
	@Getter @Setter private String tipo;

	@Column(name="tempo_preparo")
	@Getter @Setter private String tempoPreparo;
	
	@Column(name="rendimento")
	@Getter @Setter private String rendimento;
	
	@Column(name="instrucoes")
	@Getter @Setter private String instrucoes;
	
	@Column(name="autor_id")
	@Getter @Setter private int autorId;
	
	@ManyToOne
	@JoinColumn(name = "autor_id", insertable=false, updatable=false)
	private Usuario usuario;
}