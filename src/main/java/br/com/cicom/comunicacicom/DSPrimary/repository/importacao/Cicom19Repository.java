package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom19;

@Repository
public interface Cicom19Repository extends JpaRepository<Cicom19, Long> {
	
	List<Cicom19> findById (Cicom19 id);
	
	List<Cicom19> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
