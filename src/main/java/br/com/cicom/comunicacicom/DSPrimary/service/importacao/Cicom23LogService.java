package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom23Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom23LogRepository;

@Service
public class Cicom23LogService extends CecocoLog {

	@Autowired
	private Cicom23LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom23Log> listacicom6Log = new ArrayList<Cicom23Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom6Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom23Log cicom6Log : listacicom6Log) {
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
