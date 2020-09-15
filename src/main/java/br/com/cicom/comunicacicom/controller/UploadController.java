package br.com.cicom.comunicacicom.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSArquivo.service.ArquivoService;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class UploadController {
	
	@Autowired
	private ArquivoService serviceArquivo;
	
	@Autowired
	private ServidorService serviceServidor;
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@GetMapping("**/servidores/arquivo")
    public String index() {
        return "multipartfile/uploadform.html";
    }
	
	public File convert(MultipartFile file) throws IOException
	{    
		String pdfPath = getClass().getClassLoader().getResource("").toString();
		pdfPath = pdfPath.substring(pdfPath.indexOf(':') + 1).replace("%20", " ") + "templates/principal/arquivo/";
		String nome = pdfPath + System.currentTimeMillis()+file.getOriginalFilename();		
	    File convFile = new File(nome);
	    convFile.createNewFile(); 	    
	    FileOutputStream fos = new FileOutputStream(nome); 
	    fos.write(file.getBytes());
	    fos.close(); 	    
	    return convFile;
	}
	
	@PostMapping("**/arquivo/enviar/{id}")
    public String uploadMultipartFile(@PathVariable("id") Long id,@RequestParam("descricao") String descricao,@RequestParam ("tipo") String tipo, @RequestParam("uploadfile") MultipartFile file, Model model) throws IOException {
		File arquivoFile = this.convert(file);
		
		try {			
			Servidor servidor = serviceServidor.buscaPorId(id).get();
			Arquivo arquivo = new Arquivo();
			arquivo.setNome(file.getOriginalFilename());
			arquivo.setTipo(tipo);
			arquivo.setDescricao(descricao);			
			arquivo.setDataEnvio(LocalDate.now());
			arquivo.setServidor( serviceServidor.buscaPorId(id).get().getId() );
			BufferedInputStream bis = null;
			byte[] bfile = new byte[(int) arquivoFile.length()];
			bis = new BufferedInputStream(new FileInputStream(arquivoFile));
			bis.read(bfile);
			bis.close();
			arquivo.setArquivo(bfile);			
			serviceArquivo.cadastrar(arquivo);
			//serviceServidor.alterar(id, servidor);					
			model.addAttribute("mensagem", "O arquivo " + file.getOriginalFilename() + " foi enviado com sucesso!");
		
		} catch (Exception e) {
			model.addAttribute("mensagem", "Falha ao enviar o arquivo: " + file.getOriginalFilename());
		}
		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		return "fragmentos/cadastros/adicionarSolicitacao";
    }
	
	@GetMapping(value="**/arquivo/excluir/{id}")
	public String excluirArquivo(@PathVariable("id") Long id, ModelAndView model) {	
		serviceArquivo.deletar(id);
		return "redirect:/admin/historico/servidores";	
	}
}
