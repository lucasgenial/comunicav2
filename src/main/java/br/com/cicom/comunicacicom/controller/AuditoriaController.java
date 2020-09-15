package br.com.cicom.comunicacicom.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSAudit.model.AuditoriaDT;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaDTService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class AuditoriaController {

	@Autowired 
	private AuditoriaDTService serviceAuditoriaDT;
	@Autowired
	private UsuarioService servicoUsuario;

	
	@GetMapping("*/historico/auditorias")
	public ModelAndView telainicialAuditar(ModelAndView mv){		
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		mv.setViewName("fragmentos/historicos/historicoAuditorias");
		// Titulo da Página
		mv.addObject("tituloPagina", "ComunicaCICOM - Auditoria");
		return mv;
	}	
	

	@ResponseBody
	@RequestMapping(value = "/auditoria/buscar/historico",method = {RequestMethod.POST, RequestMethod.GET})
	public DataTablesOutput<AuditoriaDT> listPost(DataTablesInput input){
		return serviceAuditoriaDT.listDTAuditoria(input);
	}

	@ResponseBody
	@RequestMapping(value = "/auditoria/buscar/historicolist",method = {RequestMethod.GET})
	public List<AuditoriaDT> listPost(){
		return serviceAuditoriaDT.listAuditoriaDT();
	}
	
}
