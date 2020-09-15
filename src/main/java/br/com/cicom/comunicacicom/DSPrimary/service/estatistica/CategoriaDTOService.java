package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.POJO.CategoriaPOJO;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.CategoriaPOJORepository;

@Service
public class CategoriaDTOService {

	@Autowired
	private CategoriaPOJORepository repositorio;
	
	public List<CategoriaPOJO> buscarEstatistica(List<Long> listaCidades, List<Long> listaCategorias, 
			 LocalDateTime dataInicial, LocalDateTime dataFinal){
		return repositorio.buscaDadosEstatistica(listaCidades, listaCategorias, dataInicial.toLocalDate(), dataFinal.toLocalDate()); 
	}

	public List<CategoriaPOJO> buscarPorTipificacao(List<Long> listaCidades, List<Long> categorias, LocalDateTime inicio,
			LocalDateTime fim) {
		return repositorio.buscaDadosEstatisticaTipo(listaCidades, categorias, inicio.toLocalDate(), fim.toLocalDate());
	}
}
