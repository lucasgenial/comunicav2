package br.com.cicom.comunicacicom.controller;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.DadosGraficos;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;

@Controller
public class GraficosController {

	@Autowired
	private OcorrenciaService serviceOcorrencia;

	@PostMapping("/gerarGrafico")
	public String gerarGrafico(@RequestParam(value = "dataInicial") String dataInicio,
			@RequestParam(value = "horaInicio") String horaInicio, @RequestParam(value = "dataFim") String dataFim,
			@RequestParam(value = "horaFim") String horaFim, DadosGraficos dados, BindingResult result,
			RedirectAttributes redirectAttributes) {

		Map<Object, Object> resultados = new HashMap<>();

		List<String> nomes = new ArrayList<>();
		List<Long> quantidades = new ArrayList<>();

		List<Object[]> respostaBanco = new ArrayList<>();

		if (!dataInicio.isEmpty() && !horaInicio.isEmpty()) {
			dados.setDataInicial(LocalDateTime.of(LocalDate.parse(dataInicio), LocalTime.parse(horaInicio)));
		} else {
			dados.setDataInicial(LocalDateTime.of(LocalDate.now(), LocalTime.parse("00:00:00")));
		}

		if (!dataFim.isEmpty() && !horaFim.isEmpty()) {
			dados.setDataFinal(LocalDateTime.of(LocalDate.parse(dataFim), LocalTime.parse(horaFim)));
		} else {
			dados.setDataFinal(LocalDateTime.of(LocalDate.now(), LocalTime.parse("23:59:59")));
		}

		if (!dados.getListaTipificacao().isEmpty()) {
			if (!dados.getListaCidades().isEmpty()) {
				if (dados.getListaLocalidades().isEmpty()) {
					// PESQUISA OCORRENCIAS POR TIPIFICAÇÃO E POR CIDADES SELECIONADAS

					respostaBanco = serviceOcorrencia.buscaOcorrenciaPorTipificacoesCidades(dados.getListaTipificacao(),
							dados.getListaCidades(), dados.getDataInicial(), dados.getDataFinal());
				} else {
					if (dados.getListaBairros().isEmpty()) {
						// PESQUISA OCORRENCIAS POR TIPIFICAÇÕES POR LOCALIDADES SELECIONADAS

						respostaBanco = serviceOcorrencia.buscaOcorrenciaPorTipificacoesLocalidades(
								dados.getListaTipificacao(), dados.getListaLocalidades(), dados.getListaCidades(),
								dados.getDataInicial(), dados.getDataFinal());

					} else {
						// PESQUISA OCORRENCIA POR TIPIFICAÇÃO PELOS BAIRROS SELECIONADOS
						respostaBanco = serviceOcorrencia.buscaOcorrenciaPorTipificacoesBairros(
								dados.getListaTipificacao(), dados.getListaCidades(), dados.getListaLocalidades(),
								dados.getListaBairros(), dados.getDataInicial(), dados.getDataFinal());
					}
				}

			} else {
				if (dados.getEstabelecimentos().isEmpty()) {
					// PESQUISA OCORRENCIAS POR ESTABELECIMENTO SELECIONADO NO PERIODO
					respostaBanco = serviceOcorrencia.buscaOcorrenciaPorEstabelecimentos(dados.getEstabelecimentos(),
							dados.getDataInicial(), dados.getDataFinal());

				}
			}
		}

		for (Object[] respBanco : respostaBanco) {
			nomes.add((String) respBanco[0]);
			quantidades.add(((BigInteger) respBanco[1]).longValue());
		}

		resultados.put("nome", nomes);
		resultados.put("quantidade", quantidades);

		redirectAttributes.addFlashAttribute("dados", resultados);
		redirectAttributes.addFlashAttribute("tipoGrafico", dados.getTipoGrafico());
		redirectAttributes.addFlashAttribute("tituloGrafico", dados.getTitulo());

		return "redirect:/admin/relatorio/graficos";

	}
}