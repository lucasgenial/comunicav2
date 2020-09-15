package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom3;

@Repository
public interface Cicom3Repository extends JpaRepository<Cicom3, Long> {
	
	List<Cicom3> findById (Cicom3 id);
	
	List<Cicom3> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
