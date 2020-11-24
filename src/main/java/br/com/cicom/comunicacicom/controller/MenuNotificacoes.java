package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.MensagemDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Mensagem;
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

	@RequestMapping(value = "/admin/mensagens/nova")
	public String novaMensagensPage(Model model) {
		//Model model = new ModelAndView("/fragmentos/mensagem/novaMensagem");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		model.addAttribute("usuario", user);
		
		if (user == null) {
			model.addAttribute("erro", true);
			
			model.addAttribute("mensagem", "Foi encontrado um erro!");

			return "/fragmentos/mensagem/novaMensagem";
		}
				
		model.addAttribute("usuario", user);
		
		MensagemDTO mensagem = new MensagemDTO();
		mensagem.setEmissor(user.getId());
//		System.out.println(mensagem.getEmissor());
				
		// Adiciona uma nova mensagem na view
		// Verifica se já foi passado a ocorrência
		if (!model.containsAttribute("novaMensagem")) {
			mensagem.setDataCriacao(LocalDateTime.now());
			model.addAttribute("novaMensagem", mensagem);
		}
		
		// Adiciona uma lista de Grupos
		model.addAttribute("listaGrupos", servicoGrupo.listarTodos().stream().collect(Collectors.toList()));
	
		// Adiciona uma lista de Usuários por estabelecimento do Usuário Logado.
		model.addAttribute("listaUsuarios", servicoUsuario.buscarPorEstabelecimentos(user.getEstabelecimento())
				.stream().filter(e -> e.getServidor()!=null && !e.getServidor().getNome().equalsIgnoreCase("BOMBEIRO MILITAR"))
				.collect(Collectors.toList()));
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Mensagem");

		return "/fragmentos/mensagem/novaMensagem";
	}

	public GrupoDTO converterParaGrupoDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}

	public Grupo converterParaGrupo(GrupoDTO grupoDTO) {
		return modelMapper.map(grupoDTO, Grupo.class);
	}

	public UsuarioDTO user(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	public Usuario converterParaUsuario(UsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO, Usuario.class);
	}
}
