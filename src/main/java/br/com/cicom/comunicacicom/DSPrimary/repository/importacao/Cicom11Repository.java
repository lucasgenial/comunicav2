package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom11;

@Repository
public interface Cicom11Repository extends JpaRepository<Cicom11, Long> {
	
	List<Cicom11> findById (Cicom11 id);
	
	List<Cicom11> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
