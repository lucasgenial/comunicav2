package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.PoliciamentoRepository;
import br.com.cicom.comunicacicom.DSPrimary.specification.PoliciamentoEspecification;

@Service
public class PoliciamentoService {

	@Autowired
	private PoliciamentoRepository repositorio;

	public Policiamento cadastrar(Policiamento policiamento) {
		return (Policiamento) this.repositorio.saveAndFlush(policiamento);
	}

	public Policiamento alterar(Long id, Policiamento policiamento) {
		Policiamento policiamentoBanco = (Policiamento) this.repositorio.getOne(id);
		if (policiamentoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties((Object) policiamento, (Object) policiamentoBanco, (String[]) new String[] { "id" });
		return (Policiamento) this.repositorio.saveAndFlush(policiamentoBanco);
	}

	public void deletar(Long id) {
		this.repositorio.deleteById(id);
	}

	public Page<Policiamento> buscaPaginada(String pesquisa, Pageable pageable) {
		return null;
	}

	public List<Policiamento> listarTodos() {
		return this.repositorio.findAll();
	}

	public Optional<Policiamento> buscaPorId(Long id) {
		return this.repositorio.findById(id);
	}

	public List<Policiamento> buscaPorListaDeCidade(List<Cidade> cidades) {
		return this.repositorio.findAllByCidadeIn(cidades);
	}

	public List<Policiamento> buscaPorListaDePoliciamentosAtivos(List<Cidade> cidades, LocalDateTime fimPlantao) {
		return this.repositorio.findAllByCidadeInAndTerminoPlantaoAfter(cidades, fimPlantao);
	}

	public Policiamento desativar(Long id, boolean situacao) {
		Policiamento gp = (Policiamento) this.repositorio.findById(id).get();
		if (gp != null) {
			gp.setAtivo(situacao);
		}
		return (Policiamento) this.repositorio.save(gp);
	}

	public List<Policiamento> listarTodosPorStatus(Boolean status) {
		return this.repositorio.findAllByBaixado(status);
	}

	public List<BigInteger> buscaEfetivoExternoAtivoPorEstabelecimento(Estabelecimento estabelecimento) {
		return this.repositorio.listarEfetivoExternoAtivoPorEstabelecimento(estabelecimento);
	}

	public List<Object[]> buscarEfetivoExternoAtivo(Estabelecimento estabelecimento) {
		return this.repositorio.listarUltimosPoliciamento(LocalDateTime.now(), estabelecimento);
	}

	public DataTablesOutput<Policiamento> buscardadosDataTable(DataTablesInput input,
			Specification<Policiamento> especificacaoDeFiltro) {
		return repositorio.findAll(input, especificacaoDeFiltro);

	}

	public DataTablesOutput<Map<String, Object>> buscardadosDataTable(DataTablesInput input, Cidade cidade,
			Long idMesa) {
		return repositorio.findAll(input, Specification.where(PoliciamentoEspecification.porCidade(cidade)
				.and(PoliciamentoEspecification.porIdDaMesa(idMesa)).and(PoliciamentoEspecification.quandoAtivo())),
				null, new Function<Policiamento, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(Policiamento policiamento) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("mesa", policiamento.getMesa().getNome());
						mapa.put("id", policiamento.getId());
						mapa.put("comecoPlantao", policiamento.getComecoPlantao());
						mapa.put("terminoPlantao", policiamento.getTerminoPlantao());
						mapa.put("modalidade", policiamento.getModalidade().getNome());
						mapa.put("prefixo", policiamento.getPrefixo());
						mapa.put("unidade", policiamento.getUnidade().getNome());
						mapa.put("abrangencia", policiamento.getAbrangencia());
						mapa.put("estabelecimento", policiamento.getCidade().getEstabelecimento().getNome());
						mapa.put("ativo", policiamento.isAtivo());

						return mapa;
					}
				});

	}

	public DataTablesOutput<Map<String, Object>> buscardadosDataTable(DataTablesInput input, Mesa mesa) {
		return repositorio.findAll(input,
				Specification.where(PoliciamentoEspecification.porMesa(mesa))
						.and(PoliciamentoEspecification.quandoAtivo()),
				null, new Function<Policiamento, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(Policiamento policiamento) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("mesa", policiamento.getMesa().getNome());
						mapa.put("id", policiamento.getId());
						mapa.put("comecoPlantao", policiamento.getComecoPlantao());
						mapa.put("terminoPlantao", policiamento.getTerminoPlantao());
						mapa.put("modalidade", policiamento.getModalidade().getNome());
						mapa.put("prefixo", policiamento.getPrefixo());
						mapa.put("unidade", policiamento.getUnidade().getNome());
						mapa.put("abrangencia", policiamento.getAbrangencia());
						mapa.put("estabelecimento", policiamento.getCidade().getEstabelecimento().getNome());
						mapa.put("ativo", policiamento.isAtivo());

						return mapa;
					}
				});

	}

	public DataTablesOutput<Map<String, Object>> buscarDadosDataTable(DataTablesInput input) {
		return repositorio.findAll(input,
				Specification.where(PoliciamentoEspecification.porDataDeCriacao(LocalDateTime.now().minusHours(36))),
				null, new Function<Policiamento, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(Policiamento policiamento) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("mesa", policiamento.getMesa().getNome());
						mapa.put("id", policiamento.getId());
						mapa.put("comecoPlantao", policiamento.getComecoPlantao());
						mapa.put("terminoPlantao", policiamento.getTerminoPlantao());
						mapa.put("modalidade", policiamento.getModalidade().getNome());
						mapa.put("prefixo", policiamento.getPrefixo());
						mapa.put("unidade", policiamento.getUnidade().getNome());
						mapa.put("abrangencia", policiamento.getAbrangencia());
						mapa.put("estabelecimento", policiamento.getCidade().getEstabelecimento().getNome());
						mapa.put("ativo", policiamento.isAtivo());

						return mapa;
					}
				});
	}

	public DataTablesOutput<Map<String, Object>> buscarDadosDataTable(DataTablesInput input,
			Estabelecimento estabelecimento) {
		return repositorio.findAll(input,
				Specification.where(PoliciamentoEspecification.porDataDeCriacao(LocalDateTime.now().minusMonths(1))
						.and(PoliciamentoEspecification.porEstabelecimento(estabelecimento))),
				null, new Function<Policiamento, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(Policiamento policiamento) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("mesa", policiamento.getMesa().getNome());
						mapa.put("id", policiamento.getId());
						mapa.put("comecoPlantao", policiamento.getComecoPlantao());
						mapa.put("terminoPlantao", policiamento.getTerminoPlantao());
						mapa.put("modalidade", policiamento.getModalidade().getNome());
						mapa.put("prefixo", policiamento.getPrefixo());
						mapa.put("unidade", policiamento.getUnidade().getNome());
						mapa.put("abrangencia", policiamento.getAbrangencia());
						mapa.put("estabelecimento", policiamento.getCidade().getEstabelecimento().getNome());
						mapa.put("ativo", policiamento.isAtivo());

						return mapa;
					}
				});
	}

	public DataTablesOutput<Map<String, Object>> buscarDadosDataTable(DataTablesInput input, Long idMesa) {
		return repositorio.findAll(input, Specification.where(PoliciamentoEspecification.porIdDaMesa(idMesa)), null,
				new Function<Policiamento, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(Policiamento policiamento) {
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("mesa", policiamento.getMesa().getNome());
						mapa.put("id", policiamento.getId());
						mapa.put("comecoPlantao", policiamento.getComecoPlantao());
						mapa.put("terminoPlantao", policiamento.getTerminoPlantao());
						mapa.put("modalidade", policiamento.getModalidade().getNome());
						mapa.put("prefixo", policiamento.getPrefixo());
						mapa.put("unidade", policiamento.getUnidade().getNome());
						mapa.put("abrangencia", policiamento.getAbrangencia());
						mapa.put("estabelecimento", policiamento.getCidade().getEstabelecimento().getNome());
						mapa.put("ativo", policiamento.isAtivo());

						return mapa;
					}
				});
		// .and(PoliciamentoEspecification.quandoAtivo()));
	}

	public DataTablesOutput<Policiamento> buscarDadosDataTable(DataTablesInput input, Long idMesa,
			Estabelecimento estabelecimento) {

		return repositorio.findAll(input,
				Specification.where(PoliciamentoEspecification.porIdDaMesa(idMesa))
						.and(PoliciamentoEspecification.quandoAtivo())
						.and(PoliciamentoEspecification.porEstabelecimento(estabelecimento)));
	}

	public List<Policiamento> filtrar(Policiamento policiamento, Estabelecimento estabelecimento) {
		return repositorio.findAll(PoliciamentoEspecification.filtro(policiamento, estabelecimento));

	}

