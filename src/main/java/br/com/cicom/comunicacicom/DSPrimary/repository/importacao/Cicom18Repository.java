package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom18;

@Repository
public interface Cicom18Repository extends JpaRepository<Cicom18, Long>{

	List<Cicom18> findById (Cicom18 id);
	
	List<Cicom18> findByIdAndEstabelecimento (Long id, Long estabelecimento);
	
}
