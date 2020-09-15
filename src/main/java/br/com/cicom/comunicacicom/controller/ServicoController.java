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

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Servico;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.ServicoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class ServicoController {
	
	@Autowired
	private ServicoService serviceServico;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@GetMapping("*/historico/servicos")
	public ModelAndView telaHistorico(ModelAndView mv) {
				
		mv.setViewName("fragmentos/historicos/historicoServicos");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Serviços");
		
		return mv;
	}
	
	@RequestMapping(value = "**/servicos/cadastro", method = RequestMethod.GET)
	public String cadastraServico(Model model) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("servico", new Servico());
		model.addAttribute("edicao", false);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servico");

		return "fragmentos/cadastros/servico";
	}
	
	@RequestMapping(value = "**/servicos/editar/{id}", method = RequestMethod.GET)
	public String editarServico(@PathVariable("id") Long id, Model model) {
		
		Servico servicoBanco = serviceServico.buscaPorId(id).get();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario",user);
		model.addAttribute("servico", servicoBanco);
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servico");

		return "fragmentos/cadastros/servico";
	}
	
	@RequestMapping(value = "**/cadastrarServico", params = {"cadastrar"})
	public String cadastrarServico(@Valid Servico servico, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/servicos/cadastro";
		}
		serviceServico.cadastrar(servico);
		return "redirect:/admin/historico/servicos";
	}
	
	@RequestMapping(value = "**/cadastrarServico", params = {"atualizar"})
	public String atualizaServico(@Valid Servico servico, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/servicos/cadastro";
		}

		serviceServico.alterar(servico.getId(),servico);
		return "redirect:/admin/historico/servicos";
	}
	
	@GetMapping(value = "**/servicos/excluir/{id}")
	public String excluirServico(@PathVariable("id") Long id, ModelAndView model) {
		serviceServico.deletar(id);
		return "redirect:/admin/historico/servicos";
	}
	
	@ResponseBody
	@RequestMapping(value = "/servicos/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Servico> listPOST(DataTablesInput input) {	
		return serviceServico.listarTodosServicos(input);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/servicos/buscar/todos", method = { RequestMethod.POST,RequestMethod.GET })
	public List<Servico> buscarTodos() {	
		return serviceServico.listarTodos();
	}
	
	@GetMapping(value = "**/servicos/status/{id}")
	public String modificarStatusServico(@PathVariable("id") Long id, ModelAndView model) {
		Servico servicoBanco = serviceServico.buscaPorId(id).get();

		if (servicoBanco != null) {
			servicoBanco.setStatus(!servicoBanco.isAtivo());
		}
		serviceServico.alterar(id, servicoBanco);
		return "redirect:/admin/historico/servicos";
	}	
}
