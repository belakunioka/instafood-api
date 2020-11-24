package br.com.instafood.api.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.instafood.api.model.CriteriosDeBuscaDTO;
import br.com.instafood.api.model.Receita;
import br.com.instafood.api.model.errors.ObjetoNaoEncontradoException;
import br.com.instafood.api.repository.ReceitaRepository;
import br.com.instafood.api.service.ReceitaService;

@RestController
@RequestMapping("api/receitas")
public class ReceitaController {
	
	@Value("${files.upload-dir}")
	private String diretorioUpload;
	
	@Autowired
	public ReceitaRepository receitaRepository;
	
	@Autowired
	public ReceitaService receitaService;
	
	@PostMapping
	public ResponseEntity<Receita> createReceita(@RequestBody Receita receita) {
		
		receita.setDataCriacao(new Date());
		Receita novaReceita = receitaRepository.save(receita);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita);
	}
	
	@PatchMapping("receita/imagem")
	public ResponseEntity<Object> uploadImagemReceita(@RequestParam int id, @RequestParam MultipartFile imagem) throws Throwable {
		String extensao = FilenameUtils.getExtension(imagem.getOriginalFilename());
		String nomeDoArquivo = "receita" + id + "." + extensao;
		Path caminho = Paths.get(diretorioUpload + File.separator + nomeDoArquivo);
		Files.copy(imagem.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);
		Receita receita = receitaRepository.findById(id);
		receita.setImagem(nomeDoArquivo);
		receitaRepository.save(receita);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(receita);
	}
	
	@PatchMapping
	public ResponseEntity<Receita> updateReceita(@RequestBody Receita receitaAtualizada) {
		Receita receita = receitaRepository.findById(receitaAtualizada.getId());
		
		receitaRepository.save(receita);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(receita);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteReceita(@PathVariable int id) throws Throwable {
		
		if (!receitaRepository.existsById(id))
			throw new ObjetoNaoEncontradoException("Receita ID: " + id + " não foi encontrada.");
		
		receitaRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Receita>> findAllReceitas() {
		Iterable<Receita> receitas = receitaRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(receitas);
	}

	
	@GetMapping("{id}")
	public ResponseEntity<Receita> findReceitabyId(@PathVariable int id) {
		Receita receita = receitaRepository.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(receita);
	}

	@PostMapping("busca")
	public ResponseEntity<?> findAllByParams(@RequestBody CriteriosDeBuscaDTO criterios){
		List<Receita> receitas = receitaService.buscarReceitas(criterios);
		
		PagedListHolder<Receita> pagina = new PagedListHolder<Receita>(receitas);
		pagina.setPageSize(criterios.getTamanho());
		pagina.setPage(criterios.getPagina());
		
		return ResponseEntity.status(HttpStatus.OK).body(pagina.getPageList());
	}	
}
