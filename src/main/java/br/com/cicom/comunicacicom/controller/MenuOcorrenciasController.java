package br.com.cicom.comunicacicom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.EstadoOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.EstadoOcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class MenuOcorrenciasController {

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private TipificacaoService serviceTipificacao;

	@Autowired
	private OcorrenciaService serviceOcorrencia;
	

	@Autowired
	private EstadoOcorrenciaService serviceEstadoOcorrencia;


	@Autowired
	private EmailService serviceEmail;

	@GetMapping("*/historico/ocorrencias")
	public String telaHistorico(Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Ocorrências");
		model.addAttribute("usuario", user);
		model.addAttribute("ocorrenciasLista", serviceOcorrencia.listaUltimos3Dias(user.getServidor().getEstabelecimento()));

		List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
		if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
			emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
		}
		model.addAttribute("emails", emails);
		
		return "fragmentos/historicos/historicoOcorrencias";
	}

	@GetMapping("**/ocorrencias/historico/emailEnviado")
	public ModelAndView telaHistoricoEmailEnviado(ModelAndView modelAndView,
			@RequestParam("page") Optional<Integer> page, @RequestParam(defaultValue = "") String parametroDePesquisa,
			@RequestParam("size") Optional<Integer> size) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Map<String, Permissao> mapa = new HashMap<>();
		for (int i = 0; i < user.getGrupo().getPermissoes().size(); i++) {
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(10);
		modelAndView.addObject("mappingCompleto", "/admin/cadastro/ocorrencias/historico");
		if (mapa.containsKey("ADMINISTRADOR")) {

			modelAndView.addObject("bookPage",
					serviceOcorrencia.buscaPaginada(parametroDePesquisa, PageRequest.of(currentPage, pageSize)));
		} else {
			modelAndView.addObject("bookPage", serviceOcorrencia.buscaPaginadaPorEstabelecimento(parametroDePesquisa,
					user.getServidor().getEstabelecimento(), PageRequest.of(currentPage, pageSize)));
		}

		modelAndView.addObject("emailEnviado", "ok");
		modelAndView.addObject("aviso", "Email enviado com Sucesso.");
		modelAndView.addObject("link", "/admin/historico/ocorrencias");

		modelAndView.addObject("ocorrencias",
				serviceOcorrencia.buscarTodasDe2DiasTrasPorEstabelecimento(user.getServidor().getEstabelecimento()));

		modelAndView.addObject("permissoes", mapa);
		modelAndView.addObject("verificador", "false");
		modelAndView.addObject("usuario", user);

		List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
		if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
			emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
		}
		modelAndView.addObject("emails", emails);

		modelAndView.setViewName("fragmentos/historicos/historicoOcorrencias");
		return modelAndView;
	}

	@GetMapping("*/pesquisa/ocorrencias")
	public String telaBuscar(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("ocorrencia", new Ocorrencia());
		model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
		if (user.getEstabelecimento().size() == 1) {
			model.addAttribute("cidades", user.getEstabelecimento().get(0).getCidades());
		} else {
			model.addAttribute("cicoms", user.getEstabelecimento());
		}
		model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());

		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Ocorrências");

		return "fragmentos/pesquisas/pesquisaOcorrencia";
	}
}
