package br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca;

import br.com.cicom.comunicacicom.DSPrimary.DTO.rh.ServidorDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;

public class UsuarioDTO implements Comparable<UsuarioDTO> {
	private Long id;
	private ServidorDTO servidor = new ServidorDTO();
	
	public UsuarioDTO() {	
	}

	public UsuarioDTO(Long id, Servidor servidor) {
		this.id = id;
		this.setServidor(servidor);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return "UsuarioDTO [id=" + id + ", servidor=" + servidor + "]";
	}
	
	@Override
	public int compareTo(UsuarioDTO usuario) {
		return this.servidor.getNome().toUpperCase().compareTo(usuario.servidor.getNome().toUpperCase());
	}
}