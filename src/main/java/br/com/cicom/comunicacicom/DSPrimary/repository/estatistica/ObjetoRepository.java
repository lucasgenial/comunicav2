package br.com.cicom.comunicacicom.DSPrimary.repository.estatistica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Objeto;


@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Long>{
		
}