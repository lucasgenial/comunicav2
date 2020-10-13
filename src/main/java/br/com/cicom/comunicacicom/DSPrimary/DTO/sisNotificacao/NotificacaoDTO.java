package br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;

public class NotificacaoDTO {

	private Long id;
	
	@NotNull(message = "O Assunto deve ser preenchido!")
	private String assunto;
	
	@NotNull(message = "Não é possível enviar notificações sem mensagens!")
	private String mensagem;
	
	private LocalDateTime dataCriacao;
	
	private UsuarioDTO criador;
	
	@NotNull(message = "Não é possível criar notificações sem destinatários")
	private List<UsuarioDTO> listaUsuario;
	
	private List<GrupoDTO> listaGrupo;
	
	public NotificacaoDTO() {
		
	}
	
	public NotificacaoDTO(Long id, String assunto, String mensagem, LocalDateTime dataCriacao, UsuarioDTO criador,
			List<UsuarioDTO> listaUsuario, List<GrupoDTO> listaGrupo) {
		super();
		this.id = id;
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.dataCriacao = dataCriacao;
		this.criador = criador;
		this.listaUsuario = listaUsuario;
		this.listaGrupo = listaGrupo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<UsuarioDTO> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<UsuarioDTO> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public List<GrupoDTO> getListaGrupo() {
		return listaGrupo;
	}

	public void setListaGrupo(List<GrupoDTO> listaGrupo) {
		this.listaGrupo = listaGrupo;
	}
	
	public UsuarioDTO getCriador() {
		return criador;
	}

	public void setCriador(UsuarioDTO criador) {
		this.criador = criador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assunto == null) ? 0 : assunto.hashCode());
		result = prime * result + ((criador == null) ? 0 : criador.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listaGrupo == null) ? 0 : listaGrupo.hashCode());
		result = prime * result + ((listaUsuario == null) ? 0 : listaUsuario.hashCode());
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificacaoDTO other = (NotificacaoDTO) obj;
		if (assunto == null) {
			if (other.assunto != null)
				return false;
		} else if (!assunto.equals(other.assunto))
			return false;
		if (criador == null) {
			if (other.criador != null)
				return false;
		} else if (!criador.equals(other.criador))
			return false;
		if (dataCriacao == null) {
			if (other.dataCriacao != null)
				return false;
		} else if (!dataCriacao.equals(other.dataCriacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listaGrupo == null) {
			if (other.listaGrupo != null)
				return false;
		} else if (!listaGrupo.equals(other.listaGrupo))
			return false;
		if (listaUsuario == null) {
			if (other.listaUsuario != null)
				return false;
		} else if (!listaUsuario.equals(other.listaUsuario))
			return false;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotificacaoDTO [id=" + id + ", assunto=" + assunto + ", mensagem=" + mensagem + ", dataCriacao="
				+ dataCriacao + ", criador=" + criador + ", listaUsuario=" + listaUsuario + ", listaGrupo=" + listaGrupo
				+ "]";
	}
	
}
