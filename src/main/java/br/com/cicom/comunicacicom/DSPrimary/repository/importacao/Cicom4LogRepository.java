package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom4Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom4LogRepository extends JpaRepository<Cicom4Log, Long>,Importacao{
	
	List<Cicom4Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom4Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
