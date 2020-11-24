package br.com.instafood.api.component;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.instafood.api.model.errors.MensagemDeErro;
import br.com.instafood.api.model.errors.ObjetoJaExisteException;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import io.jsonwebtoken.JwtException;

/**
 * O objetivo dessa classe é uniformizar o tratamento de erros 
 * e mensagens da API.
 *
 */
@ControllerAdvice
public class InstafoodExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Validação de Atributos
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		
		if (errors.isEmpty()) {
			MensagemDeErro erro = new MensagemDeErro(status, "Erro de Validação Desconhecido", ex);
			return handleExceptionInternal(ex, erro, headers, status, request);
		}
			
		String mensagem = errors.get(0).getDefaultMessage();
		MensagemDeErro erro = new MensagemDeErro(status, mensagem, ex);
		
		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	/**
	 * Estrutura da request incorreta
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MensagemDeErro erro = new MensagemDeErro(status, ex.getMessage(), ex);
		return handleExceptionInternal(ex, erro, headers, erro.getStatus(), request);
	}
	
	@ExceptionHandler({ ObjetoNaoEncontradoException.class })
	public ResponseEntity<Object> handleObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, WebRequest request) {
		MensagemDeErro erro = new MensagemDeErro(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), erro.getStatus(), request);
	}
	
	@ExceptionHandler({ ObjetoJaExisteException.class })
	public ResponseEntity<Object> handleObjetoNaoEncontrado(ObjetoJaExisteException ex, WebRequest request) {
		MensagemDeErro erro = new MensagemDeErro(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), ex);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), erro.getStatus(), request);
	}
	
	@ExceptionHandler({ JwtException.class })
	public ResponseEntity<?> handleJwtException(JwtException ex, WebRequest request) {
		MensagemDeErro erro = new MensagemDeErro(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), erro.getStatus(), request);
	}
	
	@ExceptionHandler(value = { AuthenticationException.class })
	public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
		MensagemDeErro erro = new MensagemDeErro(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		return handleExceptionInternal(ex, erro, new HttpHeaders(), erro.getStatus(), request);
	}
}
