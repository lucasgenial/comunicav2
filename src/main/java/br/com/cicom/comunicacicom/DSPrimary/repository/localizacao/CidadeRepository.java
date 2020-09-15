package br.com.cicom.comunicacicom.DSPrimary.repository.localizacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;

@Repository
public interface CidadeRepository extends DataTablesRepository<Cidade, Long>, JpaRepository<Cidade, Long> {

	List<Cidade> findAllByEstabelecimentoIdIn(List<Long> ids);
	Cidade findByNome (String nome);
	public Page<Cidade> findAll(Pageable p);
	
	public DataTablesOutput<Cidade> findAllByUf(UnidadeFederativa uf);
	 
	Page<Cidade> findByNomeLikeOrUfNomeLike(String nomeCidade,String nomeUf, Pageable pageable);

}