package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom15Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom15LogRepository extends JpaRepository<Cicom15Log, Long>,Importacao{
	
	List<Cicom15Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom15Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
