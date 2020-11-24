package br.com.cicom.comunicacicom.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.MensagemDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Mensagem;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MensagemController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private GrupoService servicoGrupo;
	
	@Autowired
    private ModelMapper modelMapper;

	@ResponseBody
	@RequestMapping(value = { "/mensagem/listar/" }, method = { RequestMethod.POST, RequestMethod.GET })
	public List<MensagemDTO> listarMensagens(ModelAndView mv) {
		return null;
	}

	@RequestMapping(value = "**/cadastrarMensagem", method = { RequestMethod.POST})
	public String cadastrarMensagem(Model model, @Valid MensagemDTO mensagem, BindingResult result) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		
		if (result.hasErrors()) {
			
			System.out.println("ERROR OOOOOO 1");
			
//			model.addAttribute("org.springframework.validation.BindingResult.mensagem", result);
//			model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Mesa");		
//			model.addAttribute("novaMensagem", mensagem);
//			model.addAttribute("usuario", user);
//			model.addAttribute("criador", user);
//			
//			model.addAttribute("listaUsuarios", servicoUsuario.buscarPorEstabelecimentos(user.getEstabelecimento())
//					.stream().filter(e -> e.getServidor()!=null && !e.getServidor().getNome().equalsIgnoreCase("BOMBEIRO MILITAR"))
//					.collect(Collectors.toList()));
//			
//			model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Mensagem");
			
			return "/fragmentos/mensagem/novaMensagem";
		}
		
		System.out.println(mensagem);
//		System.out.println(listaUsuarios);
		
		return "redirect:/admin/mensagens/entrada";
		
	}

	@ResponseBody
	@RequestMapping(value = "/admin/mensagens/nova/usuarios/{listaGrupo}", method = { RequestMethod.GET })
	public List<UsuarioDTO> buscarUsuarios(@PathVariable("listaGrupo") List<Grupo> listaGrupo) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		
		List<UsuarioDTO> usuarios = new ArrayList<>();
				
		if(!listaGrupo.toString().equals("[null]")) {
			usuarios = servicoUsuario.buscarPorEstabelecimentosGrupo(user.getEstabelecimento(), listaGrupo).stream()
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
	@RequestMapping(value = "/admin/mensagens/nova/grupos/", method = { RequestMethod.GET })
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