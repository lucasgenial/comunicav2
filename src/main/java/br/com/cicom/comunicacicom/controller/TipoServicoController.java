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

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoServico;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.TipoServicoService;

@Controller
public class TipoServicoController {	

	@Autowired
	private TipoServicoService serviceTipoServico;
	
	@Autowired
	private UsuarioService serviceUsuario;
	
		
	@GetMapping("*/historico/tipoServicos")
	public ModelAndView telaInicialTipoServico(ModelAndView mv) {
				
		mv.setViewName("fragmentos/historicos/historicoTipoServicos");
		mv.addObject("usuario", serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Relação de Tipo de Servicos");

		return mv;
	}
	
	@RequestMapping(value = "**/tipoServicos/cadastro", method = RequestMethod.GET)
	public String cadastraTipoServico(Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("tipoServico", new TipoServico());
		mv.addAttribute("edicao", false);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Tipo de Servico");
		
		return "fragmentos/cadastros/tipoServico";	
	
	}	
	
	@RequestMapping(value = "**/tipoServicos/editar/{id}", method = RequestMethod.GET)
	public String editarTipoServico(@PathVariable("id") Long id, Model mv) {
		
		Usuario user = serviceUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		TipoServico tipoServicoBanco = serviceTipoServico.buscaPorId(id).get();
		
		mv.addAttribute("usuario", user);	
		mv.addAttribute("tipoServico", tipoServicoBanco);
		mv.addAttribute("edicao", true);
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Tipo de Servico");
		
		return "fragmentos/cadastros/tipoServico";		
	}	
	
	@RequestMapping(value = "**/cadastrarTipoServico", params = {"cadastrar"})
	public String cadastrarTipoServico(@Valid TipoServico tipoServico, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/tipoServicos/cadastro";
		}
		serviceTipoServico.cadastrar(tipoServico);
		return "redirect:/admin/historico/tipoServicos";
	}
	
	@RequestMapping(value = "**/cadastrarTipoServico", params = {"atualizar"})
	public String atualizarTipoServico(@Valid TipoServico tipoServico, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/tipoServicos/cadastro";
		}
		serviceTipoServico.alterar(tipoServico.getId(),tipoServico);
		return "redirect:/admin/historico/tipoServicos";
	}
	
	@ResponseBody
	@RequestMapping(value = "/tipoServicos/buscar/historico", method = {RequestMethod.POST,RequestMethod.GET})
	public DataTablesOutput<TipoServico> listPOST(DataTablesInput input) {	
		return serviceTipoServico.listarTodosTiposServico(input);
	}
	
	@ResponseBody
	@GetMapping("**/tipoServico/consultar/{id}")
	public TipoServico consultarTipoServico(@PathVariable("id") Long id, ModelAndView model) {
		return serviceTipoServico.buscaPorId(id).get();
	}

		
	@GetMapping(value="**/tipoServicos/status/{id}")
	public String modificarStatusTipoServico(@PathVariable("id") Long id, ModelAndView model) {
		TipoServico tipoServicoBanco = serviceTipoServico.buscaPorId(id).get();
		
		if(tipoServicoBanco!=null) {
			tipoServicoBanco.setAtivo(!tipoServicoBanco.isAtivo());
		}
		serviceTipoServico.alterar(id, tipoServicoBanco);
		return "redirect:/admin/historico/tipoServicos";
	}
	
	@GetMapping(value="**/tipoServicos/excluir/{id}")
	public String excluirTipoServico(@PathVariable("id") Long id, ModelAndView model) {
		serviceTipoServico.deletar(id);
		return "redirect:/admin/historico/tipoServicos";
	}
	
	
}