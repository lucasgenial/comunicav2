package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom8;

@Repository
public interface Cicom8Repository extends JpaRepository<Cicom8, Long> {

	List<Cicom8> findById (Cicom8 id);	
	List<Cicom8> findByIdAndEstabelecimento (Long id, Long estabelecimento);
}
