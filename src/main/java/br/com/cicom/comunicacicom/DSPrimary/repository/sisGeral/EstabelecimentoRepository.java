package br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends DataTablesRepository<Estabelecimento, Long>, JpaRepository<Estabelecimento, Long> {
	Optional<Estabelecimento> findById(Long id);
}