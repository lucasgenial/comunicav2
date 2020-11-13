package br.com.cicom.comunicacicom.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.GrupoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.seguranca.UsuarioDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.MensagemDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Mensagem;
import br.com.cicom.comunicacicom.DSPrimary.model.sisMensagem.Notificacao;
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
	public String cadastrarMensagem(Model model, @ModelAttribute("novaMensagem") Mensagem mensagem, @RequestParam(value = "listaUsuario", required = false) List<Usuario> listaUsuarios, BindingResult result, RedirectAttributes redirAttrs) {
		
		if(listaUsuarios != null) {
			Notificacao notificacao = new Notificacao();
			
			for (Usuario destinatario : listaUsuarios) {
				
				notificacao.setDestinatario(destinatario);
				mensagem.getNotificacoes().add(notificacao);
				
			}
		}else {
			//redirAttrs.addAttribute("novaMensagem", mensagem);
			return "redirect:/admin/mensagens/nova";
		}
		
		System.out.println(mensagem);
		
		if (result.hasErrors()) {
			System.out.println(result);
			return "redirect:/admin/mensagens/nova";
		}
		
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