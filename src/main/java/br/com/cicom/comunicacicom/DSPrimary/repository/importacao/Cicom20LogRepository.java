package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom20Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom20LogRepository extends JpaRepository<Cicom20Log, Long>,Importacao{
	
	List<Cicom20Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom20Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
