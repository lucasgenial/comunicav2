package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom10Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom10LogRepository extends JpaRepository<Cicom10Log, Long>,Importacao{
	
	List<Cicom10Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom10Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
