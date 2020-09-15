package br.com.cicom.comunicacicom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

import br.com.cicom.comunicacicom.DSPrimary.POJO.MonitoraPlanilhaRelatorio;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Objeto;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Pessoa;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.RegistroOcorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.TipificacaoObjeto;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Sexo;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.TipoPessoa;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.ContextoCrimeService;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.RegistroOcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.TipoEnvolvimentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.EstadoOcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EstadoCivilService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EtniaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.NacionalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Controller
public class RegistroDeOcorrenciaController {
	
	@Autowired
	private OcorrenciaService serviceOcorrencia;

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private RegistroOcorrenciaService serviceRegistroOcorrencia;

	@Autowired
	private CategoriaService serviceCategoria;

	@Autowired
	private EstadoOcorrenciaService serviceEstadoOcorrencia;

	@Autowired
	private TipoEnvolvimentoService serviceTipoEnvolvimento;

	@Autowired
	private ContextoCrimeService serviceContextoCrime;

	@Autowired
	private EtniaService serviceEtnia;

	@Autowired
	private EstadoCivilService serviceEstadoCivil;
	
	@Autowired
	private NacionalidadeService serviceNacionalidade;

	private MonitoraPlanilhaRelatorio monitoraPlanilhaRelatorio = new MonitoraPlanilhaRelatorio();
	
	@GetMapping("**/relatorio/ocorrencias/cadastro/{id}")
	public String cadastraRegistroOcorrencia(@PathVariable("id") Long idOcorrencia, Model model, HttpServletRequest req) {
	
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	
		model.addAttribute("usuario", user);

		// Verifica se já foi passado a ocorrência
		if (!model.containsAttribute("registro")) {

			monitoraPlanilhaRelatorio.criaListas(user.getLogin(), new ArrayList<Pessoa>(), new ArrayList<Objeto>());
			RegistroOcorrencia registro = new RegistroOcorrencia();
			registro.setOcorrencia(serviceOcorrencia.buscaPorId(idOcorrencia).get());
			
			model.addAttribute("registro", registro);

		} else {

			// Recupera o registro que estamos trabalhando na view
			RegistroOcorrencia registro = (RegistroOcorrencia) model.asMap().get("registro");

		}

		model.addAttribute("cicoms", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento());
		model.addAttribute("cidades",servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getEstabelecimento().get(0).getCidades());
		model.addAttribute("categorias", serviceCategoria.listarTodos());
		model.addAttribute("estadosOcorrencia", serviceEstadoOcorrencia.listarTodos());

		// Referentes ao Objetos Pessoas
		if (model.asMap().get("pessoa") == null) {
			model.addAttribute("pessoa", new Pessoa());
		}
		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro de Registro de Ocorrências");
		model.addAttribute("etnias", serviceEtnia.listarTodos());
		model.addAttribute("nacionalidades", serviceNacionalidade.listarTodos());
		model.addAttribute("estadosCivis", serviceEstadoCivil.listarTodos());
		model.addAttribute("envolvimentos", serviceTipoEnvolvimento.listarTodos());
		model.addAttribute("contextos", serviceContextoCrime.listarTodos());
		model.addAttribute("sexos", Sexo.values());
		model.addAttribute("tipoPessoas", TipoPessoa.values());

		// Referentes ao Objetos
		if (model.asMap().get("objeto") == null) {
			model.addAttribute("objeto", new Objeto());
		}
		model.addAttribute("tipificacoesObjeto", TipificacaoObjeto.values());

		return "fragmentos/cadastros/registroOcorrencia";
	}
	

	@RequestMapping(value = "/cadastraRegistroOcorrencia", params = { "salvar" })
	public String cadastrarOcorrencia(@Valid RegistroOcorrencia registro, BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (result.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registro", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.endereco", result);
			redirectAttributes.addFlashAttribute("registro", registro);

			return "redirect:/admin/home/relatorio/ocorrencias/cadastro"+registro.getOcorrencia().getId();
		}
		registro.setOcorrencia(serviceOcorrencia.buscaPorId(registro.getOcorrencia().getId()).get());

		serviceRegistroOcorrencia.cadastrar(registro);

		registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());
		registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());

		for (Objeto obj : monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos()) {
			obj.setRegitroOcorrencia(registro);
		}

		for (Pessoa pess : monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas()) {
			pess.setRegitroOcorrencia(registro);
		}

		serviceRegistroOcorrencia.alterar(registro.getId(), registro);
		monitoraPlanilhaRelatorio.removeMensagem(user.getLogin());
		if(registro.getOcorrencia().getTipificacao().getCategoria().getNome().equals("CVLI - CRIMES VIOLENTOS LETAIS INTENCIONAIS")) {
			return "redirect:/admin/historico/registroOcorrencias/cvli";
		}

		return "redirect:/admin/historico/registroOcorrencias/roubosEFurtos";
	}

	@PostMapping(value = "/cadastraRegistroOcorrencia", params = { "inserirPessoa" })
	public String inserirPessoas(final RegistroOcorrencia registro, @Valid Pessoa novaPessoa,
			final BindingResult result, RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if (result.hasErrors()) {
			
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.pessoa", result);

			// Retorna a pessoa com o erro
			redirect.addFlashAttribute("pessoa", novaPessoa);

			// Passa a lista para o registro de ocorrência
			registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());
			registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());

			// Redireciona o registro para o view
			redirect.addFlashAttribute("registro", registro);
		} else {
			// Adiciona na lista de Pessoas uma nova pessoa
			monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas().add(novaPessoa);

			// Passa a lista para o registro de ocorrência
			registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());
			registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());

			// Redireciona o registro para o view
			redirect.addFlashAttribute("registro", registro);
		}
		return "redirect:/admin/home/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#pessoas-envolvidas";
	}

	@RequestMapping(value = "/cadastraRegistroOcorrencia", method = RequestMethod.POST, params = { "editarPessoa" })
	public String editarPessoa(RegistroOcorrencia registro, BindingResult result, HttpServletRequest req,
			RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		int rowId = Integer.valueOf(req.getParameter("editarPessoa"));
		
		if (!monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas().isEmpty()) {
			redirect.addFlashAttribute("pessoa", monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas().remove(rowId));
		}

		// Passa a lista para o registro de ocorrência
		registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());
		registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

		// Redireciona o registro para o view
		redirect.addFlashAttribute("registro", registro);

		return "redirect:/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#pessoas-envolvidas";
	}

	@RequestMapping(value = "/cadastraRegistroOcorrencia", method = RequestMethod.POST, params = { "excluirPessoa" })
	public String excluirPessoa(RegistroOcorrencia registro, BindingResult result, HttpServletRequest req,
			RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		int rowId = Integer.valueOf(req.getParameter("excluirPessoa"));

		if (!monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas().isEmpty()) {
			monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas().remove(rowId);
		}

		// Passa a lista para o registro de ocorrência
		registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());
		registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

		// Redireciona o registro para o view
		redirect.addFlashAttribute("registro", registro);
				
		return "redirect:/admin/home/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#pessoas-envolvidas";
	}

	@PostMapping(value = "/cadastraRegistroOcorrencia", params = { "inserirObjeto" })
	public String inserirObjeto(final RegistroOcorrencia registro, @Valid Objeto novoObjeto, final BindingResult result,
			RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if (result.hasErrors()) {
			
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.objeto", result);

			// Retorna o objeto com o erro
			redirect.addFlashAttribute("objeto", novoObjeto);

			// Passa a lista para o registro de ocorrência
			registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());

			registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

			// Redireciona o registro para o view
			redirect.addFlashAttribute("registro", registro);
		} else {
			// Adiciona na lista de Objetos um novo Objeto
			monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos().add(novoObjeto);

			// Passa a lista para o registro de ocorrência
			registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());

			registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

			// Redireciona o registro para o view
			redirect.addFlashAttribute("registro", registro);
		}

		return "redirect:/admin/home/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#objetos-envolvidos";
	}

	@RequestMapping(value = "/cadastraRegistroOcorrencia", method = RequestMethod.POST, params = { "editarObjeto" })
	public String editarObjeto( RegistroOcorrencia registro, BindingResult result, HttpServletRequest req,
			RedirectAttributes redirect) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		int rowId = Integer.valueOf(req.getParameter("editarObjeto"));

		if (!monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos().isEmpty()) {
			redirect.addFlashAttribute("objeto", monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos().remove(rowId));
			
		}

		// Passa a lista para o registro de ocorrência
		registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());
		registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

		// Redireciona o registro para o view
		redirect.addFlashAttribute("registro", registro);

		return "redirect:/admin/home/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#objetos-envolvidos";
	}

	@RequestMapping(value = "**/relatorio/ocorrencias/cadastro/excluirObjeto/{id}", method = RequestMethod.POST, params = { "excluirObjeto" })
	public String excluirObjeto( RegistroOcorrencia registro, BindingResult result, HttpServletRequest req,
			RedirectAttributes redirect) {
		int rowId = Integer.valueOf(req.getParameter("excluirObjeto"));
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		if (!monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos().isEmpty()) {
			monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos().remove(rowId);
		}

		// Passa a lista para o registro de ocorrência
		registro.getObjetosEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaObjetos());
		registro.getEnvolvidos().addAll(monitoraPlanilhaRelatorio.getListas(user.getLogin()).getListaPessoas());

		// Redireciona o registro para o view
		redirect.addFlashAttribute("registro", registro);

		return "redirect:/admin/home/relatorio/ocorrencias/cadastro/"+registro.getOcorrencia().getId()+"#objetos-envolvidos";
	}

	@GetMapping(value = "**/relatorio/ocorrencias/excluir/{id}")
	public String excluirocorrencia(@PathVariable("id") Long id, ModelAndView model) {

		serviceRegistroOcorrencia.deletar(id);
		return "redirect:/admin/historico/registroOcorrencias";
	}

	@ResponseBody
	@GetMapping("/relatorio/registros/diario")
	public List<RegistroOcorrencia> consultarRegistrosDiarios(ModelAndView model) {
		return serviceRegistroOcorrencia.listarTodos();
	}
	
	
	
	
	@GetMapping("*/historico/registroOcorrencias/cvli")
	public String telaHistorico(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
				// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Registro de Ocorrências");

		return "fragmentos/historicos/historicoRegistroOcorrencias";
	}
	
	@GetMapping("*/historico/registroOcorrencias/roubosEFurtos")
	public String telaHistoricoRoubosEFurtos(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
				// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Registro de Ocorrências Roubos e Furtos");

		return "fragmentos/historicos/historicoRegistroOcorrenciasRoubosEFurtos";
	}
	
	@ResponseBody
	@RequestMapping(value = "/registros/ocorrencias/buscar/historico", method = { RequestMethod.POST, RequestMethod.GET })
	public DataTablesOutput<Ocorrencia> listPOST(DataTablesInput input) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Tipificacao> tipificacoes = serviceCategoria.buscarPorNome("CVLI - CRIMES VIOLENTOS LETAIS INTENCIONAIS").get(0).getTiposOcorrencia();

		return serviceOcorrencia.listarOcorrencias(input,user.getServidor().getEstabelecimento(),tipificacoes);
	}
	
	@ResponseBody
	@RequestMapping(value = "/registros/ocorrencias/buscar/historico/roubosEFurtosNaoRegistrados", method = { RequestMethod.POST, RequestMethod.GET })
	public DataTablesOutput<Ocorrencia> listRegistrosPOST(DataTablesInput input) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<Tipificacao> tipificacoes = serviceCategoria.buscarPorNome("ROUBOS E FURTOS DE VEÍCULOS").get(0).getTiposOcorrencia();
		return serviceOcorrencia.listarOcorrencias(input,user.getServidor().getEstabelecimento(),tipificacoes);
		
	}
	
	
	@ResponseBody
	@GetMapping("/registros/ocorrencias/quantidade/naoRegistradas")
	public Long ocorrenciasNaoRegistradas() {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Tipificacao> tipificacao = serviceCategoria.buscarPorNome("CVLI - CRIMES VIOLENTOS LETAIS INTENCIONAIS").get(0).getTiposOcorrencia();

		return serviceOcorrencia.quantidadeDeOcorrenciasNaoRegistradas(user.getServidor().getEstabelecimento(),tipificacao);
	}
	
	@ResponseBody
	@GetMapping("/registros/ocorrencias/quantidade/naoRegistradasRoubosEFurtos")
	public Long ocorrenciasNaoRegistradasCVP() {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Tipificacao> tipificacao = serviceCategoria.buscarPorNome("ROUBOS E FURTOS DE VEÍCULOS").get(0).getTiposOcorrencia();

		return serviceOcorrencia.quantidadeDeOcorrenciasNaoRegistradas(user.getServidor().getEstabelecimento(),tipificacao);
	}
}
