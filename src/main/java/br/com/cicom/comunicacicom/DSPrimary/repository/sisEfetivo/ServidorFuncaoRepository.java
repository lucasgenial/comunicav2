package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncao;

@Repository
public interface ServidorFuncaoRepository extends JpaRepository<ServidorFuncao, Long> {
	List<ServidorFuncao> findAllByAtivo(Boolean ativo); 

}