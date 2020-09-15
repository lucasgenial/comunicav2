package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom14Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom14LogRepository;

@Service
public class Cicom14LogService extends CecocoLog {

	@Autowired
	private Cicom14LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom14Log> listacicom14Log = new ArrayList<Cicom14Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom14Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom14Log cicom14Log : listacicom14Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom14Log.getId());
			cecocoLog.setNumeroocorrencia(cicom14Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom14Log.getEstabelecimento());
			cecocoLog.setData(cicom14Log.getData());
			cecocoLog.setAnotacao(cicom14Log.getAnotacao());
			cecocoLog.setDescricao(cicom14Log.getDescricao());
			cecocoLog.setOperador(cicom14Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
