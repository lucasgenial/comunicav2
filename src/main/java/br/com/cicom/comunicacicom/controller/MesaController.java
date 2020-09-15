package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
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

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.POJO.MonitoraMesa;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Bairro;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorExterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.TipoFuncao;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.MesaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ModalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.PoliciamentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.RecursoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ServidorFuncaoInternoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.TipoServicoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;

@Controller
public class MesaController {
	
	@Autowired
	private UsuarioService servicoUsuario;
	
	@Autowired
	private InstituicaoService serviceInstituicao;
	
	@Autowired
	private FuncaoService serviceFuncao;
	
	@Autowired
	private ServidorService serviceServidor;
	
	@Autowired
	private MesaService serviceMesa;
	
	@Autowired
	private PoliciamentoService servicePoliciamento;
	
	@Autowired
	private ServidorFuncaoInternoService serviceServidorFuncaoInterno;

	@Autowired
	private AuditoriaService serviceAuditoria;

	@Autowired
	private TipoServicoService serviceTipoServico;

	@Autowired
	private ModalidadeService serviceModalidade;

	@Autowired
	private RecursoService serviceRecurso;

	@Autowired
	private UnidadeService serviceUnidade;
			
	private MonitoraMesa mapaDeStatusMesa = new MonitoraMesa();
	
