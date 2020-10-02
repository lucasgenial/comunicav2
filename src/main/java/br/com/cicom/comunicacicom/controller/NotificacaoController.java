package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.NotificacaoDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class NotificacaoController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private GrupoService servicoGrupo;
	
	@Autowired
    private ModelMapper modelMapper;

	@ResponseBody
	@RequestMapping(value = { "/hotificacao/listar/" }, method = { RequestMethod.POST, RequestMethod.GET })
	public List<NotificacaoDTO> listarNotificacoes(ModelAndView mv) {
		return null;
	}

	@RequestMapping(value = "**/cadastrarNotificao")
	public String cadastrarNotificao(NotificacaoDTO notificacao, BindingResult result) {
		
		System.out.println(notificacao);
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/caracteristica";
		}
		
		return "redirect:/admin/notificacoes/entrada";
	}

	@ResponseBody
	@RequestMapping(value = "/admin/notificacoes/nova/usuarios/", method = { RequestMethod.POST })
	public List<UsuarioDTO> buscarUsuarios(@RequestParam(value = "grupos[]") ArrayList<Grupo> grupos) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		
		List<UsuarioDTO> usuarios = new ArrayList<>();

		if (!grupos.toString().equals("[null]")) {
			usuarios = servicoUsuario.buscarPorEstabelecimentosGrupo(user.getEstabelecimento(), grupos).stream()
					.filter(e -> e.getServidor() != null).map(this::converterParaUsuarioDTO)
					.collect(Collectors.toList());
		} else {
			usuarios = servicoUsuario.buscarPorEstabelecimentos(user.getEstabelecimento()).stream()
					.filter(e -> e.getServidor() != null).map(this::converterParaUsuarioDTO)
					.collect(Collectors.toList());
		}
		
		//Ordena a lista
		Collections.sort(usuarios);

		return usuarios;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/notificacoes/nova/grupos/", method = { RequestMethod.POST })
	public List<GrupoDTO> buscarGrupos() {
		List<GrupoDTO> grupos = new ArrayList<>();

		grupos = servicoGrupo.listarTodos().stream().map(this::converterParaGrupoDTO).collect(Collectors.toList());
		
		//Ordena a lista
		Collections.sort(grupos);
		
		return grupos;
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