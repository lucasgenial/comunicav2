package br.com.cicom.comunicacicom.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Unidade;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class UnidadeController {

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private UsuarioService serviceUsuario;
	
	@Autowired
	private EmailService serviceEmail;

	@RequestMapping(value = "*/historico/unidades", method = RequestMethod.GET)
	public String telaInicialUnidade(Model model) {
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Unidades");
		
		return "fragmentos/historicos/historicoUnidades";
	}
	
	@RequestMapping(value = "**/unidades/cadastro/", method = RequestMethod.GET)
	public String telaCadastroUnidade(Model model) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);		
		
		Set<Estabelecimento> estabelecimentos = new HashSet<>();
		
		// Caso seja administrador carrega todas os estabelecimentos e unidades destes
		if (user.getGrupo().getNome().equalsIgnoreCase("ADMINISTRADOR")) {
			
			estabelecimentos.addAll(user.getEstabelecimento());
			model.addAttribute("unidade", new Unidade());
			model.addAttribute("estabelecimentos", estabelecimentos);
			model.addAttribute("instituicoes", new ArrayList<>());
			model.addAttribute("cidades", new ArrayList<>());
			
		} else {
			estabelecimentos.add(user.getServidor().getEstabelecimento());
			model.addAttribute("unidade", new Unidade());
			model.addAttribute("estabelecimentos", estabelecimentos);
			model.addAttribute("instituicoes", new ArrayList<>());
			model.addAttribute("cidades", new ArrayList<>());
		}
		
		model.addAttribute("edicao", false);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Unidade");
		
		return "fragmentos/cadastros/unidade";
	}
	
	@RequestMapping(value = "**/unidades/editar/{id}", method = RequestMethod.GET)
	public String telaEditarUnidade(@PathVariable("id") Long id, Model model) {
		Unidade unidadeBanco = serviceUnidade.buscaPorId(id).get();
			
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		
		// Caso seja administrador carrega todas os estabelecimentos e unidades destes
		if (user.getGrupo().getNome().equalsIgnoreCase("ADMINISTRADOR")) {
			
			model.addAttribute("estabelecimentos", user.getEstabelecimento());			
		} else {		
			model.addAttribute("estabelecimentos",user.getServidor().getEstabelecimento());
		}
		
		model.addAttribute("unidade", unidadeBanco);
		model.addAttribute("instituicoes", unidadeBanco.getEstabelecimento().getInstituicoes());
		model.addAttribute("cidades", unidadeBanco.getEstabelecimento().getCidades());
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Unidade");
		
		return "fragmentos/cadastros/unidade";
	}
	
	@RequestMapping(value = "**/cadastrarUnidade", params = { "cadastrar" })
	public String cadastrarUnidade(@Valid Unidade unidade, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/historico/unidades/cadastro/";
		}

		serviceUnidade.cadastrar(unidade);

		return "redirect:/admin/historico/unidades";
	}
	
	@RequestMapping(value = "**/cadastrarUnidade", params = { "atualizar" })
	public String atualizarUnidade(@Valid Unidade unidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/historico/unidades";
		}
		
		if (unidade.getEmail().getId() != null) {
			serviceEmail.alterar(unidade.getEmail().getId(), unidade.getEmail());
		}

		serviceUnidade.alterar(unidade.getId(), unidade);

		return "redirect:/admin/historico/unidades";
	}

	@ResponseBody
	@GetMapping("**/unidades/consultar/{id}")
	public Unidade consultarUnidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceUnidade.buscaPorId(id).get();

	}

	@ResponseBody
	@GetMapping("**/unidades/consultar/email/enderecoDeEmail/{id}")
	public Email consultarEmail(@PathVariable("id") Long id, ModelAndView model) {
		return serviceUnidade.buscaPorId(id).get().getEmail();
	}

	@ResponseBody
	@GetMapping("**/unidades/consultar/cidade/{id}")
	public Cidade consultarUnidadePorCidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceUnidade.buscaPorId(id).get().getCidade();
	}

	@ResponseBody
	@GetMapping("**/unidades/consultar/cidade/estado/{id}")
	public UnidadeFederativa consultarUnidadePorEstado(@PathVariable("id") Long id, ModelAndView model) {
		return serviceUnidade.buscaPorId(id).get().getCidade().getUf();
	}

	@GetMapping(value = "**/unidades/status/{id}")
	public String modificarStatusUnidade(@PathVariable("id") Long id, ModelAndView model) {
		Unidade unidadeBanco = serviceUnidade.buscaPorId(id).get();

		if (unidadeBanco != null) {
			unidadeBanco.setAtivo(!unidadeBanco.isAtivo());
		}

		serviceUnidade.alterar(id, unidadeBanco);

		return "redirect:/admin/historico/unidades";
	}

	@GetMapping(value = "**/unidades/excluir/{id}")
	public String excluirUnidade(@PathVariable("id") Long id, ModelAndView model) {

		serviceUnidade.deletar(id);
		
		return "redirect:/admin/historico/unidades";
	}
	
	@ResponseBody
	@RequestMapping(value = "/unidades/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })	
	public DataTablesOutput<Unidade> listPOST(DataTablesInput input) {
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());	
		return serviceUnidade.listarTodasUnidades(input, user);
	
	}
}