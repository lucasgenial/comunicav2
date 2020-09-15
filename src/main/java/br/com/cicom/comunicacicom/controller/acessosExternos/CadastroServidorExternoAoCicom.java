package br.com.cicom.comunicacicom.controller.acessosExternos;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.HierarquiaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;

@Controller
public class CadastroServidorExternoAoCicom {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private EstabelecimentoService serviceEstabelecimento;

	@Autowired
	private InstituicaoService serviceInstituicao;

	@Autowired
	private ServidorService serviceServidor;

	@Autowired
	private EmailService serviceEmail;
	
	@Autowired
	private FuncaoService serviceFuncao;
	
	@Autowired
	private HierarquiaService serviceHierarquia;

	@GetMapping(value = "**/servidoresExternoAoCicom/cadastro")
	public String cadastroDoServidor(Model model,
									RedirectAttributes redirectAttributes) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (!model.containsAttribute("servidor")) {
			Servidor servidor = new Servidor();
			servidor.setMatricula(user.getLogin());
			if (serviceServidor.buscarMatricula(user.getLogin()) != null) {
				servidor = serviceServidor.buscarMatricula(user.getLogin());
				model.addAttribute("hierarquias",serviceHierarquia.buscarPorInstituicao(servidor.getInstituicao()));
				if (servidor.getUsuario() != null) {
					servidor.setUsuario(user);
				}
			}else {
				model.addAttribute("email", new Email());
			}
			model.addAttribute("servidor", servidor);
		}

		model.addAttribute("usuario", user);
		model.addAttribute("linkCadastro", "/admin/cadastro/servidoresExternoAoCicom/salvar");
		model.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		model.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		model.addAttribute("funcoes", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.ACESSO_EXTERNO));
		model.addAttribute("erros");

		return "acessosExternos/cadastroUsuarioExterno";
	}

	@PostMapping("**/servidoresExternoAoCicom/salvar")
	public String salvarServidorComAcessoExterno(@Valid Servidor servidor,
												Model model, 
												BindingResult result,
												RedirectAttributes redirectAttributes) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if ( serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null ) {
			redirectAttributes.addFlashAttribute("EmailDuplicado", "Este endereco de email ja esta em uso!");
			ObjectError erro1 = new ObjectError("eMail","Este endereco de email ja esta em uso!");
			result.addError(erro1);
		}
		if (result.hasErrors() || (serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()) != null) ) {
		
			redirectAttributes.addFlashAttribute("erros",
					"Existe erros no preenchimento, verifique se todos o campos estão preenchidos corretamente.");
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.email", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);
			redirectAttributes.addFlashAttribute("servidor", servidor);
			redirectAttributes.addFlashAttribute("estabelecimento", servidor.getEstabelecimento());
			redirectAttributes.addFlashAttribute("instituicao", servidor.getInstituicao());
			redirectAttributes.addFlashAttribute("hierarquias", servidor.getHierarquia());
			redirectAttributes.addFlashAttribute("sexo", servidor.getSexo());
			model.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
			model.addAttribute("instituicoes", serviceInstituicao.listarTodos());
			if (servidor.getHierarquia()!= null) {
				model.addAttribute("hierarquias", serviceHierarquia.buscarPorInstituicao(servidor.getInstituicao()));
			}
			redirectAttributes.addFlashAttribute("linkCadastro", "/admin/cadastro/servidoresExternoAoCicom/salvar");

			return "acessosExternos/cadastroUsuarioExterno";
		}
		servidor.setUsuario(user);
		servidor.getUsuario().setStatus(false);
		servidor.getUsuario().getEstabelecimento().add(servidor.getEstabelecimento());

		servidor.getEmail().setNome(servidor.getNome());
		servidor.setUsuario(user);
		serviceServidor.cadastrar(servidor);

		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(servidor.getUsuario().getId());
		auditoria.setHistorico("Cadastramento de servidorComAcessoExterno: " + servidor.getNome());

		return "redirect:/admin/servidores/cadastroRealizadoSucesso";

	}

	@GetMapping(value = "**/servidoresExternoAoCicom/editar/{id}")
	public String edicaoDoServidor(@PathVariable("id") Long id,
									Model model,
									RedirectAttributes redirectAttributes) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (!model.containsAttribute("servidor")) {
			Servidor servidor = serviceServidor.buscaPorId(id).get();
			servidor.setEmail(serviceServidor.buscaPorId(id).get().getEmail());
			servidor.setUsuario(servidor.getUsuario());
			model.addAttribute("servidor", servidor);
			model.addAttribute("hierarquias",serviceHierarquia.buscarPorInstituicao(servidor.getInstituicao()));
		}
		model.addAttribute("usuario", user);
		model.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		model.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		model.addAttribute("funcoes", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.ACESSO_EXTERNO));
		model.addAttribute("linkCadastro", "/admin/cadastro/servidoresExternoAoCicom/alterar/" + id);
		model.addAttribute("erros");

		return "acessosExternos/editaUsuarioExterno";
	}

	@PostMapping("**/servidoresExternoAoCicom/alterar/{id}")
	public String alterarServidorComAcessoExterno(@Valid Servidor servidor,
												@PathVariable("id") Long id,
												Model model, 
												BindingResult result,
												RedirectAttributes redirectAttributes) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (result.hasErrors() ) {
//				for (FieldError error : result.getFieldErrors()) {
//				System.out.println(error.getField() + ": " + error.getDefaultMessage());
//			}
			redirectAttributes.addFlashAttribute("erros",
					"Existe erros no preenchimento, verifique se todos o campos estão preenchidos corretamente.");
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.email", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);
			redirectAttributes.addFlashAttribute("servidor", servidor);
			redirectAttributes.addFlashAttribute("estabelecimento", servidor.getEstabelecimento());
			redirectAttributes.addFlashAttribute("instituicao", servidor.getInstituicao());
			redirectAttributes.addFlashAttribute("hierarquias", servidor.getHierarquia());
			redirectAttributes.addFlashAttribute("sexo", servidor.getSexo());
			redirectAttributes.addFlashAttribute("usuario", user);
			model.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
			model.addAttribute("instituicoes", serviceInstituicao.listarTodos());
			if (servidor.getHierarquia()!= null) {
				model.addAttribute("hierarquias", serviceHierarquia.buscarPorInstituicao(servidor.getInstituicao()));
			}
			redirectAttributes.addFlashAttribute("linkCadastro", "/admin/cadastro/servidoresExternoAoCicom/salvar");

			return "acessosExternos/editaUsuarioExterno";
		}
		servidor.getEmail().setId(serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()).getId());
		servidor.getEmail().setNome(serviceEmail.buscarPeloEnderecoEmail(servidor.getEmail().getEnderecoDeEmail()).getNome());
		
		serviceServidor.alterar(id, servidor);

		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(servidor.getUsuario().getId());
		auditoria.setHistorico("Edicao de servidorComAcessoExterno: " + servidor.getNome());
		if (!model.containsAttribute("retorno")) {
			return "redirect:/404";
		}
		return "redirect:/admin/historico/servidores";

	}
}
