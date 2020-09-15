package br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.OcorrenciaLog;

public interface OcorrenciaLogRepository extends JpaRepository<OcorrenciaLog, Long>{

	List<OcorrenciaLog> findByOcorrencia(Ocorrencia ocorrencia);
	List<OcorrenciaLog> findByid(Long id);

}
