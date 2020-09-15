package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.EmailRepository;

@Service
public class EmailService {

	@Autowired
	private EmailRepository repositorio;

	public Email cadastrar(Email email) {
		return repositorio.saveAndFlush(email);
	}

	public Email alterar(Long id, Email email) {
		Email emailBanco = repositorio.findById(id).get();
//		System.out.println(emailBanco);
		if (emailBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(email, emailBanco, "id");

		return repositorio.saveAndFlush(emailBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Email> buscaPorId(Long id) {
		return (Optional<Email>) repositorio.findById(id);
	}

	public List<Email> listarTodos() {
		return repositorio.findAll();
	}

	public Email pegarEmail(Long id) {
		return repositorio.getOne(id);
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Email buscarPeloEnderecoEmail(String enderecoEmail) {
		return repositorio.findByEnderecoDeEmail(enderecoEmail);
	}

	public Email desativar(Long id, boolean situacao) {
		Email gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public String pegarRemetente(Usuario user) {
		return user.getServidor().getEstabelecimento().getEmail().getEnderecoDeEmail().toString();
	}

	public String pegarRemetenteSenha(Usuario user) {
		byte[] decoded = Base64.getDecoder().decode(user.getServidor().getEstabelecimento().getSenhaDoEmail().toString());
		String decodedString = new String(decoded);
		return decodedString;
	}

	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Email> listarTodosEmails(@Valid DataTablesInput input) {
		return repositorio.findAll(input);		
	}

}
