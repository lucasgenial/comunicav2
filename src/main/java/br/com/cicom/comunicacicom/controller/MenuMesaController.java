package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.MesaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ServidorFuncaoInternoService;

@Controller
public class MenuMesaController {
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private MesaService serviceMesa;
	
	@Autowired
	private ServidorService serviceServidor;
	
	@Autowired
	private ServidorFuncaoInternoService ServiceservidorFuncaoInterco ;
	
	@RequestMapping("*/pesquisa/mesas")
	public String telaBuscar(Model model) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		
		Mesa mesa = new Mesa();
		mesa.setInicioPlantao(LocalDateTime.now().minusMonths(6));
		model.addAttribute("mesa", mesa);
			
		if(user.getEstabelecimento().size()==1) {
			model.addAttribute("cidades", user.getEstabelecimento().get(0).getCidades());
		}else {
			model.addAttribute("cicoms", user.getEstabelecimento());
		}
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Mesa");
		model.addAttribute("servidoresInternos",serviceServidor.buscarPorEstabelecimento(user.getServidor().getEstabelecimento()));
		return "fragmentos/pesquisas/pesquisaMesa";
	}
	
	@RequestMapping("*/menu/mesa")
	public String menu(Model model) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		
		Mesa mesa = new Mesa();
		long periodAsMinutes = 0;
		
		if(serviceMesa.buscarUltimaAtivaPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome()) != null){
			mesa = serviceMesa.buscarUltimaAtivaPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome()); 
			periodAsMinutes = ChronoUnit.SECONDS.between(LocalDateTime.now(),mesa.getFimPlantao()); 
		}
		
		model.addAttribute("mesa",mesa);
		model.addAttribute("tempoParaDesabilitar", periodAsMinutes);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Menu Mesa");
		return "fragmentos/menus/menuMesa";
	}
	
	@ResponseBody
	@GetMapping(value = "**/mesa/filtrar")
	public ModelAndView searchOcorrencia(Mesa mesa,@RequestParam(value="listaDeServidores", required=false) List<Servidor> servidores,
			BindingResult result, ModelAndView mv) throws NoSuchFieldException, SecurityException {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	
		List<Mesa> mesasEncontradas = new ArrayList<>();
	
		if(mesa.getInicioPlantao()== null)
		 { 
			 mesa.setInicioPlantao(LocalDateTime.now().minusMonths(6)); 
		 }
		 if(mesa.getFimPlantao() == null)
		 { 
			 mesa.setFimPlantao(LocalDateTime.now().plusDays(1)); 
		 }
		
		mesasEncontradas = serviceMesa.filtrar(mesa, user.getEstabelecimento(), ServiceservidorFuncaoInterco.buscaPorServidores(servidores));
		
		mv.setViewName("fragmentos/pesquisas/pesquisaMesa");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		mv.addObject("mesas",mesasEncontradas);
		
		if(user.getEstabelecimento().size()==1) {
			mv.addObject("cidades", user.getEstabelecimento().get(0).getCidades());
		}else {
			mv.addObject("cicoms", user.getEstabelecimento());
		}
		
		mv.addObject("tituloPagina", "ComunicaCICOM - Pesquisa Mesa");
		mv.addObject("servidoresInternos",serviceServidor.buscarPorEstabelecimento(user.getServidor().getEstabelecimento()));
   		
		return mv;
    }

	
}
