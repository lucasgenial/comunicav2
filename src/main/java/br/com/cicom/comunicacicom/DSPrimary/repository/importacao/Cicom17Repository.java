package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom17;

@Repository
public interface Cicom17Repository extends JpaRepository<Cicom17, Long> {
	
	List<Cicom17> findById (Cicom17 id);	
	List<Cicom17> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
