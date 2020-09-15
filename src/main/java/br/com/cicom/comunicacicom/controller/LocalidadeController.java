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

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.BairroService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.LocalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class LocalidadeController {
	
	@Autowired
	private UsuarioService servicoUsuario;
		
	@Autowired
	private LocalidadeService serviceLocalidade;
	
	@Autowired
	private BairroService serviceBairro;
	
	@GetMapping("*/historico/localidades")
	public String telainicialLocalidade(Model mv) {
		//Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());	
		mv.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Localidades");
		return "fragmentos/historicos/historicoLocalidades";
	}

	
	@RequestMapping(value = "**/localidades/cadastro", method = RequestMethod.GET)
	public String cadastrarLocalidade(Model mv) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);
		mv.addAttribute("localidade", new Localidade());
		mv.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
		mv.addAttribute("edicao", false);
		
		// Titulo da Página
		
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Localidade");
		
		return "fragmentos/cadastros/localidade";
	}
	
	
	@RequestMapping(value="**/localidades/editar/{id}", method = RequestMethod.GET)
	public String editarLocalidade(@PathVariable("id") Long id, Model model) {
		
		Localidade localidadeBanco =  serviceLocalidade.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);				
		model.addAttribute("localidade", localidadeBanco);
		model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
		model.addAttribute("edicao", true);		
			
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Localidade");
		
		return "fragmentos/cadastros/localidade";
	}
	
	@RequestMapping(value = "**/cadastrarLocalidade", params = {"cadastrar"})
	public String cadastrarLocalidade(@Valid Localidade localidade, BindingResult result) {
		
		if (result.hasErrors()) {

			return "redirect:/admin/cadastros/localidades/cadastro";
		}
		serviceLocalidade.cadastrar(localidade);
		return "redirect:/admin/cadastros/localidades/historico";
	}
	
	@RequestMapping(value = "**/cadastrarLocalidade", params = {"atualizar"})
	public String atualizarLocalidade(@Valid Localidade localidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/localidades/cadastro";
		}
		serviceLocalidade.alterar(localidade.getId(), localidade);
		return "redirect:/admin/historico/localidades";
	}
	
	@ResponseBody
	@GetMapping("**/localidades/consultar/{id}")
	public Localidade consultarLocalidades(@PathVariable("id") Long id, ModelAndView model) {
		return serviceLocalidade.buscaPorId(id).get();
	}
	
	@GetMapping(value="**/localidades/excluir/{id}")
	public String excluirLocalidades(@PathVariable("id") Long id, ModelAndView model) {
		serviceLocalidade.deletar(id);
		return "redirect:/admin/historico/localidades";
	}
	
	@GetMapping(value="**/localidades/status/{id}")
	public String modificarStatusLocalidade(@PathVariable("id") Long id, ModelAndView model) {
		Localidade localidadeBanco = serviceLocalidade.buscaPorId(id).get();
		
		if(localidadeBanco!=null) {
			localidadeBanco.setStatus(!localidadeBanco.isAtivo());
		}
		serviceLocalidade.alterar(id, localidadeBanco);
		return "redirect:/admin/cadastros/localidades/historico";
	}	
	
	// Verificar localização da Mapping na Controller Bairros
	@ResponseBody
	@GetMapping("**/localidades/bairros/{id}")
	public List<Bairro> consultarBairrosDaCidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceLocalidade.buscaPorId(id).get().getBairros();
	}

 	@ResponseBody
	@GetMapping("**/localidades/bairrosSort/{id}")
	public List<Bairro> consultarBairrosDaCidadeSort(@PathVariable("id") Long id, ModelAndView model) {
		return serviceBairro.buscaPorLocalidadeSort( serviceLocalidade.buscaPorId(id).get() );
	}
 	
	@ResponseBody
	@GetMapping("**/localidades/consultarCidade/{id}")
	public Cidade consultarcidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceLocalidade.buscaPorId(id).get().getCidade();
	}
	
	/**
	 ** IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	
	@ResponseBody
	@RequestMapping(value = "/localidades/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Localidade> listPOST(DataTablesInput input) {	
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());	
		
		return serviceLocalidade.listarTodasLocalidades(input,user);
	}
}
