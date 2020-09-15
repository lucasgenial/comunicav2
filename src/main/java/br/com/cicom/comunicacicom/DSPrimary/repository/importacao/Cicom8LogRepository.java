package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom8Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom8LogRepository extends JpaRepository<Cicom8Log, Long>,Importacao{
	
	List<Cicom8Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom8Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
