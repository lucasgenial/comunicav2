package br.com.cicom.comunicacicom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cicom.comunicacicom.DSPrimary.model.Formatador;
import br.com.cicom.comunicacicom.DSPrimary.model.GerenciadorDeEnvioPorEmail;

@Controller
public class ContatoController {
	
	@PostMapping(value = "/cadastrarContato")
	public String cadastrarContato(@RequestParam (value = "nome", required = false)String nome,@RequestParam (value = "email", required = false)String email,
								   @RequestParam (value = "estebelecimento", required = false)String estabelecimento,
								   @RequestParam (value = "mensagem", required = false)String mensagem) {
		
		GerenciadorDeEnvioPorEmail enviarEmail = new GerenciadorDeEnvioPorEmail();
		
		enviarEmail.enviarEmailContato(new Formatador().formataEmailSuporte(nome,email,estabelecimento,mensagem));
		return "redirect:/";
	}
}
	    


