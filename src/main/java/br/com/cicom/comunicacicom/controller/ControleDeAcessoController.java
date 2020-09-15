package br.com.cicom.comunicacicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class ControleDeAcessoController {


	@Autowired
	private UsuarioService servicoUsuario;
	
	
	@GetMapping("**/acesso_negado")
	public ModelAndView telaInicialAdministracao(ModelAndView mv) {
		
		mv.setView(new RedirectView("*/cadastros/acesso_negado"));
		mv.setViewName("principal/acesso_negado");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("usuarios", servicoUsuario.listarTodos());
		return mv;
	}

}
