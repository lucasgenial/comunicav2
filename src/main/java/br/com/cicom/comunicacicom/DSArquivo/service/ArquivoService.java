package br.com.cicom.comunicacicom.DSArquivo.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSArquivo.repository.ArquivoRepository;
import br.com.cicom.comunicacicom.DSArquivo.specification.ArquivoEspecification;

@Service
public class ArquivoService {

	@Autowired
	private ArquivoRepository repositorio;

	public Arquivo cadastrar(Arquivo arquivo) {
		return repositorio.saveAndFlush(arquivo);
	}

	public Arquivo alterar(Long id, Arquivo arquivo) {
		Arquivo arquivoBanco = repositorio.findById(id).get();
		if (arquivoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(arquivo, arquivoBanco, "id");
		return repositorio.saveAndFlush(arquivoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Arquivo> buscaPorId(Long id) {
		return repositorio.findById(id);
	}

	public List<Arquivo> buscarPorServidor(Long id){
		return repositorio.findByServidor(id);
	}
	public List<Arquivo> listarTodos() {
		return repositorio.findAll();
	}

	public DataTablesOutput<Arquivo> listarTodosArquivos(@Valid DataTablesInput input, Long idServidor) {
		return repositorio.findAll(input, ArquivoEspecification.porIdDoServidor(idServidor));
	}
}
