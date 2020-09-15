package br.com.cicom.comunicacicom.controller;

import java.util.List;

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

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Categoria;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class CategoriaController {
	
	@Autowired
	private CategoriaService serviceCategoria;

	@Autowired
	private TipificacaoService serviceTipificacao;
		
	@Autowired
	private UsuarioService servicoUsuario;
		
	@GetMapping("**/categorias")
	public ModelAndView telaInicialCategorias(ModelAndView mv) {
	
		mv.setView(new RedirectView("*/cadastros/categorias"));
		mv.setViewName("principal/categorias");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("categorias",serviceCategoria.listarTodos());

		return mv;
	}

	@ResponseBody
	@GetMapping("**/tipificacoes/categorias/{ids}")
	public List<Tipificacao> consultarTipificacoes(@PathVariable(value = "ids", required = false) List<Long> ids,
			ModelAndView model) {
		return serviceTipificacao.buscaPorIdsDeCategoria(ids);
	}
	
	@ResponseBody
	@GetMapping("**/categorias/tipificacoes/{id}")
	public List<Tipificacao> consultarTipificacoes(@PathVariable("id") Long id, ModelAndView model) {
		return serviceCategoria.buscaPorId(id).get().getTiposOcorrencia();
	}
	
	@PostMapping("*/cadastraCategoria")
	public String cadastrarCategoria(@Valid Categoria value, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/cadastros/categorias";
		}
		serviceCategoria.cadastrar(value);
		return "redirect:/admin/cadastros/categorias";
	}
}
