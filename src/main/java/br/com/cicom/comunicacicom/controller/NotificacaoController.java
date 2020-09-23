package br.com.cicom.comunicacicom.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.NotificacaoDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class NotificacaoController {
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@ResponseBody
	@RequestMapping(value = { "/hotificacao/listar/" }, method = { RequestMethod.POST, RequestMethod.GET })
	public List<NotificacaoDTO> listarNotificacoes(ModelAndView mv) {
		return null;
	}
	
	@RequestMapping(value = "**/cadastrarNotificao", params = {"cadastrar"})
	public String cadastrarNotificao(ModelAndView mv) {
		
		return "redirect:/admin/notificacoes/entrada";
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/notificacoes/nova/usuarios/", method = { RequestMethod.POST})
	public List<UsuarioDTO> buscarUsuarios(@RequestParam(value = "grupos") List<Grupo> grupos) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
				
		List<UsuarioDTO> usuarios = servicoUsuario.buscarPorEstabelecimentosGrupo(user.getEstabelecimento(), grupos)
				.stream().filter(e -> e.getServidor()!=null)
				.map(this::converterParaUsuarioDTO).collect(Collectors.toList());
		
		System.out.println(usuarios);
		
		return usuarios;
	}
	
	public UsuarioDTO converterParaUsuarioDTO(Usuario usuario) {
		return new UsuarioDTO(usuario.getId(), usuario.getServidor());
	}

	public Usuario converterParaUsuario(UsuarioDTO usuarioDTO) {
		return servicoUsuario.buscaPorId(usuarioDTO.getId()).get();
	}
}