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

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Recurso;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.RecursoService;

@Controller
public class RecursoController {	

	@Autowired
	private RecursoService serviceRecurso;
	
	@Autowired
	private UsuarioService serviceUsuario;	
		
	@GetMapping("*/historico/recursos")
	public ModelAndView telaInicialRecurso(ModelAndView mv) {	
		
		mv.setViewName("fragmentos/historicos/historicoRecursos");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Recursos");
		return mv;
	}
		
	@RequestMapping(value = "**/recursos/cadastro", method = RequestMethod.GET)
	public String cadastraRecurso(Model mv) {
		
		mv.addAttribute("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));	
		mv.addAttribute("recurso", new Recurso());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Recurso");
		
		return "fragmentos/cadastros/recurso";		
	}	
	
	@RequestMapping(value = "**/recursos/editar/{id}", method = RequestMethod.GET)
	public String editarRecurso(@PathVariable("id") Long id, Model mv) {
		
		Recurso recursoBanco = serviceRecurso.buscaPorId(id).get();
		
		mv.addAttribute("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));	
		mv.addAttribute("recurso", recursoBanco);
		mv.addAttribute("edicao", true);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Recurso");
		
		return "fragmentos/cadastros/recurso";	
	}
	
	@RequestMapping(value = "**/cadastrarRecurso", params = {"cadastrar"})
	public String cadastrarRecurso(@Valid Recurso recurso, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/recursos/cadastro";
		}
		serviceRecurso.cadastrar(recurso);
		return "redirect:/admin/historico/recursos";
	}
	
	@RequestMapping(value = "**/cadastrarRecurso", params = {"atualizar"})
	public String atualizarRecurso(@Valid Recurso recurso, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/recursos/cadastro";
		}
		serviceRecurso.alterar(recurso.getId(),recurso);
		return "redirect:/admin/historico/recursos";
	}
	
	@ResponseBody
	@RequestMapping(value = "/recursos/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Recurso> listPOST(DataTablesInput input) {	
		return serviceRecurso.listarTodosRecursos(input);
	}	
	
	@ResponseBody
	@GetMapping("**/recurso/consultar/{id}")
	public Recurso consultarRecurso(@PathVariable("id") Long id, ModelAndView model) {
		return serviceRecurso.buscaPorId(id).get();
	}
	
	@GetMapping(value="**/recursos/status/{id}")
	public String modificarStatusRecurso(@PathVariable("id") Long id, ModelAndView model) {
		Recurso recursoBanco = serviceRecurso.buscaPorId(id).get();
		
		if(recursoBanco!=null) {
			recursoBanco.setAtivo(!recursoBanco.isAtivo());
		}
		serviceRecurso.alterar(id, recursoBanco);
		return "redirect:/admin/historico/recursos";
	}
	
	@GetMapping(value="**/recursos/excluir/{id}")
	public String excluirRecurso(@PathVariable("id") Long id, ModelAndView model) {
		serviceRecurso.deletar(id);
		return "redirect:/admin/historico/recursos";
	}
}