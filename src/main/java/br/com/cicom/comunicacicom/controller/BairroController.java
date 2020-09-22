package br.com.cicom.comunicacicom.controller;

import java.util.List;
import java.util.Map;
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

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.BairroService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class BairroController {

	@Autowired
	private BairroService serviceBairro;

	@Autowired
	private UsuarioService servicoUsuario;

	@GetMapping("**/bairros")
	public ModelAndView telaInicialBairro(ModelAndView mv) {				
		
		mv.setViewName("fragmentos/historicos/historicoBairros");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Bairros");
		
		return mv;
	}
	
	@RequestMapping(value = "**/bairros/cadastro", method = RequestMethod.GET)
	public String cadastraBairro(Model mv) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("bairro", new Bairro());
		mv.addAttribute("cidades", user.getEstabelecimento().get(0).getCidades());
		mv.addAttribute("edicao", false);
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Bairro");
		
		return "fragmentos/cadastros/bairro";	
	
}	
	
	@RequestMapping(value = "**/bairros/editar/{id}", method = RequestMethod.GET)
	public String editarBairro(@PathVariable("id") Long id, Model model) {
		
		Bairro bairroBanco = serviceBairro.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);			

		model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
		model.addAttribute("bairro", bairroBanco);
		model.addAttribute("localidades", bairroBanco.getLocalidade().getCidade().getLocalidades());
		model.addAttribute("edicao", true);
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Bairro");
		
		return "fragmentos/cadastros/bairro";
	}		
	
	
	@RequestMapping(value = "**/cadastrarBairro", params = {"cadastrar"})
	public String cadastrarBairro(@Valid Bairro bairro, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/bairros/cadastro";
		}
		serviceBairro.cadastrar(bairro);
		return "redirect:/admin/historico/bairros";
	}
	
	@RequestMapping(value = "**/cadastrarBairro", params = {"atualizar"})
	public String atualizarBairro(@Valid Bairro bairro, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/bairros/cadastro";
		}
		serviceBairro.alterar(bairro.getId(), bairro);
		return "redirect:/admin/historico/bairros";
	}
	
	
	@ResponseBody
	@GetMapping("**/bairros/consultar/{id}")
	public Bairro consultarBairro(@PathVariable("id") Long id, ModelAndView model) {
		return serviceBairro.buscaPorId(id).get();
	}
	

	@GetMapping(value = "**/bairros/status/{id}")
	public String modificarStatusBairro(@PathVariable("id") Long id, ModelAndView model) {
		Bairro bairroBanco = serviceBairro.buscaPorId(id).get();

		if (bairroBanco != null) {
			bairroBanco.setStatus(!bairroBanco.isAtivo());
		}
		serviceBairro.alterar(id, bairroBanco);
		return "redirect:/admin/historico/bairros";
	}

	@GetMapping(value = "**/bairros/excluir/{id}")
	public String excluirBairro(@PathVariable("id") Long id, ModelAndView model) {
		serviceBairro.deletar(id);
		return "redirect:/admin/historico/bairros";
	}

	@ResponseBody
	@GetMapping("**/bairros/consultarlocalidade/{id}")
	public Localidade consultarlocalidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceBairro.buscaPorId(id).get().getLocalidade();
	}

	@ResponseBody
	@GetMapping("**/bairros/consultarcidade/{id}")
	public Cidade consultarcidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceBairro.buscaPorId(id).get().getLocalidade().getCidade();
	}

	@ResponseBody
	@GetMapping("**/bairros/consultarlocalidadesporcidade/{id}")
	public Set<Localidade> consultarlocalidadesporcidade(@PathVariable("id") Long id, ModelAndView model) {

		return serviceBairro.buscaPorId(id).get().getLocalidade().getCidade().getLocalidades();
	}

	@ResponseBody
	@GetMapping("**/bairros/consultarcidadeporestabelecimento/{id}")
	public List<Cidade> consultarcidadeporestabelecimento(@PathVariable("id") Long id, ModelAndView model) {
		Estabelecimento cidadeEstabelecimento = serviceBairro.buscaPorId(id).get().getLocalidade().getCidade()
				.getEstabelecimento();
		return cidadeEstabelecimento.getCidades();
	}
	
	/**
	 ** IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	
	@ResponseBody
	@RequestMapping(value = "/bairros/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input) {
		return serviceBairro.listarTodosBairros(input);
	}

}