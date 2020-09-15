package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom13Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom13LogRepository extends JpaRepository<Cicom13Log, Long>,Importacao{
	
	List<Cicom13Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom13Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
