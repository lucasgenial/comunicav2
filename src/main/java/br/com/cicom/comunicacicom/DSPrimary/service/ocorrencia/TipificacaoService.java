package br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia.TipificacaoRepository;

@Service
public class TipificacaoService {

	@Autowired
	private TipificacaoRepository repositorio;

	public Tipificacao cadastrar(Tipificacao tipificacao) {
		return repositorio.saveAndFlush(tipificacao);
	}

	public Tipificacao alterar(Long id, Tipificacao tipificacao) {
		Tipificacao tipificacaoBanco = repositorio.findById(id).get();

		if (tipificacaoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(tipificacao, tipificacaoBanco, "id");

		return repositorio.saveAndFlush(tipificacaoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Tipificacao buscaPorNome(String nome) {
		return repositorio.findByNome(nome);
	}

	public List<Tipificacao> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Tipificacao desativar(Long id, boolean situacao) {
		Tipificacao gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public Optional<Tipificacao> buscaPorId(Long id) {
		return (Optional<Tipificacao>) repositorio.findById(id);
	}
	
	public List<Tipificacao> buscaPorIdsDeCategoria(List<Long> idsDeCategoria) {
		return repositorio.findAllByCategoriaIdIn(idsDeCategoria);
	}

	public List<Tipificacao> buscaPorListaDeCategorias(List<Categoria> categorias) {
		return repositorio.findAllByCategoriaIn(categorias);
	}
	
	
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Tipificacao> listarTodasTipificacoes(@Valid DataTablesInput input) {
		return repositorio.findAll(input);		
	}
	
}
