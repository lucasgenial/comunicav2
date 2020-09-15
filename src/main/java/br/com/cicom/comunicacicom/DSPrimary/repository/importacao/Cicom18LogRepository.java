package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom18Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom18LogRepository extends JpaRepository<Cicom18Log, Long>,Importacao{
	
	List<Cicom18Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom18Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
