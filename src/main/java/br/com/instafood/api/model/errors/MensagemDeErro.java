package br.com.instafood.api.model.errors;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MensagemDeErro {

	private HttpStatus status;
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	
	private String codigo;
	
	private String mensagem;
	
	private String debug;
		
	private MensagemDeErro() {
	    timestamp = new Date();
	}

	public MensagemDeErro(HttpStatus status) {
	    this();
	    this.status = status;
	}

	public MensagemDeErro(HttpStatus status, Throwable ex) {
	    this();
	    this.status = status;
	    this.mensagem = ex.getMessage();
	    this.debug = ex.getLocalizedMessage();
	}

	public MensagemDeErro(HttpStatus status, String mensagem, Throwable ex) {
	    this();
	    this.status = status;
	    this.mensagem = mensagem;
	    this.debug = ex.getLocalizedMessage();
	}
	
	public MensagemDeErro(HttpStatus status, String codigo, String mensagem, Throwable ex) {
	    this();
	    this.status = status;
	    this.codigo = codigo;
	    this.mensagem = mensagem;
	    this.debug = ex.getLocalizedMessage();
	}
}
