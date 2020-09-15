package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom6;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

@Repository
public interface Cicom6Repository extends JpaRepository<Cicom6, Long>,Importacao{

	List<Cicom6> findById(Cicom6 id);
	List<Cicom6> findByIdAndEstabelecimento(Long id, Long Estabelecimento);

}