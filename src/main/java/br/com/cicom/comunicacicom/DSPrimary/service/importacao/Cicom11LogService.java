package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom11Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom11LogRepository;

@Service
public class Cicom11LogService extends CecocoLog {

	@Autowired
	private Cicom11LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom11Log> listacicom11Log = new ArrayList<Cicom11Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom11Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom11Log cicom11Log : listacicom11Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom11Log.getId());
			cecocoLog.setNumeroocorrencia(cicom11Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom11Log.getEstabelecimento());
			cecocoLog.setData(cicom11Log.getData());
			cecocoLog.setAnotacao(cicom11Log.getAnotacao());
			cecocoLog.setDescricao(cicom11Log.getDescricao());
			cecocoLog.setOperador(cicom11Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
