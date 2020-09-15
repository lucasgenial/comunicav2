package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom13;

@Repository
public interface Cicom13Repository extends JpaRepository<Cicom13, Long>{
	
	List<Cicom13> findById (Cicom13 id);	
	List<Cicom13> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
