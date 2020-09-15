package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom22;

@Repository
public interface Cicom22Repository extends JpaRepository<Cicom22, Long> {
	
	List<Cicom22> findById (Cicom22 id);
	
	List<Cicom22> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
