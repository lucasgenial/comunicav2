package br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

@Repository
public interface InstituicaoRepository extends DataTablesRepository<Instituicao, Long>, JpaRepository<Instituicao, Long>{

	Optional<Instituicao> findByNome(String nome);
}
