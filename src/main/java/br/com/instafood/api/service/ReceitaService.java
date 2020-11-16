package br.com.instafood.api.service;

import java.util.List;

import br.com.instafood.api.model.CriteriosDeBusca;
import br.com.instafood.api.model.Receita;

public interface ReceitaService {
	
	List<Receita> buscarReceitas(CriteriosDeBusca criterios);
}
