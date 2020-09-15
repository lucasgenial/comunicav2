package br.com.cicom.comunicacicom.DSPrimary.POJO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Objeto;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Pessoa;

public class MonitoraPlanilhaRelatorio {

	 List<Dados> dados = new ArrayList<>();
	 
	 public void criaListas(String matriculaServidor, List<Pessoa> listaPessoas, List<Objeto> listaObjetos) {
		 this.verificaECriaNovosDados(matriculaServidor, listaPessoas, listaObjetos);
	 }
	 
	 public void alteraDadosDasListas(String matriculaServidor, List<Pessoa> listaPessoas, List<Objeto> listaObjetos) {
			
		 for(int i = 0; i< dados.size(); i++) {
			if(dados.get(i).getMatriculaServidor().equals(matriculaServidor)) {
				dados.get(i).setListaObjetos(listaObjetos);
				dados.get(i).setListaPessoas(listaPessoas);
				dados.get(i).setDataEHora(LocalDateTime.now());
			}
		 }
	 }
	 
	 private void verificaECriaNovosDados(String matriculaServidor, List<Pessoa> listaPessoas, List<Objeto> listaObjetos) {
		 for(int i = 0; i< dados.size(); i++) {
			if(dados.get(i).getMatriculaServidor().equals(matriculaServidor)) {
				dados.remove(i);
			}
		 }
		this.dados.add(new Dados(LocalDateTime.now(),listaPessoas,listaObjetos,matriculaServidor));
 
	 }
	 
	 public Dados getListas(String matriculaServidor) {
	
		 for(int i = 0; i< dados.size(); i++) {
			if(dados.get(i).getMatriculaServidor().equals(matriculaServidor)) {
				dados.get(i).setDataEHora(LocalDateTime.now());
				return dados.get(i);
			}
		 }
		 return null;
	 }
	 
	 public void removeMensagem(String matriculaServidor) {
		 List<Dados> mensagensARemover = new ArrayList<>();
		 Duration duracao;
		
		 for(int i = 0; i< dados.size(); i++) {
			duracao = Duration.between(dados.get(i).getDataEHora(), LocalDateTime.now());
			
			if(dados.get(i).getMatriculaServidor().equals(matriculaServidor) || duracao.toMinutes() > 20) {
				mensagensARemover.add(dados.get(i));
			}
		 }
		 
		 dados.removeAll(mensagensARemover);
	 }
 
	 public class Dados {
		 
		 LocalDateTime dataEHora;
		 List<Pessoa> listaPessoas;
		 List<Objeto> listaObjetos;
		 String matriculaServidor;
		 
		private Dados(LocalDateTime dataEHora,List<Pessoa> listaPessoas, List<Objeto> listaObjetos,String matriculaServidor) {
		
			this.dataEHora = dataEHora;
			this.listaPessoas = listaPessoas;
			this.listaObjetos = listaObjetos;
	
			this.matriculaServidor = matriculaServidor;
		}
		
		public List<Pessoa> getListaPessoas() {
			return listaPessoas;
		}

		public void setListaPessoas(List<Pessoa> listaPessoas) {
			this.listaPessoas = listaPessoas;
		}

		public List<Objeto> getListaObjetos() {
			return listaObjetos;
		}

		public void setListaObjetos(List<Objeto> listaObjetos) {
			this.listaObjetos = listaObjetos;
		}

		public LocalDateTime getDataEHora() {
			return dataEHora;
		}
		public void setDataEHora(LocalDateTime dataEHora) {
			this.dataEHora = dataEHora;
		}
		public String getMatriculaServidor() {
			return matriculaServidor;
		}
		public void setMatriculaServidor(String matriculaServidor) {
			this.matriculaServidor = matriculaServidor;
		}	 

	 } 

}
