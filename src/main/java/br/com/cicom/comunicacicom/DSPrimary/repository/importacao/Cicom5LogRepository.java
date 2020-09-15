package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom5Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom5LogRepository extends JpaRepository<Cicom5Log, Long>,Importacao{
	
	List<Cicom5Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom5Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
