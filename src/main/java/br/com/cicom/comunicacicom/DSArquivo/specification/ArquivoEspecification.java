package br.com.cicom.comunicacicom.DSArquivo.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSArquivo.model.ArquivoDT;

public class ArquivoEspecification {
	
	public static Specification<Arquivo> porIdDoServidor(Long servidor) {
	
		return (root, query, cb) ->
		{			
		 return  cb.equal(root.get("servidor").as(Long.class), servidor);
		};

	}
	
	public static Specification<Arquivo> quandoAtivo() {
		return (root, query, cb) ->
		{			
			return cb.equal(root.get("ativo"), true);
		};
	}	

	public static Specification<ArquivoDT> arquivoDTporServidor(Long id){
		return (root, query, cb) ->
		{
			return cb.equal(root.get("servidor").as(Long.class), id);
		};
	}

}	
