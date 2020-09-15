package br.com.cicom.comunicacicom.DSArquivo.repository;

import java.util.List;
import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSArquivo.model.ArquivoDT;

@Repository
public interface ArquivoDTRepository extends DataTablesRepository<ArquivoDT, Long>, JpaRepository<ArquivoDT, Long>{
	List<ArquivoDT> findByServidor(Long id);

//	@Query(value = "SELECT ar.id AS id, "
//			+ "ar.arquivo AS arquivo, "
//			+ "ar.status AS ativo, "
//			+ "ar.DATA_ENVIO AS dataEnvio, "
//			+ "ar.descricao AS descricao, "
//			+ "ar.nome AS nome, "
//			+ "ar.tipo AS tipo, "
//			+ "ar.servidor AS servidor "
//			//+ "ar.servidor_nome AS servidor_nome "
//			+ "FROM view_arquivo as ar "
//			+ "WHERE ar.servidor = :id", nativeQuery = true)
//	DataTablesOutput<ArquivoDT> findByServidor(@Valid DataTablesInput input,@Param("id") Long id);
	DataTablesOutput<ArquivoDT> findAll(@Valid DataTablesInput input);

}
