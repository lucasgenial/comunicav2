package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom7;

@Repository
public interface Cicom7Repository extends JpaRepository<Cicom7, Long>{
	
	List<Cicom7> findById (Cicom7 id);	
	List<Cicom7> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
