package br.com.cicom.comunicacicom.DSPrimary.repository.localizacao;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;

@Repository
public interface BairroRepository extends DataTablesRepository<Bairro, Long>, JpaRepository<Bairro, Long> {
	List<Bairro> findByLocalidade(Localidade local);
	Bairro findByLocalidadeAndNome(Localidade localidade, String nome);
	List<Bairro> findByLocalidadeNome(String localidadenome);
	
} 