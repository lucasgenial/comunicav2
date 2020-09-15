package br.com.cicom.comunicacicom.DSPrimary.repository.sisGeral;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;

@Repository
public interface UnidadeRepository extends DataTablesRepository<Unidade, Long>, JpaRepository<Unidade, Long> {
	List<Unidade> findByInstituicao(Instituicao Instituicao);
	List<Unidade> findByInstituicaoId(Long IdInstituicao);
	List<Unidade> findAllByCidade(Cidade cidade);
	List<Unidade> findAllByInstituicaoInAndEstabelecimento(Set<Instituicao> instituições, Estabelecimento estabelecimento);
	List<Unidade> findAllByInstituicaoIdAndEstabelecimentoId(Long id, Long Id);
	List<Unidade> findByEstabelecimentoIn(Set<Estabelecimento> estabelecimento);	
}