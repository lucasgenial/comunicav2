package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom14;

@Repository
public interface Cicom14Repository extends JpaRepository<Cicom14, Long>{
	
	List<Cicom14> findById (Cicom14 id);	
	List<Cicom14> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
