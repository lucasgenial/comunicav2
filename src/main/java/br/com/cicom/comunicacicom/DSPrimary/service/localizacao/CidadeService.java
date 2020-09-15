package br.com.cicom.comunicacicom.DSPrimary.service.localizacao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.repository.localizacao.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repositorio;

	public Cidade cadastrar(Cidade cidade) {
		return repositorio.saveAndFlush(cidade);
	}

	public Cidade alterar(Long id, Cidade cidade) {
		Cidade cidadeBanco = repositorio.findById(id).get();

		if (cidadeBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(cidade, cidadeBanco, "id");

		return repositorio.saveAndFlush(cidadeBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);;
	}
	
	public Page<Cidade> buscaPaginada(String pesquisa, Pageable pageable) {
		
		return repositorio.findByNomeLikeOrUfNomeLike("%"+pesquisa+"%", "%"+pesquisa+"%",pageable);    
    }

	public Page<Cidade> buscaPaginada(PageRequest paginacao){
		return repositorio.findAll(paginacao);
	}

	public Optional<Cidade> buscaPorId(Long id) {
		return (Optional<Cidade>) repositorio.findById(id);
	}
	
	public List<Cidade> buscaPorListaDeId(List<Long> ids) {
		
		return  repositorio.findAllById(ids);
	}

	public List<Cidade> listarTodos() {
		return repositorio.findAll();
	}
	
	public List<Cidade> listarTodos(PageRequest pageRequest) {
		return repositorio.findAll(pageRequest).getContent();
	}
	
	public List<Cidade> buscarPorListaDeIdsDeEstabelecimento(List<Long> ids) {
		return repositorio.findAllByEstabelecimentoIdIn(ids);
	}
	
	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}
	
	public Cidade buscarPorNome(String nome) {
		return repositorio.findByNome(nome);
	}
	
	public DataTablesOutput<Cidade> listarTodasCidades(@Valid DataTablesInput input) {
		input.getColumns().get(2).getSearch().setValue("Bahia");
		return repositorio.findAll(input);		
	}

}
