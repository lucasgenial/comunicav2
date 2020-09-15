package br.com.cicom.comunicacicom.DSPrimary.model.estatistica;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.EstadoCivil;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Etnia;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Sexo;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.TipoPessoa;

/**
*
* @author Lucas Matos
*/
@Entity
@Table(name = "PESSOA")
@SuppressWarnings("serial")
public class Pessoa implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;
	
	@NotNull
	@NotBlank
	@Column(name = "NOME")
	private String nome;
	
	@NotNull
	@OneToOne(targetEntity = TipoEnvolvimento.class, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "ENVOLVIMENTO_ID")
	private TipoEnvolvimento envolvimento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    @Column(name = "SEXO")
	private Sexo sexo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PESSOA")
	private TipoPessoa tipoPessoa;
	
	@Nullable
	@Column(name = "NACIONALIDADE")
	private String nacionalidade;
	
	@Nullable
	@Past(message = "A data deve estar no passado")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATA_NASCIMENTO")
	private LocalDate dataNascimento;
	
	@Column(name = "IDADE")
	private int idade;
	
	@Nullable
	@OneToOne(targetEntity = EstadoCivil.class, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "ESTADO_CIVIL")
	private EstadoCivil estadoCivil;
	
	@Nullable
	@OneToOne(targetEntity = Etnia.class, cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "ETNIA_ID")
	private Etnia etnia;
	
	@NotNull
	@OneToOne(targetEntity = ContextoCrime.class, cascade = {CascadeType.DETACH })
	@JoinColumn(name = "CONTEXTO")
	private ContextoCrime contexto;
	
	@JsonIgnore
	@ManyToOne(targetEntity = RegistroOcorrencia.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "ID_REGISTRO_OCORRENCIA")
	private RegistroOcorrencia regitroOcorrencia;

	public Pessoa() {
	}
	
	public Pessoa(Long id, @NotNull String nome, @NotNull TipoEnvolvimento envolvimento, Sexo sexo,
			TipoPessoa tipoPessoa, @NotNull String nacionalidade, @NotNull LocalDate dataNascimento, @NotNull int idade,
			@NotNull EstadoCivil estadoCivil, @NotNull Etnia etnia, @NotNull ContextoCrime contexto,
			@NotNull RegistroOcorrencia regitroOcorrencia) {
		this.id = id;
		this.nome = nome;
		this.envolvimento = envolvimento;
		this.sexo = sexo;
		this.tipoPessoa = tipoPessoa;
		this.nacionalidade = nacionalidade;
		this.dataNascimento = dataNascimento;
		this.idade = idade;
		this.estadoCivil = estadoCivil;
		this.etnia = etnia;
		this.contexto = contexto;
		this.regitroOcorrencia = regitroOcorrencia;
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

	public TipoEnvolvimento getEnvolvimento() {
		return envolvimento;
	}

	public void setEnvolvimento(TipoEnvolvimento envolvimento) {
		this.envolvimento = envolvimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Etnia getEtnia() {
		return etnia;
	}
	
	public ContextoCrime getContexto() {
		return contexto;
	}

	public void setContexto(ContextoCrime contexto) {
		this.contexto = contexto;
	}

	public void setEtnia(Etnia valor) {
		this.etnia = valor;
	}
	
	public RegistroOcorrencia getRegitroOcorrencia() {
		return regitroOcorrencia;
	}

	public void setRegitroOcorrencia(RegistroOcorrencia regitroOcorrencia) {
		this.regitroOcorrencia = regitroOcorrencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contexto == null) ? 0 : contexto.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((envolvimento == null) ? 0 : envolvimento.hashCode());
		result = prime * result + ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + ((etnia == null) ? 0 : etnia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + idade;
		result = prime * result + ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((regitroOcorrencia == null) ? 0 : regitroOcorrencia.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((tipoPessoa == null) ? 0 : tipoPessoa.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (contexto == null) {
			if (other.contexto != null)
				return false;
		} else if (!contexto.equals(other.contexto))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (envolvimento == null) {
			if (other.envolvimento != null)
				return false;
		} else if (!envolvimento.equals(other.envolvimento))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
			return false;
		if (etnia == null) {
			if (other.etnia != null)
				return false;
		} else if (!etnia.equals(other.etnia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idade != other.idade)
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (regitroOcorrencia == null) {
			if (other.regitroOcorrencia != null)
				return false;
		} else if (!regitroOcorrencia.equals(other.regitroOcorrencia))
			return false;
		if (sexo != other.sexo)
			return false;
		if (tipoPessoa != other.tipoPessoa)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", envolvimento=" + envolvimento.getNome() + ", sexo=" + sexo.name()
				+ ", tipoPessoa=" + tipoPessoa.name() + ", nacionalidade=" + nacionalidade + ", dataNascimento="
				+ dataNascimento + ", idade=" + idade + ", estadoCivil=" + estadoCivil.getNome() + ", etnia=" + etnia.getNome()
				+ ", contexto=" + contexto.getNome() + ", regitroOcorrencia=" + regitroOcorrencia.getId() + "]";
	}
}
