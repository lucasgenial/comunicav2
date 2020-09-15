package br.com.cicom.comunicacicom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.UfService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;

@Controller
public class CidadeController {

	@Autowired
	private CidadeService serviceCidade;

	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private EstabelecimentoService serviceEstabelecimento;
	
	@Autowired
	private UfService serviceUnidadeFederativa;
		

	@GetMapping("*/historico/cidades")
	public ModelAndView telaInicialCidades(ModelAndView mv,@RequestParam("page") Optional<Integer> page, @RequestParam(defaultValue="") String parametroDePesquisa, @RequestParam("size") Optional<Integer> size) {

		mv.setViewName("fragmentos/historicos/historicoCidades");
		mv.addObject("usuario",	servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Cidades");
	
		return mv;
	}
	
	@RequestMapping(value = "**/cidades/cadastro", method = RequestMethod.GET)
	public String cadastraCidade(Model mv) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("cidade", new Cidade());
		mv.addAttribute("estados", serviceUnidadeFederativa.listarTodosPorStatus(true));
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Cidade");
		
		return "fragmentos/cadastros/cidade";	
	
	}
	
	@RequestMapping(value = "**/cidades/editar/{id}", method = RequestMethod.GET)
	public String editarCidade(@PathVariable("id") Long id, Model model) {
		
		Cidade cidadeBanco = serviceCidade.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		model.addAttribute("cidade", cidadeBanco);
		model.addAttribute("estados", cidadeBanco.getUf());
		model.addAttribute("edicao", true);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Cidade");
		
		return "fragmentos/cadastros/cidade";
	}
	
	@RequestMapping(value = "**/cadastrarCidade", params = {"cadastrar"})
	public String cadastrarCidade(@Valid Cidade cidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/cidades/cadastro";
		}
		serviceCidade.cadastrar(cidade);
		return "redirect:/admin/historico/cidades";
	}
	
	@RequestMapping(value = "**/cadastrarCidade", params = {"atualizar"})
	public String atualizarCidade(@Valid Cidade cidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/cidades/cadastro";
		}
		serviceCidade.alterar(cidade.getId(),cidade);
		return "redirect:/admin/historico/cidades";
	}
	
	@ResponseBody
	@GetMapping("**/cidades/consultar/{id}")
	public Cidade consultarCidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceCidade.buscaPorId(id).get();
	}

	@ResponseBody
	@GetMapping("**/cidades/localidades/{id}")
	public Set<Localidade> consultarLocalidades(@PathVariable("id") Long id, ModelAndView model) {
		return serviceCidade.buscaPorId(id).get().getLocalidades();
	}

	@ResponseBody
	@GetMapping("**/cidades/localidades/estabelecimento")
	public List<Localidade> consultarTodasLocalidades(@RequestParam(value = "cidade") List<Cidade> cidades,
			ModelAndView model) {
		List<Localidade> listaLocalidade = new ArrayList<>();

		for (Cidade cidade : cidades) {
			if (cidade.getLocalidades() != null) {
				listaLocalidade.addAll(cidade.getLocalidades());
			}
		}

		return listaLocalidade;
	}

	@ResponseBody
	@GetMapping("**/cidades/estabelecimento/{id}")
	public Estabelecimento consultarEstabelecimento(@PathVariable("id") Long id, ModelAndView model) {
		return serviceCidade.buscaPorId(id).get().getEstabelecimento();
	}
	
	@ResponseBody
	@GetMapping("**/cidades/estabelecimentos/{ids}")
	public List<Cidade> buscarCidadesPorListaDeEstabelecimentos(
			@PathVariable(value = "ids", required = false) List<Long> ids) {
		return serviceCidade.buscarPorListaDeIdsDeEstabelecimento(ids);
	}
	
	@ResponseBody
	@GetMapping("**/estabelecimento/cidades/{id}")
	public List<Cidade> consultarCidadesEstabelecimento(@PathVariable("id") Long id, ModelAndView model) {
		return serviceEstabelecimento.buscaPorId(id).get().getCidades();
	}

	@GetMapping(value = "**/cidades/status/{id}")
	public String modificarStatusCidade(@PathVariable("id") Long id, ModelAndView model) {
		Cidade cidadeBanco = serviceCidade.buscaPorId(id).get();

		if (cidadeBanco != null) {
			cidadeBanco.setStatus(!cidadeBanco.isAtivo());
		}
		
		serviceCidade.alterar(id, cidadeBanco);
		
		return "redirect:/admin/historico/cidades";
	}

	@GetMapping(value = "**/cidades/excluir/{id}")
	public String excluirCidade(@PathVariable("id") Long id, ModelAndView model) {

		serviceCidade.deletar(id);
		
		return "redirect:/admin/historico/cidades";
	}

	@ResponseBody
	@GetMapping("**/cidades/consultarPorNome/{nome}")
	public Cidade consultarPorNome(@PathVariable("nome") String nome, ModelAndView model) {
		return serviceCidade.buscarPorNome(nome);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cidades/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Cidade> listPOST(DataTablesInput input) {		
		return serviceCidade.listarTodasCidades(input);
	}
}