	@GetMapping(value = "*/historico/mesas")
	public String telaCadastroDiarioMesa(Model model,String nome) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Histórico Mesa");
		model.addAttribute("usuario", user);
		model.addAttribute("horaAtual", LocalDateTime.now());
		model.addAttribute("mesas", serviceMesa.buscarListaPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome()));
		return "fragmentos/historicos/historicoMesas";

	}
	
	@GetMapping(value = "*/cadastra/mesa")
	public String telaCadastro5(Model model) {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		
		Mesa mesa = new Mesa();
		mesa.setNome(user.getServidor().getEstabelecimento().getNome()+" "+(serviceMesa.buscarQuantidadeDeMesasPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome())+1));
		
		if(LocalDateTime.now().getHour() >= 6 && LocalDateTime.now().getHour() <= 18) {
			mesa.setInicioPlantao(LocalDateTime.now().withHour(7).withMinute(0).withSecond(0));
			mesa.setFimPlantao(mesa.getInicioPlantao().plusHours(12));
		}else {
			mesa.setInicioPlantao(LocalDateTime.now().withHour(19).withMinute(0).withSecond(0));
			mesa.setFimPlantao(mesa.getInicioPlantao().plusHours(12));
		}
		
		model.addAttribute("mesa",mesa);
		model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Mesa");		
		model.addAttribute("listaDeInstituicoes", serviceInstituicao.listarTodos());
			
		return "fragmentos/cadastros/mesa";
	}
	
	
	//Aqui salva a nova mesa no banco de dados
	@RequestMapping(value = "*/salva/cadastro/mesa", method = RequestMethod.POST )
	public String gravaNovaMesa(@RequestParam(value="instituicoes",required = false) Set<Instituicao> instituicoes,	@Valid Mesa mesa, Model model, BindingResult result ) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Long milli_dif_agora = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - mesa.getInicioPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() ;  
		Long milli_dif = mesa.getFimPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - mesa.getInicioPlantao().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	
		if (mesa.getInstituicoes().size() == 0){
			ObjectError erro0 = new ObjectError("instituicoes","Selecione pelo menos uma instituição!");
			result.addError(erro0);
		}
		if( (milli_dif_agora > 43200000 || milli_dif_agora < 0 )) {
			ObjectError erro1 = new ObjectError("inicioPlantao","Data e hora de início do plantão inválido!");
			result.addError(erro1);
		}
		if( (milli_dif < 3600000)) {
			ObjectError erro2 = new ObjectError("fimPlantao","Intervalo de tempo entre o início e o término do plantão inválido!");
			result.addError(erro2);
		}
		if( (milli_dif > 86400000)) {
			ObjectError erro2 = new ObjectError("fimPlantao","Intervalo de tempo entre o início e o término do plantão inválido!");
			result.addError(erro2);
		}
		
		
		if ( result.hasErrors() ) {
			model.addAttribute("org.springframework.validation.BindingResult.mesa", result);
			model.addAttribute("tituloPagina", "ComunicaCICOM - Nova Mesa");		
			model.addAttribute("mesa", mesa);
			model.addAttribute("usuario", user);
			model.addAttribute("listaDeInstituicoes", serviceInstituicao.listarTodos());
			model.addAttribute("nomeMesa", mesa.getNome());
			model.addAttribute("instituicoes", mesa.getInstituicoes());
			
			return "fragmentos/cadastros/mesa";
		}

		List<Policiamento> policiamentosAtivos = servicePoliciamento.buscaPorListaDePoliciamentosAtivos(user.getServidor().getEstabelecimento().getCidades(), mesa.getInicioPlantao());
		if (!policiamentosAtivos.isEmpty()) {
			for (Policiamento policiamento : policiamentosAtivos) {
				if (mesa.getInstituicoes().contains(policiamento.getUnidade().getInstituicao())) {
					mesa.getListaDePoliciamentos().add(policiamento);
				}

			}
		}
		      
		 for(ServidorFuncaoInterno servidorInterno: mesa.getServidores()) {
			 servidorInterno.setMesa(mesa);
		  }
		
		 if(servicePoliciamento.buscaPorListaDePoliciamentosAtivos(user.getServidor().getEstabelecimento().getCidades(), mesa.getInicioPlantao()) != null) {
			 mesa.setListaDePoliciamentos(new HashSet<Policiamento>(servicePoliciamento.buscaPorListaDePoliciamentosAtivos(user.getServidor().getEstabelecimento().getCidades(), mesa.getInicioPlantao())));
			 for(Policiamento policiamento : servicePoliciamento.buscaPorListaDePoliciamentosAtivos(user.getServidor().getEstabelecimento().getCidades(), mesa.getInicioPlantao())){
				 policiamento.setMesa(mesa);
			 }
		 }
		  mesa.setEstabelecimento(user.getServidor().getEstabelecimento());
		  serviceMesa.cadastrar(mesa);

		  Auditoria auditoria = new Auditoria();
		  auditoria.setDatahora(LocalDateTime.now());
		  auditoria.setUsuario(user.getId());
		  auditoria.setHistorico("Cadastrou mesa: " + mesa.getId());
		  serviceAuditoria.cadastrar(auditoria);
		  
		  return "redirect:/admin/menu/mesa";
		}
	
	@RequestMapping(value = "**/gestaoefetivo/diariodemesa/visualizar/mesaCompleta/{id}", method = RequestMethod.GET)
	public ModelAndView visualizarMesaCompleta( @PathVariable("id") Long id, ModelAndView mv) {	
		mv.setViewName("fragmentos/visualizacoes/visualizaMesa");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
   		mv.addObject("mesa",serviceMesa.buscaPorId(id).get());
   		mv.addObject("tituloPagina", "ComunicaCICOM - Visualiza Mesa");
		return mv;
    }

	//Aqui abre a template de editar mesa,esse controller é acionado pelo botao "editar" na tela de histórico de mesas
	@RequestMapping(value = "*/edita/mesa/{id}", method = RequestMethod.GET)	
	public String editarMesa(@PathVariable("id") Long id, Model model) {		
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Mesa mesa = serviceMesa.buscaPorId(id).get();
		model.addAttribute("mesa", mesa);
		model.addAttribute("usuario", user);
		model.addAttribute("listaDeInstituicoes", serviceInstituicao.listarTodos());
		model.addAttribute("nomeMesa", mesa.getNome());
	    model.addAttribute("tituloPagina", "ComunicaCICOM - Edição Mesa");
	    mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está editando essa mesa.", mesa.getId());     
		
		return "fragmentos/edicoes/editaMesa";
	}
	
	//Aqui abre a template de editar mesa,esse controller é acionado pelo botao "editar" na tela de histórico de mesas
	@RequestMapping(value = "*/edita/mesa/interno/{id}", method = RequestMethod.GET)	
	public String editarEfetivosMesa(@PathVariable("id") Long id, Model model) {		
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Mesa mesa = serviceMesa.buscaPorId(id).get();
		model.addAttribute("funcoes", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_INTERNO));	
		model.addAttribute("mesa", mesa);
		model.addAttribute("usuario", user);
	    model.addAttribute("tituloPagina", "ComunicaCICOM - Edição Mesa");
		
	    mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está editando o efetivo interno dessa mesa.", mesa.getId());     
		
		return "fragmentos/edicoes/editaEfetivoInterno";
	}
	
	//Esse controller é acionado pelo botão de adicionar servidores,esse controller salva o estado atual da mesa e ativa o controller abaixo
	@PostMapping(value = "*/salva/edicao/mesa/interno/{id}", params = { "adicionar-efetivo" })
	public String salvarEstadoAtualDaMesa(Model model, @PathVariable("id") Long id, Mesa mesaEditando){

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Mesa mesa = serviceMesa.buscaPorId(id).get();
		mesa.setServidores(mesaEditando.getServidores());
			
		model.addAttribute("abrirModal","ok");
		model.addAttribute("mesa",mesa);
		model.addAttribute("usuario", user);
		model.addAttribute("servidoresMesa", serviceServidor.buscarPorEstabelecimentoInstituicoesENaoEstarNaMesa(user.getServidor().getEstabelecimento(),mesa.getInstituicoes(),mesa.getServidores()));
		model.addAttribute("funcoes", serviceFuncao.buscarPorPrioridadeMesa());			
		model.addAttribute("tituloPagina", "ComunicaCICOM - Edição Mesa");
		
		mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os efetivos internos dessa mesa.", mesa.getId());  
		
		return "fragmentos/edicoes/editaEfetivoInterno";
	}
		
	//Esse controller é acionado pelo botão de adicionar servidores,esse controller salva o estado atual da mesa e ativa o controller abaixo
	@PostMapping(value = "*/salva/edicao/mesa/interno/{id}", params = { "remover-servidorFuncao" })
	public String salvarEstadoAtualDaMesaParaRemoverOServidor(@PathVariable("id") Long id,Mesa mesaEditando, Model model, HttpServletRequest req) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		Integer rowId = Integer.valueOf(req.getParameter("remover-servidorFuncao"));
		Mesa mesa = serviceMesa.buscaPorId(id).get();
		mesaEditando.getServidores().remove(rowId.intValue());
		mesa.setServidores(mesaEditando.getServidores());
		model.addAttribute("mesa", mesa);
		model.addAttribute("usuario", user);
		model.addAttribute("servidoresMesa", serviceServidor.buscarPorEstabelecimentoInstituicoesENaoEstarNaMesa(user.getServidor().getEstabelecimento(), mesa.getInstituicoes(),mesa.getServidores()));
		model.addAttribute("funcoes", serviceFuncao.buscarPorPrioridadeMesa());		
		model.addAttribute("tituloPagina", "ComunicaCICOM - Edição Mesa");
		mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os servidores internos dessa mesa.", mesa.getId());  
			 
		return "fragmentos/edicoes/editaEfetivoInterno";
		}
	
	
		//Esse controller é acionado pelo botão "Adicionar Servidor" do modal de seleção de servidores
		@PostMapping(value = "*/salva/edicao/mesa/interno/{id}", params = { "adicionaServidorNaMesa" })
		public String insereServidoresNaListaEditando(@RequestParam("listaServidoresSelecionados")List<Long> listaServidores, Mesa mesaEditando, Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		List<Servidor> listaDeservidoreQueForamSelecionados = new ArrayList<>();
		listaDeservidoreQueForamSelecionados = serviceServidor.buscarPorListaDeId(listaServidores);
		
		Mesa mesa = serviceMesa.buscaPorId(mesaEditando.getId()).get();
				
			for(Servidor servidor : listaDeservidoreQueForamSelecionados){
				ServidorFuncaoInterno  servidorFuncao = new ServidorFuncaoInterno();
				servidorFuncao.setServidor(servidor);
				servidorFuncao.setInicioPlantao(mesa.getInicioPlantao());
				servidorFuncao.setFimPlantao(mesa.getFimPlantao());
				servidorFuncao.setPausa1(mesa.getInicioPlantao().toLocalTime().plusHours(4));
				servidorFuncao.setPausa2(mesa.getInicioPlantao().toLocalTime().plusHours(8));
				servidorFuncao.setFuncao(servidor.getFuncao());
				mesaEditando.getServidores().add(servidorFuncao);
			}
			mesa.setServidores(mesaEditando.getServidores());
			model.addAttribute("mesa", mesa);
			model.addAttribute("tituloPagina", "ComunicaCICOM - Edição Mesa");
			model.addAttribute("usuario", user);
		    model.addAttribute("funcoes",serviceFuncao.buscarPorPrioridadeMesa());		
			mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os efetivos internos dessa mesa.", mesa.getId());  

			return "fragmentos/edicoes/editaEfetivoInterno";
			
		}

		//Salva a mesa, alterada no banco de dados
		@PostMapping(value = "*/salva/edicao/mesa/{id}")	
		public String alterarMesa(@PathVariable("id") Long id,@RequestParam("instituicoes") List<Long> listaDeInstituicoes, Mesa mesa, Model model) {
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

				Set<Instituicao> instituicoes = new HashSet<>();
				for (int i = 0; i < listaDeInstituicoes.size(); i++) {
					instituicoes.add(serviceInstituicao.buscaPorId(listaDeInstituicoes.get(i)).get());
				}
			    mesa.setInstituicoes(instituicoes);
			    mesa.setServidores(serviceMesa.buscaPorId(id).get().getServidores());
				serviceMesa.alterar(id, mesa);

			    Auditoria auditoria = new Auditoria();
			    auditoria.setDatahora(LocalDateTime.now());
			    auditoria.setUsuario(user.getId());
			    auditoria.setHistorico("Alterações na mesa: " + mesa.getId());
			    serviceAuditoria.cadastrar(auditoria);

			    return "redirect:/admin/menu/mesa";
	    }
		
	//Salva a mesa, alterada no banco de dados
	@PostMapping(value = "*/salva/edicao/mesa/interno/{id}", params = { "salvar-efetivoInterno" })	
	public String alterarMesa(@PathVariable("id") Long id, Model model, Mesa mesaEditando) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	
		Mesa mesa = serviceMesa.buscaPorId(id).get();
		mesa.setServidores(mesaEditando.getServidores());			
	    mesa.atualizaIdsDosServidoresInternos(serviceMesa.buscaPorId(id).get());
	    mesa.setEstabelecimento(user.getServidor().getEstabelecimento());
	    serviceMesa.alterar(id, mesa);
	    Auditoria auditoria = new Auditoria();
	    auditoria.setDatahora(LocalDateTime.now());
	    auditoria.setUsuario(user.getId());
	    auditoria.setHistorico("Alterações na mesa: " + mesa.getId());
	    serviceAuditoria.cadastrar(auditoria);
	    
	return "redirect:/admin/menu/mesa";
    }
		
	
	@ResponseBody
	@GetMapping("**/gestaoefetivo/pesquisaEfetivosAtivos/{dataInicio}")
	public boolean consultarEfetivosAtivos(@PathVariable("dataInicio") String data, ModelAndView model) {
		DateTimeFormatter dhformat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	   	LocalDateTime dataInicioMesa = LocalDateTime.parse(data, dhformat);
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		return serviceServidorFuncaoInterno.verificarServidoresAtivos(serviceMesa.buscarListaPorNomeEstabelecimento(user.getServidor().getEstabelecimento().getNome()), dataInicioMesa);

	}
	

		@ResponseBody
		@RequestMapping(value = "/mesas/buscar/historico", method = { RequestMethod.POST,RequestMethod.GET })
		public DataTablesOutput<Mesa> listPOST(DataTablesInput input) {
			
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			
			if (user.getGrupo().getNome().equals("ADMINISTRADOR")) {
				return serviceMesa.buscardadosDataTable(input);
			} else {
				return serviceMesa.buscardadosDataTable(input,user.getServidor().getEstabelecimento());
			}
	
		}
		
		@ResponseBody
		@RequestMapping(value = "/buscar/status/mesa/{id}", method = { RequestMethod.POST,RequestMethod.GET })
		public String statusDaMesa(@PathVariable("id")Long id) {
			
			return mapaDeStatusMesa.getMensagens(id);
		
		}
		
		@ResponseBody
		@PostMapping("/atualizar/status/mesa")
		public void statusDaMesaAtualizar() {
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			mapaDeStatusMesa.removeMensagem(user.getLogin());
		}
		
		
			/*
			 * ################################################################
			 * ################### EFETIVOS EXTERNOS ##########################
			 * ################################################################ 
			 */
		
		
		/* Abre o histórico do efetivo externo de uma determinada mesa */
		@GetMapping(value = "*/historico/policiamentos/{id}")
		public String historicoEfetivoExternoDeUmaMesa(@PathVariable("id") Long id, Model model) {

			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Histórico Efetivo Externo da mesa");
			model.addAttribute("idMesa", id);
			model.addAttribute("usuario", user);

			return "fragmentos/historicos/historicoPoliciamento";

		}		
		
		
		/* começa o cadastro de um efetivo externo em uma mesa */
		@GetMapping(value = "*/cadastra/policiamento/{id}")
		public String cadastroEfetivoExterno(@PathVariable("id") Long id, Model model) {
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

			model.addAttribute("usuario", user);

			if (!model.containsAttribute("policiamento")) {
				// Objetos para a página cadastra efetivo externo
				model.addAttribute("unidade", serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(serviceMesa.buscaPorId(id).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
				model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
				model.addAttribute("modalidade", serviceModalidade.listarTodos());
				model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());
				model.addAttribute("bairro", new Bairro());
				model.addAttribute("localidade", new Localidade());
				model.addAttribute("recurso", serviceRecurso.listarTodos());
				model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
				model.addAttribute("mesa", serviceMesa.buscaPorId(id).get());
				Policiamento policiamento = new Policiamento();
				policiamento.setMesa(serviceMesa.buscaPorId(id).get());
				policiamento.setComecoPlantao(LocalDateTime.now().withSecond(0));
				policiamento.setTerminoPlantao(LocalDateTime.now().plusHours(12).withSecond(0));
				model.addAttribute("policiamento", policiamento);
				model.addAttribute("servidorFuncao", new ServidorFuncao());
				model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");
				mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os efetivos externos dessa mesa.", id);  

			}

			return "fragmentos/cadastros/policiamento";

		}
		

		@GetMapping(value = "*/edita/policiamento/{id}")
		public String editaEfetivoExterno(@PathVariable("id") Long id, Model model) {
			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

			model.addAttribute("usuario", user);

			Policiamento policiamento = servicePoliciamento.buscaPorId(id).get();

			// Objetos para a página cadastra efetivo externo
			model.addAttribute("unidade",serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(serviceMesa.buscaPorId(policiamento.getMesa().getId()).get().getInstituicoes(),	user.getServidor().getEstabelecimento()));
			model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
			model.addAttribute("modalidade", serviceModalidade.listarTodos());
			model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());

			
			 if (policiamento.getCidade() != null) {
				 	model.addAttribute("localidade", policiamento.getCidade().getLocalidades()); 
				 } else {
					 model.addAttribute("localidade", new Localidade()); 
				 } 
			 if(policiamento.getLocalidade() != null) { 
				 model.addAttribute("bairro",policiamento.getLocalidade().getBairros()); 
				} else {
					 model.addAttribute("bairro", new Bairro()); 
				}
			 
			model.addAttribute("recurso", serviceRecurso.listarTodos());
			model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
			model.addAttribute("mesa", servicePoliciamento.buscaPorId(id).get().getMesa());
			model.addAttribute("policiamento", policiamento);
			model.addAttribute("servidorFuncao", new ServidorFuncao());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");
			mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os efetivos externos dessa mesa.", servicePoliciamento.buscaPorId(id).get().getMesa().getId());  

			return "fragmentos/edicoes/editaPoliciamento";

		}
		

		// Esse controller é acionado pelo botão de remover servidores,esse controller
		// salva o estado atual da guarnição e ativa o controller abaixo
		@PostMapping(value = "*/salva/edicao/policiamento/{id}", params = { "salvar" })
		public String salvarEfetivoExterno(Policiamento policiamento, @PathVariable("id") Long id) {
			policiamento.setMesa(servicePoliciamento.buscaPorId(policiamento.getId()).get().getMesa());
			policiamento.atualizaIdsDosServidoresDaGuarnicao(servicePoliciamento.buscaPorId(policiamento.getId()).get());
			servicePoliciamento.alterar(policiamento.getId(), policiamento);

			return "redirect:/admin/historico/policiamentos/" + policiamento.getMesa().getId();
		}

		// Esse controller é acionado pelo botão de adicionar servidores, sse controller
		// salva o estado atual da guarnição e ativa o controller abaixo
		
		// Esse controller é acionado pelo botão de remover servidores,esse controller
		// salva o estado atual da guarnição e ativa o controller abaixo
		@PostMapping(value = "*/salva/edicao/policiamento/{id}", params = {"remover-efetivoGuarnicao" })
		public String salvarEstadoAtualDoEfetivoExternoParaRemoverOServidor(@PathVariable("id") Long id, Policiamento policiamento, final HttpServletRequest req, Model model) {

			final Integer rowId = Integer.valueOf(req.getParameter("remover-efetivoGuarnicao"));

			policiamento.getGuarnicao().remove(rowId.intValue());

			Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

			model.addAttribute("usuario", user);

			// Objetos para a página cadastra efetivo externo

			model.addAttribute("unidade",serviceUnidade.buscarPorListaDeInstituicaoEEstabelecimento(serviceMesa.buscaPorId(servicePoliciamento.buscaPorId(id).get().getMesa().getId()).get().getInstituicoes(), user.getServidor().getEstabelecimento()));
			model.addAttribute("tipoServico", serviceTipoServico.listarTodos());
			model.addAttribute("modalidade", serviceModalidade.listarTodos());

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

			model.addAttribute("cidade", user.getServidor().getEstabelecimento().getCidades());
			model.addAttribute("abrirModal", null);
			model.addAttribute("recurso", serviceRecurso.listarTodos());
			model.addAttribute("funcao", serviceFuncao.buscarPorTipoDeFuncao(TipoFuncao.EFETIVO_EXTERNO));
			model.addAttribute("mesa", servicePoliciamento.buscaPorId(id).get().getMesa());
			model.addAttribute("policiamento", policiamento);
			model.addAttribute("servidorFuncao", new ServidorFuncao());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Cadastro Efetivo Externo");
			mapaDeStatusMesa.adicionaMensagem(user.getLogin(), user.getServidor().getSobreNome()+ " está gerenciando os efetivos externos dessa mesa.", servicePoliciamento.buscaPorId(id).get().getMesa().getId());  

			return "fragmentos/edicoes/editaPoliciamento";

		}

	}
