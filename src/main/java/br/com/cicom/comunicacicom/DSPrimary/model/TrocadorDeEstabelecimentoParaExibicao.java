package br.com.cicom.comunicacicom.DSPrimary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

public final class TrocadorDeEstabelecimentoParaExibicao {
	
	/*	
	 * 	Atributo utilizado para tornar a classe singular
	 */
	private static TrocadorDeEstabelecimentoParaExibicao exibicao;
	

	private Map<Long, List<InformacaoAApresentar>> listaDeEstabelecimentosPorUsuario = new HashMap<Long, List<InformacaoAApresentar>>();
	private Map<Long, Integer> listaDePonteiros = new HashMap<Long, Integer>();

	
	/*	
	 * 	Método utilizado proibir a instancia dessa classe
	 */
	private TrocadorDeEstabelecimentoParaExibicao() {
		
	}
	/*	
	 * 	Método utilizado para tornar a classe singular
	 */
	public static TrocadorDeEstabelecimentoParaExibicao criar() {
		if(exibicao == null) {
			exibicao = new TrocadorDeEstabelecimentoParaExibicao();
		}
		return exibicao;
	}
	
	public void setListaDeIds(Usuario usuario){
		 
		
		if(!listaDeEstabelecimentosPorUsuario.containsKey(usuario.getId()) ) {
			
			listaDeEstabelecimentosPorUsuario.put(usuario.getId(), new ArrayList<>());
		  }

		if( listaDeEstabelecimentosPorUsuario.get(usuario.getId()).size() != usuario.getEstabelecimento().size()) {
			
		
		  for(Estabelecimento estabelecimento : usuario.getEstabelecimento()){
	
			  listaDeEstabelecimentosPorUsuario.get(usuario.getId()).add(new InformacaoAApresentar(estabelecimento.getNome(), estabelecimento.getId())); 
		  
		  } 
		  listaDePonteiros.put(usuario.getId(), 0);  
		}		  
		  	  
	}
	
	
	public String getNomeEstabelecimento(Usuario usuario) {		
		return listaDeEstabelecimentosPorUsuario.get(usuario.getId()).get(listaDePonteiros.get(usuario.getId())).getNome();
	
	}
	
	
	public Long nextId(Usuario usuario) {		
		 
		  int ponteiroRegular = listaDePonteiros.get(usuario.getId());
		  
		  if(ponteiroRegular+1 == listaDeEstabelecimentosPorUsuario.get(usuario.getId()).size()) {
			  
			  listaDePonteiros.put(usuario.getId(),0);
		  
		  }else{
			  
			  listaDePonteiros.put(usuario.getId(),ponteiroRegular+1);
		  } 
		  	
		  	return listaDeEstabelecimentosPorUsuario.get(usuario.getId()).get(ponteiroRegular).getId();

	}
	
	/*	Classe de auxilio para guardar informações de nome e id de estabelecimento a
	 * 	fim de não encher a memória com os atributos reias de Estabelecimento
	 */
	private class InformacaoAApresentar{
		private String Nome;
		private Long Id;
		
		public String getNome() {
			return Nome;
		}
		public Long getId() {
			return Id;
		}
		public InformacaoAApresentar(String nome, Long id) {
			super();
			this.Nome = nome;
			this.Id = id;
		}

	}
		
}
