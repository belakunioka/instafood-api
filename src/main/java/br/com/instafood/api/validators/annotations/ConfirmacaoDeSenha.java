package br.com.instafood.api.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.instafood.api.validators.ConfirmacaoDeSenhaValidator;

@Documented
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmacaoDeSenhaValidator.class)
public @interface ConfirmacaoDeSenha {

	String message() default "";
	
	String[] campos();
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
