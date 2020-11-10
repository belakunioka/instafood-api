package br.com.instafood.api.model.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjetoNaoEncontradoException extends RuntimeException {

	public ObjetoNaoEncontradoException(String message) {
		super(message);
	}
}
