package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom15;

@Repository
public interface Cicom15Repository extends JpaRepository<Cicom15, Long> {
	
	List<Cicom15> findById (Cicom15 id);
	
	List<Cicom15> findByIdAndEstabelecimento (Long id, Long estabelecimento);

}
