package br.com.cicom.comunicacicom.DSPrimary.service.estatistica;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Pessoa;
import br.com.cicom.comunicacicom.DSPrimary.repository.estatistica.PessoaRepository;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository repositorio;

	public Pessoa cadastrar(Pessoa value) {
		return repositorio.saveAndFlush(value);
	}
	
	public Pessoa alterar(Long id, Pessoa pessoa) {
		Pessoa pessoaBanco = repositorio.getOne(id);

		if (pessoaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(pessoa, pessoaBanco, "id");

		return repositorio.saveAndFlush(pessoaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Pessoa> buscaPorId(Long id) {
		return (Optional<Pessoa>) repositorio.findById(id);
	}

	public List<Pessoa> listarTodos() {
		return repositorio.findAll();
	}

}