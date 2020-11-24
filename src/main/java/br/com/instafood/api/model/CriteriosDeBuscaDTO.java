package br.com.instafood.api.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CriteriosDeBuscaDTO {
	
	private List<Integer> produtos;
	private List<Integer> utensilios;
	private List<Integer> tags;
	
	private int pagina;
	private int tamanho;
}
