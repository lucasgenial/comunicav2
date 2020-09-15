package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom13Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom13LogRepository;

@Service
public class Cicom13LogService extends CecocoLog {

	@Autowired
	private Cicom13LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom13Log> listacicom13Log = new ArrayList<Cicom13Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom13Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom13Log cicom13Log : listacicom13Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom13Log.getId());
			cecocoLog.setNumeroocorrencia(cicom13Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom13Log.getEstabelecimento());
			cecocoLog.setData(cicom13Log.getData());
			cecocoLog.setAnotacao(cicom13Log.getAnotacao());
			cecocoLog.setDescricao(cicom13Log.getDescricao());
			cecocoLog.setOperador(cicom13Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
