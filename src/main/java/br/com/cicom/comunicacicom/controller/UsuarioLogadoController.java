package br.com.cicom.comunicacicom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class UsuarioLogadoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ResponseBody
	@RequestMapping(value = "**/usuario/buscar/logado", method = { RequestMethod.POST,RequestMethod.GET })
	public Usuario listPOST() {
		return usuarioService.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	}

}
