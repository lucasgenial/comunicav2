package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom19Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom19LogRepository extends JpaRepository<Cicom19Log, Long>,Importacao{
	
	List<Cicom19Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom19Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
