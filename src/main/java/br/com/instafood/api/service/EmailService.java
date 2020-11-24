package br.com.instafood.api.service;

import br.com.instafood.api.model.Usuario;

public interface EmailService {

	void sendEmailConfirmarConta(Usuario usuario);
	
	void sendEmailBemvindo(Usuario usuario);
	
	void sendEmailRedefinirSenha(Usuario usuario);
}
