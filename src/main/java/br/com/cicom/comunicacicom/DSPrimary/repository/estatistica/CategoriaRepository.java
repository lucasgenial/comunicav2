package br.com.cicom.comunicacicom.DSPrimary.repository.estatistica;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;

@Repository
public interface CategoriaRepository extends DataTablesRepository<Categoria, Long>, JpaRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {

	List<Categoria> findByAtivo(boolean value);
	
	List<Categoria> findByNome(String nome);//DELIVELTON
}