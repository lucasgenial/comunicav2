package br.com.cicom.comunicacicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class AjudaController {
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@GetMapping("**/ajuda")
	public ModelAndView telaAjuda(ModelAndView mv) {
		
		mv.setView(new RedirectView("*/controle/recursos"));
		mv.setViewName("fragmentos/ajuda/ajuda");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		return mv;
	}

}
