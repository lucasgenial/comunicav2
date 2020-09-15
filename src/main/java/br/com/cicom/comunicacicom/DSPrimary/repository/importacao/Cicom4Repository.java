package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom4;

@Repository
public interface Cicom4Repository extends JpaRepository<Cicom4, Long> {
	
	List<Cicom4> findById (Cicom4 id);	
	List<Cicom4> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
