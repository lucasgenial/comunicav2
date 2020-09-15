package br.com.cicom.comunicacicom.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.model.Formatador;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.Cecoco;
import br.com.cicom.comunicacicom.DSPrimary.model.importacao.CecocoLog;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.OcorrenciaLog;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom10LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom10Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom11LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom11Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom12LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom12Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom13LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom13Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom14LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom14Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom15LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom15Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom16LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom16Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom17LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom17Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom18LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom18Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom19LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom19Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom1LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom1Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom20LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom20Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom22LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom22Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom23LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom23Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom2LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom2Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom3LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom3Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom4LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom4Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom5LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom5Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom6LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom6Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom7LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom7Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom8LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom8Service;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom9LogService;
import br.com.cicom.comunicacicom.DSPrimary.service.importacao.Cicom9Service;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.BairroService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.LocalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.EstadoOcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaLogService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class OcorrenciaController {
	
	@Autowired
	private OcorrenciaService serviceOcorrencia;
	
	@Autowired 
	private OcorrenciaLogService serviceOcorrenciaLog;
	
	@Autowired
	private AuditoriaService serviceAuditoria;

	@Autowired
	private CidadeService serviceCidade;

	@Autowired
	private LocalidadeService serviceLocalidade;

	@Autowired
	private BairroService serviceBairro;

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private TipificacaoService serviceTipificacao;

	@Autowired
	private EstadoOcorrenciaService serviceEstadoOcorrencia;

	@Autowired
	private EstabelecimentoService serviceEstabelecimento;

	@Autowired
	private EmailService serviceEmail;

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private Cicom10Service serviceCicom10;

	@Autowired
	private Cicom11Service serviceCicom11;
	
	@Autowired
	private Cicom12Service serviceCicom12;
	
	@Autowired
	private Cicom13Service serviceCicom13;
	
	@Autowired
	private Cicom14Service serviceCicom14;
	
	@Autowired
	private Cicom15Service serviceCicom15;
	
	@Autowired
	private Cicom16Service serviceCicom16;
	
	@Autowired
	private Cicom17Service serviceCicom17;
	
	@Autowired
	private Cicom18Service serviceCicom18;
	
	@Autowired
	private Cicom19Service serviceCicom19;
	
	@Autowired
	private Cicom1Service serviceCicom1;
	
	@Autowired
	private Cicom20Service serviceCicom20;
	
	@Autowired
	private Cicom22Service serviceCicom22;
	
	@Autowired
	private Cicom23Service serviceCicom23;
	
	@Autowired
	private Cicom2Service serviceCicom2;
	
	@Autowired
	private Cicom3Service serviceCicom3;
	
	@Autowired
	private Cicom4Service serviceCicom4;
	
	@Autowired
	private Cicom5Service serviceCicom5;
	
	@Autowired
	private Cicom6Service serviceCicom6;
	
	@Autowired
	private Cicom7Service serviceCicom7;
	
	@Autowired
	private Cicom8Service serviceCicom8;
	
	@Autowired
	private Cicom9Service serviceCicom9;
	
	@Autowired
	private Cicom1LogService serviceCicom1Log;
	
	@Autowired
	private Cicom2LogService serviceCicom2Log;
	
	@Autowired
	private Cicom3LogService serviceCicom3Log;
	
	@Autowired
	private Cicom4LogService serviceCicom4Log;
	
	@Autowired
	private Cicom5LogService serviceCicom5Log;
	
	@Autowired
	private Cicom6LogService serviceCicom6Log;
	
	@Autowired
	private Cicom7LogService serviceCicom7Log;
	
	@Autowired
	private Cicom8LogService serviceCicom8Log;
	
	@Autowired
	private Cicom9LogService serviceCicom9Log;
	
	@Autowired
	private Cicom10LogService serviceCicom10Log;
	
	@Autowired
	private Cicom11LogService serviceCicom11Log;
	
	@Autowired
	private Cicom12LogService serviceCicom12Log;
	
	@Autowired
	private Cicom13LogService serviceCicom13Log;
	
	@Autowired
	private Cicom14LogService serviceCicom14Log;
	
	@Autowired
	private Cicom15LogService serviceCicom15Log;
	
	@Autowired
	private Cicom16LogService serviceCicom16Log;
	
	@Autowired
	private Cicom17LogService serviceCicom17Log;
	
	@Autowired
	private Cicom18LogService serviceCicom18Log;
	
	@Autowired
	private Cicom19LogService serviceCicom19Log;
	
	@Autowired
	private Cicom20LogService serviceCicom20Log;
	
	@Autowired
	private Cicom22LogService serviceCicom22Log;
	
	@Autowired
	private Cicom23LogService serviceCicom23Log;
	
	@ResponseBody
	@GetMapping("**/ocorrencias/consultar/{id}")
	public Ocorrencia consultarOcorrencia(@PathVariable("id") Long id, ModelAndView model) {
		return serviceOcorrencia.buscaPorId(id).get();
	}

	@ResponseBody
	@GetMapping("**/ocorrencias/consultarEndereco/{id}")
	public Endereco consultarEndereco(@PathVariable("id") Long id, ModelAndView model) {
		return serviceOcorrencia.buscaPorId(id).get().getEndereco();
	}

	@ResponseBody
	@RequestMapping(value = "/ocorrencias/buscar/historico", method = { RequestMethod.POST, RequestMethod.GET})
	public List<Map<String, Object>> listPOST(DataTablesInput input){ 
		  Usuario user =  servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext(). getAuthentication().getName());
		  return serviceOcorrencia.listaUltimos3Dias(user.getServidor().getEstabelecimento());
	  }
	
	/*
	 * public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input)
	 * { Usuario user =
	 * servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().
	 * getAuthentication().getName()); return
	 * serviceOcorrencia.listarTodasOcorrencias(input, user); }
	 */

	@GetMapping(value = { "*/editar/ocorrencias/{id}", "*/ocorrencias/altera/{id}" })
	public String cadastrarOcorrencias(@PathVariable("id") Long id, Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);

		Optional<Ocorrencia> ocorrenciaOptinal = serviceOcorrencia.buscaPorId(id);

		if (!ocorrenciaOptinal.isPresent()) {
			model.addAttribute("tituloPagina", "Comunica CICOM");
			model.addAttribute("msgErro", "Ocorrência Inválida, retorne a página inicial do sistema!");
			return "pagina404";

		} else {
			Ocorrencia ocorrencia = ocorrenciaOptinal.get();

			model.addAttribute("ocorrencia", ocorrencia);

			// Lista os Estabelecimentos
			model.addAttribute("estabelecimento", user.getEstabelecimento());

			// Lista de Cidades
			model.addAttribute("cidades", ocorrencia.getEstabelecimento().getCidades());

			// Lista de Localidades da Cidade da Ocorrencia
			model.addAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());

			// Lista dos Bairros da Localidade
			model.addAttribute("bairros", serviceBairro.buscaPorLocalidade(ocorrencia.getEndereco().getBairro().getLocalidade()));

			model.addAttribute("servidor", ocorrencia.getServidor());
		}

		model.addAttribute("bloqueiasic", "sim");
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Ocorrência");

		model.addAttribute("link", "/admin/cadastro/ocorrencia/alteraOcorrencia/" + id);
		model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
		model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
		model.addAttribute("bloqueiasic", "sim");

		return "fragmentos/cadastros/ocorrencia";

	}

	@GetMapping("*/cadastra/ocorrencia")
	public String telaCadastro(Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		model.addAttribute("estabelecimentos", user.getEstabelecimento());

		// Verifica se já foi passado a ocorrência
		if (!model.containsAttribute("ocorrencia")) {

			Ocorrencia ocorrencia = new Ocorrencia();
			ocorrencia.setDataOcorrencia(LocalDateTime.now());
			ocorrencia.setDataDaCriacao(LocalDateTime.now());
			ocorrencia.setDataUltimaModificao(LocalDateTime.now());

			List<OcorrenciaLog>  ocorrencialog = new ArrayList<OcorrenciaLog>();
			ocorrencia.setOcorrencialog(ocorrencialog);
			model.addAttribute("ocorrencia", ocorrencia );

			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setNome("Selecionar");
			model.addAttribute("estabelecimento", estabelecimento);

			Localidade localidade = new Localidade();
			localidade.setNome("Selecionar");
			model.addAttribute("localidade", localidade);

			Bairro bairro = new Bairro();
			bairro.setNome("Selecionar");
			model.addAttribute("bairro", bairro);
			model.addAttribute("link", "/admin/salva/cadastro/ocorrencia");
		}else {
			model.addAttribute("link", "/admin/salva/cadastro/ocorrencia");
		}

		if (user.getEstabelecimento().size() == 1) {
			model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
			model.addAttribute("estabelecimento", user.getEstabelecimento());
		} else {
			model.addAttribute("estabelecimentos", user.getEstabelecimento());
		}
		model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
		model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
		model.addAttribute("atualizar", null);

		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Ocorrência");

		return "fragmentos/cadastros/ocorrencia";
	}

	@GetMapping("**/ocorrencias/importarOcorrencia/{sic}")
	public String importarOcorrencia(@PathVariable("sic") String sic, Ocorrencia ocorrencia, Model model) {
		
		if (sic.isEmpty()) {
			return "fragmentos/cadastros/ocorrencia";
		}

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		model.addAttribute("link", "/admin/salva/cadastro/ocorrencia");

		List<Ocorrencia> ocorrenciaComunica = serviceOcorrencia.buscarPorSICeEstabelecimento(sic, user.getEstabelecimento());

		if (!ocorrenciaComunica.isEmpty()) {

			ocorrencia = ocorrenciaComunica.get(0);

			model.addAttribute("link", "/admin/cadastro/ocorrencia/alteraOcorrencia/" + ocorrencia.getId());
			model.addAttribute("bloqueiasic", "sim");
			model.addAttribute("Aviso", "Ocorrencia já cadastrada!");
			model.addAttribute("atualizar", "sim");
			model.addAttribute("ocorrencia", ocorrencia);
			model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
			model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
			model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
			model.addAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());
			model.addAttribute("bairros", ocorrencia.getEndereco().getBairro().getLocalidade().getBairros());
			model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Importa Ocorrência");

			return "fragmentos/cadastros/ocorrencia";

		}

		/********
		 * Funcionalidade para importar ocorrencias de origens diferentes
		 */

		try {

			// Titulo da Página
			model.addAttribute("tituloPagina", "ComunicaCICOM - Importa Ocorrência");
			Cecoco cecoco = new Cecoco();
			List<CecocoLog> cecocolog = new ArrayList<CecocoLog>();
			Cecoco importa = (Cecoco) cecoco.importa(Long.parseLong(sic), user.getEstabelecimento().get(0).getId(),serviceCicom10, serviceCicom11, serviceCicom12,
					serviceCicom13, serviceCicom14, serviceCicom15, serviceCicom16, serviceCicom17, serviceCicom18, serviceCicom19, serviceCicom1,
					serviceCicom20, serviceCicom22, serviceCicom23, serviceCicom2, serviceCicom3, serviceCicom4, serviceCicom5, serviceCicom6, 
					serviceCicom7, serviceCicom8, serviceCicom9);
			// Verifica se já foi passado a ocorrência
			if (importa.getId().equals((long) 0)) {
				model.addAttribute("bloqueiasic", null);
				model.addAttribute("Aviso", "Verifique o numero da ocorrencia!");
				return "fragmentos/cadastros/ocorrencia";
			} else
				if ( !importa.getId().equals((long) 0) && !importa.getTipo().equals("_DUPLICIDADE") && !importa.getTipo().equals("OCORRÊNCIA DESCARTADA") ) {
					ocorrencia = importa.converte(serviceTipificacao, serviceEstadoOcorrencia, serviceCidade, serviceLocalidade, serviceBairro, serviceEstabelecimento, importa);
					cecocolog =  (List<CecocoLog>) importa.importaLog(Long.parseLong(ocorrencia.getSic()), user.getEstabelecimento().get(0), serviceCicom1Log, serviceCicom2Log, serviceCicom3Log, serviceCicom4Log, serviceCicom5Log, serviceCicom6Log, serviceCicom7Log, serviceCicom8Log, serviceCicom9Log, serviceCicom10Log, serviceCicom11Log, serviceCicom12Log, serviceCicom13Log, serviceCicom14Log, serviceCicom15Log, serviceCicom16Log, serviceCicom17Log, serviceCicom18Log, serviceCicom19Log, serviceCicom20Log, serviceCicom22Log, serviceCicom23Log);
					if (cecocolog.size() > 0) {
						List<OcorrenciaLog> ocorrenciaLog = importa.convertLog( cecocolog);
						for (OcorrenciaLog log : ocorrenciaLog) {
							log.setOcorrencia(ocorrencia);
						}
						ocorrencia.getOcorrencialog().addAll(ocorrenciaLog);
					}
					model.addAttribute("ocorrencia", ocorrencia);
					model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
					model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
					model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
					if (ocorrencia.getEndereco().getCidade().equals(null)) {
						model.addAttribute("error_importacao", true);
					}
					if (ocorrencia.getEndereco().getCidade() != null) {
						model.addAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());
						if (ocorrencia.getEndereco().getBairro().getLocalidade() !=null ) {
								model.addAttribute("bairros", ocorrencia.getEndereco().getBairro().getLocalidade().getBairros());
						}
					}
				model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
				model.addAttribute("bloqueiasic", "sim");
			} else
				if(importa.getTipo().equals("_DUPLICIDADE") || importa.getTipo().equals("OCORRÊNCIA DESCARTADA")) {
					model.addAttribute("bloqueiasic", null);
					model.addAttribute("Aviso", "Verifique o numero da ocorrencia!");
				}
		} catch (Exception e) {
			model.addAttribute("error_importacao", true);
			model.addAttribute("bloqueiasic", "sim");
			model.addAttribute("Aviso", "Verifique os campos de preenchimento obrigatórios!");
			model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
			model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());

			Localidade localidade = new Localidade();
			localidade.setNome("Selecionar");
			Bairro bairro = new Bairro();
			bairro.setNome("Selecionar");
			model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());

			return "fragmentos/cadastros/ocorrencia";
		}

		if (servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName())
				.getEstabelecimento().size() == 1) {
			model.addAttribute("cidades",
					servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName())
							.getServidor().getEstabelecimento().getCidades());
		} else {
			model.addAttribute("cicoms",
					servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName())
							.getEstabelecimento());
		}

		return "fragmentos/cadastros/ocorrencia";
	}

	@GetMapping("**/ocorrencias/atualizarOcorrencia/{sic}")
	public String atualizarOcorrencia(@PathVariable("sic") String sic, Ocorrencia ocorrencia, Model model) {

		if (sic.isEmpty()) {
			return "fragmentos/cadastros/ocorrencia";
		}

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<Ocorrencia> ocorrenciaOld = serviceOcorrencia.buscarPorSICeEstabelecimento(sic, user.getEstabelecimento());
		model.addAttribute("usuario", user);
		model.addAttribute("link", "/admin/cadastro/ocorrencia/alteraOcorrencia/" + ocorrenciaOld.get(0).getId());
		model.addAttribute("Aviso", "Para confirmar a atualização click em salvar ocorrência!");
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Atualiza Ocorrência");

		/********
		 * Funcionalidade para atualizar ocorrencias ja importada de origens diferentes
		 */

		try {

			Cecoco cecoco = new Cecoco();
			List<CecocoLog> cecocolog = new ArrayList<CecocoLog>();
			Cecoco importa1 = (Cecoco) cecoco.importa(Long.parseLong(sic), user.getEstabelecimento().get(0).getId(), serviceCicom10, serviceCicom11, serviceCicom12, 
					serviceCicom13, serviceCicom14, serviceCicom15, serviceCicom16, serviceCicom17, serviceCicom18, serviceCicom19, serviceCicom1, 
					serviceCicom20, serviceCicom22, serviceCicom23, serviceCicom2, serviceCicom3, serviceCicom4, serviceCicom5, serviceCicom6, serviceCicom7, 
					serviceCicom8, serviceCicom9);

			if (importa1.getId().equals((long) 0)) {
				model.addAttribute("bloqueiasic", "sim");
				model.addAttribute("Aviso", "Ocorrencia não disponível para atualização!");
				return "fragmentos/cadastros/ocorrencia";
			} else
			if (!importa1.getId().equals((long) 0) && !importa1.getTipo().equals("_DUPLICIDADE") && !importa1.getTipo().equals("OCORRÊNCIA DESCARTADA")) {
				ocorrencia = importa1.converte(serviceTipificacao, serviceEstadoOcorrencia, serviceCidade, serviceLocalidade, serviceBairro, serviceEstabelecimento, importa1);
				cecocolog =  (List<CecocoLog>) importa1.importaLog(Long.parseLong(ocorrencia.getSic()), user.getEstabelecimento().get(0), serviceCicom1Log, serviceCicom2Log, serviceCicom3Log, serviceCicom4Log, serviceCicom5Log, serviceCicom6Log, serviceCicom7Log, serviceCicom8Log, serviceCicom9Log, serviceCicom10Log, serviceCicom11Log, serviceCicom12Log, serviceCicom13Log, serviceCicom14Log, serviceCicom15Log, serviceCicom16Log, serviceCicom17Log, serviceCicom18Log, serviceCicom19Log, serviceCicom20Log, serviceCicom22Log, serviceCicom23Log);
				if (cecocolog.size() > 0) {
					List<OcorrenciaLog> ocorrenciaLog = importa1.convertLog( cecocolog);
					for (OcorrenciaLog log : ocorrenciaLog) {
						log.setOcorrencia(ocorrencia);
					}
					ocorrencia.getOcorrencialog().addAll(ocorrenciaLog);
				}
				ocorrencia.setGuarnicao(ocorrenciaOld.get(0).getGuarnicao());
				ocorrencia.getEndereco().setCidade(ocorrenciaOld.get(0).getEndereco().getCidade());
				ocorrencia.getEndereco().setBairro(ocorrenciaOld.get(0).getEndereco().getBairro());
				ocorrencia.getEndereco().getBairro().setLocalidade(ocorrenciaOld.get(0).getEndereco().getBairro().getLocalidade());
				model.addAttribute("ocorrencia", ocorrencia);
				model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
				model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
				model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
				if (ocorrencia.getEndereco().getCidade().equals(null)) {
						model.addAttribute("error_importacao", true);
				}
				if (ocorrencia.getEndereco().getCidade() != null) {
					model.addAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());
					if (ocorrencia.getEndereco().getBairro().getLocalidade() != null) {
						model.addAttribute("bairros", ocorrencia.getEndereco().getBairro().getLocalidade().getBairros());
					}
				}
				model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
				model.addAttribute("bloqueiasic", "sim");
			} else {
				model.addAttribute("ocorrencia", ocorrenciaOld.get(0));
				model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
				model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
				if (ocorrenciaOld.get(0).getEndereco().getCidade() != null) {
					model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
					model.addAttribute("localidades", ocorrenciaOld.get(0).getEndereco().getCidade().getLocalidades());
					model.addAttribute("bairros", ocorrenciaOld.get(0).getEndereco().getBairro().getLocalidade().getBairros());
				}
				model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
				model.addAttribute("bloqueiasic", "sim");
				model.addAttribute("Aviso", "Ocorrencia não disponível para atualização!");
				return "fragmentos/cadastros/ocorrencia";
			}
		} catch (Exception e) {
			model.addAttribute("error_importacao", true);
			model.addAttribute("bloqueiasic", "sim");
			model.addAttribute("Aviso", "Verifique os campos de preenchimento obrigatórios!");
			model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
			model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
			if (ocorrencia.getEndereco().getCidade() != null) {
				model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
				if(ocorrenciaOld.get(0).getEndereco().getBairro().getLocalidade()!= null) {
					model.addAttribute("localidades", ocorrenciaOld.get(0).getEndereco().getCidade().getLocalidades());
					if(ocorrenciaOld.get(0).getEndereco().getBairro()!= null) {
						model.addAttribute("bairros", ocorrenciaOld.get(0).getEndereco().getBairro().getLocalidade().getBairros());
					}
				} else {
					Bairro bairro = new Bairro();
					bairro.setNome("Selecionar");
				}
			}else {
				Localidade localidade = new Localidade();
				localidade.setNome("Selecionar");
				model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
			}
			model.addAttribute("estabelecimento", user.getServidor().getEstabelecimento());
			return "fragmentos/cadastros/ocorrencia";
		}

		if (servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento().size() == 1) {
			model.addAttribute("cidades", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getServidor().getEstabelecimento().getCidades());
		} else {
			model.addAttribute("cicoms", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento());
		}
		return "fragmentos/cadastros/ocorrencia";
	}

	@GetMapping("**/ocorrencias/altera/{id}")
	public String ocorrenciaAltera(Model model, @PathVariable("id") Long id) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		// Verifica se já foi passado a ocorrência
		if (!model.containsAttribute("ocorrencia")) {

			if (user.getEstabelecimento().size() == 1) {
				model.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
			} else {
				model.addAttribute("cicoms", user.getEstabelecimento());
			}
		}
		model.addAttribute("link", "/admin/cadastro/ocorrencia/alteraOcorrencia/" + id);
		model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());
		model.addAttribute("tipificacoes", serviceTipificacao.listarTodos());
		model.addAttribute("bloqueiasic", "sim");
		model.addAttribute("atualizar", null);

		return "fragmentos/cadastros/ocorrencia";
	}

	@PostMapping("*/salva/cadastro/ocorrencia")
	@Transactional(rollbackFor = { Exception.class })
	public String cadastrarOcorrencia(@Valid Ocorrencia ocorrencia, BindingResult result, RedirectAttributes redirectAttributes) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ocorrencia", result);
			redirectAttributes.addFlashAttribute("ocorrencia", ocorrencia);
			redirectAttributes.addFlashAttribute("estabelecimento", ocorrencia.getEstabelecimento());
			redirectAttributes.addFlashAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());
			redirectAttributes.addFlashAttribute("bairros", serviceBairro.buscaPorLocalidadeSort(ocorrencia.getEndereco().getBairro().getLocalidade()));
			redirectAttributes.addFlashAttribute("servidor", ocorrencia.getServidor());
			return "redirect:/admin/cadastra/ocorrencia";
		}
		Cecoco importa = new Cecoco();
		List<CecocoLog> cecocolog = new ArrayList<CecocoLog>();
		cecocolog =  (List<CecocoLog>) importa.importaLog(Long.parseLong(ocorrencia.getSic()), user.getEstabelecimento().get(0), serviceCicom1Log, serviceCicom2Log, serviceCicom3Log, serviceCicom4Log, serviceCicom5Log, serviceCicom6Log, serviceCicom7Log, serviceCicom8Log, serviceCicom9Log, serviceCicom10Log, serviceCicom11Log, serviceCicom12Log, serviceCicom13Log, serviceCicom14Log, serviceCicom15Log, serviceCicom16Log, serviceCicom17Log, serviceCicom18Log, serviceCicom19Log, serviceCicom20Log, serviceCicom22Log, serviceCicom23Log);
		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(
		servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
		auditoria.setHistorico("Cadastrada Ocorrencia Nº: " + ocorrencia.getSic().toString());
		serviceAuditoria.cadastrar(auditoria);
		serviceOcorrencia.cadastrar(ocorrencia);
		if (cecocolog.size() > 0) {
			List<OcorrenciaLog> ocorrenciaLog = importa.convertLog( cecocolog);
			ocorrencia.getOcorrencialog().addAll(ocorrenciaLog);
			for (OcorrenciaLog log : ocorrenciaLog) {
				log.setOcorrencia(ocorrencia);
			}
			serviceOcorrencia.alterar(ocorrencia.getId(), ocorrencia);
		}
		return "redirect:/admin/historico/ocorrencias";
	}

	@GetMapping("**/ocorrencias/enviarVarias/email")
	public String enviarOcorrencia(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("ocorrencias", serviceOcorrencia.buscarTodasDe2DiasTrasPorEstabelecimento(user.getServidor().getEstabelecimento()));
		List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
		if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
			emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
		}
		model.addAttribute("emails", emails);
		model.addAttribute("usuario", user);
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Enviar várias ocorrências para vários e-mails");

		return "fragmentos/utilitarios/enviar-variasOcorrencias-para-variosEmails";
	}

	
	@GetMapping("**/ocorrencias/enviarVarias/whatsapp")
	public String enviarOcorrenciaWhatsapp(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("ocorrencias",
				serviceOcorrencia.buscarTodasDe2DiasTrasPorEstabelecimento(user.getServidor().getEstabelecimento()));

		List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
		if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
			emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
		}
		model.addAttribute("emails", emails);
		model.addAttribute("usuario", user);
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Enviar várias Ocorrências para Whatsapp");

		return "fragmentos/utilitarios/enviar-variasOcorrencias-para-Whatsapp";
	}

	// @PostMapping(value = " **/ocorrencias/enviandoVarias")
	/*
	public String enviandoVarias(
			@RequestParam(value = "listaDeOcorrencias", required = false) List<Long> listaDeOcorrencias,
			@RequestParam(value = "listaDeEmails", required = false) List<Long> listaDeEmails) {

		return "fragmentos/utilitarios/enviar-variasOcorrencias-para-variosEmails";
	}*/

	@PostMapping("**/ocorrencia/alteraOcorrencia/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public String alterarOcorrencia(@PathVariable("id") Long id, Ocorrencia ocorrencia, BindingResult result, RedirectAttributes redirectAttributes) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ocorrencia", result);
			redirectAttributes.addFlashAttribute("ocorrencia", ocorrencia);
			redirectAttributes.addFlashAttribute("estabelecimento", ocorrencia.getEstabelecimento());
			redirectAttributes.addFlashAttribute("localidades", ocorrencia.getEndereco().getCidade().getLocalidades());
			redirectAttributes.addFlashAttribute("bairros", ocorrencia.getEndereco().getBairro().getLocalidade().getBairros());
			redirectAttributes.addFlashAttribute("servidor", ocorrencia.getServidor());

			return "redirect:/admin/editar/ocorrencias/" + id;
		}
		Cecoco importa = new Cecoco();
		List<CecocoLog> cecocolog = new ArrayList<CecocoLog>();
		cecocolog =  (List<CecocoLog>) importa.importaLog(Long.parseLong(ocorrencia.getSic()), user.getEstabelecimento().get(0), serviceCicom1Log, serviceCicom2Log, serviceCicom3Log, serviceCicom4Log, serviceCicom5Log, serviceCicom6Log, serviceCicom7Log, serviceCicom8Log, serviceCicom9Log, serviceCicom10Log, serviceCicom11Log, serviceCicom12Log, serviceCicom13Log, serviceCicom14Log, serviceCicom15Log, serviceCicom16Log, serviceCicom17Log, serviceCicom18Log, serviceCicom19Log, serviceCicom20Log, serviceCicom22Log, serviceCicom23Log);

		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(user.getId());
		auditoria.setHistorico("Alteração na Ocorrencia Nº: " + ocorrencia.getSic().toString());
		serviceAuditoria.cadastrar(auditoria);
		if (cecocolog.size() > 0) {
			serviceOcorrenciaLog.deletar(ocorrencia);
			List<OcorrenciaLog> ocorrenciaLog = importa.convertLog( cecocolog);
			ocorrencia.getOcorrencialog().addAll(ocorrenciaLog);
			for (OcorrenciaLog log : ocorrenciaLog) {
				log.setOcorrencia(ocorrencia);
			}
		}
		serviceOcorrencia.alterar(id, ocorrencia);
		return "redirect:/admin/historico/ocorrencias";
	}

	@RequestMapping(value = "**/ocorrencias/visualizar/filtrar", method = RequestMethod.POST)
	public ModelAndView searchOcorrencia(Ocorrencia ocorrencia, String dataInicio, String dataFim, BindingResult result,
			ModelAndView mv) throws NoSuchFieldException, SecurityException {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<Ocorrencia> ocorrenciasEncontradas;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dataDeInicio = null;
		LocalDateTime dataDeFim = null;

		if (dataInicio != null && !dataInicio.equals("")) {
			dataDeInicio = LocalDateTime.parse(dataInicio, formatter);
		}
		if (dataFim != null && !dataFim.equals("")) {
			dataDeFim = LocalDateTime.parse(dataFim, formatter);
		}
		ocorrencia.setEstabelecimento(user.getServidor().getEstabelecimento());
		ocorrenciasEncontradas = serviceOcorrencia.findAllByOcorrencia(ocorrencia, dataDeInicio, dataDeFim);

		mv.setViewName("fragmentos/pesquisas/pesquisaOcorrencia");
		mv.addObject("usuario", user);

		if (servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento().size() == 1) {
			mv.addObject("cidades", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento().get(0).getCidades());
		} else {
			mv.addObject("cicoms", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento());
		}

		mv.addObject("ocorrencia", new Ocorrencia());
		mv.addObject("tipificacoes", serviceTipificacao.listarTodos());
		mv.addObject("ocorrencias", ocorrenciasEncontradas);

		List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
		if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
			emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
		}
		mv.addObject("emails", emails);

		// Titulo da Página
		mv.addObject("tituloPagina", "ComunicaCICOM - Pesquisa Ocorrências");
		return mv;

	}

	@RequestMapping(value = "*/visualiza/ocorrencias/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarOcorrencia(@PathVariable("id") Long id, ModelAndView mv) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		mv.setViewName("fragmentos/visualizacoes/visualizaOcorrencia");
		mv.addObject("usuario", user);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String datahorastr = LocalDateTime.now().format(formatter);

		mv.addObject("datahora", datahorastr);
		mv.addObject("servidor", user.getServidor());
		mv.addObject("ocorrencia", serviceOcorrencia.buscaPorId(id).get());

		return mv;
	}

	@GetMapping(value = "**/ocorrencias/excluir/{id}")
	public String excluirocorrencia(@PathVariable("id") Long id, ModelAndView model) {

		serviceOcorrencia.deletar(id);
		return "redirect:/admin/historico/ocorrencias";
	}

	@ResponseBody
	@GetMapping("**/ocorrencias/descricaoWhastapp/viaLink/{id}")
	public String getMensagemWhastappViaLink(@PathVariable("id") Long id, ModelAndView model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(user.getId());
		auditoria.setHistorico("Enviada via WhatsApp com link Ocorrencia Nº: " + serviceOcorrencia.buscaPorId(id).get().getSic().toString() );
		serviceAuditoria.cadastrar(auditoria);
		Ocorrencia ocorrencia = serviceOcorrencia.buscaPorId(id).get();
		if (!(ocorrencia.getHistorico() == null)) {
			ocorrencia.setHistorico(" ");
		}
		return new Formatador().formataParaWhatsappViaLink(ocorrencia, user);
	}
	

	@ResponseBody
	@GetMapping("**/ocorrencias/descricaoWhastapp/{id}")
	public String getMensagemWhastapp(@PathVariable("id") Long id, ModelAndView model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Auditoria auditoria = new Auditoria();
		auditoria.setDatahora(LocalDateTime.now());
		auditoria.setUsuario(user.getId());
		auditoria.setHistorico(
				"Enviada via WhatsApp Ocorrencia Nº: " + serviceOcorrencia.buscaPorId(id).get().getSic().toString());
		serviceAuditoria.cadastrar(auditoria);
		return new Formatador().formataParaWhatsapp(serviceOcorrencia.buscaPorId(id).get(), user);
	}
		

	@ResponseBody
	@GetMapping("**/ocorrencias/descricaoHtml/{id}")
	public String getMensagemHtml(@PathVariable("id") Long id, ModelAndView model) {
		return new Formatador().formataParaHtml(serviceOcorrencia.buscaPorId(id).get());
	}

	@ResponseBody
	@GetMapping("**/ocorrencias/enviarSelecionadas/whatsappEnviando/{listaDeOcorrencias}")
	public String getMensagemsWhastapp(@PathVariable("listaDeOcorrencias") List<Long> listaOcorrencias,
			ModelAndView model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		String mensagem = "";

		for (int i = 0; i < listaOcorrencias.size(); i++) {
			if (i != 0) {
				mensagem = mensagem + "%0A%0A[----------------------------------------------------------]%0A%0A";
			}
			mensagem = mensagem + new Formatador().formataParaWhatsapp(serviceOcorrencia.buscaPorId(listaOcorrencias.get(i)).get(), user);
		}
		return mensagem.replaceAll("\"", "%22");
	}

	@ResponseBody
	@GetMapping("**/ocorrencias/enviarSelecionadas/whatsappMensagemFormatada/{listaDeOcorrencias}")
	public String getMensagemsWhastappFormatado(@PathVariable("listaDeOcorrencias") List<Long> listaOcorrencias,
			ModelAndView model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		String mensagem = "";

		for (int i = 0; i < listaOcorrencias.size(); i++) {
			if (i != 0) {
				mensagem = mensagem + "<br><br>[----------------------------------------------------------]<br><br>";
			}
			mensagem = mensagem + new Formatador()
					.formataParaWhatsappComHTML(serviceOcorrencia.buscaPorId(listaOcorrencias.get(i)).get(), user);
		}
		return mensagem.replaceAll("\"", "%22");
	}

	@ResponseBody
	@GetMapping("**/ocorrencias/descricaoEmail/{id}")
	public String getMensagemEmail(@PathVariable("id") Long id, ModelAndView model) {
		return new Formatador().formataParaEmail(serviceOcorrencia.buscaPorId(id).get());
	}
	
}

