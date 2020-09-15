package br.com.cicom.comunicacicom.DSPrimary.repository.importacao;

import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Importacao;

public interface RepositorioImport{

	List<Importacao> findById (Importacao id);
	
	List<Importacao> findByIdAndEstabelecimento (Long id, Long estabelecimento);
}
