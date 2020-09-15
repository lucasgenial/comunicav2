package br.com.cicom.comunicacicom.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cicom.comunicacicom.DSPrimary.POJO.CategoriaPOJO;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaDTOService;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class MenuEstatisticaController {
	
	@Autowired
	private CategoriaDTOService serviceCategoriaDTO;

	@Autowired
	private CategoriaService serviceCategoria;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	
	public MenuEstatisticaController() {
		super();
	}
	
	@GetMapping(value = "**/estatistica")
	public String gerarDadosEstatisticosTela(Model model, HttpServletRequest req) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
							
		model.addAttribute("categorias", serviceCategoria.listarTodos());
		model.addAttribute("estabelecimentos", user.getEstabelecimento());
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Gerador de Dados Estatistico");
		
		return "fragmentos/estatistica/gerarDadosEstatisticos";
	}
		
	@PostMapping(value = "**/mostrarDados")
	public String mostraDados(Model model, 
			@RequestParam(value = "categoria") List<Long> categorias, 
			@RequestParam(value = "listaCidades") List<Long> listaCidades,
			@RequestParam(value = "dataInicial") String dataInicial,
			@RequestParam(value = "dataFinal") String dataFinal ) {

		LocalDateTime inicio = LocalDateTime.of(LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.of(0, 0, 0));
		LocalDateTime fim = LocalDateTime.of(LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.of(23, 59, 59));
		String titulo = "";
		if(categorias.size() == 1) {
			List<CategoriaPOJO> dadosTipificacao = serviceCategoriaDTO.buscarPorTipificacao(listaCidades, categorias, inicio, fim);
			model.addAttribute("tipificacaoDTO", dadosTipificacao);
			titulo = "Tipificação";
		}else {
			List<CategoriaPOJO> dadosCategorias = serviceCategoriaDTO.buscarEstatistica(listaCidades, categorias, inicio, fim);
			model.addAttribute("categoriasDTO", dadosCategorias);
			titulo = "Categoria";
		}
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);
							
		model.addAttribute("categorias", serviceCategoria.listarTodos());
		model.addAttribute("estabelecimentos", user.getEstabelecimento());
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Tabela Dados Estatistico");
		
		model.addAttribute("dataInicial", inicio);
		model.addAttribute("dataFinal", fim);
		model.addAttribute("listaCidades", listaCidades);
		model.addAttribute("titulo", titulo);
		
		return "fragmentos/estatistica/tabelaDadosEstatisticos";
	}
}