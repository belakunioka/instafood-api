package br.com.instafood.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.repository.UsuarioRepository;

@RestController
@RequestMapping(path = "usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/{id}")
	public Optional<Usuario> getUsuario(@PathVariable int id) {
		return usuarioRepository.findById(id);
	}
	
	@PostMapping
	public Usuario createUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@PatchMapping("/{id}")
	public Usuario updateUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@PostMapping("/{id}/reset-password")
	public void resetPassword() {}
	
	@PostMapping("/login")
	public void login() {}
}
