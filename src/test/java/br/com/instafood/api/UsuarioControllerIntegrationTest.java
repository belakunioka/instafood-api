package br.com.instafood.api;

import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.any;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.instafood.api.controller.UsuarioController;
import br.com.instafood.api.model.Usuario;
import br.com.instafood.api.repository.UsuarioRepository;

@WebMvcTest(UsuarioController.class)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class UsuarioControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	private final MediaType DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON;
	
	@Test
    void contextLoads() {}
	
	@Test
	public void testing() throws Exception {
		ObjectNode usuario = objectMapper.createObjectNode();
		usuario.put("nome", "Maria Joana");
		usuario.put("email", "maria.joana@gmail.com");
		usuario.put("senha", "test1234");
		usuario.put("confirmacaoSenha", "test1234");
		
		Usuario user = new Usuario();
		user.setNome("Maria Joana");
		user.setEmail("maria.joana@gmail.com");
		user.setSenha("test1234");
		user.setConfirmacaoSenha("test1234");
		
		mvc.perform(post("http://localhost:8080/usuarios")
//				.contentType(DEFAULT_MEDIA_TYPE)
				.content(objectMapper.writeValueAsString(usuario)))
//			.andExpect(status().isCreated())
//			.andExpect(content().contentTypeCompatibleWith(DEFAULT_MEDIA_TYPE))
//			.andDo(print())
			//.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nome", is("Maria Joana")))
			.andExpect(jsonPath("$.email", is("maria.joana@gmail.com")))
			.andExpect(jsonPath("$.ativo", is(true)))
			.andExpect(jsonPath("$.confirmado", is(false)));
	}
}
