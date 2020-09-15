package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom17Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom17LogRepository extends JpaRepository<Cicom17Log, Long>,Importacao{
	
	List<Cicom17Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom17Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
