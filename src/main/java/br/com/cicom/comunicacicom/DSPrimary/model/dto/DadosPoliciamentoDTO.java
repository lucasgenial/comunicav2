package br.com.cicom.comunicacicom.DSPrimary.model.dto;

import java.util.ArrayList;
import java.util.List;

public class DadosPoliciamentoDTO {
	private String nomeCidade;
	private Long idCidade;
	private List<InstituicaoDTO> instituicoes = new ArrayList<>();

	public DadosPoliciamentoDTO() {
	}

	public DadosPoliciamentoDTO(String cidade, Long idCidade, List<InstituicaoDTO> instituicoes) {
		this.nomeCidade = cidade;
		this.idCidade = idCidade;
		this.instituicoes = instituicoes;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String cidade) {
		this.nomeCidade = cidade;
	}

	public Long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	public List<InstituicaoDTO> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<InstituicaoDTO> instituicoes) {
		this.instituicoes = instituicoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeCidade == null) ? 0 : nomeCidade.hashCode());
		result = prime * result + ((idCidade == null) ? 0 : idCidade.hashCode());
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
		DadosPoliciamentoDTO other = (DadosPoliciamentoDTO) obj;
		if (nomeCidade == null) {
			if (other.nomeCidade != null)
				return false;
		} else if (!nomeCidade.equals(other.nomeCidade))
			return false;
		if (idCidade == null) {
			if (other.idCidade != null)
				return false;
		} else if (!idCidade.equals(other.idCidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DadosPoliciamentoDTO [nomeCidade=" + nomeCidade + ", idCidade=" + idCidade + ", instituicoes=" + instituicoes
				+ "]";
	}

}