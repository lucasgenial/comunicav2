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

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Modalidade;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.CaracteristicaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ModalidadeService;

@Controller
public class ModalidadeController {	

	@Autowired
	private ModalidadeService serviceModalidade;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@Autowired
	private CaracteristicaService serviceCaracteristica;
	
		
	@GetMapping("*/historico/modalidades")
	public ModelAndView telaInicialModalidade(ModelAndView mv) {		
		mv.setViewName("fragmentos/historicos/historicoModalidades");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Modalidades");
		
		return mv;
	}
	
	@RequestMapping(value = "**/modalidades/cadastro", method = RequestMethod.GET)
	public String cadastraModalidade(Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("modalidade", new Modalidade());
		mv.addAttribute("caracteristica",serviceCaracteristica.listarTodos());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Nova Modalidade");		
		return "fragmentos/cadastros/modalidade";		
	}	
	
	@RequestMapping(value="**/modalidades/editar/{id}", method = RequestMethod.GET)
	public String editarModalidade(@PathVariable("id") Long id, Model mv) {
		
		Modalidade modalidadeBanco = serviceModalidade.buscaPorId(id).get();
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		mv.addAttribute("usuario", user);	
		mv.addAttribute("modalidade", modalidadeBanco);
		mv.addAttribute("edicao", true);		
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Modalidade");	
		return "fragmentos/cadastros/modalidade";		
	}
	
	@RequestMapping(value = "**/cadastrarModalidade", params = {"cadastrar"})
	public String cadastrarModalidade(@Valid Modalidade modalidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/modalidades/cadastro";
		}
		serviceModalidade.cadastrar(modalidade);
		return "redirect:/admin/historico/modalidades";
	}
	
	@RequestMapping(value = "**/cadastrarModalidade", params = {"atualizar"})
	public String atualizarModalidade(@Valid Modalidade modalidade, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/modalidades/cadastro";
		}
		serviceModalidade.alterar(modalidade.getId(),modalidade);
		return "redirect:/admin/historico/modalidades";
	}	
	
	@ResponseBody
	@RequestMapping(value = "/modalidades/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Modalidade> listPOST(DataTablesInput input) {	
		return serviceModalidade.listarTodasModalidades(input);
	}
	
	@ResponseBody
	@GetMapping("**/modalidade/consultar/{id}")
	public Modalidade consultarModalidade(@PathVariable("id") Long id, ModelAndView model) {
		return serviceModalidade.buscaPorId(id).get();
	}
	
	@GetMapping(value="**/modalidades/status/{id}")
	public String modificarStatusModalidade(@PathVariable("id") Long id, ModelAndView model) {
		Modalidade modalidadeBanco = serviceModalidade.buscaPorId(id).get();
		
		if(modalidadeBanco!=null) {
			modalidadeBanco.setAtivo(!modalidadeBanco.isAtivo());
		}
		serviceModalidade.alterar(id, modalidadeBanco);
		return "redirect:/admin/historico/modalidades";
	}
	
	@GetMapping(value="**/modalidades/excluir/{id}")
	public String excluirModalidade(@PathVariable("id") Long id, ModelAndView model) {
		serviceModalidade.deletar(id);
		return "redirect:/admin/historico/modalidades";
	}
	
	@ResponseBody
	@GetMapping("*/buscar/dados/graficoTabela/modalidade/{id}")
	public List<Object> buscarModalidade(@PathVariable("id") Long idEstabelecimento, ModelAndView model) {
		return serviceModalidade.buscarDadosDaTabelaGraficoEfetivo(idEstabelecimento);
	}
}