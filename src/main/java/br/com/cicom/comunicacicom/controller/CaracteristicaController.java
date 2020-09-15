package br.com.cicom.comunicacicom.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Caracteristica;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.CaracteristicaService;

@Controller
public class CaracteristicaController {
	

	@Autowired
	private CaracteristicaService serviceCaracteristica;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
		
	@GetMapping("*/historico/caracteristicas")
	public ModelAndView telaInicialCaracteristica(ModelAndView mv) {
		
		mv.setView(new RedirectView("*/configuracao/caracteristicas"));
		mv.setViewName("fragmentos/historicos/historicoCaracteristica");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("caracteristica", new Caracteristica());
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Caracteristicas");
		mv.addObject("caracteristicaLista",serviceCaracteristica.listarTodos());

		return mv;
	}
	
	@ResponseBody
	@GetMapping("**/caracteristica/consultar/{id}")
	public Caracteristica consultarCaracteristica(@PathVariable("id") Long id, ModelAndView model) {
		return serviceCaracteristica.buscaPorId(id).get();
	}

	@PostMapping("**/cadastraCaracteristica")
	public String cadastrarCaracteristica(@Valid Caracteristica caracteristica, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/caracteristica";
		}

		serviceCaracteristica.cadastrar(caracteristica);
		return "redirect:/admin/historico/caracteristicas";
	}

	@RequestMapping("*/cadastra/caracteristica")
	public ModelAndView cadastraCaracteristica() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("caracteristica", new Caracteristica());
		mv.addObject("tituloPagina", "ComunicaCICOM - Cadastro de Caracteristicas");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.setViewName("fragmentos/cadastros/caracteristica");
		
		return mv;
	}
	
	@RequestMapping("*/edita/caracteristica/{id}")
	public ModelAndView editaCaracteristica(@PathVariable("id") Long id, ModelAndView mv) {
		
		mv.addObject("caracteristica", serviceCaracteristica.buscaPorId(id));
		mv.addObject("tituloPagina", "ComunicaCICOM - Editar Caracteristica");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.setViewName("fragmentos/edicoes/editaCaracteristica");
		
		return mv;
	}

	@PostMapping(value="*/salva/edicao/caracteristica")
	public String editarcaracteristica(@Valid Caracteristica caracteristica, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastra/caracteristica";
		}
		
		serviceCaracteristica.alterar(caracteristica.getId(),caracteristica);
		
		return "redirect:/admin/historico/caracteristicas";
	}
	
	@GetMapping(value="**/caracteristicas/status/{id}")
	public String modificarStatusCaracteristica(@PathVariable("id") Long id, ModelAndView model) {
		Caracteristica caracteristicaBanco = serviceCaracteristica.buscaPorId(id).get();
		
		if(caracteristicaBanco!=null) {
			caracteristicaBanco.setAtivo(!caracteristicaBanco.isAtivo());
		}
		serviceCaracteristica.alterar(id, caracteristicaBanco);
		return "redirect:/admin/historico/caracteristicas";
	}
	
	@GetMapping(value="**/caracteristicas/excluir/{id}")
	public String excluirCaracteristica(@PathVariable("id") Long id, ModelAndView model) {
		serviceCaracteristica.deletar(id);
		return "redirect:/admin/configuracao/caracteristicas";
	}
	
	@ResponseBody
	@RequestMapping(value = "/caracteristicas/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Caracteristica> listPOST(DataTablesInput input) {
		return serviceCaracteristica.buscarDadosDataTable(input);
	}
	
}