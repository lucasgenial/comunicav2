package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class ServidorController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private ServidorService serviceServidor;

	@Autowired
	private EmailService serviceEmail;

	@PostMapping("*/salva/cadastro/servidor")
	public String cadastrarServidor(@Valid Servidor servidor, BindingResult result,
			RedirectAttributes redirectAttributes) {

		servidor.getEmail().setNome(servidor.getNome());

		servidor.getUsuario().setStatus(false);

		if (result.hasErrors()
				|| (serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null)) {
		
			if (serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null) {
				redirectAttributes.addFlashAttribute("EmailDuplicado", "Este endereco de email ja esta em uso");
			}

			redirectAttributes.addFlashAttribute("erros",
					"Existe erros no preenchimento, verifique se todos o campos estão preenchidos corretamente.");
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.email", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);
			redirectAttributes.addFlashAttribute("servidor", servidor);
			redirectAttributes.addFlashAttribute("estabelecimento", servidor.getEstabelecimento());
			redirectAttributes.addFlashAttribute("instituicao", servidor.getInstituicao());
			redirectAttributes.addFlashAttribute("hierarquias", servidor.getHierarquia());
			redirectAttributes.addFlashAttribute("funcoes", servidor.getFuncao());
			redirectAttributes.addFlashAttribute("cidades", servidor.getEndereco().getCidade());
			redirectAttributes.addFlashAttribute("sexo", servidor.getSexo());

			return "redirect:/admin/servidores/cadastro";
		}
		servidor.getUsuario().getEstabelecimento().add(servidor.getEstabelecimento());

		serviceServidor.cadastrar(servidor);

		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(servidor.getUsuario().getId());
		auditoria.setHistorico("Cadastramento de servidor: " + servidor.getNome());

		return "redirect:/admin/servidores/cadastroRealizadoSucesso";
	}
	            
	@PostMapping(value = "*/salva/cadastro/servidorTerceirizado")
	public String cadastrarServidorTerceirizado(@Valid Servidor servidor, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if (result.hasErrors()
				|| (serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null)) {

			if (serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null) {
				redirectAttributes.addFlashAttribute("EmailDuplicado", "Este endereco de email ja esta em uso");
			}
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.email", result);
			redirectAttributes.addFlashAttribute("servidor", servidor);
			redirectAttributes.addFlashAttribute("estabelecimento", servidor.getEstabelecimento());
			redirectAttributes.addFlashAttribute("instituicao", servidor.getInstituicao());
			redirectAttributes.addFlashAttribute("hierarquias", servidor.getHierarquia());
			redirectAttributes.addFlashAttribute("funcoes", servidor.getFuncao());
			redirectAttributes.addFlashAttribute("cidades", servidor.getEndereco().getCidade());
			redirectAttributes.addFlashAttribute("sexo", servidor.getSexo());

			return "redirect:/admin/cadastra/cadastroTerceirizado";
		}
		servidor.getEmail().setNome(servidor.getNome());
		serviceServidor.cadastrar(servidor);
		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(user.getId());
		auditoria.setHistorico("Cadastrou servidor: " + servidor.getNome() + '/' + servidor.getCpf());
		return "redirect:/admin/cadastro/servidores/cadastroTerceirizadoRealizadoSucesso";
	}

	@RequestMapping(value = "**/servidores/visualizar/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarServidor(@PathVariable("id") Long id, ModelAndView modelAndView) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		modelAndView.setViewName("fragmentos/visualizacoes/visualizaServidor");
		modelAndView.addObject("usuario", user);
		modelAndView.addObject("servidor", serviceServidor.buscaPorId(id).get());

		return modelAndView;
	}

	@RequestMapping(value = "**/solicitacao/{id}", method = RequestMethod.GET)
	public ModelAndView adicionarSolicitacao(@PathVariable("id") Long id, ModelAndView modelAndView) {
		modelAndView.addObject("servidor", serviceServidor.buscaPorId(id));
		modelAndView.setViewName("fragmentos/cadastros/adicionarSolicitacao");
		modelAndView.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/servidores/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input) {			
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());	
		return serviceServidor.listarTodosServidores(input, user);
	}
	/* --------------------------------------------------------------------------------------------- */

	@ResponseBody
	@RequestMapping(value = "/servidores/buscartodos/historico", method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<Servidor> listDTPOST(DataTablesInput input) {			
		//Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());	
		return serviceServidor.listTodosServidoresDT(input);
	}
	/* --------------------------------------------------------------------------------------------- */

}
