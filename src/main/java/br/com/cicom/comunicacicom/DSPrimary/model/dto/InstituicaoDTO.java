package br.com.cicom.comunicacicom.DSPrimary.model.dto;

import java.util.ArrayList;
import java.util.List;

public class InstituicaoDTO {
	private String nomeInstituicao;
	private Long idInstituicao;
	private List<ModalidadeDTO> modalidades = new ArrayList<>();

	public InstituicaoDTO() {

	}

	public InstituicaoDTO(String nome, Long idInstituicao, List<ModalidadeDTO> modalidades) {
		this.nomeInstituicao = nome;
		this.idInstituicao = idInstituicao;
		this.modalidades = modalidades;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nome) {
		this.nomeInstituicao = nome;
	}

	public Long getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public List<ModalidadeDTO> getModalidades() {
		return modalidades;
	}

	public void setModalidades(List<ModalidadeDTO> modalidades) {
		this.modalidades = modalidades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idInstituicao == null) ? 0 : idInstituicao.hashCode());
		result = prime * result + ((modalidades == null) ? 0 : modalidades.hashCode());
		result = prime * result + ((nomeInstituicao == null) ? 0 : nomeInstituicao.hashCode());
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
		InstituicaoDTO other = (InstituicaoDTO) obj;
		if (idInstituicao == null) {
			if (other.idInstituicao != null)
				return false;
		} else if (!idInstituicao.equals(other.idInstituicao))
			return false;
		if (modalidades == null) {
			if (other.modalidades != null)
				return false;
		} else if (!modalidades.equals(other.modalidades))
			return false;
		if (nomeInstituicao == null) {
			if (other.nomeInstituicao != null)
				return false;
		} else if (!nomeInstituicao.equals(other.nomeInstituicao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InstituicaoDTO [nomeInstituicao=" + nomeInstituicao + ", idInstituicao=" + idInstituicao + ", modalidades=" + modalidades
				+ "]";
	}

}
