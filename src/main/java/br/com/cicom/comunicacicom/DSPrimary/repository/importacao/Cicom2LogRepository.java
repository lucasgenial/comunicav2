package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom2Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom2LogRepository extends JpaRepository<Cicom2Log, Long>,Importacao{
	
	List<Cicom2Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom2Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
