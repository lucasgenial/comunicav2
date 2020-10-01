package br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca;

import java.util.ArrayList;
import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.DTO.rh.ServidorDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisGeral.EstabelecimentoDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;

public class UsuarioDTO implements Comparable<UsuarioDTO> {
	private Long id;
	private ServidorDTO servidor = new ServidorDTO();
	private List<EstabelecimentoDTO> estabelecimento = new ArrayList<>();

	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Long id, Servidor servidor) {
		this.id = id;
		this.setServidor(servidor);
	}

	public UsuarioDTO(Long id, Servidor servidor, List<EstabelecimentoDTO> estabelecimento) {
		this.id = id;
		this.setServidor(servidor);
		this.estabelecimento = estabelecimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServidorDTO getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor.setId(servidor.getId());
		this.servidor.setNome(servidor.getNome());
	}

	public List<EstabelecimentoDTO> getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(List<EstabelecimentoDTO> estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((servidor == null) ? 0 : servidor.hashCode());
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
		UsuarioDTO other = (UsuarioDTO) obj;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (servidor == null) {
			if (other.servidor != null)
				return false;
		} else if (!servidor.equals(other.servidor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", servidor=" + servidor + ", estabelecimento=" + estabelecimento + "]";
	}
	
	@Override
	public int compareTo(UsuarioDTO usuario) {
		return this.servidor.getNome().toUpperCase().compareTo(usuario.servidor.getNome().toUpperCase());
	}
}