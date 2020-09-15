package br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.OcorrenciaLog;
import br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia.OcorrenciaLogRepository;

@Service
public class OcorrenciaLogService {

	@Autowired
	private OcorrenciaLogRepository repositorio;

	public void cadastrar(List<OcorrenciaLog> ocorrencialog) {
		repositorio.saveAll(ocorrencialog);
	}

	public void deletar(Ocorrencia ocorrencia) {
		int i;
		List<OcorrenciaLog> ocorrenciaLog = repositorio.findByOcorrencia(ocorrencia) ;
		i = ocorrenciaLog.size();
		while ( i > 0 ) {
			repositorio.deleteById(ocorrenciaLog.get(i-1).getId());
			i--;
		}
	}

	public Optional<OcorrenciaLog> buscaPorId(Long id) {
		return repositorio.findById(id);
	}

	public List<OcorrenciaLog> buscaPorid(Long id) {
		return repositorio.findByid(id);
	}

	public List<OcorrenciaLog> listarTodos() {
		return repositorio.findAll();
	}
}
