package br.com.cicom.comunicacicom.DSPrimary.repository.estatistica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.RegistroOcorrencia;

@Repository
public interface RegistroOcorrenciaRepository extends JpaRepository<RegistroOcorrencia, Long> {
	
	RegistroOcorrencia findByOcorrenciaSic(String value);

}