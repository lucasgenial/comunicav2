package br.com.cicom.comunicacicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class GerenciadorAcessoController {

		@Autowired
		private UsuarioService servicoUsuario;
		
		@GetMapping("*/edita/acesso")
		public ModelAndView telaInicialAdministracao(ModelAndView mv) {
			mv.setView(new RedirectView("*/cadastros/gerenciador_acesso"));
			mv.setViewName("fragmentos/edicoes/editaAcesso");
			mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
			mv.addObject("usuarios", servicoUsuario.listarTodos());
			return mv;
		}
		
		@GetMapping("**/pagina_em_construcao")
		public ModelAndView telaInicialDeAviso(ModelAndView mv) {
							
			mv.setViewName("/pagina_em_construcao");
			mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
			mv.addObject("usuarios", servicoUsuario.listarTodos());
			return mv;
		}
}
