package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom4Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom4LogRepository;

@Service
public class Cicom4LogService extends CecocoLog {

	@Autowired
	private Cicom4LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom4Log> listacicom6Log = new ArrayList<Cicom4Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom6Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom4Log cicom6Log : listacicom6Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom6Log.getId());
			cecocoLog.setNumeroocorrencia(cicom6Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom6Log.getEstabelecimento());
			cecocoLog.setData(cicom6Log.getData());
			cecocoLog.setAnotacao(cicom6Log.getAnotacao());
			cecocoLog.setDescricao(cicom6Log.getDescricao());
			cecocoLog.setOperador(cicom6Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
