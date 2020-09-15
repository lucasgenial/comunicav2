package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom20;

@Repository
public interface Cicom20Repository extends JpaRepository<Cicom20, Long> {
	
	List<Cicom20> findById (Cicom20 id);
	
	List<Cicom20> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
