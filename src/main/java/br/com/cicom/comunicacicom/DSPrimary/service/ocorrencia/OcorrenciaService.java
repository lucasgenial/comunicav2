package br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.POJO.Mes;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.DadosEstatisticosPorMes;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.DadosPorCategoria;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.ocorrencia.OcorrenciaRepository;
import br.com.cicom.comunicacicom.DSPrimary.specification.OcorrenciaSpecification;

@Service
public class OcorrenciaService {

	@Autowired
	private OcorrenciaRepository repositorio;

	public Ocorrencia cadastrar(Ocorrencia ocorrencia) {
		return repositorio.saveAndFlush(ocorrencia);
	}

	public Ocorrencia alterar(Long id, Ocorrencia ocorrencia) {

		ocorrencia.setDataUltimaModificao(LocalDateTime.now());

		return repositorio.saveAndFlush(ocorrencia);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Ocorrencia> buscaPorId(Long id) {
		return (Optional<Ocorrencia>) repositorio.findById(id);
	}

	public List<Ocorrencia> listarTodos() {
		return repositorio.findAll();
	}

	public List<Ocorrencia> buscarPeloSIC(String sic) {
		return repositorio.findBySic(sic);
	}

	public List<Ocorrencia> buscarPorSICeEstabelecimento(String sic, List<Estabelecimento> estabelecimento) {
		return repositorio.findBySicAndEstabelecimento(sic, estabelecimento);
	}

	public List<Ocorrencia> buscarPorEstabelecimento(Estabelecimento estabelecimento) {
		return repositorio.findByEstabelecimento(estabelecimento);
	}

	public List<Ocorrencia> buscarTodasDe2DiasTras() {
		return repositorio.findByDataOcorrenciaGreaterThanEqual(LocalDate.now().minusDays(2));
	}

	public List<Ocorrencia> buscarTodasDe2DiasTrasPorEstabelecimento(Estabelecimento estabelecimento) {
		return repositorio.findByEstabelecimentoAndDataOcorrenciaGreaterThanEqual(estabelecimento,
				LocalDateTime.now().minusDays(2));
	}

	public List<Ocorrencia> buscarTodasDe1DiasTrasPorEstabelecimento(Estabelecimento estabelecimento) {

		return repositorio.findByEstabelecimentoAndDataOcorrenciaGreaterThanEqual(estabelecimento,
				LocalDateTime.now().minusDays(1));
	}

	public List<Ocorrencia> findAllByOcorrencia(Ocorrencia ocorrenciaExampler, LocalDateTime dataInicio,
			LocalDateTime dataFim) {

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
				.withIgnorePaths("endereco.cidade.estabelecimento", "endereco.bairro.localidade.cidade.estabelecimento",
						"ativo", "enviada", "estabelecimento.endereco", "senha_email");

		Example<Ocorrencia> example = Example.of(ocorrenciaExampler, exampleMatcher);

		List<Ocorrencia> listaDeOcorrencias = repositorio.findAll(example);

		if (dataFim == null) {
			dataFim = LocalDateTime.now();
		}

		if (dataInicio != null) {
			List<Ocorrencia> listaPorData = new ArrayList<>();
			Iterator<Ocorrencia> ocorrenciaAsIterator = listaDeOcorrencias.iterator();

			while (ocorrenciaAsIterator.hasNext()) {
				Ocorrencia ocorrencia = ocorrenciaAsIterator.next();

				if ((ocorrencia.getDataOcorrencia().isAfter(dataInicio)
						|| ocorrencia.getDataOcorrencia().isEqual(dataInicio))
						&& (ocorrencia.getDataOcorrencia().isBefore(dataFim)
								|| ocorrencia.getDataOcorrencia().isEqual(dataFim))) {
					listaPorData.add(ocorrencia);
				}
			}
			listaDeOcorrencias = listaPorData;
		} else {
			List<Ocorrencia> listaPorData = new ArrayList<>();
			Iterator<Ocorrencia> ocorrenciaAsIterator = listaDeOcorrencias.iterator();

			while (ocorrenciaAsIterator.hasNext()) {
				Ocorrencia ocorrencia = ocorrenciaAsIterator.next();

				if (ocorrencia.getDataOcorrencia().isBefore(dataFim)
						|| ocorrencia.getDataOcorrencia().isEqual(dataFim)) {
					listaPorData.add(ocorrencia);
				}
			}
			listaDeOcorrencias = listaPorData;
		}
		return listaDeOcorrencias;
	}

	public List<Ocorrencia> findAllByOcorrenciaRecentes(Ocorrencia ocorrenciaExampler, LocalDateTime dataInicio,
			LocalDate dataFim, LocalTime horaInicio) {

		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
				.withIgnorePaths("endereco.cidade.estabelecimento", "endereco.bairro.localidade.cidade.estabelecimento",
						"ativo", "enviada", "estabelecimento.endereco");

		Example<Ocorrencia> example = Example.of(ocorrenciaExampler, exampleMatcher);

		List<Ocorrencia> listaDeOcorrencias = repositorio.findAll(example);

		if (dataFim == null) {
			dataFim = LocalDate.now();
			// dataFim = LocalDateTime.now();
		}
		if (dataInicio == null) {
			dataInicio = LocalDateTime.now().minusDays(1);
		}
		if (horaInicio == null) {
			horaInicio = LocalTime.now();
		}

		if (dataInicio != null) {
			List<Ocorrencia> listaPorData = new ArrayList<Ocorrencia>();
			Iterator<Ocorrencia> ocorrenciaAsIterator = listaDeOcorrencias.iterator();

			while (ocorrenciaAsIterator.hasNext()) {
				Ocorrencia ocorrencia = ocorrenciaAsIterator.next();

				if (ocorrencia.getDataOcorrencia().isEqual(dataInicio)
						|| ocorrencia.getDataOcorrencia().isAfter(dataInicio)) {
					listaPorData.add(ocorrencia);
				}
			}
			listaDeOcorrencias = listaPorData;
		}

		return listaDeOcorrencias;

	}

	public List<Object[]> listaUltimasOcorrenciasGraficos(LocalDateTime dataAnterior, LocalDateTime dataAtual,
			Estabelecimento estabelecimento) {
		return repositorio.listaUltimasOcorrencias(dataAnterior, dataAtual, estabelecimento);
	}

	public List<Object[]> listaUltimasOcorrenciasPorStatus(LocalDateTime dataAnterior, LocalDateTime dataAtual,
			Estabelecimento estabelecimento) {
		return repositorio.listarUltimasOcorrenciasPorStatus(dataAnterior, dataAtual, estabelecimento);
	}

	public List<Object[]> listaOcorrenciaPorHora(LocalDate dia, Estabelecimento estabelecimento) {
		return repositorio.buscaOcorrenciasPorHora(dia, estabelecimento);
	}

	public List<Object[]> listaOcorrenciaPorAbrangencia(LocalDateTime dataAnterior, LocalDateTime dataAtual,
			Estabelecimento estabelecimento) {
		return repositorio.buscaRegistroPorAbrangencia(dataAnterior, dataAtual, estabelecimento);
	}

	public List<Object[]> buscaOcorrenciaPorTipificacoesCidades(List<Tipificacao> listaTipificacao,
			List<Cidade> listaCidades, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return repositorio.buscaPorTipificaoCidades(dataInicial, dataFinal, listaTipificacao, listaCidades);
	}

	public List<Object[]> buscaOcorrenciaPorTipificacoesLocalidades(List<Tipificacao> listaTipificacao,
			List<Localidade> listaLocalidades, List<Cidade> listaCidades, LocalDateTime dataInicial,
			LocalDateTime dataFinal) {
		return repositorio.buscaPorTipificacaoLocalidades(dataInicial, dataFinal, listaTipificacao, listaCidades,
				listaLocalidades);
	}

	public List<Object[]> buscaOcorrenciaPorTipificacoesBairros(List<Tipificacao> listaTipificacao,
			List<Cidade> listaCidades, List<Localidade> listaLocalidades, List<Bairro> listaBairros,
			LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return repositorio.buscaPorTipificacaoBairros(dataInicial, dataFinal, listaTipificacao, listaCidades,
				listaLocalidades, listaBairros);
	}

	public List<Object[]> buscaOcorrenciaPorEstabelecimentos(List<Estabelecimento> estabelecimentos,
			LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return repositorio.buscaPorEstabelecimento(dataInicial, dataFinal, estabelecimentos);
	}

	public List<Object[]> buscaDadosEstatisticos(List<Cidade> listaCidades, List<Tipificacao> listaTipificacao,
			Integer anoInicial, Integer anoFinal, Integer mesInicial, Integer mesFinal) {
		return repositorio.buscaDadosEstatistica(listaCidades, listaTipificacao, anoInicial, anoFinal, mesInicial,
				mesFinal);
	}

	public List<Object[]> buscaDadosEstatisticosAgrupadoPorMes(List<Cidade> listaCidades,
			List<Tipificacao> listaTipificacao, Integer anoInicial, Integer anoFinal, Integer mesInicial,
			Integer mesFinal) {
		return repositorio.buscaDadosEstatisticaAgrupadoPorMes(listaCidades, listaTipificacao, anoInicial, anoFinal,
				mesInicial, mesFinal);
	}// D E L I V E L T O N

	public Page<Ocorrencia> buscaPaginadaPorEstabelecimento(String pesquisa, Estabelecimento estabelecimento,
			Pageable pageable) {
		return repositorio.buscaPaginadaComQuery("%" + pesquisa + "%", estabelecimento.getId(), pageable);
	}

	public Page<Ocorrencia> buscaPaginada(String pesquisa, Pageable pageable) {

		return repositorio
				.findBySicLikeOrTipificacaoNomeLikeOrDescricaoLikeOrEnderecoCidadeNomeLikeOrEnderecoBairroNomeLikeOrEnderecoBairroLocalidadeNomeLikeOrderByIdDesc(
						"%" + pesquisa + "%", "%" + pesquisa + "%", "%" + pesquisa + "%", "%" + pesquisa + "%",
						"%" + pesquisa + "%", "%" + pesquisa + "%", pageable);
	}

	public DataTablesOutput<Ocorrencia> listarTodasOcorrencias(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}

	public DataTablesOutput<Ocorrencia> listarTodasOcorrencias(@Valid DataTablesInput input,
			@Valid DataTablesInput additionalSpecification) {
//		return repositorio.findAll(input,additionalSpecification);
		return null;
	}

	/**
	 * Método de Lucas
	 * 
	 * @param input
	 * @param user
	 * @return
	 */
	public DataTablesOutput<Map<String, Object>> listarTodasOcorrencias(DataTablesInput input, Usuario user) {
		if (user.getGrupo().getNome().contentEquals("TELEDESPACHO")
				|| user.getGrupo().getNome().contentEquals("COORDENADOR-ADJUNTO")
				|| user.getGrupo().getNome().contentEquals("COORDENADOR-SUPERVISOR")) {
			input.getColumns().get(8).getSearch()
					.setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));

			return repositorio.findAll(input, Specification.where(OcorrenciaSpecification.dosUltimos3Dias()), null,
					new Function<Ocorrencia, Map<String, Object>>() {
						private int a = 0;

						@Override
						public Map<String, Object> apply(Ocorrencia ocorrencia) {
							System.out.println("A letra " + a);

							Map<String, Object> mapa = new HashMap<>();
							mapa.put("sic", ocorrencia.getSic());
							mapa.put("dataOcorrencia", ocorrencia.getDataOcorrencia());
							mapa.put("tipificacao", ocorrencia.getTipificacao().getNome());
							mapa.put("descricao", ocorrencia.getDescricao());
							mapa.put("cidade", ocorrencia.getEndereco().getCidade().getNome());
							mapa.put("localidade", ocorrencia.getEndereco().getBairro().getLocalidade().getNome());
							mapa.put("bairro", ocorrencia.getEndereco().getBairro().getNome());
							mapa.put("enviada", ocorrencia.isEnviada());
							mapa.put("estabelecimento", ocorrencia.getEstabelecimento().getNome());
							mapa.put("id", ocorrencia.getId());

							return mapa;
						}
					});

		}

