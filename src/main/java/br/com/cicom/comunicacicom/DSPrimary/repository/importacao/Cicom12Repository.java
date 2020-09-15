package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom12;

@Repository
public interface Cicom12Repository extends JpaRepository<Cicom12, Long>{

	List<Cicom12> findById (Cicom12 id);
	
	List<Cicom12> findByIdAndEstabelecimento (Long id, Long estabelecimento);
	
}
