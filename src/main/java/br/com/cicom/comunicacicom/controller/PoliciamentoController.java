package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSPrimary.DTO.sisEfetivo.DadosPoliciamentoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisEfetivo.InstituicaoDTO;
import br.com.cicom.comunicacicom.DSPrimary.DTO.sisEfetivo.ModalidadeDTO;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorExterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.CidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.MesaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ModalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.PoliciamentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.RecursoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ServidorExternoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.TipoServicoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class PoliciamentoController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private FuncaoService serviceFuncao;

	@Autowired
	private ServidorExternoService serviceServidor;

	@Autowired
	private MesaService serviceMesa;

	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private TipoServicoService serviceTipoServico;

	@Autowired
	private ModalidadeService serviceModalidade;

	@Autowired
	private RecursoService serviceRecurso;

	@Autowired
	private PoliciamentoService servicePoliciamento;

	@Autowired
	private CidadeService serviceCidade;

	// private Policiamento PoliciamentoNovo = new Policiamento();

	/* Abre o histórico do efetivo externo */
	@GetMapping(value = "*/historico/policiamentos")
	public String historicoEfetivoExterno(Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Histórico Efetivo Externo");
		model.addAttribute("usuario", user);

		return "fragmentos/historicos/historicoPoliciamento";
	}

	/* Abre o histórico do efetivo externo */
	@GetMapping(value = "*/pesquisa/policiamentos")
	public String pesquisaPoliciamento(Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Pesquisa Policiamento");
		model.addAttribute("usuario", user);
		model.addAttribute("policiamento", new Policiamento());
		model.addAttribute("unidades", serviceUnidade.listarTodos());
		model.addAttribute("modalidades", serviceModalidade.listarTodos());

		return "fragmentos/pesquisas/pesquisaPoliciamento";
	}

	/* Abre o histórico do efetivo externo de uma determinada mesa */
	@GetMapping(value = { "*/historico/policiamentos/ativos/{idMesa}", "*/historico/policiamentos/ativos",
			"*/historico/policiamentos/ativos/{idMesa}/{idCidade}" })
	public String historicoEfetivoExternoDeUmaMesaAtivoPorCidade(
			@PathVariable(required = false, value = "idMesa") Long idMesa,
			@PathVariable(required = false, value = "idCidade") Long idCidade, Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Visualiza Efetivo Externo Ativos Por Cidade");

		model.addAttribute("usuario", user);

		if (idMesa == null) {
			if (serviceMesa.buscarUltimaAtivaPorNomeEstabelecimento(
					user.getServidor().getEstabelecimento().getNome()) != null) {
				model.addAttribute("mesa", serviceMesa
						.buscarUltimaAtivaPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome()));
			} else {
				Mesa mesa = new Mesa();
				mesa.setId(0L);
				model.addAttribute("mesa", mesa);

			}

			Cidade cidade = new Cidade();
			cidade.setId(0L);
			model.addAttribute("cidade", cidade);

			return "fragmentos/historicos/historicoPoliciamentosAtivosPorCidade";

		} else {
			model.addAttribute("mesa", serviceMesa.buscaPorId(idMesa).get());
		}

		if (idCidade == null) {
			Cidade cidade = new Cidade();
			cidade.setId(0L);
			model.addAttribute("cidade", cidade);
		} else {
			model.addAttribute("cidade", serviceCidade.buscaPorId(idCidade).get());
		}

		return "fragmentos/historicos/historicoPoliciamentosAtivosPorCidade";

	}

	@RequestMapping(value = "**/policiamento/filtrar")
	public ModelAndView searchPoliciamento(Policiamento policiamento, ModelAndView mv) {

		if (policiamento.getComecoPlantao() == null) {
			policiamento.setComecoPlantao(LocalDateTime.now().minusMonths(6));
		}
		if (policiamento.getTerminoPlantao() == null) {
			policiamento.setTerminoPlantao(LocalDateTime.now().plusDays(1));
		}
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		mv.addObject("policiamento", policiamento);
		mv.addObject("usuario",
				servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addObject("tituloPagina", "ComunicaCICOM - Pesquisa Policiamento");
		mv.addObject("resultadoPoliciamentos",
				servicePoliciamento.filtrar(policiamento, user.getServidor().getEstabelecimento()));
		mv.addObject("unidades", serviceUnidade.listarTodos());
		mv.addObject("modalidades", serviceModalidade.listarTodos());
		mv.setViewName("fragmentos/pesquisas/pesquisaPoliciamento");

		return mv;
	}

	@PostMapping(value = "admin/cadastros/gestaoefetivo/cadastra-efetivoExterno/{id}/", params = {
			"inserirServidoresExternos" })
	public String inserirServidores(Model model, @PathVariable("id") Long id, Policiamento policiamento,
			@RequestParam(value = "listaDeRecursos", required = false) List<Long> listaDeRecursos,
			final HttpServletRequest req) {

		final String rowId = req.getParameter("matriculaServidor");

		if (listaDeRecursos != null) {
			policiamento.setRecursos(new HashSet<>(serviceRecurso.pegarPorListaDeId(listaDeRecursos)));
		}

		if (policiamento.getCidade() != null) {
			model.addAttribute("localidade", policiamento.getCidade().getLocalidades());
		} else {
			model.addAttribute("localidade", new Localidade());
		}

		if (policiamento.getLocalidade() != null) {

			model.addAttribute("bairro", policiamento.getLocalidade().getBairros());
		} else {
			model.addAttribute("bairro", new Bairro());
		}
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);

		List<ServidorExterno> listaDeservidoreQueForamSelecionados = new ArrayList<>();
		List<ServidorExterno> listaDeservidoreQueTemNaGuarnicao = new ArrayList<>();

		for (ServidorFuncao servidor : policiamento.getGuarnicao()) {
			listaDeservidoreQueTemNaGuarnicao.add(serviceServidor.buscaPorId(servidor.getServidor().getId()).get());
		}

		if (rowId != null && rowId.length() == 10) {
			listaDeservidoreQueForamSelecionados.add(this.filtroMatricula(rowId));
		}

		// remove servidores que foram selecionados porém já estão na mesa
		listaDeservidoreQueForamSelecionados.removeAll(listaDeservidoreQueTemNaGuarnicao);

		for (ServidorExterno servidor : listaDeservidoreQueForamSelecionados) {
			ServidorFuncao servidorFuncao = new ServidorFuncao();
			servidorFuncao.setServidor(servidor);
			servidorFuncao.setInicioPlantao(policiamento.getComecoPlantao().withSecond(0));
			servidorFuncao.setFimPlantao(policiamento.getTerminoPlantao().withSecond(0));
			policiamento.getGuarnicao().add(servidorFuncao);
		}

		// Salva o estado atual da guarnição numa variavel global
		// PoliciamentoNovo = policiamento;

		// Objetos para a página cadastra efetivo externo
		model.addAttribute("unidade", serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(
				serviceMesa.buscaPorId(id).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
		model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
		model.addAttribute("modalidade", serviceModalidade.listarTodos());
		model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());
		model.addAttribute("recurso", serviceRecurso.listarTodos());
		model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
		model.addAttribute("mesa", serviceMesa.buscaPorId(id).get());
		model.addAttribute("policiamento", policiamento);
		model.addAttribute("servidorFuncao", new ServidorFuncao());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");

		return "fragmentos/cadastros/policiamento";

	}

	@GetMapping(value = "admin/cadastros/gestaoefetivo/guarnicao/comEfetivos/{id}")
	public String EfetivoExterno(@PathVariable("id") Long id, Model model, Policiamento policiamento) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);

		// Objetos para a página cadastra efetivo externo
		model.addAttribute("unidade", serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(
				serviceMesa.buscaPorId(id).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
		model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
		model.addAttribute("modalidade", serviceModalidade.listarTodos());

		model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());

		if (policiamento.getCidade() != null) {
			model.addAttribute("localidade", policiamento.getCidade().getLocalidades());
		} else {
			model.addAttribute("localidade", new Localidade());
		}

		if (policiamento.getLocalidade() != null) {
			model.addAttribute("bairro", policiamento.getLocalidade().getBairros());
		} else {
			model.addAttribute("bairro", new Bairro());
		}

		model.addAttribute("abrirModal", null);
		model.addAttribute("recurso", serviceRecurso.listarTodos());
		model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
		model.addAttribute("mesa", serviceMesa.buscaPorId(id).get());
		model.addAttribute("policiamento", policiamento);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");

		return "fragmentos/cadastros/policiamento";

	}

	// Esse controller é acionado pelo botão de remover servidores,esse controller
	// salva o estado atual da guarnição e ativa o controller abaixo
	@PostMapping(value = "admin/cadastros/gestaoefetivo/cadastra-efetivoExterno/{id}/", params = {
			"remover-efetivoGuarnicao" })
	public String salvarEstadoAtualDaMesaParaRemoverOServidor(Mesa mesa, @PathVariable("id") Long id,
			final HttpServletRequest req, Model model, Policiamento policiamento) {

		final Integer rowId = Integer.valueOf(req.getParameter("remover-efetivoGuarnicao"));

		policiamento.getGuarnicao().remove(rowId.intValue());

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);

		model.addAttribute("unidade", serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(
				serviceMesa.buscaPorId(id).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
		model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
		model.addAttribute("modalidade", serviceModalidade.listarTodos());

		model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());

		if (policiamento.getCidade() != null) {
			model.addAttribute("localidade", policiamento.getCidade().getLocalidades());
		} else {
			model.addAttribute("localidade", new Localidade());
		}

		if (policiamento.getLocalidade() != null) {
			model.addAttribute("bairro", policiamento.getLocalidade().getBairros());
		} else {
			model.addAttribute("bairro", new Bairro());
		}

		model.addAttribute("abrirModal", null);
		model.addAttribute("recurso", serviceRecurso.listarTodos());
		model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
		model.addAttribute("mesa", serviceMesa.buscaPorId(id).get());
		model.addAttribute("policiamento", policiamento);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");

		return "fragmentos/cadastros/policiamento";

	}

	// Esse controller é acionado pelo botão de salvar, esse controller
	// salva a mesa no banco de dados e ativa o controller de histórico de
	// policiamentos
	@PostMapping(value = "admin/cadastros/gestaoefetivo/cadastra-efetivoExterno/{id}/", params = { "salvar" })
	public String alterarEfetivoExterno(Policiamento policiamento, @PathVariable("id") Long id, Model model,
			BindingResult result) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		Long milli_dif_agora = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
				- policiamento.getComecoPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		Long milli_dif = policiamento.getTerminoPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
				- policiamento.getComecoPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		if ((milli_dif_agora > 43200000 || milli_dif_agora < 0)) {
			ObjectError erro1 = new ObjectError("comecoPlantao", "Data e hora de início do efetivo inválido!");
			result.addError(erro1);
		}

		if ((policiamento.getTerminoPlantao().isBefore(LocalDateTime.now()))) {
			ObjectError erro1 = new ObjectError("terminoPlantao", "O fim do plantão nao pode estar no pasado!");
			result.addError(erro1);
		} else if ((milli_dif < 14400000)) {
			ObjectError erro2 = new ObjectError("terminoPlantao",
					"Intervalo de tempo entre o início e o término do efetivo não pode ter menos de 4 horas!");
			result.addError(erro2);
		}

		if ((milli_dif > 172800000)) {
			ObjectError erro2 = new ObjectError("terminoPlantao",
					"Intervalo de tempo entre o início e o término do efetivo não pode ter mais de 48 horas!");
			result.addError(erro2);
		}

		if (result.hasErrors()) {
			model.addAttribute("org.springframework.validation.BindingResult.mesa", result);
			model.addAttribute("unidade", serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(
					serviceMesa.buscaPorId(id).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
			model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
			model.addAttribute("modalidade", serviceModalidade.listarTodos());
			model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());
			model.addAttribute("bairro", policiamento.getBairro());
			model.addAttribute("localidade", policiamento.getLocalidade());
			model.addAttribute("recurso", serviceRecurso.listarTodos());
			model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
			model.addAttribute("mesa", serviceMesa.buscaPorId(id).get());
			model.addAttribute("servidorFuncao", new ServidorFuncao());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");
			model.addAttribute("usuario", user);

			return "fragmentos/cadastros/policiamento";
		}

		Mesa mesa = serviceMesa.buscaPorId(id).get();

		policiamento.setMesa(mesa);
		policiamento.setId(null);

		servicePoliciamento.cadastrar(policiamento);

		Set<Policiamento> policiamentos = mesa.getListaDePoliciamentos();
		policiamentos.add(policiamento);

		mesa.setListaDePoliciamentos(policiamentos);
		serviceMesa.alterar(id, mesa);

		return "redirect:/admin/historico/policiamentos/" + id;
	}

	@PostMapping(value = "*/salva/edicao/policiamento/{id}", params = { "inserirServidoresExternos" })
	public String inserirServidoresEditando(Model model, @PathVariable("id") Long id, Policiamento policiamento,
			@RequestParam(value = "listaDeRecursos", required = false) List<Long> listaDeRecursos,
			final HttpServletRequest req) {

		final String rowId = req.getParameter("matriculaServidor");

		if (listaDeRecursos != null) {
			policiamento.setRecursos(new HashSet<>(serviceRecurso.pegarPorListaDeId(listaDeRecursos)));
		}
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		model.addAttribute("usuario", user);

		List<ServidorExterno> listaDeservidoreQueForamSelecionados = new ArrayList<>();
		List<ServidorExterno> listaDeservidoreQueTemNaGuarnicao = new ArrayList<>();

		for (ServidorFuncao servidor : policiamento.getGuarnicao()) {
			listaDeservidoreQueTemNaGuarnicao.add(serviceServidor.buscaPorId(servidor.getServidor().getId()).get());
		}

		if (rowId != null && rowId.length() == 10) {
			listaDeservidoreQueForamSelecionados.add(this.filtroMatricula(rowId));
		}

		// remove servidores que foram selecionados porém já estão na mesa
		listaDeservidoreQueForamSelecionados.removeAll(listaDeservidoreQueTemNaGuarnicao);

		for (ServidorExterno servidor : listaDeservidoreQueForamSelecionados) {
			ServidorFuncao servidorFuncao = new ServidorFuncao();
			servidorFuncao.setServidor(servidor);
			servidorFuncao.setInicioPlantao(policiamento.getComecoPlantao());
			servidorFuncao.setFimPlantao(policiamento.getTerminoPlantao());
			policiamento.getGuarnicao().add(servidorFuncao);
		}

		// Salva o estado atual da guarnição numa variavel global
		// PoliciamentoNovo = policiamento;

		// Objetos para a página cadastra efetivo externo

		if (policiamento.getCidade() != null) {
			model.addAttribute("localidade", policiamento.getCidade().getLocalidades());
		} else {
			model.addAttribute("localidade", new Localidade());
		}

		if (policiamento.getLocalidade() != null) {
			model.addAttribute("bairro", policiamento.getLocalidade().getBairros());
		} else {
			model.addAttribute("bairro", new Bairro());
		}

		model.addAttribute("unidade",
				serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(serviceMesa
						.buscaPorId(servicePoliciamento.buscaPorId(id).get().getMesa().getId()).get().getInstituicoes(),
						user.getServidor().getEstabelecimento()));
		model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
		model.addAttribute("modalidade", serviceModalidade.listarTodos());
		model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());
		model.addAttribute("recurso", serviceRecurso.listarTodos());
		model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
		model.addAttribute("mesa", servicePoliciamento.buscaPorId(id).get().getMesa());
		model.addAttribute("policiamento", policiamento);
		model.addAttribute("servidorFuncao", new ServidorFuncao());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");

		return "fragmentos/edicoes/editaPoliciamento";

	}

	@GetMapping(value = "**/policiamento/status/{id}")
	public String modificarStatusBairro(@PathVariable("id") Long id, ModelAndView model) {
		Policiamento policiamentoBanco = servicePoliciamento.buscaPorId(id).get();

		if (policiamentoBanco != null) {
			policiamentoBanco.setStatus(!policiamentoBanco.isAtivo());
		}
		servicePoliciamento.alterar(id, policiamentoBanco);
		return "redirect:/admin/historico/policiamentos/" + policiamentoBanco.getMesa().getId();
	}

	@RequestMapping(value = "**/policiamentos/visualizar/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarPoliciamento(@PathVariable("id") Long id, ModelAndView mv) {

		mv.setViewName("fragmentos/visualizacoes/visualizaGuarnicao");
		mv.addObject("usuario",
				servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

		mv.addObject("policiamento", servicePoliciamento.buscaPorId(id).get());

		return mv;
	}

	@ResponseBody
	@GetMapping("**/gestaoefetivo/pesquisa/{matricula}")
	public ServidorExterno filtroMatricula(@PathVariable("matricula") String matricula) {
		return serviceServidor.buscarPorMatricula(matricula, true);
	}

	@ResponseBody
	@RequestMapping(value = "/policiamento/buscar/historico/{idMesa}", method = { RequestMethod.POST,
			RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input, @PathVariable("idMesa") Long idMesa) {

		return servicePoliciamento.buscarDadosDataTable(input, idMesa);

	}

	@ResponseBody
	@RequestMapping(value = "/policiamento/buscar/historico", method = { RequestMethod.POST, RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> list(DataTablesInput input) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user.getGrupo().getNome().contentEquals("ADMINISTRADOR")) {
			return servicePoliciamento.buscarDadosDataTable(input);
		} else {
			return servicePoliciamento.buscarDadosDataTable(input, user.getServidor().getEstabelecimento());
		}

	}

	@ResponseBody
	@RequestMapping(value = "/admin/policiamento/buscar/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public Policiamento policiamento(@PathVariable(name = "id", required = false) Long id) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		Policiamento policiamento = servicePoliciamento.buscaPorId(id).get();
		if (user.getEstabelecimento().contains(policiamento.getMesa().getEstabelecimento())) {
			return policiamento;
		} else {
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = { "/policiamento/buscar/historico/ativo/{idMesa}/{idCidade}" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public DataTablesOutput<Map<String, Object>> listPOST(DataTablesInput input, @PathVariable("idMesa") Long idMesa,
			@PathVariable("idCidade") Long idCidade) {

		// Usuario user =
		// servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (idCidade != 0 && idMesa != 0) {
			return servicePoliciamento.buscardadosDataTable(input, serviceCidade.buscaPorId(idCidade).get(), idMesa);
		} else if (idMesa != 0) {
			return servicePoliciamento.buscarDadosDataTable(input, idMesa);

		}

		// data table vazio
		DataTablesOutput<Map<String, Object>> dados = new DataTablesOutput<>();
		List<Map<String, Object>> lista = new ArrayList<>();
		dados.setData(lista);
		return dados;
	}

	@ResponseBody
	@RequestMapping(value = { "/policiamento/buscar/dados/grafico/{id}" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	public List<DadosPoliciamentoDTO> listDadosParaGrafico(@PathVariable("id") Estabelecimento estabelecimento,
			ModelAndView mv) {
		/*
		 * respBanco[0] == nomeCidade 
		 * respBanco[1] == idCidade 
		 * respBanco[2] == nomeModalidade 
		 * respBanco[3] == idModalidade
		 * respBanco[4] == nomeInstituicao
		 * respBanco[5] == idInstituicao 
		 * respBanco[6] == qtdServidores 
		 * respBanco[7] == qtdModalidade
		 * 
		 */

		//List<Map<Object, Object>> listaCidades = new ArrayList<>();
		
		List<DadosPoliciamentoDTO> listaDados = new ArrayList<>();
		
		List<Object[]> listaBanco = servicePoliciamento.listarPoliciamentoDashboard(estabelecimento.getCidades());
		
		for (Object[] respBanco : listaBanco) {
			
			DadosPoliciamentoDTO dados = new DadosPoliciamentoDTO();
			
			dados.setNomeCidade(String.valueOf(respBanco[0]));
			dados.setIdCidade(Long.valueOf(""+respBanco[1]));
			
			for (DadosPoliciamentoDTO dd: listaDados) {
				if (dd.getNomeCidade().equalsIgnoreCase(String.valueOf(respBanco[0]))) {
					dados = dd;
				}
			}
			
			if(!listaDados.contains(dados)) {
				//Adiciona os dados na lista
				listaDados.add(dados);
			}
						
			InstituicaoDTO inst = new InstituicaoDTO();
			
			inst.setNomeInstituicao(String.valueOf(respBanco[4]));
			inst.setIdInstituicao(Long.valueOf(""+respBanco[5]));
			
			for (InstituicaoDTO idto: dados.getInstituicoes()) {
				if(idto.getNomeInstituicao().equalsIgnoreCase(String.valueOf(respBanco[4]))) {
					inst = idto;
				}
			}
			
			if(!dados.getInstituicoes().contains(inst)) {
				//Adiciona a instituicao nos dados
				dados.getInstituicoes().add(inst);					
			}	
			
			ModalidadeDTO modalidade = new ModalidadeDTO();
			
			modalidade.setNomeModalidade(String.valueOf(respBanco[2]));
			modalidade.setIdModalidade(Long.valueOf(""+respBanco[3]));
			modalidade.setQtdPoliciamento(Long.valueOf(""+respBanco[6]))	;
			modalidade.setQtdModalidade(Long.valueOf(""+respBanco[7]));
			
			for(InstituicaoDTO insts : dados.getInstituicoes()) {
				if(!inst.getModalidades().contains(modalidade)) {
					inst.getModalidades().add(modalidade);
				}
			}
			
		}
		
		return listaDados;

	}

}
