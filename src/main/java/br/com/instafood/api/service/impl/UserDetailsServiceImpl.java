package br.com.instafood.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		
		// Verifica se o usuário existe
		if (usuario == null) throw new UsernameNotFoundException("Usuário não encontrado");
		
		// Se o usuário for ADMIN, atribui as roles 'ADMIN' e 'USER', senão apenas 'USER'
		String[] roles = usuario.getPerfil().equals("ADMIN") ? new String[]{ "ADMIN", "USER" } : new String[]{ "USER" };
		
		return User.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.disabled(!usuario.isAtivo())
				.accountLocked(!usuario.isConfirmado())
				.roles(roles)
				.build();
	}
}
