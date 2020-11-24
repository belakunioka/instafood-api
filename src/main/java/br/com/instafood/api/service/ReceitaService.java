package br.com.instafood.api.service;

import java.util.List;

import br.com.instafood.api.model.CriteriosDeBuscaDTO;
import br.com.instafood.api.model.Receita;

public interface ReceitaService {
	
	List<Receita> buscarReceitas(CriteriosDeBuscaDTO criterios);
}
