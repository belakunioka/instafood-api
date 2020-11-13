package br.com.instafood.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.model.errors.ObjetoJaExisteException;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.UsuarioRepository;
import br.com.instafood.api.validators.NaCriacao;
import br.com.instafood.api.validators.NaAtualizacao;

// TODO Garantir confirmado = false na criação do usuário (auth)

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@Validated(NaCriacao.class) @RequestBody Usuario usuario) throws Throwable {
		
		if (usuarioRepository.existsByEmail(usuario.getEmail())) 
			throw new ObjetoJaExisteException("Usuário com e-mail '" + usuario.getEmail() + "' já existe");
		
		usuario.setAtivo(true);
		usuario.setConfirmado(false);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		Usuario novoUsuario = usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}
	
	@PatchMapping
	public ResponseEntity<Usuario> updateUsuario(@Validated(NaAtualizacao.class) @RequestBody Usuario usuarioAtualizado) throws Throwable {
		Usuario usuario = usuarioRepository.findById(usuarioAtualizado.getId());
				
		if (usuario == null)
			throw new ObjetoNaoEncontradoException("Usuário ID " + usuarioAtualizado.getId() + "não foi encontrado");
	
		usuario.setNome(usuarioAtualizado.getNome());
		usuario.setEmail(usuarioAtualizado.getEmail());
		usuario.setAtivo(usuarioAtualizado.isAtivo());		
		usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
	}
	
	// TODO Quando excluir o usuário, exclui todas as receitas dele também?!
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUsuario(@PathVariable int id) throws Throwable {
		
		if (!usuarioRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Usuário ID " + id + "não foi encontrado");
		
		usuarioRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	// TODO Incluir critérios de busca e paginação
	@GetMapping
	public ResponseEntity<Iterable<Usuario>> findAllUsuarios() {
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario> findUsuariobyId(@PathVariable int id) {
		Usuario usuario = usuarioRepository.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}
	
	/**
	 * Retorna o usuário logado
	 */
	// TODO Implementar
	@GetMapping("perfil")
	public ResponseEntity<?> findPerfilDoUsuario(HttpServletRequest request, 
			HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
	}
	
	/**
	 * Modifica o usuário logado
	 */
	// TODO Implementar
	@PostMapping("perfil")
	public ResponseEntity<?> updatePerfilDoUsuario(HttpServletRequest request, 
			HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
	}
	
	/**
	 * Altera a senha do usuário logado
	 */
	// TODO Implementar
	@PostMapping("perfil/senha")
	public ResponseEntity<?> mudarSenha(@RequestParam String password) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
	}
}
