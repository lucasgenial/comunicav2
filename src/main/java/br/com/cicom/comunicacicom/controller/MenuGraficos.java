package br.com.cicom.comunicacicom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.model.TrocadorDeEstabelecimentoParaExibicao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ModalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.PoliciamentoService;

@Controller
public class MenuGraficos {
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private PoliciamentoService servicoPoliciamento;
	
	@Autowired
	private ModalidadeService servicoModalidade;
	
	@RequestMapping(value = "/admin/home/grafico/ocorrencia")
	public ModelAndView graficoOcorrencia(HttpSession session, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("/admin/home");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());

		if (user == null) {
			model.addObject("erro", true);
			model.addObject("mensagem", "Foi encontrado um erro!");

			model.setView(new RedirectView("/"));
			model.setViewName("login");
			//model.addObject("tituloPagina", "ComunicaCICOM - Login");

			return model;
		}

		model.addObject("usuario", user);

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		if (user.getGrupo().getNome().equalsIgnoreCase("ADMINISTRADOR")
				|| user.getGrupo().getNome().equalsIgnoreCase("COORDENADOR-SUPERVISOR")
				|| user.getGrupo().getNome().equalsIgnoreCase("MASTER")) {

			model.setViewName("grafico_ocorrencia");
			model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Ocorrência - Tipificação");

		}if (user.getGrupo().getNome().equalsIgnoreCase("GRAFICOS-VISAO-GERAL")) {

			model.setViewName("grafico_ocorrencia_geral");
			
			TrocadorDeEstabelecimentoParaExibicao geradorDeId = TrocadorDeEstabelecimentoParaExibicao.criar();
			geradorDeId.setListaDeIds(user);
			model.addObject("geradorDeId", geradorDeId);
			model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Ocorrência - Geral");
			return model;
		}
		
		

		return model;
	}

	@RequestMapping(value = "/admin/home/grafico/efetivo")
	public ModelAndView graficoEfetivo(ModelAndView model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());

		if (user == null) {
			model.addObject("erro", true);
			model.addObject("mensagem", "Foi encontrado um erro!");

			model.setView(new RedirectView("/"));
			model.setViewName("login");

			return model;
		}

		model.addObject("usuario", user);

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		if (user.getGrupo().getNome().equalsIgnoreCase("ADMINISTRADOR") || user.getGrupo().getNome().equalsIgnoreCase("COORDENADOR-SUPERVISOR") || user.getGrupo().getNome().equalsIgnoreCase("MASTER")) {

			model.setView(new RedirectView("/admin/home/"));
			model.setViewName("grafico_efetivo");

		}
		
		model.addObject("policiamentos", servicoPoliciamento.listarTodosPorStatus(false));
		model.addObject("modalidades", servicoModalidade.listarTodosPorStatus(true));
		
		model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Efetivo");

		return model;
	}
	
	@PostMapping("/admin/home/grafico/ocorrencia")
	public ModelAndView configuraDashboardOcorrencia(Estabelecimento estabelecimento, ModelAndView model, BindingResult result, RedirectAttributes redirect) {

		if (result.hasErrors()) {
			
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.estabelecimento", result);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		
		model.addObject("usuario", user);
		model.addObject("estabelecimento", estabelecimento);
		model.setViewName("grafico_ocorrencia");
		
		model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Ocorrência - Tipificação");

		return model;
	}

	@PostMapping("/admin/home/grafico/efetivo")
	public ModelAndView configuraDashboardEfetivo(Estabelecimento estabelecimento, @RequestParam(name = "classificacao") int classificacao, ModelAndView model, BindingResult result,
			RedirectAttributes redirect) {

		if (result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.estabelecimento", result);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		
		model.addObject("usuario", user);
		model.addObject("estabelecimento", estabelecimento);
		model.addObject("classificacao", classificacao);
		model.setViewName("grafico_efetivo");
		
		model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Efetivo");

		return model;
	}
}
