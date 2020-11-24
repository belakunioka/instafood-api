package br.com.instafood.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.instafood.api.validators.annotations.ConfirmacaoDeSenha;
import br.com.instafood.api.validators.NaCriacao;
import br.com.instafood.api.validators.NaTrocaDeSenha;
import br.com.instafood.api.validators.NaAtualizacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @NoArgsConstructor
@ConfirmacaoDeSenha(campos = { "senha", "confirmacaoSenha" }, 
	message = "As senhas devem ser iguais", 
	groups = { NaCriacao.class, NaTrocaDeSenha.class })
public class Usuario {

	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@NotNull(message = "O campo nome não pode ser nulo", 
		groups = { NaCriacao.class, NaAtualizacao.class })
	@Size(min = 2, max = 60, message = "O campo nome deve conter entre 2 e 60 caracteres", 
		groups = { NaCriacao.class, NaAtualizacao.class })
	private String nome;
	
	@Column(unique = true)
	@NotEmpty(message = "O campo email não pode ser nulo", 
		groups = { NaCriacao.class })
	@Email(message = "O campo email deve conter um e-mail válido", 
		groups = { NaCriacao.class })
	private String email;
	
	@Column
	private String imagem;
	
	@Column
	@ToString.Exclude
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotNull(message = "O campo senha não pode ser nulo", 
		groups = { NaCriacao.class, NaTrocaDeSenha.class })
	@Size(min = 6, max = 16, message = "O campo senha deve conter entre 6 e 16 caracteres", 
		groups = { NaCriacao.class, NaTrocaDeSenha.class })
	private String senha;
	
	@Transient
	@ToString.Exclude
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotNull(message = "O campo confirmação de senha não pode ser nulo", 
		groups = { NaCriacao.class, NaTrocaDeSenha.class })
	@Size(min = 6, max = 16, message = "O campo confirmação de senha deve conter entre 6 e 16 caracteres", 
		groups = { NaCriacao.class, NaTrocaDeSenha.class })
	private String confirmacaoSenha;
	
	@Column
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Date dataCriacao;
	
	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean ativo;
	
	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean confirmado;
	
	@Column(columnDefinition = "ENUM('ADMIN', 'USER')")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String perfil = "USER";
	
	@Column
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String tokenRedefinirSenha;
	
	@Column
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String tokenConfirmarConta;
	
	@Transient
	@ToString.Exclude
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String token;
}