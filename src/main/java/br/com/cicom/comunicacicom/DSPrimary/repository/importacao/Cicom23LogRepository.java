package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom23Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom23LogRepository extends JpaRepository<Cicom23Log, Long>,Importacao{
	
	List<Cicom23Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom23Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
