package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom1;

@Repository
public interface Cicom1Repository extends JpaRepository<Cicom1, Long> {

	List<Cicom1> findById (Cicom1 id);	
	List<Cicom1> findByIdAndEstabelecimento (Long id, Long estabelecimento);
	
}
