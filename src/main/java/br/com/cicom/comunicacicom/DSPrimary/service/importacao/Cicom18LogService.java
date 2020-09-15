package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom18Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom18LogRepository;

@Service
public class Cicom18LogService extends CecocoLog {

	@Autowired
	private Cicom18LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom18Log> listacicomLog = new ArrayList<Cicom18Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicomLog =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom18Log cicomLog : listacicomLog) {
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
