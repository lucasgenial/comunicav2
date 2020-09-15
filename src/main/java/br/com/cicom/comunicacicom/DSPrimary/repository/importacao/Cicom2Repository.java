package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom2;

@Repository
public interface Cicom2Repository extends JpaRepository<Cicom2, Long>{
	
	List<Cicom2> findById (Cicom2 id);	
	List<Cicom2> findByIdAndEstabelecimento (Long id, Long estabelecimento);
}
