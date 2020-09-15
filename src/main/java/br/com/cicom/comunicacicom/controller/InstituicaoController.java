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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;

@Controller
public class InstituicaoController {

	@Autowired
	private InstituicaoService serviceInstituicao;
		
	@Autowired
	private UsuarioService serviceUsuario;
	
		
	@GetMapping("*/historico/instituicoes")
	public ModelAndView telaInicialInstituicao(ModelAndView mv) {
		mv.setViewName("fragmentos/historicos/historicoInstituicoes");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Instituicoes");

		return mv;
	}
		 
	@RequestMapping(value = "**/instituicoes/cadastro", method = RequestMethod.GET)
	public String cadastraBairro(Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("instituicao", new Instituicao());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Instituicao");
		
		return "fragmentos/cadastros/instituicao";	
	
	}
	 
	 @RequestMapping(value="**/instituicoes/editar/{id}", method = RequestMethod.GET)
	 public String editarInstituicao(@PathVariable("id") Long id, Model mv) {
		 
			Instituicao instituicaoBanco = serviceInstituicao.buscaPorId(id).get();
			Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			
			mv.addAttribute("usuario", user);
			mv.addAttribute("instituicao", instituicaoBanco);
			mv.addAttribute("edicao", true);
			mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Instituicao");
			
			return "fragmentos/cadastros/instituicao";
		}
	 
	 @RequestMapping(value = "**/cadastrarInstituicao", params = {"cadastrar"})
	 public String cadastrarInstituicao(@Valid Instituicao instituicao, BindingResult result) {
			if (result.hasErrors()) {
				return "redirect:/admin/cadastros/instituicoes/cadastro";
			}
			serviceInstituicao.cadastrar(instituicao);
			return "redirect:/admin/cadastros/instituicoes";
		}
	 
	 @RequestMapping(value = "**/cadastrarInstituicao", params = {"atualizar"})
	 public String atualizarInstituicao(@Valid Instituicao instituicao, BindingResult result) {
			if (result.hasErrors()) {
				return "redirect:/admin/cadastros/instituicoes/cadastro";
			}
			serviceInstituicao.alterar(instituicao.getId(),instituicao);
			return "redirect:/admin/cadastros/instituicoes";
		}
	 
	 @ResponseBody
	 @RequestMapping(value = "/instituicoes/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	 public DataTablesOutput<Instituicao> listPOST(DataTablesInput input) {	
			return serviceInstituicao.listarTodasInstituicoes(input);
		}
	 	
	@ResponseBody
	@GetMapping("**/instituicoes/hierarquias/{id}")	
	public List<Hierarquia> consultarHierarquias(@PathVariable("id") Long id, ModelAndView model) {
		return serviceInstituicao.buscaPorId(id).get().getHierarquias();
	}
	
	@ResponseBody
	@GetMapping("**/instituicoes/consultar/{id}")
	public Instituicao consultarInstituicao(@PathVariable("id") Long id, ModelAndView model) {
		return serviceInstituicao.buscaPorId(id).get();
	}
	
	@GetMapping(value="**/instituicoes/status/{id}")
	public String modificarStatusInstituicao(@PathVariable("id") Long id, ModelAndView model) {
		Instituicao instituicaoBanco = serviceInstituicao.buscaPorId(id).get();
		
		if(instituicaoBanco!=null) {
			instituicaoBanco.setAtivo(!instituicaoBanco.isAtivo());
		}
		
		serviceInstituicao.alterar(id, instituicaoBanco);
		
		return "redirect:/admin/configuracao/instituicoes";
	}
	
	@GetMapping(value="**/instituicoes/excluir/{id}")
	public String excluirInstituicao(@PathVariable("id") Long id, ModelAndView model) {
		
		serviceInstituicao.deletar(id);
		
		return "redirect:/admin/configuracao/instituicoes";
	}
}
