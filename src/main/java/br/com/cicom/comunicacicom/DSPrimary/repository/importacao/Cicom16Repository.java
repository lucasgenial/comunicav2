package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom16;

@Repository
public interface Cicom16Repository extends JpaRepository<Cicom16, Long>{
	
	List<Cicom16> findById (Cicom16 id);	
	List<Cicom16> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
