package br.com.cicom.comunicacicom.DSArquivo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ARQUIVO", schema = "comunicacicom3")
@SuppressWarnings("serial")
public class Arquivo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@NotNull
	@Size(min=3, max=120)
	@Column(name = "NOME", unique = false)
	private String nome;
	
	@NotNull
	@Size(min=3, max=120)
	@Column(name = "DESCRICAO" )
	private String descricao;
	
	@NotNull
	@Column(name = "TIPO" )
	private String tipo;
		
	@NotNull
	@Column(name = "STATUS" )
	private boolean ativo;
	
	@NotNull
	@Column(name = "ARQUIVO", nullable=false, columnDefinition="mediumblob")
	private byte[] arquivo;
		
	//@ManyToMany(mappedBy="arquivo")
	@Column(name = "SERVIDOR")
	private Long servidor;
	
	@NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DATA_ENVIO", nullable = false)
    private LocalDate dataEnvio;
			
	public Arquivo() {
		this.ativo = true;
	}	

	public Arquivo(Long id, @NotNull @Size(min = 3, max = 120) String nome,
			@NotNull @Size(min = 3, max = 120) String descricao, @NotNull String tipo, @NotNull boolean ativo,
			@NotNull byte[] arquivo, Long servidor, @NotNull LocalDate dataEnvio) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	public LocalDate getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Long getServidor() {
		return servidor;
	}

	public void setServidor(Long servidor) {
		this.servidor = servidor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arquivo);
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((dataEnvio == null) ? 0 : dataEnvio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Arquivo other = (Arquivo) obj;
		if (!Arrays.equals(arquivo, other.arquivo))
			return false;
		if (ativo != other.ativo)
			return false;
		if (dataEnvio == null) {
			if (other.dataEnvio != null)
				return false;
		} else if (!dataEnvio.equals(other.dataEnvio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arquivo [" 
				+ (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (descricao != null ? "descricao=" + descricao + ", " : "")
				+ (tipo != null ? "tipo=" + tipo + ", " : "") + "ativo=" + ativo + ", "
				+ (arquivo != null ? "arquivo=" + Arrays.toString(arquivo) + ", " : "")
				+ (servidor != null ? "servidor=" + servidor + ", " : "")
				+ (dataEnvio != null ? "dataEnvio=" + dataEnvio : "") + "]";
	}

	
	
	
}
