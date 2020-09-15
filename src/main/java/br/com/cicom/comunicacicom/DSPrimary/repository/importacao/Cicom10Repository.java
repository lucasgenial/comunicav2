package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom10;

@Repository
public interface Cicom10Repository extends JpaRepository<Cicom10, Long>{

	List<Cicom10> findById (Cicom10 id);
	
	List<Cicom10> findByIdAndEstabelecimento (Long id, Long estabelecimento);
	
}
