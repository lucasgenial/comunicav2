package br.com.cicom.comunicacicom.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.POJO.Aviso;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.PermissaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class GrupoController {

	@Autowired
	private GrupoService serviceGrupo;
	
	@Autowired
	private PermissaoService servicePermissao;
	
	@Autowired
	private UsuarioService serviceUsuario;
		
	@Autowired
	private UsuarioService servicoUsuario;

	private Grupo grupo;
	private List<Permissao> permissoes;
	private List<Permissao> listapermissoes;
	
	
	@GetMapping(value = {"*/historico/grupos","*/historico/grupos/{ok}"})
	public ModelAndView telaInicialGrupo(ModelAndView model,@PathVariable(required = false, name ="ok") Boolean ok) {
		
		model.setViewName("fragmentos/historicos/historicoGrupos");
		model.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addObject("tituloPagina", "ComunicaCICOM - Relação de Grupos");
		Aviso.criaAviso().mensagemCasoDeuCerto("Grupo cadastrado com sucesso").mensagemCasoDeuErrado("Houve um erro no cadastro do grupo").linkCasoDeuCerto("/admin/historico/grupos").linkCasoDeuErrado("/admin/historico/grupos").booleanoQueVerificaSeDeuCerto(ok).finalizaMensagem(model);
		return model;
	}
	
	
	@GetMapping("*/edita/grupo/{id}")
	public ModelAndView cadastraGrupo(ModelAndView model, @PathVariable("id") Long id) {
		
		model.setViewName("fragmentos/edicoes/editaGrupo");
		model.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addObject("tituloPagina", "ComunicaCICOM - Novo de Grupo");
		model.addObject("grupo", serviceGrupo.buscaPorId(id).get());
		return model;
	}
	
	@GetMapping("*/cadastra/grupo")
	public ModelAndView cadastraGrupo(ModelAndView model) {
		
		model.setViewName("fragmentos/cadastros/grupo");
		model.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addObject("tituloPagina", "ComunicaCICOM - Novo de Grupo");
		model.addObject("grupo", new Grupo());
				
		return model;
	}
	
	@RequestMapping(value = "**/grupos/consultar/{id}", method = RequestMethod.GET)
	public String consultarPermissao(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		grupo = serviceGrupo.buscaPorId(id).get();
		grupo.setPermissoes(serviceGrupo.listaPermissoesdoGrupo(grupo.getId()));
		permissoes = grupo.getPermissoes();
		listapermissoes = servicePermissao.listarTodos();
		listapermissoes.removeAll(grupo.getPermissoes());
		model.addAttribute("grupo", grupo);
		model.addAttribute("permissoes", permissoes);
		model.addAttribute("listapermissoes", listapermissoes);
		model.addAttribute("permissao", new Permissao());
		return "fragmentos/cadastros/grupo";
	}

	
	
	@RequestMapping(value="**/admin/salva/edicao/grupoPermissao")
	public String salvarGrupoPermissao(Grupo grupo, Model model) {
	
		try {
			Grupo grupoBanco = serviceGrupo.buscaPorId(grupo.getId()).get();
			grupoBanco.setPermissoes(grupo.getPermissoes());
			serviceGrupo.alterar(grupo.getId(), grupoBanco);
		} catch (Exception e) {

			return "redirect:/admin/historico/permissoes/0";
		}
		
		return "redirect:/admin/historico/permissoes/1";
	}
	
	
	@RequestMapping(value="**/grupos/excluirpermissao/{id}", method = RequestMethod.GET)
	public String excluirGrupoPermissao(@PathVariable("id") int id, Model model, RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		if(!grupo.getPermissoes().isEmpty()) {
			permissoes.remove(servicePermissao.buscaPorId(Long.valueOf(id)).get());
			grupo.setPermissoes(permissoes);
			serviceGrupo.alterar(grupo.getId(),grupo);
		}
		return "redirect:/admin/grupos/consultar/"+grupo.getId();
	}

	@PostMapping(value="**/grupos/adicionarPermissao")
	public String adiconarGrupoPermissao(Permissao permissao, Model model, RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		redirect.addFlashAttribute("usuario", user);
		
		if(grupo != null) {
			if(permissao.getId()!=null) {
				permissao = servicePermissao.buscaPorId(permissao.getId()).get();	
			}
			grupo.getPermissoes().add(permissao);
		}
		
		serviceGrupo.alterar(grupo.getId(),grupo);
		
		return "redirect:/admin/grupos/consultar/"+grupo.getId();
	}

	@ResponseBody
	@GetMapping("**/grupo/listarPermissoes/{id}")
	public List<Permissao> consultarPermissoesDesseGrupo(@PathVariable("id") Long id) {
		return serviceGrupo.buscaPorId(id).get().getPermissoes();
	}

	@ResponseBody
	@GetMapping("**/grupo/listarOsIdsDasPermissoes/{id}")
	public Long[] listarIdsDasPermissoesDesseGrupo(@PathVariable("id") Long id) {
		return servicePermissao.listarIdsDePermissoesDeUmGrupo(id);
		
	}
	
	@PostMapping(value="*/salva/edicao/grupo")
	public String editarGrupo(@Valid Grupo grupo, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/historico/grupos";
		}
		grupo.setPermissoes(serviceGrupo.listaPermissoesdoGrupo(grupo.getId()));
		serviceGrupo.alterar(grupo.getId(),grupo);
		return "redirect:/admin/historico/grupos";
	}
	
	@PostMapping("*/salva/cadastro/grupo")
	public String cadastrarGrupo(@Valid Grupo grupo, BindingResult result, @RequestParam(required = false, value = "url") String url) {
		try {
			serviceGrupo.cadastrar(grupo);
		} catch (Exception e) {
			
			return "redirect:"+url+"/0";
		}
		return "redirect:"+url+"/1";
	}

	@GetMapping(value="**/grupos/status/{id}")
	public String modificarStatusGrupo(@PathVariable("id") Long id, ModelAndView model) {
		Grupo grupoBanco = serviceGrupo.buscaPorId(id).get();
		
		if(grupoBanco!=null) {
			grupoBanco.setStatus(!grupoBanco.isAtivo());
		}
		serviceGrupo.alterar(id, grupoBanco);
		return "redirect:/admin/historico/grupos";
	}
	
	@GetMapping(value="**/grupos/excluir/{id}")
	public String excluirGrupo(@PathVariable("id") Long id, ModelAndView model) {
		Grupo grupo = serviceGrupo.buscaPorId(id).get();
		grupo.getPermissoes().removeAll(grupo.getPermissoes());
		serviceGrupo.deletar(id);
		return "redirect:/admin/historico/grupos";
	}

	@PostMapping(value="**/grupos/alterarGrupo/{id}")
	public String editarUsuario(@PathVariable("id") Long id,Usuario editarUsuario, ModelAndView model) {
				
		 Usuario user = serviceUsuario.buscaPorId(id).get();
		 user.setGrupo(editarUsuario.getGrupo());
		 
		serviceUsuario.alterarGrupo(id, user);
		return "redirect:/admin/historico/usuarios";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/grupos/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Grupo> listPOST(DataTablesInput input) {		
		return serviceGrupo.listarTodosGrupos(input);
	}

}