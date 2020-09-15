package br.com.cicom.comunicacicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSArquivo.model.ArquivoDT;
import br.com.cicom.comunicacicom.DSArquivo.service.ArquivoDTService;
import br.com.cicom.comunicacicom.DSArquivo.service.ArquivoService;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class DownloadController {
	
	@Autowired
	private ArquivoService serviceArquivo;
	
	@Autowired
	private ArquivoDTService serviceArquivoDT;

	@Autowired
	private ServidorService serviceServidor;

	@Autowired
	private UsuarioService servicoUsuario;

	@GetMapping("*/historico/arquivos")
	public String getListFiles(Model model) {
		model.addAttribute("files", serviceArquivo.buscarPorServidor(  serviceServidor.buscaPorId( servicoUsuario.buscaPeloLogin( SecurityContextHolder.getContext().getAuthentication().getName() ).getServidor().getId() ).get().getId()  ));
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Arquivos");
		return "fragmentos/historicos/historicoArquivos";
	}
	
	@GetMapping("**/arquivo/download/{id}")
	public void downloadFile(final HttpServletResponse response,@PathVariable Long id) throws IOException {
		
		byte[] files = serviceArquivo.buscaPorId(id).get().getArquivo();
		response.setContentType("application/file");
		response.setHeader("Content-Disposition", "inline ; filename=" + serviceArquivo.buscaPorId(id).get().getNome());
		response.setContentLength(files.length);
		
		final ServletOutputStream output = response.getOutputStream();
		output.write(files, 0, files.length);
		output.close();

	}
	
	@GetMapping("*/historico/arquivos/{id}")
	public String listarArquivos(@PathVariable ("id") Long id, Model model) {
		model.addAttribute("files", serviceArquivo.buscarPorServidor( serviceServidor.buscaPorId(id).get().getId() ) );
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Arquivos");
		return "fragmentos/historicos/historicoArquivos";
	}

	@ResponseBody
	@RequestMapping(value = "**/arquivos/buscar/historico", method = {RequestMethod.POST, RequestMethod.GET})
	public DataTablesOutput<Arquivo> listPOST(DataTablesInput input) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return serviceArquivo.listarTodosArquivos(input,user.getServidor().getId());
	}
	
	@GetMapping("*/historico/arquivosDT/{id}")
	public String listarArquivosDt(@PathVariable ("id") Long id, Model model) {
		model.addAttribute("files", serviceArquivo.buscarPorServidor(id));
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addAttribute("idServidor", id);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Arquivos por Servidor");
		return "fragmentos/historicos/historicoArquivosPorServidor";
	}

	@ResponseBody
	@RequestMapping(value = "**/arquivosDT/buscar/historico/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public DataTablesOutput<Arquivo> listPOST(DataTablesInput input, @PathVariable ("id") Long id ) {
		//Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		return serviceArquivo.listarTodosArquivos(input, id);
	}
}
