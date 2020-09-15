package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom12Log;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface Cicom12LogRepository extends JpaRepository<Cicom12Log, Long>,Importacao{
	
	List<Cicom12Log> findByIdAndEstabelecimento(Long id, Long estabelecimento);
	List<Cicom12Log> findByNumeroocorrencia(Long numeroocorrencia);
} 
