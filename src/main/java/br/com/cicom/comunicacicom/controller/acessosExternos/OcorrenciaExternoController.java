package br.com.cicom.comunicacicom.controller.acessosExternos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.criptografia.CriptografiaDeUrl;


@Controller
public class OcorrenciaExternoController {
	
	@Autowired
	private UsuarioService servicoUsuario; 
	
	@Autowired
	private OcorrenciaService serviceOcorrencia; 
	
	String valor0Inserido;
	String valor1Inserido;
	String valor2Inserido;
	String dataCriptografada;
	String idCriptografado;
	
	@RequestMapping(value = "*/ocorrencias/visualizar/externo/{valor0}/{valor1}/{valor2}", method = RequestMethod.GET)
	public ModelAndView visualizarOcorrencia(@PathVariable("valor0") String valor0, @PathVariable("valor1") String valor1,@PathVariable("valor2") String valor2, ModelAndView mv,HttpServletRequest req) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH");
		String datahorastr = LocalDateTime.now().format(formatter); 		
		datahorastr = datahorastr.replace("-", "").replace(" ", "");
		dataCriptografada = CriptografiaDeUrl.criptografarNumerosString(datahorastr);

		idCriptografado = CriptografiaDeUrl.criptografarNumerosString(user.getId().toString());
		
		mv.setViewName("redirect:/registro/ocorrencia/visualizar/comunica/"+idCriptografado+"/"+dataCriptografada);
	
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
	
		valor2Inserido = valor2;
		valor1Inserido = valor1;
		valor0Inserido = valor0;
		
		return mv;
    }
	
	@RequestMapping(value = "/registro/ocorrencia/visualizar/comunica/{valor1}/{valor2}", method = RequestMethod.GET)
	public ModelAndView visualizarOcorrenciaSemUrl(@PathVariable("valor1") String valor1,@PathVariable("valor2") String valor2, ModelAndView mv,HttpServletRequest req) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Map<String, Permissao> mapa = new HashMap<>();
		for(int i=0; i < user.getGrupo().getPermissoes().size(); i++){
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}
		mv.addObject("permissoes", mapa);
		
				if((!valor1.equals(idCriptografado)) || !valor2.equals(dataCriptografada)) {		
					mv.setViewName("redirect:/registro/ocorrencia/visualizar/comunica/"+idCriptografado+"/"+dataCriptografada);
					return mv;
				}else {
					mv.setViewName("acessosExternos/visualizaOcorrencia");
				}
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String datahorastr = LocalDateTime.now().format(formatter); 
		
		mv.addObject("datahora",datahorastr);
		mv.addObject("servidor", user.getServidor());
		Ocorrencia ocorrencia = new Ocorrencia();
		try {
			ocorrencia = serviceOcorrencia.buscaPorId(Long.valueOf(CriptografiaDeUrl.descriptografarUrl(valor2Inserido))).get();
		} catch (Exception e) {
			mv.clear();
			mv.setViewName("redirect:/404");
			return mv;
		}
		if((!ocorrencia.getEstabelecimento().equals(user.getServidor().getEstabelecimento())) && !mapa.containsKey("ADMINISTRADOR")){
			mv.clear();
			mv.setViewName("redirect:/404");
			return mv;
		}
		
		mv.addObject("ocorrencia",ocorrencia);
		return mv;
    }
	
	
	@RequestMapping(value = "**/ocorrencias/visualizarWhatsapp/externo/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarWhatsappOcorrencia( @PathVariable("id") Long id, ModelAndView mv) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
					
		mv.setViewName("acessosExternos/ocorrenciaParaSerEnviadaViaWhatsapp");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String datahorastr = LocalDateTime.now().format(formatter); 
		
		mv.addObject("datahora",datahorastr);
		mv.addObject("servidor", user.getServidor());
		Ocorrencia ocorrencia = serviceOcorrencia.buscaPorId(id).get();
		if (!(ocorrencia.getHistorico() == null)) {
			ocorrencia.setHistorico(" ");;
		}
		mv.addObject("ocorrencia",ocorrencia);
   		
		return mv;
    }

	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception ex) {
	    return "redirect:/404";
	}
	
	@RequestMapping("/404")
	public ModelAndView erro40() {
		ModelAndView mv = new ModelAndView();
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			mv.setViewName("404");
			mv.addObject("usuario",user);

			return mv;
	
	}
	

}
