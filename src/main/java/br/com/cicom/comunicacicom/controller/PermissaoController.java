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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.POJO.Aviso;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.PermissaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class PermissaoController {

	@Autowired
	private PermissaoService servicePermissao;
	
	@Autowired
	private GrupoService serviceGrupo;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@GetMapping(value = {"*/historico/permissoes","*/historico/permissoes/{ok}"})
	public ModelAndView telaInicialPermissao(@PathVariable(required = false, name ="ok") Boolean ok) {
		ModelAndView mv = new ModelAndView();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		mv.setViewName("fragmentos/historicos/historicoPermissoes");
		mv.addObject("usuario", user);
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Permissoes");
		mv.addObject("permissoes", servicePermissao.listarTodos());
		mv.addObject("grupo", new Grupo());
		mv.addObject("grupos", serviceGrupo.listarTodos());
		Aviso.criaAviso().mensagemCasoDeuCerto("Grupo alterado com sucesso")
						 .mensagemCasoDeuErrado("Houve um erro na alteração do grupo")
						 .linkCasoDeuCerto("/admin/historico/permissoes")
						 .linkCasoDeuErrado("/admin/historico/permissoes")
						 .booleanoQueVerificaSeDeuCerto(ok)
						 .finalizaMensagem(mv);
		return mv;
	}
	
	@RequestMapping(value = "**/permissoes/cadastro", method = RequestMethod.GET)
	public String cadastraPermissao(Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("permissao", new Permissao());
		mv.addAttribute("edicao", false);
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Permissao");
		
		return "fragmentos/cadastros/permissao";		
	}	
	
	@RequestMapping(value = "**/permissoes/editar/{id}", method = RequestMethod.GET)
	public String editarPermissao(@PathVariable("id") Long id, Model model) {
		
		Permissao permissaoBanco = servicePermissao.buscaPorId(id).get();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);			
		model.addAttribute("permissao", permissaoBanco);
		model.addAttribute("edicao", true);
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Permissao");
		
		return "fragmentos/cadastros/permissao";
	}			
	
	@RequestMapping(value = "**/cadastrarPermissao", params = {"cadastrar"})
	public String cadastrarPermissao(@Valid Permissao permissao, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/permissoes/cadastro";
		}
		servicePermissao.cadastrar(permissao);
		return "redirect:/admin/historico/permissoes";
	}
	
	@RequestMapping(value = "**/cadastrarPermissao", params = {"atualizar"})
	public String atualizarPermissao(@Valid Permissao permissao, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/permissoes/cadastro";
		}
		servicePermissao.alterar(permissao.getId(),permissao);
			return "redirect:/admin/historico/permissoes";
	}
	
	@ResponseBody
	@GetMapping("**/permissoes/consultar/{id}")
	public Permissao consultarPermissao(@PathVariable("id") Long id, ModelAndView model) {
		return servicePermissao.buscaPorId(id).get();
	}

	@PostMapping(value="**/permissoes/editar/{id}")
	public String editarPermissao(@PathVariable("id") Long id,@Valid Permissao permissao, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/historico/permissoes";
		}
		
		servicePermissao.alterar(id,permissao);
		return "redirect:/admin/historico/permissoes";
	}
	
	@GetMapping(value="**/permissoes/status/{id}")
	public String modificarStatusPermissao(@PathVariable("id") Long id, ModelAndView model) {
		Permissao permissaoBanco = servicePermissao.buscaPorId(id).get();
		
		if(permissaoBanco!=null) {
			permissaoBanco.setStatus(!permissaoBanco.isAtivo());
		}
		servicePermissao.alterar(id, permissaoBanco);
		return "redirect:/admin/historico/permissoes";
	}
	
	@GetMapping(value="**/permissoes/excluir/{id}")
	public String excluirPermissao(@PathVariable("id") Long id, ModelAndView model) {
		servicePermissao.deletar(id);
		return "redirect:/admin/historico/permissoes";
	}
	
	@ResponseBody
	@GetMapping("*/buscar/permissoes/listarTodas")
	public List<Permissao> consultarPermissoes() {
		return servicePermissao.listarTodos();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/permissoes/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Permissao> listPOST(DataTablesInput input) {		
		return servicePermissao.listarTodasPermissoes(input);
	}
}