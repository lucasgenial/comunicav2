package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom5;

@Repository
public interface Cicom5Repository extends JpaRepository<Cicom5, Long> {
	
	List<Cicom5> findById (Cicom5 id);	
	List<Cicom5> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
