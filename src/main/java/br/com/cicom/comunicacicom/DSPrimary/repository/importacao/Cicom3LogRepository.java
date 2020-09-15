package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom3Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom3LogRepository extends JpaRepository<Cicom3Log, Long>,Importacao{
	
	List<Cicom3Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom3Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
