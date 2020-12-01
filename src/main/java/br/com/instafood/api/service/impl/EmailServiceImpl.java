package br.com.instafood.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender emailSender;
	 
	@Override
	public void sendEmailConfirmarConta(Usuario usuario) {
		String nome = usuario.getNome().split(" ")[0];
		String titulo = "Instafood - Confirme sua conta";
		String texto = "Olá " + nome + "! Você recebeu este e-mail "
				+ "pois se cadastrou em nosso site. Por favor, clique no link "
				+ "abaixo e confirme sua conta.\n"
				+ "http://127.0.0.1:5500/confirmar.html?token="
				+ usuario.getTokenConfirmarConta();
		
		sendEmail(nome, usuario.getEmail(), titulo, texto);
	}

	@Override
	public void sendEmailBemvindo(Usuario usuario) {
		String nome = usuario.getNome().split(" ")[0];
		String titulo = "Bem-vindo ao Instafood";
		String texto = "Olá " + nome + "! Bem-vindo ao Instafood!";
		
		sendEmail(nome, usuario.getEmail(), titulo, texto);
	}

	@Override
	public void sendEmailRedefinirSenha(Usuario usuario) {
		String nome = usuario.getNome().split(" ")[0];
		String titulo = "Instafood - Redefinir senha";
		String texto = "Olá " + nome + "! Você recebeu este e-mail "
				+ "pois solicitou troca de senha em nosso site. Por favor, "
				+ "clique no link abaixo e redefina sua senha.\n"
				+ "http://127.0.01:5500/redefinir.html?token="
				+ usuario.getTokenRedefinirSenha();
		
		sendEmail(nome, usuario.getEmail(), titulo, texto);
	}
	
	private void sendEmail(String nome, String email, String titulo, String texto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@instafood.com.br");
		message.setTo(email);
		message.setSubject(titulo);
		message.setText(texto);
		
		try {
			emailSender.send(message);
		} catch (MailException exc) {
			exc.printStackTrace();
		}
	}

}
