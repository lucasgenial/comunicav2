package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom16Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom16LogRepository;
@Service
public class Cicom16LogService extends CecocoLog{

	@Autowired
	private Cicom16LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom16Log> listacicomLog = new ArrayList<Cicom16Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicomLog =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom16Log cicomLog : listacicomLog) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicomLog.getId());
			cecocoLog.setNumeroocorrencia(cicomLog.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicomLog.getEstabelecimento());
			cecocoLog.setData(cicomLog.getData());
			cecocoLog.setAnotacao(cicomLog.getAnotacao());
			cecocoLog.setDescricao(cicomLog.getDescricao());
			cecocoLog.setOperador(cicomLog.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
