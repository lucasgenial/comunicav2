package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom11Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom11LogRepository extends JpaRepository<Cicom11Log, Long>,Importacao{
	
	List<Cicom11Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom11Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
