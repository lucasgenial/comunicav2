package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom23;

@Repository
public interface Cicom23Repository extends JpaRepository<Cicom23, Long> {
	
	List<Cicom23> findById (Cicom23 id);
	
	List<Cicom23> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
