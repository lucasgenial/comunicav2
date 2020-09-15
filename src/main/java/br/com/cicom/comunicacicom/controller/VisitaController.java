package br.com.cicom.comunicacicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Servico;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visita;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.ServicoService;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.VisitaService;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.VisitanteService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class VisitaController {

	@Autowired
	private VisitaService serviceVisita;

	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private VisitanteService servicoVisitante;
	
	@GetMapping("*/historico/visitas")
	public String telaInicialVisitas(Model model) {			
		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));		
		
		// Titulo da Página
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Visitas");
		
		return "fragmentos/historicos/historicoVisitas";
	
	}
	
	@GetMapping("*/menu/visitas")
	public String telaMenuVisitas(Model model) {			
		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		// Titulo da Página
		//serviceVisita.listarTodasVisitasDtos(user.getServidor().getEstabelecimento()
		//model.addAttribute("visitas", serviceVisita.listarTodasVisitas();		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Visitas");
		
		return "fragmentos/menus/menuVisitas";
	
	}
	
	@RequestMapping(value = "*/cadastra/visita", method = RequestMethod.GET)
	public String cadastraVisita(Model mv) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);
		
		Visita visita = new Visita();
		visita.setEstabelecimento(user.getServidor().getEstabelecimento());
		visita.setUsuario(user);
		visita.getVisitantes().add(new Visitante());
		mv.addAttribute("visita", visita);
		mv.addAttribute("servicos", servicoService.listarTodos());
		mv.addAttribute("edicao", false);
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Visita");
		
		return "fragmentos/cadastros/visita";	
	
}
	
	@RequestMapping(value = "*/edita/visita/{id}", method = RequestMethod.GET)
	public String editarVisita(@PathVariable("id") Long id, Model model) {
		
		Visita visitaBanco = serviceVisita.buscaPorId(id).get();
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		model.addAttribute("usuario", user);	
		model.addAttribute("visita", visitaBanco);
		model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());		
		model.addAttribute("servicos", servicoService.listarTodos());
		model.addAttribute("edicao", true);
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Visita");
		
		return "fragmentos/cadastros/visita";
	}
	
	
	@RequestMapping(value = "*/salva/cadastro/visita", params = { "adicionarVisitante" })
	public String adicionarVisitante(@RequestParam(value = "novoServico", required = false) String novoServico ,@Valid Visita visita, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/visita";
		}
		
		if(novoServico != "" && servicoService.buscarPorNome(novoServico)== null) {
			Servico servico = new Servico();
			servico.setNome(novoServico);
			servico.setAtivo(true);
			servicoService.cadastrar(servico);
			visita.setServico(servicoService.buscarPorNome(novoServico));
		}
			
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		visita.getVisitantes().add(new Visitante());

		model.addAttribute("usuario", user);	
		model.addAttribute("visita", visita);
		model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());		
		model.addAttribute("servicos", servicoService.listarTodos());
		if(visita.getId() != null) {
			model.addAttribute("edicao", true);

		}else {
			model.addAttribute("edicao", false);

		}
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Visita");
		
		return "fragmentos/cadastros/visita";
	}
	
	
	
	@RequestMapping(value = "*/salva/cadastro/visita", params = { "removerVisitante" })
	public String removerVisitante(@RequestParam(value = "novoServico", required = false) String novoServico ,@Valid Visita visita, BindingResult result, Model model, HttpServletRequest req) {
	
	 int rowId = Integer.valueOf(req.getParameter("removerVisitante"));
		
	 if(visita.getVisitantes().size() > 1) {
		 visita.getVisitantes().remove(rowId);
	 }
	
	 
	if (result.hasErrors()) {
			return "redirect:/admin/cadastra/visita";
		}
		
		if(novoServico != "" && servicoService.buscarPorNome(novoServico)== null) {
			Servico servico = new Servico();
			servico.setNome(novoServico);
			servico.setAtivo(true);
			servicoService.cadastrar(servico);
			visita.setServico(servicoService.buscarPorNome(novoServico));
		}
		 
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(visita.getId() != null) {
			model.addAttribute("edicao", true);
		}else {
			model.addAttribute("edicao", false);
		}
		
		model.addAttribute("usuario", user);	
		model.addAttribute("visita", visita);
		model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());		
		model.addAttribute("servicos", servicoService.listarTodos());
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Visita");
		
		return "fragmentos/cadastros/visita";
	}
	
	@RequestMapping(value = "*/salva/cadastro/visita", params = { "cadastrar" })
	public String cadastrarVisita(@RequestParam(value = "novoServico", required = false)String novoServico ,@Valid Visita visita, BindingResult result) throws IOException {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/visita";
		}
		
		if(novoServico != "") {
			Servico servico = new Servico();
			servico.setNome(novoServico);
			servico.setAtivo(true);
			servicoService.cadastrar(servico);
			visita.setServico(servicoService.listarTodos().get(servicoService.listarTodos().size()-1));
		}
		
		  for(Visitante visitante : visita.getVisitantes()) {
			  if(visitante.getId() == null){
				  servicoVisitante.cadastrar(visitante);
			  }
		  }
		
		serviceVisita.cadastrar(visita);
		
		return "redirect:/admin/menu/visitas";
	}

	
	@RequestMapping(value = "*/salva/cadastro/visita", params = {"atualizar"})
	public String atualizarVisita(@Valid Visita visita, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/visita";
		}
		serviceVisita.alterar(visita.getId(), visita);
		return "redirect:/admin/historico/visitas";
	}
	
	@ResponseBody
	@GetMapping("**/visitas/consultar/{id}")
	public Visita consultarVisitas(@PathVariable("id") Long id, ModelAndView model) {		
		return serviceVisita.buscarPorId(id);
	}
	
	@ResponseBody
	@GetMapping("**/visitas/consultar/servico/{id}")
	public Servico consultarServico(@PathVariable("id") Long id) {
		return serviceVisita.buscaPorId(id).get().getServico();
	}
	
	@GetMapping(value="**/visitas/excluir/{id}")
	public String excluirVisita(@PathVariable("id") Long id, ModelAndView model) {
		serviceVisita.deletar(id);
		return "redirect:/admin/historico/visitas";
	}
	
	@RequestMapping(value = "*/visualiza/visita/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarVisita( @PathVariable("id") Long id, ModelAndView modelAndView) {
							
		modelAndView.setViewName("fragmentos/visualizacoes/visualizaVisita");
		modelAndView.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		modelAndView.addObject("visita",serviceVisita.buscaPorId(id).get());
   		
		return modelAndView;
    }
	
	/**
	 ** IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	
	@ResponseBody
	@RequestMapping(value = "/visitas/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input) {	
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if(user.getGrupo().getNome().equals("ADMINISTRADOR")) {
			return serviceVisita.listarTodasVisitas(input);
		}else {
			return serviceVisita.listarTodasVisitasPorEstabelecimento(input, user.getServidor().getEstabelecimento());
		}
	}
	
	@ResponseBody
	@GetMapping("/busca/visitante/porCPF/{cpf}")
	public Visitante consultarVisitante(@PathVariable("cpf") String cpf) {
		return servicoVisitante.buscarPorCpf(cpf);
	}
	
	@GetMapping("*/pesquisa/visitas")
	public String telaBuscar(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Visita visita = new Visita();
		visita.getVisitantes().add(new Visitante());
		model.addAttribute("usuario", user);
		model.addAttribute("visita", visita);
		model.addAttribute("servicos", servicoService.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Visitas");

		return "fragmentos/pesquisas/pesquisaVisita";
	}
	
	
	@PostMapping("/visita/filtrar")
	public String resultadoBuscar(Model model, Visita visita) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("visita", visita);
		model.addAttribute("visitasResultado", serviceVisita.filtrar(visita, user.getEstabelecimento().get(0)));
		model.addAttribute("servicos", servicoService.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Visitas");

		return "fragmentos/pesquisas/pesquisaVisita";
	}
	
}
