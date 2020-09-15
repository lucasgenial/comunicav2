package br.com.cicom.comunicacicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.ServicoService;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.VisitaService;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.VisitanteService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class VisitanteController {

	@Autowired
	private VisitaService serviceVisita;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private VisitanteService servicoVisitante;
	
	@GetMapping("*/pesquisa/visitantes")
	public String visitanteBuscar(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	
		model.addAttribute("usuario", user);
		model.addAttribute("visitante", new Visitante());
		model.addAttribute("servicos", servicoService.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Visitas");

		return "fragmentos/pesquisas/pesquisaVisitante";
	}
	
	@PostMapping("/visitante/filtrar")
	public String resultadoBuscar(Model model, Visitante visitante) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		model.addAttribute("visitante", new Visitante());
		model.addAttribute("visitantesResultado", servicoVisitante.filtrar(visitante));
		model.addAttribute("servicos", servicoService.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Visitas");

		return "fragmentos/pesquisas/pesquisaVisitante";
	}
	
	@RequestMapping(value = "*/visualiza/visitante/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarVisita( @PathVariable("id") Long id, ModelAndView modelAndView) {
							
		Visitante visitante = servicoVisitante.buscaPorId(id).get();
		
		modelAndView.setViewName("fragmentos/visualizacoes/visualizaVisitante");
		modelAndView.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		modelAndView.addObject("visitante",visitante);
		modelAndView.addObject("quantidadeDeVisitas",serviceVisita.quantidadedeVisitasPorVisitante(visitante));
		modelAndView.addObject("empresasVinculado",serviceVisita.empresasPorVisitante(visitante));

		return modelAndView;
    }
}
