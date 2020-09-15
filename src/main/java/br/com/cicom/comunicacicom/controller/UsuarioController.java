package br.com.cicom.comunicacicom.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.BairroService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

/**
 * @author Delivelton
 *
 */
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private GrupoService servicoGrupo;

	@SuppressWarnings("unused")
	@Autowired
	private BairroService serviceBairro;

	@SuppressWarnings("unused")
	@Autowired
	private CidadeService serviceCidade;

	@Autowired
	private EmailService serviceEmail;

	@Autowired
	private ServidorService serviceServidor;

  	
  	@GetMapping("*/historico/usuarios")
	public String telaHistorico(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuarioEditar", new Usuario());		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		if(user.getGrupo().getNome().equals("ADMINISTRADOR")) {
			model.addAttribute("usuarios",servicoUsuario.listarTodos());
			model.addAttribute("grupos",servicoGrupo.listarTodos());
		}else {
			model.addAttribute("usuarios",servicoUsuario.buscarPorEstabelecimento(user.getServidor().getEstabelecimento()));
			model.addAttribute("grupos",servicoGrupo.listarTodos().subList(user.getGrupo().getId().intValue(), servicoGrupo.listarTodos().size()-2));
		}
		
		// Titulo da Página
			model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Usuários");
			
		return "fragmentos/historicos/historicoUsuarios";
	}

	@ResponseBody
	@GetMapping("**/usuarios/consultar/{id}")
	public Usuario consultarUsuario(@PathVariable("id") Long id, ModelAndView model) {
		return servicoUsuario.buscaPorId(id).get();
	}

	@PostMapping(value = "**/usuarios/editarGrupo/{id}")
	public String editarUsuario(@PathVariable("id") Long id, Usuario editarUsuario, ModelAndView model) {

		Usuario user = servicoUsuario.buscaPorId(id).get();
		user.setGrupo(editarUsuario.getGrupo());

		if (user != null) {
			user.setStatus(!user.isAtivo());
		}
		servicoUsuario.alterarGrupo(id, user);
		return "redirect:/admin/historico/usuarios";
	}

	@GetMapping("*/cadastraUsuario")
	public String cadastrarUsuario(@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/historico/usuarios";
		}
		servicoUsuario.cadastrar(usuario);
		return "redirect:/admin/historico/usuarios";
	}

	@GetMapping(value = "**/usuarios/status/{id}")
	public String modificarStatusUsuario(@PathVariable("id") Long id, ModelAndView model) {
		Usuario usuarioBanco = servicoUsuario.buscaPorId(id).get();

		if (usuarioBanco != null) {
			usuarioBanco.setStatus(!usuarioBanco.isAtivo());
		}
		servicoUsuario.alterar(id, usuarioBanco);
		return "redirect:/admin/historico/usuarios";
	}

	@GetMapping(value = "**/usuarios/excluir/{id}")
	public String excluirUsuario(@PathVariable("id") Long id, ModelAndView model) {
		
		serviceServidor.deletar(id);
		serviceEmail.deletar(id);
		servicoUsuario.deletar(id);
		
		return "redirect:/admin/historico/usuarios";
	}

	@PostMapping(value = "**/usuarios/editar/{id}")
	public String editarUsuario(@PathVariable("id") Long id, @Valid Usuario usuario, BindingResult result,Model model) {
		if (result.hasErrors()) {
			return "redirect:/admin/historico/usuarios";
		}
		servicoUsuario.alterar(id, usuario);
		// Titulo da Página
				model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Usuário");
		return "redirect:/admin/historico/usuarios";
	}
	
	/**
	 ** IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	@ResponseBody
	@RequestMapping(value = "/usuarios/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Usuario> listPOST(DataTablesInput input) {			
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());		
		return servicoUsuario.listarTodosUsuarios(input, user);
	}
}