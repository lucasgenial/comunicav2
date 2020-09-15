package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom10Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom10LogRepository;

@Service
public class Cicom10LogService extends CecocoLog {

	@Autowired
	private Cicom10LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom10Log> listacicom6Log = new ArrayList<Cicom10Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom6Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom10Log cicom10Log : listacicom6Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom10Log.getId());
			cecocoLog.setNumeroocorrencia(cicom10Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom10Log.getEstabelecimento());
			cecocoLog.setData(cicom10Log.getData());
			cecocoLog.setAnotacao(cicom10Log.getAnotacao());
			cecocoLog.setDescricao(cicom10Log.getDescricao());
			cecocoLog.setOperador(cicom10Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
