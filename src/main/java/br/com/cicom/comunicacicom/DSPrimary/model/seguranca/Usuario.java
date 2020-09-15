package br.com.cicom.comunicacicom.DSPrimary.model.seguranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;

/**
 *
 * @author Estatistica2
 */
@Entity
@Table(name = "USUARIO")
@SuppressWarnings("serial")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;

	@NotNull
	@Column(name = "LOGIN", unique = true)
	private String login;

	@NotNull
	@Column(name = "SENHA")
	private String senha;

	@OneToOne(targetEntity = Grupo.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "GRUPO")
	private Grupo grupo;
	
	@OneToOne(mappedBy="usuario", targetEntity = Servidor.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Servidor servidor;
	
	@Nullable
	@ManyToMany(targetEntity=Estabelecimento.class, cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name = "USUARIOS_ESTABELECIMENTO", joinColumns = {
			@JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns = {
			@JoinColumn(name = "ID_ESTABELECIMENTO") })
	private List<Estabelecimento> estabelecimento = new ArrayList<>();

	@NotNull
	@Column(name = "ATIVO")
	private boolean ativo;
	
	public Usuario() {
		this.ativo=true;
	}

	public Usuario(Long id, @NotNull String login, @NotNull String senha, Grupo grupo, Servidor servidor,
			List<Estabelecimento> estabelecimento, @NotNull boolean ativo) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.grupo = grupo;
		this.servidor = servidor;
		this.estabelecimento = estabelecimento;
		this.ativo = ativo;
	}
	
	public Usuario(Long id, @NotNull String login, @NotNull String senha, Grupo grupo,
			List<Estabelecimento> estabelecimento, @NotNull boolean ativo) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.grupo = grupo;
		this.estabelecimento = estabelecimento;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if(!senha.equals("") && !senha.contains(" ") && !senha.equals(this.senha)) {
			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			 this.senha = bCryptPasswordEncoder.encode(senha);
			
		}
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public List<Estabelecimento> getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(List<Estabelecimento> estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setStatus(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
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
		return "Usuario [" 
				+ (id != null ? "id=" + id + ", " : "") 
				+ (login != null ? "login=" + login + ", " : "")
				+ (grupo != null ? "grupo=" + grupo : "") + "]";
	}


}
