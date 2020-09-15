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

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;


@Controller
public class EmailController {

	@Autowired
	private EmailService serviceEmail;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@GetMapping("*/historico/emails")
	public ModelAndView telaInicialEmails(ModelAndView mv) {
		
		mv.setViewName("fragmentos/historicos/historicoEmails");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));		
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Emails");

		return mv;
	}

	@RequestMapping(value = "**/emails/cadastro", method = RequestMethod.GET)
	public String cadastraEmail(Model mv) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);
		mv.addAttribute("email", new Email());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Email");
		
		return "fragmentos/cadastros/email";
	}
	
	@RequestMapping(value="**/emails/editar/{id}", method = RequestMethod.GET)
	public String editarEmail(@PathVariable("id") Long id, Model model) {
		Email emailBanco = serviceEmail.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("email", emailBanco);
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Email");
		
		return "fragmentos/cadastros/email";		
	}
	
	@RequestMapping(value = "**/cadastrarEmail", params = {"cadastrar"})
	public String cadastrarEmail(@Valid Email email, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/emails/cadastro";
		}
		serviceEmail.cadastrar(email);
		return "redirect:/admin/historico/emails";
	}
	
	@RequestMapping(value = "**/cadastrarEmail", params = {"atualizar"})
	public String atualizarEmail(@Valid Email email, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/emails/cadastro";
		}
		serviceEmail.alterar(email.getId(),email);
		return "redirect:/admin/historico/emails";
	}
	
	@ResponseBody
	@GetMapping("**/emails/consultar/{id}")
	public Email consultarEmail(@PathVariable("id") Long id, ModelAndView model) {
		return serviceEmail.buscaPorId(id).get();
	}

	@GetMapping(value="**/emails/status/{id}")
	public String modificarStatusEmail(@PathVariable("id") Long id, ModelAndView model) {
		Email emailBanco = serviceEmail.buscaPorId(id).get();
		
		if(emailBanco!=null) {
			emailBanco.setStatus(!emailBanco.isAtivo());
		}
		serviceEmail.alterar(id, emailBanco);
		return "redirect:/admin/historico/emails";
	}	
	
	@GetMapping(value="**/emails/excluir/{id}")
	public String excluirEmail(@PathVariable("id") Long id, ModelAndView model) {
	
		serviceEmail.deletar(id);
		return "redirect:/admin/historico/emails";
	}

	@ResponseBody
	@RequestMapping(value = "/emails/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Email> listPOST(DataTablesInput input) {		
		return serviceEmail.listarTodosEmails(input);
	}
}