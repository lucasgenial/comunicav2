package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom9;

@Repository
public interface Cicom9Repository extends JpaRepository<Cicom9, Long> {
	
	List<Cicom9> findById (Cicom9 id);
	
	List<Cicom9> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
