package br.com.cicom.comunicacicom.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.sisNotificacao.NotificacaoDTO;

@Controller
public class NotificacaoController {
	
	@ResponseBody
	@RequestMapping(value = { "/hotificacao/listar/" }, method = { RequestMethod.POST, RequestMethod.GET })
	public List<NotificacaoDTO> listarNotificacoes(ModelAndView mv) {
		return null;
	}
	
	@RequestMapping(value = "**/cadastrarNotificao", params = {"cadastrar"})
	public String cadastrarNotificao(ModelAndView mv) {
		return "redirect:/admin/notificacoes/entrada";
	}
}