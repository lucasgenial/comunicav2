package br.com.cicom.comunicacicom.DSPrimary.service.localizacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;
import br.com.cicom.comunicacicom.DSPrimary.repository.localizacao.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repositorio;

	public Endereco cadastrar(Endereco endereco) {
		return repositorio.saveAndFlush(endereco);
	}

	public Endereco alterar(Long id, Endereco endereco) {
		Endereco enderecoBanco = repositorio.findById(id).get();

		if (enderecoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(endereco, enderecoBanco, "id");
		return repositorio.saveAndFlush(enderecoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Endereco> buscaPorId(Long id) {
		return (Optional<Endereco>) repositorio.findById(id);
	}

	public Endereco pegaEndereco(Long id) {
		return repositorio.getOne(id);
	}

	public List<Endereco> listarTodos() {
		return repositorio.findAll();
	}

}
