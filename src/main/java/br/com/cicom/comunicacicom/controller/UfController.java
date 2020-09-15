package br.com.cicom.comunicacicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.UnidadeFederativa;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.UfService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;


@Controller
public class UfController {
	
	@Autowired
	private UfService serviceUf;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private CidadeService serviceCidade;
	
	
	@GetMapping("**/estados")
	public ModelAndView telaInicialBairro(ModelAndView mv) {
		
		mv.setView(new RedirectView("*/cadastros/estados"));
		mv.setViewName("principal/estados");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("cidades",serviceCidade.listarTodos());

		return mv;
	}
		
	@ResponseBody
	@GetMapping("**/estados/cidades/{id}")
	public List<Cidade> consultarCidades(@PathVariable("id") Long id, ModelAndView model) {
		return serviceUf.buscaPorId(id).get().getCidades();
		
	}
	
	@GetMapping(value="**/estados/status/{id}")
	public String modificarStatusUf(@PathVariable("id") Long id, ModelAndView model) {
		UnidadeFederativa UFBanco = serviceUf.buscaPorId(id).get();
		
		if(UFBanco!=null) {
			UFBanco.setStatus(!UFBanco.isAtivo());
		}
		serviceUf.alterar(id, UFBanco);
		return "redirect:/admin/cadastros/estados";
	}
	
	/*novo metodo*/
	@ResponseBody
	@GetMapping("**/estados/consultarPorNome/{nome}")
	public UnidadeFederativa consultarPorNome(@PathVariable("nome") String nome, ModelAndView model) {
		return serviceUf.buscarPorNome(nome);
	}
	
	
}
