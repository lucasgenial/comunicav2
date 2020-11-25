package br.com.cicom.comunicacicom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MenuNotificacoes {

	@Autowired
	private UsuarioService servicoUsuario;

	@RequestMapping(value = "/admin/mensagens/entrada")
	public String entradaNotificacaoPage(Model model, HttpSession session, HttpServletRequest req) {
		//ModelAndView model = new ModelAndView("/fragmentos/mensagem/listarMensagem");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());

		if (user == null) {
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Foi encontrado um erro!");

			return "/fragmentos/mensagem/listarMensagem";
		}

		model.addAttribute("usuario", user);
		model.addAttribute("listaMensagens", null);

		//model.setViewName("/fragmentos/mensagem/listarMensagem");
		model.addAttribute("tituloPagina", "ComunicaCICOM - Mensagens");

		return "/fragmentos/mensagem/listarMensagem";
	}
}