//	public List<DadosPoliciamentoDTO> buscarDadosParaGrafico(Estabelecimento estabelecimento) {
//	 
//		//List<Policiamento> listaDePoliciamento = repositorio.findAll(PoliciamentoEspecification.porEstabelecimento(estabelecimento).and(PoliciamentoEspecification.quandoAtivo()));
//		List<Policiamento> listaDePoliciamento = repositorio.findAll(PoliciamentoEspecification.porEstabelecimento(estabelecimento));
//		List<DadosPoliciamentoDTO> lista = new ArrayList<>();
//		
//		//System.out.println(listaDePoliciamento);
//		
//		for(Policiamento policiamento : listaDePoliciamento) {
//			lista.add(new DadosPoliciamentoDTO(policiamento));
//		}
//		
//		return lista;
//}

	public List<Object[]> listarPoliciamentoDashboard(List<Cidade> cidades) {
		return repositorio.listarPoliciamentoDashboard(cidades);
	}

	/*
	 * public List<EfetivoPOJO>
	 * buscaPoliciamentoAtivoPorEstabelecimento(Estabelecimento estabelecimentos) {
	 * // TODO Auto-generated method stub
	 * 
	 * return
	 * repositorio.buscarPoliciamentoAtivoPorEstabelecimento(estabelecimentos); }
	 */

}