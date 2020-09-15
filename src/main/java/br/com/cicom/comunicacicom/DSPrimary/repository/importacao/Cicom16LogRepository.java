package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom16Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom16LogRepository extends JpaRepository<Cicom16Log, Long>,Importacao{
	
	List<Cicom16Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom16Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
