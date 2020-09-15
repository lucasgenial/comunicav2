package br.com.cicom.comunicacicom.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.HierarquiaService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;

@Controller
public class HierarquiaController {
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private HierarquiaService serviceHierarquia;
	
	@Autowired
	private InstituicaoService serviceInstituicao;
	
	
	@GetMapping("**/hierarquias")
	public ModelAndView telaInicialHierarquias(ModelAndView mv) {
		mv.setView(new RedirectView("*/cadastros/hierarquias"));
		mv.setViewName("principal/hierarquias");
		mv.addObject("instituicao",serviceInstituicao.listarTodos());
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));		
		mv.addObject("hierarquia", new Hierarquia());
		mv.addObject("hierarquias",serviceHierarquia.listarTodos());

		return mv;
	}
	
	@PostMapping("*/cadastraHierarquia")
	public String cadastrarHierarquia(@Valid Hierarquia hierarquia, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/hierarquias";
		}
		serviceHierarquia.cadastrar(hierarquia);
		return "redirect:/admin/cadastros/hierarquias";
	}
	
	@ResponseBody
	@GetMapping("**/hierarquias/consultar/{id}")
	public Hierarquia consultarHierarquia(@PathVariable("id") Long id, ModelAndView model) {
		return serviceHierarquia.buscaPorId(id).get();
	}
	
	@PostMapping(value="**/hierarquias/editar/{id}")
	public String editarHierarquia(@PathVariable("id") Long id,@Valid Hierarquia hierarquia, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/hierarquias";
		}
     	serviceHierarquia.alterar(id,hierarquia);
		return "redirect:/admin/cadastros/hierarquias";
	}
	
		
	@GetMapping(value="**/hierarquias/status/{id}")
	public String modificarStatusHierarquia(@PathVariable("id") Long id, ModelAndView model) {
		Hierarquia hierarquiaBanco = serviceHierarquia.buscaPorId(id).get();
		
		if(hierarquiaBanco!=null) {
			hierarquiaBanco.setStatus(!hierarquiaBanco.isAtivo());
		}
		serviceHierarquia.alterar(id, hierarquiaBanco);
		return "redirect:/admin/cadastros/hierarquias";
	}
	
	@GetMapping(value="**/hierarquias/excluir/{id}")
	public String excluirHierarquia(@PathVariable("id") Long id, ModelAndView model) {
		
		serviceHierarquia.deletar(id);
		return "redirect:/admin/cadastros/hierarquias";
	}

}
