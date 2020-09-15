package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom9Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom9LogRepository extends JpaRepository<Cicom9Log, Long>,Importacao{
	
	List<Cicom9Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom9Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
