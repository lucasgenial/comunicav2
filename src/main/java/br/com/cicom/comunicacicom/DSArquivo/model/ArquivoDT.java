package br.com.cicom.comunicacicom.DSArquivo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Immutable
@Entity
@Table(name = "view_arquivo", schema = "comunicacicom3")
public class ArquivoDT implements Serializable {

	/**
	 * Criado por Anderson Kroger Cerqueira
	 * Em 05-08-2020
	 */

	@Id
	@Column(name = "ID", unique = true)
	private Long id;
	
	private String nome;
	
	private String descricao;
	
	private String tipo;
		
	@Column(name = "status")
	private boolean ativo;
	
	private byte[] arquivo;
		
	private Long servidor;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_ENVIO")
    private LocalDate dataEnvio;

	public ArquivoDT(Long id, String nome, String descricao, String tipo, boolean ativo, byte[] arquivo, Long servidor,
			LocalDate dataEnvio) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.ativo = ativo;
		this.arquivo = arquivo;
		this.servidor = servidor;
		this.dataEnvio = dataEnvio;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public Long getServidor() {
		return servidor;
	}

	public LocalDate getDataEnvio() {
		return dataEnvio;
	}

	@Override
	public String toString() {
		return "ArquivoDT [" 
				+ (id != null ? "id=" + id + ", " : "") 
				+ (nome != null ? "nome=" + nome + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (tipo != null ? "tipo=" + tipo + ", " : "") + "ativo=" + ativo + ", "
				+ (arquivo != null ? "arquivo=" + Arrays.toString(arquivo) + ", " : "")
				+ (servidor != null ? "servidor=" + servidor + ", " : "")
				+ (dataEnvio != null ? "dataEnvio=" + dataEnvio : "") + "]";
	}
			
	
}