		return repositorio.findAll(input, Specification.where(OcorrenciaSpecification.dosUltimos3Dias()), null,
				new Function<Ocorrencia, Map<String, Object>>() {
					private int a = 0;

					@Override
					public Map<String, Object> apply(Ocorrencia ocorrencia) {
						System.out.println("A letra " + a);
						Map<String, Object> mapa = new HashMap<>();
						mapa.put("sic", ocorrencia.getSic());
						mapa.put("dataOcorrencia", ocorrencia.getDataOcorrencia());
						mapa.put("tipificacao", ocorrencia.getTipificacao().getNome());
						mapa.put("descricao", ocorrencia.getDescricao());
						mapa.put("cidade", ocorrencia.getEndereco().getCidade().getNome());
						mapa.put("localidade", ocorrencia.getEndereco().getBairro().getLocalidade().getNome());
						mapa.put("bairro", ocorrencia.getEndereco().getBairro().getNome());
						mapa.put("enviada", ocorrencia.isEnviada());
						mapa.put("estabelecimento", ocorrencia.getEstabelecimento().getNome());
						mapa.put("id", ocorrencia.getId());

						return mapa;
					}
				});

	}

	public DataTablesOutput<Ocorrencia> listarOcorrencias(DataTablesInput input, Estabelecimento esta,
			List<Tipificacao> tipificacoes) {
		return repositorio.findAll(input,
				Specification
						.where(OcorrenciaSpecification.quandoNaoTemRegistro()
								.and(OcorrenciaSpecification.byEstabelecimento(esta))
								.and(OcorrenciaSpecification.quandoEhCvli(tipificacoes))) // ano , mes , dia do mes ,
																							// hora , minuto
						.and(OcorrenciaSpecification.quandoEntreDatas(
								LocalDateTime.of(LocalDateTime.now().getYear() - 1, 1, 1, 0, 0),
								LocalDateTime.of(LocalDateTime.now().getYear() - 1, 12, 31, 23, 59))));
	}

	public List<DadosEstatisticosPorMes> buscaDadosEstatisticosCategoriaPorMes(Estabelecimento estabelecimento,
			Categoria categoria, Integer ano) {
		List<DadosEstatisticosPorMes> dados = new ArrayList<>();

		List<Object[]> objetos = repositorio.buscaDadosEstatisticaPorMesECategoria(estabelecimento.getCidades(),
				categoria.getTiposOcorrencia(), ano);

		for (Integer i = 1; i <= 12; i++) {
			DadosEstatisticosPorMes dado = new DadosEstatisticosPorMes();
			dado.setMes(Mes.getNome(i));

			for (Object[] objeto : objetos) {
				DadosPorCategoria vindoDobanco = DadosEstatisticosPorMes.criaDados((String) objeto[0], (Long) objeto[1],
						(Long) objeto[i + 1], (Long) objeto[i + 13], (Long) objeto[i + 25],
						(Long) objeto[i + 1] + (Long) objeto[i + 13] + (Long) objeto[i + 25]);
				if (i == 1) {
					vindoDobanco.setJaneiro((Long) objeto[2] + (Long) objeto[14] + (Long) objeto[26]);
					vindoDobanco.setFevereiro((Long) objeto[3] + (Long) objeto[15] + (Long) objeto[27]);
					vindoDobanco.setMarco((Long) objeto[4] + (Long) objeto[16] + (Long) objeto[28]);
					vindoDobanco.setAbril((Long) objeto[5] + (Long) objeto[17] + (Long) objeto[29]);
					vindoDobanco.setMaio((Long) objeto[6] + (Long) objeto[18] + (Long) objeto[30]);
					vindoDobanco.setJunho((Long) objeto[7] + (Long) objeto[19] + (Long) objeto[31]);
					vindoDobanco.setJulho((Long) objeto[8] + (Long) objeto[20] + (Long) objeto[32]);
					vindoDobanco.setAgosto((Long) objeto[9] + (Long) objeto[21] + (Long) objeto[33]);
					vindoDobanco.setSetembro((Long) objeto[10] + (Long) objeto[22] + (Long) objeto[34]);
					vindoDobanco.setOutubro((Long) objeto[11] + (Long) objeto[23] + (Long) objeto[35]);
					vindoDobanco.setNovembro((Long) objeto[12] + (Long) objeto[24] + (Long) objeto[36]);
					vindoDobanco.setDezembro((Long) objeto[13] + (Long) objeto[25] + (Long) objeto[37]);
				}
				dado.getDados().add(vindoDobanco);

			}
			dados.add(dado);
		}

		return dados;
	}

	public List<DadosEstatisticosPorMes> buscaDadosEstatisticosCategoriaPorMes(Estabelecimento estabelecimento,
			List<Tipificacao> tipificacoes, Integer ano) {
		List<DadosEstatisticosPorMes> dados = new ArrayList<>();

		List<Object[]> objetos = repositorio.buscaDadosEstatisticaPorMesECategoria(estabelecimento.getCidades(),
				tipificacoes, ano);

		for (Integer i = 1; i <= 12; i++) {
			DadosEstatisticosPorMes dado = new DadosEstatisticosPorMes();
			dado.setMes(Mes.getNome(i));

			for (Object[] objeto : objetos) {
				dado.getDados()
						.add(DadosEstatisticosPorMes.criaDados((String) objeto[0], (Long) objeto[1],
								(Long) objeto[i + 1], (Long) objeto[i + 13], (Long) objeto[i + 25],
								(Long) objeto[i + 1] + (Long) objeto[i + 13] + (Long) objeto[i + 25]));
			}
			dados.add(dado);
		}

		return dados;
	}

	public Long quantidadeDeOcorrenciasNaoRegistradas(Estabelecimento estabelecimento, List<Tipificacao> tipificacao) {
		return repositorio.quantidadeDeOcorrenciasNaoRegistradas(estabelecimento, tipificacao,
				LocalDateTime.now().getYear() - 1);
	}

	public List<Map<String, Object>> listaUltimos3Dias(Estabelecimento estabelecimento) {
		return repositorio.ListOcorrenciasUntimos3Dias(LocalDateTime.now().minusDays(3), LocalDateTime.now(),
				estabelecimento);

	}

}
