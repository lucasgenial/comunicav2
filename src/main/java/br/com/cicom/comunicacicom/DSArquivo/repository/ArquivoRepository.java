package br.com.cicom.comunicacicom.DSArquivo.repository;


import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;


@Repository
public interface ArquivoRepository extends DataTablesRepository<Arquivo, Long>, JpaRepository<Arquivo, Long>{
	
	List<Arquivo> findByServidor(Long id);
	DataTablesOutput<Arquivo> findAll(@Valid DataTablesInput input);
	
}
