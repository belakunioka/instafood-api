package br.com.instafood.api.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.instafood.api.component.JwtToken;
import br.com.instafood.api.model.LoginDTO;
import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.repository.UsuarioRepository;
import br.com.instafood.api.service.EmailService;
import br.com.instafood.api.validators.NaAtualizacao;
import br.com.instafood.api.validators.NaCriacao;
import br.com.instafood.api.validators.NaTrocaDeSenha;
import io.jsonwebtoken.SignatureException;

@CrossOrigin
@RestController
@RequestMapping("api")
public class UsuarioController {

	@Autowired
	private JwtToken jwtToken;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Value("${files.upload-dir}")
	private String diretorioUpload;
	
	/**
	 * Login do usuário
	 */
	@PostMapping("auth/entrar")
	public ResponseEntity<Usuario> entrar(@RequestBody LoginDTO dto) throws Throwable {
		
		// Aqui é onde a autenticação realmente acontece, se a conta do usuário estiver
		// desativada, não confirmada ou o usuário não for encontrado, esse método lançará
		// as respectivas exceções.
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
						
		// Gera o token que deverá estar presente nas requests feitas pelo front-end
		String token = jwtToken.generateLoginToken(dto.getEmail());
						
		// Instancia e atribui o token ao usuário
		Usuario usuarioLogado = usuarioRepository.findByEmail(dto.getEmail());
		usuarioLogado.setToken(token);
				
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioLogado);
	}
	
	/**
	 * Cria uma conta de usuário. Essa rota é usada para usuários se cadastrarem no site,
	 * não é possível criar contas administrativas por aqui.
	 */
	@PostMapping("auth/registrar")
	public ResponseEntity<Usuario> registrarConta(@Validated(NaCriacao.class) @RequestBody Usuario novoUsuario) throws Throwable {
		
		// Tenta instanciar o usuário para verificar se ele já existe
		Usuario usuario = usuarioRepository.findByEmail(novoUsuario.getEmail());
				
		// Se existe, lança uma exceção de acordo com o status da conta
		if (usuario != null) {
					
			// a conta está desativada
			if (!usuario.isAtivo())
				throw new DisabledException("A conta está desativada");
					
			// a conta ainda não foi confirmada
			if (!usuario.isConfirmado())
				throw new LockedException("A conta ainda não foi confirmada");
					
			// a conta existe, está ativa e já foi confirmada
			throw new BadCredentialsException("A conta já existe");
		}
				
		novoUsuario.setAtivo(true);
		novoUsuario.setConfirmado(false);
		novoUsuario.setDataCriacao(new Date());
		novoUsuario.setPerfil("USER");
		novoUsuario.setImagem("/img/usuarios/undefined.jpg");
		novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
				
		// Gera o token que será enviado para o email do usuário
		String token = jwtToken.generateConfirmationToken(novoUsuario.getEmail());
		novoUsuario.setTokenConfirmarConta(token);
		usuarioRepository.save(novoUsuario);
		
		// Envia o email de confirmação
		emailService.sendEmailConfirmarConta(novoUsuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}
	
	/**
	 * Após se cadastrar, o usuário recebe um email de confirmação de conta com um token, 
	 * essa rota é exatamente para essa confirmação.
	 */
	@GetMapping("auth/confirmar")
	public ResponseEntity<Usuario> confirmarConta(@RequestParam String token) throws Throwable {
		
		// Obtém o email codificado no token (subject)
		String email = jwtToken.getEmail(token);
					
		// Verifica se o usuário existe
		Usuario usuario = usuarioRepository.findByEmail(email);
				
		if (usuario == null)
			throw new UsernameNotFoundException("Usuário não encontrado");
				
		// Verifica se os tokens são iguais
		if (!token.equals(usuario.getTokenConfirmarConta())) 
			throw new SignatureException("Token inválido");
				
		// Confirma a conta do usuario
		usuario.setConfirmado(true);
				
		// Apaga o token de confirmação
		usuario.setTokenConfirmarConta(null);
				
		usuarioRepository.save(usuario);
		emailService.sendEmailBemvindo(usuario);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
	}
	
	/**
	 * Rota usada para solicitar um email com um token para o usuário quando ele esquece a senha.
	 */
	@GetMapping("auth/esquecer")
	public ResponseEntity<?> esquecerSenha(@RequestParam String email) throws Throwable {
		// Verifica se o usuário existe
		Usuario usuario = usuarioRepository.findByEmail(email);
						
		if (usuario == null) throw new UsernameNotFoundException("Usuário não encontrado");
				
		// Gera o token de redefinição de senha
		String token = jwtToken.generateResetToken(email);
				
		// Salva no cadastro do usuário
		usuario.setTokenRedefinirSenha(token);
		usuarioRepository.save(usuario);
				
		emailService.sendEmailRedefinirSenha(usuario);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
	}
	
	/**
	 * Redefine a senha do usuário com o token recebido por email.
	 */
	@PostMapping("auth/redefinir")
	public ResponseEntity<?> redefinirSenha(@Validated(NaTrocaDeSenha.class) @RequestBody Usuario usuario) throws Throwable {
		
		// Obtém o email codificado no token (subject)
		String email = jwtToken.getEmail(usuario.getTokenRedefinirSenha());
							
		// Verifica se o usuário existe
		Usuario usuarioEsquecido = usuarioRepository.findByEmail(email);
						
		if (usuarioEsquecido == null)
			throw new UsernameNotFoundException("Usuário não encontrado");
						
		// Verifica se os tokens são iguais
		if (!usuarioEsquecido.getTokenRedefinirSenha().equals(usuario.getTokenRedefinirSenha())) 
			throw new SignatureException("Token inválido");
				
		// Salva a nova senha e apaga o token
		usuarioEsquecido.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEsquecido.setTokenRedefinirSenha(null);
		usuarioRepository.save(usuarioEsquecido);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	private Usuario getLoggedUser(Authentication authentication) {
		try {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return usuarioRepository.findByEmail(userDetails.getUsername());
		} catch (NullPointerException ex) {
			return null;
		}
	}
	
	@GetMapping("perfil")
	public ResponseEntity<?> getUsuarioLogado(Authentication authentication) {
		Usuario usuario = getLoggedUser(authentication);
		if (usuario == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);	
	}
	
	@PostMapping("perfil")
	public ResponseEntity<?> updatePerfilDoUsuario(Authentication authentication, 
			@Validated(NaAtualizacao.class) @RequestBody Usuario usuarioAtualizado) {
		Usuario usuario = getLoggedUser(authentication);
		if (usuario == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		
		usuario.setNome(usuarioAtualizado.getNome());
		usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
	}
	
	@PostMapping("perfil/senha")
	public ResponseEntity<?> mudarSenha(Authentication authentication, 
			@Validated(NaTrocaDeSenha.class) @RequestBody Usuario usuarioAtualizado) {
		Usuario usuario = getLoggedUser(authentication);
		if (usuario == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		
		usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
		usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PatchMapping("perfil/imagem")
	public ResponseEntity<Object> uploadImagemUsuario(Authentication authentication, 
			@RequestParam MultipartFile imagem) throws Throwable {
		
		Usuario usuario = getLoggedUser(authentication);
		if (usuario == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		
		// Obtém a extensão do arquivo
		String extensao = FilenameUtils.getExtension(imagem.getOriginalFilename());
		
		// Constrói o nome do arquivo baseado no id do usuário
		String nomeDoArquivo = "usuario" + usuario.getId() + "." + extensao;
		
		// Salva o arquivo na pasta de imagens
		Path caminho = Paths.get(diretorioUpload + File.separator + nomeDoArquivo);
		Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
		
		// Salva o nome da imagem no perfil do usuário (vai ser sempre o mesmo nome)
		usuario.setImagem(nomeDoArquivo);
		usuarioRepository.save(usuario);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuario);
	}
}
