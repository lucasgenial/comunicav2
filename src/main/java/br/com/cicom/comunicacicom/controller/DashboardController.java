package br.com.cicom.comunicacicom.controller;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ModalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.PoliciamentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class DashboardController {

	@Autowired
	private OcorrenciaService serviceOcorrencia;

	@Autowired
	private ModalidadeService serviceModalidade;

	@Autowired
	private PoliciamentoService servicePoliciamento;

	@Autowired
	private UnidadeService serviceUnidade;

	/**
	 * 
	 * FUNCIONALIDADES DA TELA DE DASHBOARD OCORRENCIAS
	 * 
	 */
	@ResponseBody
	@GetMapping("**/lista/ocorrencias/{id}")
	public Map<Object, Object> jsonUltimasOcorrencias(@PathVariable(value = "id") Estabelecimento estabelecimento) {

		Map<Object, Object> resultados = new HashMap<>();

		int total = 0;

		List<String> tipificacoes = new ArrayList<>();
		List<Long> quantidade = new ArrayList<>();
		List<Float> percentual = new ArrayList<>();

		for (Object[] respBanco : serviceOcorrencia.listaUltimasOcorrenciasGraficos(LocalDateTime.now().minusDays(1),
				LocalDateTime.now(), estabelecimento)) {
			tipificacoes.add((String) respBanco[0]);

			Long i = ((BigInteger) respBanco[1]).longValue();

			total += i;

			quantidade.add(i);
		}

		DecimalFormat df = new DecimalFormat("0.00");

		df.setRoundingMode(RoundingMode.HALF_UP);

		for (Long q : quantidade) {
			percentual.add((float) q.intValue() * 100 / total);
		}
		
		resultados.put("nome", tipificacoes);
		resultados.put("quantidade", quantidade);
		resultados.put("percentual", percentual);

		return resultados;

	}

	@ResponseBody
	@GetMapping("**/lista/ocorrencia/status/{id}")
	public Map<Object, Object> jsonOcorrenciasPorStatus(@PathVariable(value = "id") Estabelecimento estabelecimento) {

		Map<Object, Object> resultados = new HashMap<>();

		List<String> status = new ArrayList<>();
		List<Long> quantidade = new ArrayList<>();

		for (Object[] respBanco : serviceOcorrencia.listaUltimasOcorrenciasPorStatus(LocalDateTime.now().minusDays(1),
				LocalDateTime.now(), estabelecimento)) {
			status.add((String) respBanco[0]);
			quantidade.add(((BigInteger) respBanco[1]).longValue());
		}

		resultados.put("nome", status);
		resultados.put("quantidade", quantidade);

		return resultados;

	}

	@ResponseBody
	@RequestMapping(value = "**/lista/ocorrencias/hora/{id}")
	public Map<Object, Object> jsonOcorrenciasPorHora(@PathVariable(value = "id") Estabelecimento estabelecimento) {

		Map<Object, Object> resultados = new HashMap<>();

		List<String> hora = new ArrayList<>();
		List<Long> quantidade = new ArrayList<>();
		
		for (Object[] respBanco : serviceOcorrencia.listaOcorrenciaPorHora(LocalDate.now(), estabelecimento)) {
			hora.add((String) respBanco[0]);
			quantidade.add(((BigInteger) respBanco[1]).longValue());
		}

		resultados.put("nome", hora);
		resultados.put("quantidade", quantidade);
		return resultados;

	}

	@ResponseBody
	@RequestMapping(value = "**/lista/ocorrencias/abrangencia/{id}")
	public Map<Object, Object> jsonOcorrenciasPorAbrangencia(@PathVariable("id") Estabelecimento estabelecimento,
			ModelAndView mv) {

		Map<Object, Object> resultados = new HashMap<>();

		List<String> nomes = new ArrayList<>();
		List<String> quantidade = new ArrayList<>();
		List<String> cores = new ArrayList<>();

		for (Object[] respBanco : serviceOcorrencia.listaOcorrenciaPorAbrangencia(LocalDateTime.now().minusDays(1),
				LocalDateTime.now(), estabelecimento)) {
			nomes.add(String.valueOf(respBanco[0]) + " - " + respBanco[1]);
			quantidade.add(String.valueOf(respBanco[1]));
			cores.add((String) respBanco[2]);
		}

		resultados.put("nome", nomes);
		resultados.put("quantidade", quantidade);
		resultados.put("cores", cores);

		return resultados;
	}

	/**
	 * 
	 * FUNCIONALIDADES DA TELA DE DASHBOARD EFETIVOS
	 * 
	 */
//	@ResponseBody
//	@GetMapping("**/admin/efetivos/externos/ativo/estabelecimento/cidade/{estabelecimento}")
//	public List<EfetivoPOJO> policiamentosAtivosPorCidadeDashboard(
//			@PathVariable("estabelecimento") Estabelecimento estabelecimento, ModelAndView mv) {
//				
//		List<EfetivoPOJO> efetivosAtivos = servicePoliciamento.buscaPoliciamentoAtivoPorEstabelecimento(estabelecimento);
//		
//		return efetivosAtivos;
//	}

	@ResponseBody
	@GetMapping("**/admin/efetivos/externos/ativo/estabelecimento/unidade/{estabelecimento}")
	public List<Map<Object, Object>> policiamentosAtivosPorUnidadeDashboard(
			@PathVariable("estabelecimento") Estabelecimento estabelecimento, ModelAndView mv) {
		List<Policiamento> respostaBanco = new ArrayList<>();
		respostaBanco.addAll(servicePoliciamento.listarTodosPorStatus(false));

		List<Modalidade> listaModalidadeBanco = new ArrayList<>();
		listaModalidadeBanco = serviceModalidade.listarTodosPorStatus(true);

		List<Map<Object, Object>> listaDeDados = new ArrayList<>();
		List<Map<Object, Object>> listaModalidade;

		Set<Estabelecimento> estabelecimentos = new HashSet<>();

		estabelecimentos.add(estabelecimento);

		for (Unidade unidade : serviceUnidade.listaTodosPorEstabelecimento(estabelecimentos)) {
			// Dados da Cidade
			Map<Object, Object> dadosUnidade = new HashMap<>();

			dadosUnidade.put("nomeUnidade", unidade.getNome());
			dadosUnidade.put("corUnidade", unidade.getCor());
			listaModalidade = new ArrayList<>();

			for (Modalidade mod : listaModalidadeBanco) {
				Map<Object, Object> dadosModalidade = new HashMap<>();

				dadosModalidade.put("nomeModalidade", mod.getNome());
				dadosModalidade.put("corModalidade", mod.getCor());

				int qtd = 0;
				Set<Long> ids = new HashSet<>();

				for (Policiamento pol : respostaBanco) {
					if (pol.getUnidade().getId() == unidade.getId() && pol.getModalidade().getId() == mod.getId()) {
						qtd++;
						ids.add(pol.getId());
					}
				}

				dadosModalidade.put("qtd", qtd);
				dadosModalidade.put("policiamentosIds", ids);
				listaModalidade.add(dadosModalidade);

			}

			dadosUnidade.put("modalidades", listaModalidade);
			listaDeDados.add(dadosUnidade);

		}

		return listaDeDados;
	}
}