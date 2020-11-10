package br.com.instafood.api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import br.com.instafood.api.validators.annotations.ConfirmacaoDeSenha;

public class ConfirmacaoDeSenhaValidator implements ConstraintValidator<ConfirmacaoDeSenha, Object> {

	private String campoSenha;
	
	private String campoSenhaConfirmacao;
	
	private String mensagem;
	
	@Override
	public void initialize(ConfirmacaoDeSenha constraintAnnotation) {
		this.campoSenha = constraintAnnotation.campos()[0];
		this.campoSenhaConfirmacao = constraintAnnotation.campos()[1];
		this.mensagem = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object senha = new BeanWrapperImpl(value).getPropertyValue(campoSenha);
		Object senhaParaConfirmar = new BeanWrapperImpl(value).getPropertyValue(campoSenhaConfirmacao);
		
		context.buildConstraintViolationWithTemplate(mensagem)
			.addPropertyNode(campoSenha)
			.addConstraintViolation();
		
		if (senha == null) return false;
		
		return senha.equals(senhaParaConfirmar);
	}
	
	
}
