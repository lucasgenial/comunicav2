package br.com.cicom.comunicacicom.DSPrimary.POJO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonitoraMesa {

	 List<Mensagem> mensagens = new ArrayList<>();
	 
	 public void adicionaMensagem(String matriculaServidor, String mensagem, Long idMesa) {
		
		 mensagens.add(new Mensagem(LocalDateTime.now(),mensagem,idMesa,matriculaServidor));
	 
	 }
		 
	 public void removeMensagem(String matriculaServidor) {
		 List<Mensagem> mensagensARemover = new ArrayList<>();
		 LocalDateTime dozeHorasAntes = LocalDateTime.now().minusHours(12);
		 Duration duracao;
		
		 for(int i = 0; i< mensagens.size(); i++) {
			duracao = Duration.between(mensagens.get(i).getDataEHora(), LocalDateTime.now());
			
			if(mensagens.get(i).getMatriculaServidor().equals(matriculaServidor) || duracao.toHours() > 2) {
				mensagensARemover.add(mensagens.get(i));
			}
		 }
		 
		 mensagens.removeAll(mensagensARemover);
	 }
	 
	 public String getMensagens(Long idMesa) {
		String texto = ""; 
		 for(int i = 0; i< mensagens.size(); i++) {
			if(mensagens.get(i).idMesa.equals(idMesa) ) {
				texto += mensagens.get(i).getMensagem()+"\n";
			}
		 }
		 return texto;
	 }
	 
	 private class Mensagem {
		 
		 LocalDateTime dataEHora;
		 String mensagem;
		 Long idMesa;
		 String matriculaServidor;
		 
		private Mensagem(LocalDateTime dataEHora, String mensagem, Long idMesa, String matriculaServidor) {
		
			this.dataEHora = dataEHora;
			this.mensagem = mensagem;
			this.idMesa = idMesa;
			this.matriculaServidor = matriculaServidor;
		}
		
		public LocalDateTime getDataEHora() {
			return dataEHora;
		}
		public void setDataEHora(LocalDateTime dataEHora) {
			this.dataEHora = dataEHora;
		}
		public String getMensagem() {
			return mensagem;
		}
		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		public Long getIdMesa() {
			return idMesa;
		}
		public void setIdMesa(Long idMesa) {
			this.idMesa = idMesa;
		}
		public String getMatriculaServidor() {
			return matriculaServidor;
		}
		public void setMatriculaServidor(String matriculaServidor) {
			this.matriculaServidor = matriculaServidor;
		}	 

	 } 

}
