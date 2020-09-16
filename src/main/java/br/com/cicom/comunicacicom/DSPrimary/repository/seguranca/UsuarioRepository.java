package br.com.cicom.comunicacicom.DSPrimary.repository.seguranca;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

@Repository
public interface UsuarioRepository extends DataTablesRepository<Usuario, Long>, JpaRepository<Usuario, Long>{
	Optional<Usuario> findByLogin(String login);
	List<Usuario> findByEstabelecimento(Estabelecimento estabelecimento);
	List<Usuario> findByEstabelecimentoIn(List<Estabelecimento> estabelecimentos);
	DataTablesOutput<Usuario> findAll(@Valid DataTablesInput input);
	Optional<Usuario> findById(Long id);
	List<Usuario> findAll();
}