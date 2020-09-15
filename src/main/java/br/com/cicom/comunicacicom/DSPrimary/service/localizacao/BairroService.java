package br.com.cicom.comunicacicom.DSPrimary.service.localizacao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.BairroSort;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.repository.localizacao.BairroRepository;

@Service
public class BairroService {

	@Autowired
	private BairroRepository repositorio;

	public Bairro cadastrar(Bairro bairro) {
		return repositorio.saveAndFlush(bairro);
	}

	public Bairro alterar(Long id, Bairro bairro) {
		Bairro bairroBanco = repositorio.findById(id).get();
		
		if (bairroBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(bairro, bairroBanco, "id");
		return repositorio.saveAndFlush(bairroBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Bairro> buscaPorId(Long id) {
		return (Optional<Bairro>) repositorio.findById(id);
	}

	public List<Bairro> listarTodos() {
		return repositorio.findAll();
	}

	/*
	 * public List<BairroDto> listarTodosDto() { return
	 * repositorio.getBairroByAtivo(true); }
	 */
	public List<Bairro> buscaPorLocalidade(Localidade localidade) {
		return repositorio.findByLocalidade(localidade);
	}

	public List<Bairro> buscaPorLocalidadeSort(Localidade localidade) {
		List<Bairro> bairros = new ArrayList<Bairro>();
		bairros = repositorio.findByLocalidade(localidade);
		bairros.sort(new BairroSort());
		return bairros;
	}

	
	public List<Bairro> listaPorLocalidade(Localidade localidade) {
		return repositorio.findByLocalidade(localidade);
	}

	public Bairro buscaPorLocalidaeNome(Localidade localidade, String nome) {
		return repositorio.findByLocalidadeAndNome(localidade, nome);
	}

	public List<Bairro> buscarPorNomedeLocalidade(String localidadenome){
		return repositorio.findByLocalidadeNome(localidadenome);
	}
	
	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Bairro desativar(Long id, boolean situacao) {
		Bairro gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Bairro> buscaPorLocalidade(List<Localidade> localidades) {
		List<Bairro> bairros = new ArrayList<>();
		for (Localidade localidade : localidades) {
			bairros.addAll(localidade.getBairros());
		}
		return bairros;
	}

	public List<Bairro> buscaPorCidade(Cidade cidade) {
		List<Localidade> localidades = new ArrayList<>();
		localidades.addAll(cidade.getLocalidades());
		List<Bairro> bairros = new ArrayList<>();
		for (Localidade localidade : localidades) {
			bairros.addAll(localidade.getBairros());
		}
		return bairros;

	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	@Transactional(noRollbackFor = Exception.class)
	public DataTablesOutput<Map<String, Object>> listarTodosBairros(@Valid DataTablesInput input) {
	
		return repositorio.findAll(input, new Function<Bairro, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(Bairro bairro) {
				Map<String, Object> mapa = new HashMap<>();
			 		mapa.put("nome", bairro.getNome());
					mapa.put("cidade",bairro.getLocalidade().getCidade().getNome());
					mapa.put("id", bairro.getId().toString());
					mapa.put("localidade",bairro.getLocalidade().getNome());
					mapa.put("estabelecimento",bairro.getLocalidade().getCidade().getEstabelecimento().getNome());
					mapa.put("ativo",bairro.isAtivo());
				 return mapa;
			}
		});
	}
}
