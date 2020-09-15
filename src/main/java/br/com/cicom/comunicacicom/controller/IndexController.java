package br.com.cicom.comunicacicom.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import br.com.cicom.comunicacicom.DSArquivo.service.ArquivoService;
import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.model.TrocadorDeEstabelecimentoParaExibicao;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.UfService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EscolaridadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EstadoCivilService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EtniaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.NacionalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.TipoSanguineoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class IndexController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private AuditoriaService serviceAuditoria;

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private OcorrenciaService serviceOcorrencia;

	@Autowired
	private EmailService serviceEmail;

	@Autowired
	private UfService serviceUf;

	@Autowired
	private EstabelecimentoService serviceEstabelecimento;

	@Autowired
	private InstituicaoService serviceInstituicao;

	@Autowired
	private FuncaoService serviceFuncao;

	@Autowired
	private EstadoCivilService serviceEstadoCivil;

	@Autowired
	private EscolaridadeService serviceEscolaridade;

	@Autowired
	private TipoSanguineoService serviceTipoSanguineo;

	@Autowired
	private EtniaService serviceEtnia;

	@Autowired
	private NacionalidadeService serviceNacionalidade;
	
	@Autowired
	private ArquivoService serviceArquivo;


	@RequestMapping(value = "/redirecionaUsuario", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView redirecionaUsuario(HttpSession session, ModelAndView model, HttpServletRequest req) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user == null) {
			model.addObject("erro", true);
			model.addObject("mensagem", "Foi encontrado um erro!");
			model.setView(new RedirectView("/"));
			model.setViewName("login");
			return model;
		}
		
		String ipAddress = req.getHeader("X-FORWARDED-FOR");

		if (ipAddress == null) {
			ipAddress = req.getRemoteAddr();
		}
		
		model.addObject("usuario", user);

		if (!model.getModel().containsKey("estabelecimento")) {
			if(user.getEstabelecimento() != null) {
				model.addObject("estabelecimentos", user.getEstabelecimento());
			}else {
				model.addObject("estabelecimento", new Estabelecimento());
			}
		}
		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(user.getId());
		
		
		Map<String, Permissao> mapa = new HashMap<>();

		for (int i = 0; i < user.getGrupo().getPermissoes().size(); i++) {
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}


		if (mapa.containsKey("PRIMEIRO-LOGIN") || mapa.containsKey("PRIMEIRO-LOGIN-EXTERNO") ) {
			
			if(mapa.containsKey("PRIMEIRO-LOGIN-EXTERNO")) {
				auditoria.setHistorico("Primeiro Login externo. " + ipAddress);
				model.addObject("funcoes", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.ACESSO_EXTERNO));
			}else {
				model.addObject("funcoes", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_INTERNO));
				auditoria.setHistorico("Primeiro Login. " + ipAddress);
			}
			
			model.addObject("ufs", serviceUf.listarTodos());
			model.addObject("estabelecimentos", serviceEstabelecimento.listarTodos());
			model.addObject("instituicoes", serviceInstituicao.listarTodos());
			model.addObject("estadoCivis", serviceEstadoCivil.listarTodos());
			model.addObject("escolaridades", serviceEscolaridade.listarTodos());
			model.addObject("tipoSanguineos", serviceTipoSanguineo.listarTodos());
			model.addObject("etnias", serviceEtnia.listarTodos());
			model.addObject("nacionalidades", serviceNacionalidade.listarTodos());

			if(!mapa.containsKey("PRIMEIRO-LOGIN-EXTERNO")) {
				model.addObject("erros");
			}
			
			if (user.getServidor() != null){
				
				model.addObject("usuario", user);
				model.addObject("EmailDuplicado", "");
				model.addObject("hierarquias", user.getServidor().getInstituicao().getHierarquias());
				model.addObject("cidades", user.getServidor().getEstabelecimento().getCidades());
				model.addObject("servidorProprio", "Ok");
				if(mapa.containsKey("PRIMEIRO-LOGIN-EXTERNO")) {
					model.addObject("linkCadastro", "/admin/cadastros/servidores/alterar/" + user.getServidor().getId());
				}
				model.addObject("servidor", user.getServidor());
				
				model.setView(new RedirectView("/admin/edita/servidor"));
				//model.setViewName("principal/editar_conta1");
			}else{
				Servidor servidor = new Servidor();
				servidor.setUsuario(user);
				model.addObject("servidor", servidor);
				if(mapa.containsKey("PRIMEIRO-LOGIN-EXTERNO")) {
					model.setViewName("redirect:/admin/cadastros/servidoresExternoAoCicom/cadastro");

				}else {
					model.setViewName("fragmentos/cadastros/cadastraServidor");
					model.addObject("linkCadastro", "/admin/salva/cadastro/servidor");				

				}
			}
			
			
		} else if (mapa.containsKey("USUARIO EXTERNO")) {
			auditoria.setHistorico("Acesso ao sistema: " + ipAddress);
			model.setView(new RedirectView("/404"));
		}else{
			auditoria.setHistorico("Acesso ao sistema: " + ipAddress);
			model.setView(new RedirectView("/admin/home/"));
		} 
		serviceAuditoria.cadastrar(auditoria);
		
		return model;
				
	}

	@RequestMapping(value = "/admin/home/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session, ModelAndView model, HttpServletRequest req) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user == null) {
			model.addObject("erro", true);
			model.addObject("mensagem", "Foi encontrado um erro!");

			model.setView(new RedirectView("/"));
			model.setViewName("login");

			return model;
		}

		Map<String, Permissao> mapa = new HashMap<>();

		for (int i = 0; i < user.getGrupo().getPermissoes().size(); i++) {
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}

		model.addObject("permissoes", mapa);
		model.addObject("usuario", user);

		if (!model.getModel().containsKey("estabelecimento")) {
			model.addObject("estabelecimento", new Estabelecimento());
		}

		// Redireciona os usuários para a página inicial deles
		if (mapa.containsKey("PRIMEIRO-LOGIN")) {
			if (user.getServidor() != null) {
				model.setView(new RedirectView("/admin/servidores/editar"));
				model.setViewName("principal/editar_conta1");
			} else {
				model.setView(new RedirectView("/admin/servidores/cadastro"));
				model.setViewName("fragmentos/cadastros/servidor");
			}

		} else if (mapa.containsKey("TELA_INICIAL_HISTORICO_OCORRENCIAS")) {

			model.addObject("usuario", user);

			List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
			if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
				emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
			}
			model.addObject("emails", emails);

			model.setView(new RedirectView("/admin/home/"));
			model.setViewName("fragmentos/historicos/historicoOcorrencias");
			model.addObject("ocorrenciasLista", serviceOcorrencia.listaUltimos3Dias(user.getServidor().getEstabelecimento()));
			model.addObject("tituloPagina", "ComunicaCICOM - Relação de Ocorrências");

		} else if (mapa.containsKey("TELA_INICIAL_GRAFICOS")) {

			model.setView(new RedirectView("/admin/home/"));
			model.setViewName("grafico_ocorrencia");
			model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Ocorrência - Tipificação");

		}else if (mapa.containsKey("TELA_INICIAL_GRAFICOS_GERAL")) {
			//model.setView(new RedirectView("/admin/home/"));
			model.setViewName("grafico_ocorrencia_geral");
			
			TrocadorDeEstabelecimentoParaExibicao geradorDeId = TrocadorDeEstabelecimentoParaExibicao.criar();
			geradorDeId.setListaDeIds(user);
			
			model.addObject("geradorDeId", geradorDeId);
			model.addObject("tituloPagina", "ComunicaCICOM - Dashboard Ocorrência - Geral");
			return model;
		}
						
		return model;

	}
	
	@RequestMapping(value = "/accesso_negado", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView model) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addObject("usuario",user);
		model.setViewName("acesso_negado");
						
		return model;

	}
 
	/* 	Por Anderson Kroger Cerqueira 28/07/2020
	 * 	Atendendo Necessidade de certificação do dominio para emissão de certticado Ssl do dominio comunica.stelecom.ba.gov.br 
	 */
	@GetMapping(value = "/.well-known/pki-validation/{filename}")
	public void pkivalidation(@PathVariable("filename") String filename, final HttpServletResponse response) throws ServletException, IOException  {
		byte[] files = serviceArquivo.buscaPorId((long) 19).get().getArquivo();
		response.setContentType("application/file");
		response.setHeader("Content-Disposition", "inline ; filename=" + serviceArquivo.buscaPorId((long) 19).get().getNome());
		response.setContentLength(files.length);
		
		final ServletOutputStream output = response.getOutputStream();
		output.write(files, 0, files.length);
		output.close();

	}
	
}