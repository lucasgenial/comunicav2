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

import br.com.cicom.comunicacicom.DSPrimary.model.agendaTelefonica.AgendaTelefonica;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.agendaTelefonica.AgendaTelefonicaService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class AgendaTelefonicaController {
	
	@Autowired
	private AgendaTelefonicaService serviceAgenda;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@GetMapping("*/historico/telefones")
	public ModelAndView telaInicialTelefone(ModelAndView mv) {		
		
		mv.setViewName("fragmentos/historicos/historicoTelefones");
		mv.addObject("usuario",	serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Telefones");
		
		return mv;
	}
	
	@GetMapping("*/historico/menu")
	public ModelAndView telaInicialAgendaTelefonica(ModelAndView mv) {		
		
		mv.setViewName("fragmentos/menus/menuAgendaTelefonica");
		mv.addObject("usuario",	serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Telefones");
		
		return mv;
	}
	
	@RequestMapping(value = "**/telefones/cadastro", method = RequestMethod.GET)
	public String cadastraTelefone(Model mv, RedirectAttributes redirectAttributes) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		mv.addAttribute("usuario", user);
		mv.addAttribute("telefone", new AgendaTelefonica()); 
		redirectAttributes.addFlashAttribute("erros","Verifique erros");
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Telefone");
		
		return "fragmentos/cadastros/telefone";
	}
	
	@RequestMapping(value = "**/telefones/editar/{id}", method = RequestMethod.GET)
	public String editarTelefone(@PathVariable("id") Long id, Model mv) {
		
		AgendaTelefonica agendaBanco = serviceAgenda.buscaPorId(id).get();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);
		mv.addAttribute("telefone", agendaBanco); 
		mv.addAttribute("edicao", true);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Telefone");
		
		return "fragmentos/cadastros/telefone";
	}
	
	@RequestMapping(value = "**/cadastrarTelefone", params = {"cadastrar"})
	public String cadastrarTelefone(@Valid AgendaTelefonica telefone, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/telefones/cadastro";
		}
		serviceAgenda.cadastrar(telefone);
		return "redirect:/admin/historico/telefones";
	}
	
	@RequestMapping(value = "**/cadastrarTelefone", params = {"atualizar"})
	public String atualizarTelefone(@Valid AgendaTelefonica telefone, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/telefones/cadastro";
		}
		serviceAgenda.alterar(telefone.getId(), telefone);
		return "redirect:/admin/historico/telefones";
	}
	
	@ResponseBody
	@RequestMapping(value = "/telefones/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<AgendaTelefonica> listPOST(DataTablesInput input) {	
		return serviceAgenda.listarTodosTelefones(input);
	}

}
