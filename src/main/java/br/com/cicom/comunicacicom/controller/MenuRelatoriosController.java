package br.com.cicom.comunicacicom.controller;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.POJO.ClasseUtilitaria;
import br.com.cicom.comunicacicom.DSPrimary.POJO.DadosDePesquisaParaGerarGraficos;
import br.com.cicom.comunicacicom.DSPrimary.POJO.Mes;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MenuRelatoriosController {

	@Autowired
	private CategoriaService serviceCategoria;
	
	@Autowired
	private TipificacaoService serviceTipificacao;

	@Autowired
	private UsuarioService servicoUsuario;

	public MenuRelatoriosController() {
		super();
	}

	
	@GetMapping(value = "**/relatorio/dadosEstatisticos")
	public String relatorioGerarDadosEstatistica(Model model, HttpServletRequest req) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);
		
		if(!model.containsAttribute("dadosDePesquisa")) {
			model.addAttribute("dadosDePesquisa", new DadosDePesquisaParaGerarGraficos());
		}
		
		List<Tipificacao> listaTipificacao = new ArrayList<>();
		model.addAttribute("tipificacoesSelecionadas", listaTipificacao);
		model.addAttribute("tipificacoesASelecionar", serviceTipificacao.listarTodos());
		model.addAttribute("anosValidos", ClasseUtilitaria.geraAnosValidos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Gerador de Dados Estatistico");// <-- Adicionado
		model.addAttribute("categorias", serviceCategoria.listarTodos());
		model.addAttribute("estabelecimentos", user.getServidor().getEstabelecimento());
		model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());


		return "fragmentos/relatorio/gerarDadosEstatisticos";
	}

	@ResponseBody
	@GetMapping("**/ano/meses/{ano}")
	public List<Mes> consultarMesesValidos(@PathVariable(value = "ano", required = false) Year ano,
			ModelAndView model) {

		return ClasseUtilitaria.geraMesesValidos(ano);
	}

	@ResponseBody
	@GetMapping("**/ano/anosValidosDeBusca/{ano}")
	public List<Year> consultarAnosValidos(@PathVariable(value = "ano", required = false) Year ano,
			ModelAndView model) {

		return ClasseUtilitaria.geraAnosValidos(ano);
	}

	@ResponseBody
	@GetMapping("**/ano/meses/{ano}/{mes}")
	public List<Mes> consultarAnosValidos(@PathVariable(value = "ano", required = false) Year ano,
			@PathVariable(value = "mes", required = false) Integer mes, ModelAndView model) {
		return ClasseUtilitaria.geraMesesValidos(ano, new Mes(Month.of(mes)));
	}
	
}
