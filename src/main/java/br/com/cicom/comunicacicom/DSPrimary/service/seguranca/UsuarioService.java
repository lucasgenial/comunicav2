package br.com.cicom.comunicacicom.DSPrimary.service.seguranca;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.Formatador;
import br.com.cicom.comunicacicom.DSPrimary.model.GerenciadorDeEnvioPorEmail;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.seguranca.UsuarioRepository;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repositorio;

	public Usuario cadastrar(Usuario user) {
		return repositorio.save(user);
	}

	public Usuario buscaPeloLogin(String login) {
		Usuario user = repositorio.findByLogin(login).get();
		return user;
	}

	public Usuario alterar(Long id, Usuario usuario) {
		Usuario usuarioBanco = repositorio.findById(id).get();

		if (usuarioBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}

		// Copia de um objeto para outro!
		BeanUtils.copyProperties(usuario, usuarioBanco, "id");
		return repositorio.saveAndFlush(usuarioBanco);
	}

	public Usuario alterarSenha(Long id, Usuario usuario) {
		Usuario usuarioBanco = repositorio.getOne(id);

		if (usuarioBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}

		// Copia de um objeto para outro!
		usuario.setSenha(usuarioBanco.getSenha());
		usuario.setSenha(BcryptGenerator(usuario.getSenha()));
		BeanUtils.copyProperties(usuario, usuarioBanco, "id");
		return repositorio.saveAndFlush(usuarioBanco);
	}

	private String BcryptGenerator(String senha) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(senha);
		return hashedPassword;
	}

	public String gerarSenhaAleatoria(Long id, Usuario usuario, EmailService serviceEmail) {
		String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		String senha = "";

		Usuario usuarioBanco = repositorio.findById(id).get();
		if (usuarioBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}

		// Gera nova senha aleória;
		for (int x = 0; x < 8; x++) {
			int j = (int) (Math.random() * carct.length);
			senha += carct[j];
		}

		GerenciadorDeEnvioPorEmail enviarEmail = new GerenciadorDeEnvioPorEmail();

		if (enviarEmail.enviarSenha("", "Senha de acesso " + usuario.getServidor().getEstabelecimento().getNome(),
				(new Formatador().formataEmailParaResetDeSenha(senha)),
				usuario.getServidor().getEmail().getEnderecoDeEmail(), serviceEmail.pegarRemetente(usuario),
				serviceEmail.pegarRemetenteSenha(usuario)).equals("Autenticacao")) {
			return "Autenticacao";
		} else {
			usuarioBanco.setSenha(senha);
			usuario.setSenha(usuarioBanco.getSenha());
			BeanUtils.copyProperties(usuario, usuarioBanco, "id");
			repositorio.saveAndFlush(usuarioBanco);
			return "Enviado";
		}
	}

	public Usuario alterarGrupo(Long id, Usuario usuario) {
		Usuario usuarioBanco = repositorio.findById(id).get();

		if (usuarioBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}

		// Copia de um objeto para outro!
		usuario.setSenha(usuarioBanco.getSenha());
		BeanUtils.copyProperties(usuario, usuarioBanco, "id");
		return repositorio.saveAndFlush(usuarioBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Usuario> buscaPorId(Long id) {
		return repositorio.findById(id);
	}

	public List<Usuario> listarTodos() {
		return repositorio.findAll();
	}

	public Usuario desativar(Long id, boolean situacao) {
		Usuario gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setStatus(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Usuario> buscarPorEstabelecimento(Estabelecimento estabelecimento) {
		return repositorio.findByEstabelecimento(estabelecimento);
	}

	public List<Usuario> buscarPorEstabelecimentos(List<Estabelecimento> estabelecimentos) {

		return repositorio.findByEstabelecimentoIn(estabelecimentos);
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */

	public DataTablesOutput<Usuario> listarTodosUsuarios(@Valid DataTablesInput input, Usuario user) {

		if (!user.getGrupo().getNome().equals("ADMINISTRADOR")) {
			input.getColumns().get(4).getSearch()
					.setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));
		}
		DataTablesOutput<Usuario> dados = repositorio.findAll(input);

		return dados;
	}

}