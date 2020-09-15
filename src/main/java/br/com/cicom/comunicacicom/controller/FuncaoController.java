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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Funcao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class FuncaoController {

	@Autowired
	private FuncaoService serviceFuncao;

	@Autowired
	private UsuarioService serviceUsuario;

	@GetMapping("*/historico/funcoes")
	public ModelAndView telaInicialFuncao(ModelAndView mv) {
		
		mv.setViewName("fragmentos/historicos/historicoFuncoes");
		mv.addObject("usuario",	serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Funções");
				
		return mv;
	}
	
	@RequestMapping(value = "**/funcoes/cadastro", method = RequestMethod.GET)
	public String cadastraFuncao(Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("funcao", new Funcao());
		mv.addAttribute("prioridade", user.getServidor().getFuncao().isPrioridade());
		mv.addAttribute("tipo",user.getServidor().getFuncao().getTipoFuncao());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Funcao");
		
		return "fragmentos/cadastros/funcao";		
	}
	
	@RequestMapping(value = "**/funcoes/editar/{id}", method = RequestMethod.GET)
	public String editarFuncao(@PathVariable("id") Long id, Model model) {
		
		Funcao funcaoBanco = serviceFuncao.buscaPorId(id).get();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		model.addAttribute("funcao", funcaoBanco);
		model.addAttribute("tipoFuncao", user.getServidor().getFuncao().getTipoFuncao());
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Funcao");
		
		return "fragmentos/cadastros/funcao";
	}
	
	@RequestMapping(value = "**/cadastrarFuncao", params = {"cadastrar"})
	public String cadastrarFuncao(@Valid Funcao funcao, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/funcoes/cadastro";
		}

		serviceFuncao.cadastrar(funcao);
		return "redirect:/admin/historico/funcoes";
	}
	
	@RequestMapping(value = "**/cadastrarFuncao", params = {"atualizar"})
	public String atualizarFuncao(@Valid Funcao funcao, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/funcoes/cadastro";
		}

		serviceFuncao.alterar(funcao.getId(),funcao);
		return "redirect:/admin/historico/funcoes";
	}
	
	@ResponseBody
	@RequestMapping(value = "/funcoes/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Funcao> listPOST(DataTablesInput input) {		
		return serviceFuncao.listarTodasFuncoes(input);
	}
	
	@ResponseBody
	@GetMapping("**/funcao/consultar/{id}")
	public Funcao consultarFuncao(@PathVariable("id") Long id, ModelAndView model) {
		return serviceFuncao.buscaPorId(id).get();
	}

	@GetMapping(value = "**/funcoes/status/{id}")
	public String modificarStatusFuncao(@PathVariable("id") Long id, ModelAndView model) {

		Funcao funcaoBanco = serviceFuncao.buscaPorId(id).get();

		if (funcaoBanco != null) {
			funcaoBanco.setAtivo(!funcaoBanco.isAtivo());
		}
		serviceFuncao.alterar(id, funcaoBanco);
		return "redirect:/admin/historico/funcoes";
	}

	@GetMapping(value = "**/funcoes/excluir/{id}")
	public String excluirFuncao(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {

		try {
			serviceFuncao.deletar(id);
			redirectAttributes.addFlashAttribute("aviso", "Função excluída com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erros",
					"Existe um servidor com esta função. Para excluir a \"funçao\" a mesma não pode estar vinculada a nenhum servidor.");
		}
		
		return "redirect:/admin/historico/funcoes";
	}
}