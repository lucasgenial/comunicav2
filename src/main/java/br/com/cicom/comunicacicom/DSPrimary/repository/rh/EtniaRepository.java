package br.com.cicom.comunicacicom.DSPrimary.repository.rh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Etnia;

@Repository
public interface EtniaRepository extends JpaRepository<Etnia, Long>{

}
