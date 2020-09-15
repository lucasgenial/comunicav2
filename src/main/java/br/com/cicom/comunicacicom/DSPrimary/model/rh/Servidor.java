package br.com.cicom.comunicacicom.DSPrimary.model.rh;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.EnderecoServidor;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;

/**
 * @author Lucas Matos
 */
@Entity
@Table(name = "SERVIDOR")
@SuppressWarnings("serial")

public class Servidor implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Size(min = 3, max = 120)
	@Column(name = "NOME", unique = false)
	private String nome="";

	@NotNull
	@OneToOne(targetEntity = Estabelecimento.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ESTABELECIMENTO")
	private Estabelecimento estabelecimento;

	@NotNull
	@OneToOne(targetEntity = Instituicao.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "INSTITUICAO")
	private Instituicao instituicao;

	@NotNull
	@OneToOne(targetEntity = Hierarquia.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "GRAU_HIERARQUICO")
	private Hierarquia hierarquia;

	@NotNull
	@OneToOne(targetEntity = Funcao.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "FUNCAO")
	private Funcao funcao;

	@Column(name = "MATRICULA")
	private String matricula;

	@Enumerated(EnumType.STRING)
	@Column(name = "SEXO")
	private Sexo sexo;

	@NotNull
	@Column(name = "CPF", unique = true)
	private String cpf;

	@Nullable
	@Size(min = 3, max = 13)
	@Column(name = "RG")
	private String rg;

	@Nullable
	@Column(name = "ORGAO_EMISSOR")
	private String orgaoEmissor;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATA_NASCIMENTO", nullable = false)
	private LocalDate dataNascimento;

	@Nullable
	@Column(name = "NATURALIDADE")
	private String naturalidade;

	@Nullable
	@OneToOne(targetEntity = Nacionalidade.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "NACIONALIDADE")
	private Nacionalidade nacionalidade;

	@Nullable
	@OneToOne(targetEntity = TipoSanguineo.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "TIPO_SANGUINEO")
	private TipoSanguineo tipoSanguineo;

	@Nullable
	@OneToOne(targetEntity = Etnia.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ETNIA")
	private Etnia etnia;

	@Nullable
	@Column(name = "CELULAR1")
	private String celular1;

	@Column(name = "CELULAR2")
	private String celular2;

	@Nullable
	@OneToOne(targetEntity = Email.class, cascade = { CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinColumn(name = "EMAIL")
	private Email email;

	@Nullable
	@OneToOne(targetEntity = SituacaoServidor.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "SITUACAO")
	private SituacaoServidor situacao;

	@Nullable
	@OneToOne(targetEntity = EstadoCivil.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ESTADO_CIVIL")
	private EstadoCivil estadoCivil;

	@Nullable
	@Column(name = "QTD_FILHOS")
	private int qtdFilhos;

	@Nullable
	@Column(name = "NOME_PAI")
	@Size(min = 1, max = 120)
	private String nomePai;

	@Nullable
	@Column(name = "NOME_MAE")
	@Size(min = 1, max = 120)
	private String nomeMae;

	@Nullable
	@OneToOne(targetEntity = Escolaridade.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ESCOLARIDADE")
	private Escolaridade escolaridade;

	@Nullable
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATA_ADMISSAO", nullable = false)
	private LocalDate dataAdmissao;

	@Column(name = "CARTAO_SUS")
	private String cartaoSUS;

	@JsonIgnore
	@OneToOne(targetEntity = UnidadeFederativa.class, cascade = CascadeType.DETACH)
	@JoinColumn(name = "UF_Naturalidade")
	private UnidadeFederativa ufNaturalidade;

	@Nullable
	@OneToOne(targetEntity = EnderecoServidor.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH })
	@JoinColumn(name = "ENDERECO")
	private EnderecoServidor endereco;

	@Nullable
	@Column(name = "CEP")
	private String cep;

	@JsonIgnoreProperties("servidor")
	@OneToOne(targetEntity = Usuario.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH })
	@JoinColumn(name = "USUARIO")
	private Usuario usuario;

//	@Nullable
//	@JsonIgnore
//	@ManyToMany(targetEntity = Arquivo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "ARQUIVO_SERVIDOR", joinColumns = { @JoinColumn(name = "ID_SERVIDOR") }, inverseJoinColumns = {
//			@JoinColumn(name = "ID_ARQUIVO") })
//	private List<Arquivo> arquivo = new ArrayList<>();

	@NotNull
	@Column(name = "STATUS")
	private boolean ativo;

	public Servidor() {
		this.ativo = true;
	}

	public Servidor(Long id, @NotNull @Size(min = 3, max = 120) String nome, @NotNull Estabelecimento estabelecimento,
			@NotNull Instituicao instituicao, @NotNull Hierarquia hierarquia, @NotNull Funcao funcao, String matricula,
			Sexo sexo, @NotNull String cpf, @Size(min = 3, max = 13) String rg, String orgaoEmissor,
			@NotNull LocalDate dataNascimento, String naturalidade, Nacionalidade nacionalidade,
			@NotNull TipoSanguineo tipoSanguineo, Etnia etnia, String celular1, String celular2, Email email,
			SituacaoServidor situacao, EstadoCivil estadoCivil, int qtdFilhos, @Size(min = 1, max = 120) String nomePai,
			@Size(min = 1, max = 120) String nomeMae, Escolaridade escolaridade, @NotNull LocalDate dataAdmissao,
			String cartaoSUS, UnidadeFederativa ufNaturalidade, EnderecoServidor endereco, String cep, Usuario usuario, @NotNull boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.estabelecimento = estabelecimento;
		this.instituicao = instituicao;
		this.hierarquia = hierarquia;
		this.funcao = funcao;
		this.matricula = matricula;
		this.sexo = sexo;
		this.cpf = cpf;
		this.rg = rg;
		this.orgaoEmissor = orgaoEmissor;
		this.dataNascimento = dataNascimento;
		this.naturalidade = naturalidade;
		this.nacionalidade = nacionalidade;
		this.tipoSanguineo = tipoSanguineo;
		this.etnia = etnia;
		this.celular1 = celular1;
		this.celular2 = celular2;
		this.email = email;
		this.situacao = situacao;
		this.estadoCivil = estadoCivil;
		this.qtdFilhos = qtdFilhos;
		this.nomePai = nomePai;
		this.nomeMae = nomeMae;
		this.escolaridade = escolaridade;
		this.dataAdmissao = dataAdmissao;
		this.cartaoSUS = cartaoSUS;
		this.ufNaturalidade = ufNaturalidade;
		this.endereco = endereco;
		this.cep = cep;
		this.usuario = usuario;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome.replace("$", "");
	}
	
	public String getNomeComCifrao() {
		return nome;
	}
	public String getNomeDeGuerra() {
		
		String nomeDeGuerra = this.getSobreNome();
		String nomeS[] = this.nome.split("\\ ");
	
		for(String nome :  nomeS) {
			if(nome.contains("$")) {
				nomeDeGuerra = nome.replace("$", ""); 
			}
		}
		
		return nomeDeGuerra;
	}

	public String getSobreNome() {

		String sobreNome = "";
		String nome1 = "";
		String nomeS[] = this.nome.split("\\ ");

		nome1 = nomeS[0];
		sobreNome = nomeS[nomeS.length - 1];

		return " " + nome1 + " " + sobreNome;
	}
	
	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public void setNomeComCifrao(String nome) {
		this.nome = nome.replace("$ ", "$").toUpperCase();
	}
	
	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Hierarquia getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(Hierarquia hierarquia) {
		this.hierarquia = hierarquia;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor.toUpperCase();
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public TipoSanguineo getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public Etnia getEtnia() {
		return etnia;
	}

	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	public String getCelular1() {
		return celular1;
	}

	public void setCelular1(String celular1) {
		this.celular1 = celular1;
	}

	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public SituacaoServidor getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoServidor situacao) {
		this.situacao = situacao;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public int getQtdFilhos() {
		return qtdFilhos;
	}

	public void setQtdFilhos(int qtdFilhos) {
		this.qtdFilhos = qtdFilhos;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getCartaoSUS() {
		return cartaoSUS;
	}

	public void setCartaoSUS(String cartaoSUS) {
		this.cartaoSUS = cartaoSUS;
	}

	public UnidadeFederativa getUfNaturalidade() {
		return ufNaturalidade;
	}

	public void setUfNaturalidade(UnidadeFederativa ufNaturalidade) {
		this.ufNaturalidade = ufNaturalidade;
	}

	public EnderecoServidor getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoServidor endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Usuario getUsuario() {

		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((cartaoSUS == null) ? 0 : cartaoSUS.hashCode());
		result = prime * result + ((celular1 == null) ? 0 : celular1.hashCode());
		result = prime * result + ((celular2 == null) ? 0 : celular2.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataAdmissao == null) ? 0 : dataAdmissao.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((escolaridade == null) ? 0 : escolaridade.hashCode());
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((estadoCivil == null) ? 0 : estadoCivil.hashCode());
		result = prime * result + ((etnia == null) ? 0 : etnia.hashCode());
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + ((hierarquia == null) ? 0 : hierarquia.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		result = prime * result + ((nacionalidade == null) ? 0 : nacionalidade.hashCode());
		result = prime * result + ((naturalidade == null) ? 0 : naturalidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeMae == null) ? 0 : nomeMae.hashCode());
		result = prime * result + ((nomePai == null) ? 0 : nomePai.hashCode());
		result = prime * result + ((orgaoEmissor == null) ? 0 : orgaoEmissor.hashCode());
		result = prime * result + qtdFilhos;
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipoSanguineo == null) ? 0 : tipoSanguineo.hashCode());
		result = prime * result + ((ufNaturalidade == null) ? 0 : ufNaturalidade.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Servidor other = (Servidor) obj;
		if (ativo != other.ativo)
			return false;
		if (cartaoSUS == null) {
			if (other.cartaoSUS != null)
				return false;
		} else if (!cartaoSUS.equals(other.cartaoSUS))
			return false;
		if (celular1 == null) {
			if (other.celular1 != null)
				return false;
		} else if (!celular1.equals(other.celular1))
			return false;
		if (celular2 == null) {
			if (other.celular2 != null)
				return false;
		} else if (!celular2.equals(other.celular2))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataAdmissao == null) {
			if (other.dataAdmissao != null)
				return false;
		} else if (!dataAdmissao.equals(other.dataAdmissao))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (escolaridade == null) {
			if (other.escolaridade != null)
				return false;
		} else if (!escolaridade.equals(other.escolaridade))
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
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
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (hierarquia == null) {
			if (other.hierarquia != null)
				return false;
		} else if (!hierarquia.equals(other.hierarquia))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		if (nacionalidade == null) {
			if (other.nacionalidade != null)
				return false;
		} else if (!nacionalidade.equals(other.nacionalidade))
			return false;
		if (naturalidade == null) {
			if (other.naturalidade != null)
				return false;
		} else if (!naturalidade.equals(other.naturalidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeMae == null) {
			if (other.nomeMae != null)
				return false;
		} else if (!nomeMae.equals(other.nomeMae))
			return false;
		if (nomePai == null) {
			if (other.nomePai != null)
				return false;
		} else if (!nomePai.equals(other.nomePai))
			return false;
		if (orgaoEmissor == null) {
			if (other.orgaoEmissor != null)
				return false;
		} else if (!orgaoEmissor.equals(other.orgaoEmissor))
			return false;
		if (qtdFilhos != other.qtdFilhos)
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (sexo != other.sexo)
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (tipoSanguineo == null) {
			if (other.tipoSanguineo != null)
				return false;
		} else if (!tipoSanguineo.equals(other.tipoSanguineo))
			return false;
		if (ufNaturalidade == null) {
			if (other.ufNaturalidade != null)
				return false;
		} else if (!ufNaturalidade.equals(other.ufNaturalidade))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Servidor [" + (id != null ? "id=" + id + ", " : "") + (nome != null ? "nome=" + nome + ", " : "")
				+ (estabelecimento != null ? "estabelecimento=" + estabelecimento + ", " : "")
				+ (instituicao != null ? "instituicao=" + instituicao + ", " : "")
				+ (hierarquia != null ? "hierarquia=" + hierarquia + ", " : "")
				+ (funcao != null ? "funcao=" + funcao + ", " : "")
				+ (matricula != null ? "matricula=" + matricula + ", " : "")
				+ (sexo != null ? "sexo=" + sexo + ", " : "") + (cpf != null ? "cpf=" + cpf + ", " : "")
				+ (rg != null ? "rg=" + rg + ", " : "")
				+ (orgaoEmissor != null ? "orgaoEmissor=" + orgaoEmissor + ", " : "")
				+ (dataNascimento != null ? "dataNascimento=" + dataNascimento + ", " : "")
				+ (naturalidade != null ? "naturalidade=" + naturalidade + ", " : "")
				+ (nacionalidade != null ? "nacionalidade=" + nacionalidade + ", " : "")
				+ (tipoSanguineo != null ? "tipoSanguineo=" + tipoSanguineo + ", " : "")
				+ (etnia != null ? "etnia=" + etnia + ", " : "")
				+ (celular1 != null ? "celular1=" + celular1 + ", " : "")
				+ (celular2 != null ? "celular2=" + celular2 + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (situacao != null ? "situacao=" + situacao + ", " : "")
				+ (estadoCivil != null ? "estadoCivil=" + estadoCivil + ", " : "") + "qtdFilhos=" + qtdFilhos + ", "
				+ (nomePai != null ? "nomePai=" + nomePai + ", " : "")
				+ (nomeMae != null ? "nomeMae=" + nomeMae + ", " : "")
				+ (escolaridade != null ? "escolaridade=" + escolaridade + ", " : "")
				+ (dataAdmissao != null ? "dataAdmissao=" + dataAdmissao + ", " : "")
				+ (cartaoSUS != null ? "cartaoSUS=" + cartaoSUS + ", " : "")
				+ (ufNaturalidade != null ? "ufNaturalidade=" + ufNaturalidade + ", " : "")
				+ (endereco != null ? "endereco=" + endereco + ", " : "") + (cep != null ? "cep=" + cep + ", " : "")
				+ (usuario != null ? "usuario=" + usuario + ", " : "") + "ativo=" + ativo + "]";
	}
	
}