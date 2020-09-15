package br.com.cicom.comunicacicom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.model.notificacao.Notificacao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MenuNotificacoes {

	@Autowired
	private UsuarioService servicoUsuario;

	@RequestMapping(value = "/admin/notificacoes/entrada")
	public ModelAndView entradaNotificacaoPage(HttpSession session, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("/fragmentos/notificacao/listarNotificacoes");

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
		model.addObject("listaNotificacao", null);

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		model.setViewName("/fragmentos/notificacao/listarNotificacoes");
		model.addObject("tituloPagina", "ComunicaCICOM - Notificações");

		return model;
	}
	
	@RequestMapping(value = "/admin/notificacoes/nova")
	public ModelAndView novaNotificacaoPage(HttpSession session, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("/fragmentos/notificacao/novaNotificacao");

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
		//Adiciona uma nova notificação na view
		model.addObject("notificacao", new Notificacao());

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		model.setViewName("/fragmentos/notificacao/novaNotificacao");
		model.addObject("tituloPagina", "ComunicaCICOM - Nova Notificação");

		return model;
	}
}
