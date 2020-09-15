package br.com.cicom.comunicacicom.DSPrimary.repository.rh;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

@Repository
public interface HierarquiaRepository extends JpaRepository<Hierarquia, Long>{
	List<Hierarquia> findByInstituicao(Instituicao instituicao);

	Optional<Hierarquia> findByNome(String string);
}
