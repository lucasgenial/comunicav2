package br.com.cicom.comunicacicom.DSPrimary.service.importacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cicom12Log;
import br.com.cicom.comunicacicom.DSPrimary.repository.importacao.Cicom12LogRepository;

@Service
public class Cicom12LogService extends CecocoLog {

	@Autowired
	private Cicom12LogRepository repositorio;

	public List<CecocoLog> buscaTodosPorNumeroocorrencia(Long numeroocorrencia) {
		this.repositorio.flush();
		List<Cicom12Log> listacicom12Log = new ArrayList<Cicom12Log>();
		List<CecocoLog> listaCecocoLog = new ArrayList<>();
		listacicom12Log =  repositorio.findByNumeroocorrencia(numeroocorrencia);

		for(Cicom12Log cicom12Log : listacicom12Log) {
			CecocoLog cecocoLog = new CecocoLog();
			cecocoLog.setId(cicom12Log.getId());
			cecocoLog.setNumeroocorrencia(cicom12Log.getNumeroocorrencia());
			cecocoLog.setEstabelecimento(cicom12Log.getEstabelecimento());
			cecocoLog.setData(cicom12Log.getData());
			cecocoLog.setAnotacao(cicom12Log.getAnotacao());
			cecocoLog.setDescricao(cicom12Log.getDescricao());
			cecocoLog.setOperador(cicom12Log.getOperador());
			listaCecocoLog.add(cecocoLog);
		}
		return listaCecocoLog;
	}
}
