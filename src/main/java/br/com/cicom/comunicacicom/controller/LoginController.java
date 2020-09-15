package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.GrupoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class LoginController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private ServidorService servicoServidor;

	@Autowired
	private GrupoService servicoGrupo;
 
	@Autowired
	private EmailService serviceEmail;

	@Autowired
	private AuditoriaService serviceAuditoria;
	
	@RequestMapping(value = {"/", "/login"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(String senhareset) {
		
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("tituloPagina", "ComunicaCICOM - Login");
		if(senhareset!=null) {
			switch (senhareset) {
			case "Autenticacao":
				modelAndView.addObject("senhareset", "Autenticacao");
				modelAndView.addObject("aviso",
						"A senha não pôde ser enviada por erro de autenticação de email contate seu Coordenador, para que ele siga o tutorial abaixo.");
				modelAndView.addObject("link", "/");
				break;
			case "Conexao":
				modelAndView.addObject("senhareset", "Conexao");
				modelAndView.addObject("aviso",
						"A senha não pôde ser enviada por erro de conexão com o servidor de emails contate o suporte.");
				modelAndView.addObject("link", "/");
				break;
			case "ok":
				modelAndView.addObject("senhareset", "ok");
				modelAndView.addObject("aviso", "A nova senha foi gerada com sucesso. Verifique a caixa de entrada de sua conta de e-mail.");
				modelAndView.addObject("link", "/");
			}
		}
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@PostMapping(value = "/recuperaSenhaUsuario")
	public ModelAndView recuperaSenhadoUsuario(@Valid Usuario user, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario userExists = servicoUsuario.buscaPeloLogin(user.getLogin());
		if (userExists != null) {
			if (userExists.getServidor() != null) {
				Usuario userBanco = servicoUsuario.buscaPeloLogin(user.getLogin());
				if (userExists.getServidor().getCpf().equals(user.getServidor().getCpf())) {
					Auditoria auditoria = new Auditoria();
					auditoria.setDatahora(LocalDateTime.now());
					auditoria.setUsuario(userBanco.getId());
					auditoria.setHistorico("Reset de senha. ");
					serviceAuditoria.cadastrar(auditoria);

					String resultado = servicoUsuario.gerarSenhaAleatoria(userExists.getId(), userExists, serviceEmail); 

					switch (resultado) {
					case "Autenticacao":
						modelAndView.addObject("login", "O cadastro não foi concluido, mantenha contato com o suporte!");
						modelAndView.setViewName("login");
						return this.index("Autenticacao");
					case "Conexao":
						modelAndView.addObject("login", "O cadastro não foi concluido, erro de conexão, mantenha contato com o suporte!");
						modelAndView.setViewName("login");
						return this.index("Conexao");
					case "Enviado":
						modelAndView.setViewName("login");
						return this.index("ok");
					}
				} else {
					modelAndView.addObject("cpf", "CPF não confere!");
					modelAndView.setViewName("recupera");
				}
			} else {
				modelAndView.addObject("login", "O cadastro não foi concluido, mantenha contato com o suporte!");
				modelAndView.setViewName("recupera");
			}
		} else {
			modelAndView.addObject("login", "Matricula não entrontrada!");
			modelAndView.setViewName("recupera");
		}
		return modelAndView;
	}

	@PostMapping(value = "/cadastrarUsuario")
	public ModelAndView adicionarNovoUsuario(@Valid Usuario user, BindingResult result, String senha, String confirmarSenha, HttpServletRequest req) {
		
		ModelAndView modelAndView = new ModelAndView();
		Usuario userExists = servicoUsuario.buscaPeloLogin(user.getLogin());
		
		String ipAddress = req.getHeader("X-FORWARDED-FOR");
		String interno = req.getParameter("interno");
		
		if (ipAddress == null) {
			ipAddress = req.getRemoteAddr();
		}
		
		if (interno == null) {
			modelAndView.addObject("aviso", "Informar se é um servidor interno ou externo ao CICOM é obrigatório!");
			modelAndView.setViewName("registro");
			return modelAndView;
		}
		
		if (!(ipAddress.substring(0,6).equals("10.48.")) && interno.equals("true")) {
			modelAndView.addObject("aviso", "Servidores internos só podem ser cadastrados de dentro de CICOMs!");
			modelAndView.setViewName("registro");
			return modelAndView;
		}
	
		if (!senha.equals(confirmarSenha)) {
			modelAndView.addObject("aviso", "As senhas não conferem!");
			modelAndView.setViewName("registro");
			return modelAndView;
		}
		if (userExists != null) {
			modelAndView.addObject("login", "Matricula já cadastrada");
			modelAndView.setViewName("registro");
		} else if (servicoServidor.buscarMatricula(user.getLogin()) != null) {
			Servidor servidor = servicoServidor.buscarMatricula(user.getLogin());
			user.setGrupo(servicoGrupo.buscaPorNome("PRIMEIRO-LOGIN"));
			user.setStatus(true);
			servicoUsuario.cadastrar(user);
			servidor.setUsuario(servicoUsuario.buscaPeloLogin(user.getLogin()));
			servicoServidor.alterar(servidor.getId(), servidor);
			modelAndView.setViewName("redirect:/");
		} else {
			Servidor primeiroLogin = new Servidor();
			if(!interno.equals("true")) {
				user.setGrupo(servicoGrupo.buscaPorNome("PRIMEIRO-LOGIN-EXTERNO"));
			}else {
				user.setGrupo(servicoGrupo.buscaPorNome("PRIMEIRO-LOGIN"));
			}
			servicoUsuario.cadastrar(user);
			primeiroLogin.setUsuario(servicoUsuario.buscaPeloLogin(user.getLogin()));
			modelAndView.setViewName("redirect:/");
		}
		return modelAndView;
		
	}

	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("usuario", new Usuario());
		modelAndView.addObject("grupos", servicoGrupo.listarTodos());
		modelAndView.setViewName("registro");
		return modelAndView;
	}
	
	@RequestMapping(value = "/contato", method = RequestMethod.GET)
	public ModelAndView contato(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("usuario", null);
		modelAndView.addObject("usuario", null);
		modelAndView.setViewName("/form_contato");
		session.invalidate();
		return modelAndView;
	}
	

	@RequestMapping(value = "/recupera", method = RequestMethod.GET)
	public ModelAndView retreive() {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		usuario.setServidor(new Servidor());
		usuario.getServidor().setCpf("");
		modelAndView.addObject("usuario", usuario);
		modelAndView.setViewName("recupera");
		return modelAndView;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("login");
		session.setAttribute("usuario", null);
		modelAndView.addObject("usuario", null);
		session.invalidate();
		return modelAndView;
	}
}
