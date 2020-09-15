package br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.EstadoOcorrencia;

@Repository
public interface EstadoOcorrenciaRepository extends JpaRepository<EstadoOcorrencia, Long> {

}