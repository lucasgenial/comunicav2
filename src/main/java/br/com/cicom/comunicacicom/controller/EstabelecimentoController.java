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
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;

@Controller
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService serviceEstabelecimento;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	
	@RequestMapping(value = "*/historico/estabelecimentos", method = RequestMethod.GET)
	public String telaHistorico(Model mv) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);
		
		// Titulo da Página		
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Estabelecimentos");
		
		return "fragmentos/historicos/historicoEstabelecimentos";
	}
	
	@RequestMapping(value = "**/estabelecimentos/cadastro", method = RequestMethod.GET)
	public String cadastraEstabelecimento(Model mv) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);		
		mv.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
		mv.addAttribute("enderecoDeEmail",new Email());
		mv.addAttribute("edicao", false);
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Estabelecimento");
		
		return "fragmentos/cadastros/estabelecimento";	
	
}
	
	@RequestMapping(value = "**/estabelecimentos/editar/{id}", method = RequestMethod.GET)
	public String editarEstabelecimento(@PathVariable("id") Long id, Model mv) {
		
		Estabelecimento estabelecimentoBanco = serviceEstabelecimento.buscaPorId(id).get();
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());		
	
		mv.addAttribute("usuario", user);
		mv.addAttribute("estabelecimento", estabelecimentoBanco);		
		mv.addAttribute("edicao", true);
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Estabelecimento");
		
		return "fragmentos/cadastros/estabelecimento";
		
	}
	
	
	@RequestMapping(value = "**/cadastrarEstabelecimento", params = {"cadastrar"})
	public String cadastrarEstabelecimento(@Valid Estabelecimento estabelecimento, BindingResult result) {
		
		if (result.hasErrors()) {

			return "redirect:/admin/cadastros/estabelecimentos/cadastro";
		}
		serviceEstabelecimento.cadastrar(estabelecimento);
		return "redirect:/admin/historico/estabelecimentos";
	}
	
	@RequestMapping(value = "**/cadastrarEstabelecimento", params = {"atualizar"})
	public String atualizarEstabelecimento(@Valid Estabelecimento estabelecimento, BindingResult result) {
		
		if (result.hasErrors()) {

			return "redirect:/admin/cadastros/estabelecimentos/cadastro";
		}
		serviceEstabelecimento.alterar(estabelecimento.getId(), estabelecimento);
		return "redirect:/admin/historico/estabelecimentos";
	}
	

	@ResponseBody
	@GetMapping("**/estabelecimentos/consultar/{id}")
	public Estabelecimento consultarEstabelecimento(@PathVariable("id") Long id, ModelAndView model) {
		return serviceEstabelecimento.buscaPorId(id).get();
	}

	@ResponseBody
	@GetMapping("**/estabelecimentos/pegarEmail/{id}")
	public String pegarEmail(@PathVariable("id") Long id, ModelAndView model) {
		return serviceEstabelecimento.pegarEmail(id);
	}

	@ResponseBody
	@GetMapping("**/estabelecimentos/pegarSenhaEmail/{id}")
	public String pegarSenhaEmail(@PathVariable("id") Long id, ModelAndView model) {
		return serviceEstabelecimento.pegarSenhaEmail(id);
	}



	@GetMapping(value = "**/estabelecimentos/status/{id}")
	public String modificarStatusestabelecimento(@PathVariable("id") Long id, ModelAndView model) {
		Estabelecimento estabelecimentoBanco = serviceEstabelecimento.buscaPorId(id).get();

		if (estabelecimentoBanco != null) {
			estabelecimentoBanco.setStatus(!estabelecimentoBanco.isAtivo());
		}
		serviceEstabelecimento.alterar(id, estabelecimentoBanco);
		return "redirect:/admin/historico/estabelecimentos";
	}

	@GetMapping(value = "**/estabelecimentos/excluir/{id}")
	public String excluirEstabelecimento(@PathVariable("id") Long id, ModelAndView model) {

		serviceEstabelecimento.deletar(id);
		return "redirect:/admin/historico/estabelecimentos";
	}
	
	/**
	 ** IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	
	@ResponseBody
	@RequestMapping(value = "/estabelecimentos/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Estabelecimento> listPOST(DataTablesInput input) {	
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		return serviceEstabelecimento.listarTodosEstabelecimentos(input, user);
	}
}
