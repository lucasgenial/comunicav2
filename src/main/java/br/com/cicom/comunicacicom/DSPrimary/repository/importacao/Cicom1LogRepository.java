package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom1Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom1LogRepository extends JpaRepository<Cicom1Log, Long>,Importacao{
	
	List<Cicom1Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom1Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
