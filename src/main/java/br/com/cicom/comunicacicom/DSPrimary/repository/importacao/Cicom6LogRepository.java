package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom6Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom6LogRepository extends JpaRepository<Cicom6Log, Long>,Importacao{
	
	List<Cicom6Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom6Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
