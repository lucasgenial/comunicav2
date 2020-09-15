package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom22Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom22LogRepository extends JpaRepository<Cicom22Log, Long>,Importacao{
	
	List<Cicom22Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom22Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
