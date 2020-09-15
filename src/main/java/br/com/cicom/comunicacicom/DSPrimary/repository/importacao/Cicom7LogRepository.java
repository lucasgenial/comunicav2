package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom7Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom7LogRepository extends JpaRepository<Cicom7Log, Long>,Importacao{
	
	List<Cicom7Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom7Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
