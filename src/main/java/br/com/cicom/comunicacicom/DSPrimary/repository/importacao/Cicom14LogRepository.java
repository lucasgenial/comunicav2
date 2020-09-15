package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom14Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom14LogRepository extends JpaRepository<Cicom14Log, Long>,Importacao{
	
	List<Cicom14Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom14Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
