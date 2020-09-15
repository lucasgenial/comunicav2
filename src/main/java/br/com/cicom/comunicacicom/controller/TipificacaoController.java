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

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class TipificacaoController {

	@Autowired
	private TipificacaoService service;

	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private CategoriaService serviceCategoria;
	

	@GetMapping("*/historico/tipificacoes")
	public ModelAndView telaInicialTipificacao(ModelAndView mv) {
	
		mv.setViewName("fragmentos/historicos/historicoTipificacoes");		
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Tipificacoes");

		return mv;
	}
	
	@RequestMapping(value = "**/tipificacoes/cadastro", method = RequestMethod.GET)
	public String cadastraTipificacao(Model mv,RedirectAttributes redirectAttributes) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("categorias", serviceCategoria.listarTodos());
		mv.addAttribute("tipificacao", new Tipificacao());
		redirectAttributes.addFlashAttribute("erros","Verifique erros");
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Tipificacao");
		
		return "fragmentos/cadastros/tipificacao";		
	}
	
	@RequestMapping(value = "**/tipificacoes/editar/{id}", method = RequestMethod.GET)
	public String editarTipificacao(@PathVariable("id") Long id, Model model) {
		
		Tipificacao tipificacaoBanco = service.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("categorias", serviceCategoria.listarTodos());
		model.addAttribute("tipificacao",tipificacaoBanco);
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Tipificacao");
		
		return "fragmentos/cadastros/tipificacao";
	}
	
	@RequestMapping(value = "**/cadastrarTipificacao", params = {"cadastrar"})
	public String cadastrarTipificacao(@Valid Tipificacao tipificacao, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/tipificacoes/cadastro";
		}

		service.cadastrar(tipificacao);
		return "redirect:/admin/historico/tipificacoes";
	}
	
	@RequestMapping(value = "**/cadastrarTipificacao", params = {"atualizar"})
	public String atualizarTipificacao(@Valid Tipificacao tipificacao, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/tipificacoes/cadastro";
		}

		service.alterar(tipificacao.getId(),tipificacao);
		return "redirect:/admin/historico/tipificacoes";
	}

	@ResponseBody
	@GetMapping("**/tipificacoes/consultar/{id}")
	public Tipificacao consultarTipificacao(@PathVariable("id") Long id, ModelAndView model) {
		return service.buscaPorId(id).get();
	}

	@GetMapping(value = "**/tipificacoes/status/{id}")
	public String modificarStatusTipificacao(@PathVariable("id") Long id, ModelAndView model) {
		Tipificacao tipificacaoBanco = service.buscaPorId(id).get();

		if (tipificacaoBanco != null) {
			tipificacaoBanco.setStatus(!tipificacaoBanco.isAtivo());
		}
		
		service.alterar(id, tipificacaoBanco);
		return "redirect:/admin/historico/tipificacoes";
	}

	@GetMapping(value = "**/tipificacoes/excluir/{id}")
	public String excluirTipificacao(@PathVariable("id") Long id, ModelAndView model) {
		service.deletar(id);
		return "redirect:/admin/historico/tipificacoes";
	}
	
	@ResponseBody
	@RequestMapping(value = "/tipificacoes/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Tipificacao> listPOST(DataTablesInput input) {	
		return service.listarTodasTipificacoes(input);
	}
}