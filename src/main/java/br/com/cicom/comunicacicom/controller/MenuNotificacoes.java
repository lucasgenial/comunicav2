package br.com.cicom.comunicacicom.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.MensagemDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MenuNotificacoes {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private GrupoService servicoGrupo;
	
	@Autowired
    private ModelMapper modelMapper;

	@RequestMapping(value = "/admin/mensagens/entrada")
	public ModelAndView entradaNotificacaoPage(HttpSession session, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("/fragmentos/mensagem/listarMensagem");

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
		model.addObject("listaMensagens", null);

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		model.setViewName("/fragmentos/mensagem/listarMensagem");
		model.addObject("tituloPagina", "ComunicaCICOM - Mensagens");

		return model;
	}

	@RequestMapping(value = "/admin/mensagens/nova")
	public ModelAndView novaMensagensPage(HttpSession session, HttpServletRequest req) {
		ModelAndView model = new ModelAndView("/fragmentos/mensagem/novaMensagem");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());

		if (user == null) {
			model.addObject("erro", true);
			model.addObject("mensagem", "Foi encontrado um erro!");

			model.setView(new RedirectView("/"));
			model.setViewName("login");

			return model;
		}
		
		UsuarioDTO userDTO = converterParaUsuarioDTO(user);
		
		model.addObject("usuario",user);
		model.addObject("criador", userDTO);
		
		MensagemDTO mensagemDTO = new MensagemDTO();
		
		mensagemDTO.setEmissor(userDTO);
				
		// Adiciona uma nova mensagem na view
		model.addObject("mensagem", mensagemDTO);
		//model.addObject("linkCadastro", "/cadastrarMensagem");
		
		// Adiciona uma lista de Grupos
		model.addObject("listaGrupos", 
			servicoGrupo.listarTodos().stream().map(this::converterParaGrupoDTO).collect(Collectors.toList()));
	
		// Adiciona uma lista de Usuários por estabelecimento do Usuário Logado.
		model.addObject("listaUsuarios", servicoUsuario.buscarPorEstabelecimentos(user.getEstabelecimento())
			.stream().filter(e -> e.getServidor()!=null)
			.map(this::converterParaUsuarioDTO).collect(Collectors.toList()));
		
		model.setViewName("/fragmentos/mensagem/novaMensagem");
		model.addObject("metodo", "POST");
		
		model.addObject("tituloPagina", "ComunicaCICOM - Nova Mensagem");

		return model;
	}

	public GrupoDTO converterParaGrupoDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}

	public Grupo converterParaGrupo(GrupoDTO grupoDTO) {
		return modelMapper.map(grupoDTO, Grupo.class);
	}

	public UsuarioDTO converterParaUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	public Usuario converterParaUsuario(UsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO, Usuario.class);
	}
}
