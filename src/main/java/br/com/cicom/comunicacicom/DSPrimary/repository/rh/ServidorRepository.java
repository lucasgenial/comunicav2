package br.com.cicom.comunicacicom.DSPrimary.repository.rh;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

@Repository
public interface ServidorRepository extends DataTablesRepository<Servidor, Long>, JpaRepository<Servidor, Long> {
	
	Grupo findByNome(String value);
	
	List<Servidor> findAllByEstabelecimentoAndInstituicaoInAndIdNotInAndAtivoIsTrue(Estabelecimento estabelecimento,Set<Instituicao> instituicoes, List<Long> ids);
	
	List<Servidor> findByEstabelecimento(Estabelecimento estabelecimento);
	
	List<Servidor> findByEstabelecimentoAndInstituicao(Estabelecimento est, Instituicao inst);
	
	List<Servidor> findByMatriculaLike(String matricula);
	
	Servidor findByMatricula(String matricula);
	
}