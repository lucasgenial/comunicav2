package br.com.cicom.comunicacicom.DSPrimary.repository.localizacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;

@Repository
public interface UfRepository extends JpaRepository<UnidadeFederativa, Long> {

	UnidadeFederativa findByNomeIgnoreCase (String nome);

	List<UnidadeFederativa> findAllByAtivo(Boolean status);
}
