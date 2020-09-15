package br.com.cicom.comunicacicom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cicom.comunicacicom.DSPrimary.POJO.DadosDePesquisaParaGerarGraficos; 
import br.com.cicom.comunicacicom.DSPrimary.POJO.Aviso.AvisoBuilderMensagemCorreto;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.DadoEstatitico;
import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.DadosEstatisticosPorMes;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Localidade;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Tipificacao;
import br.com.cicom.comunicacicom.DSPrimary.service.estatistica.CategoriaService;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.LocalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.TipificacaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;

@Controller
public class DadosEstatisticosController {
	
	@Autowired
	private OcorrenciaService serviceOcorrencia;
	
	@Autowired
	private TipificacaoService serviceTipificacao;//delivelton
	
	@Autowired
	private LocalidadeService serviceLocalidade;//delivelton

	@Autowired
	private UsuarioService servicoUsuario;//delivelton
	
	@Autowired
	private EstabelecimentoService servicoEstabelecimento;//delivelton

	@Autowired
	private CategoriaService serviceCategoria;//delivelton
	
	@PostMapping(value = "/gerarDadosEstatisticos", params = { "persquisarPor" })
	public String gerarDadosEstatisticos(@RequestParam(value = "tipificacoesSelecionadas", required = false) List<Tipificacao> tipificacoesSelecionadas ,DadosDePesquisaParaGerarGraficos parametrosDePesquisa,RedirectAttributes
			 redirectAttributes,Model model, final HttpServletRequest req) throws JsonProcessingException {
		
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		
		List<Object[]> respostaBanco = new ArrayList<>();
		List<Tipificacao> listaTipificacoes = new ArrayList<>();
		
		Long rowId = Long.valueOf(req.getParameter("persquisarPor"));
		String categoria="";
	
		if(rowId == 0) {
			
			if(tipificacoesSelecionadas == null) {
				new AvisoBuilderMensagemCorreto().mensagemCasoDeuCerto("Para esse tipo de pesquisa é necessário selecionar alguma Tipifiacação").mensagemCasoDeuErrado("").linkCasoDeuCerto("/admin/relatorio/dadosEstatisticos#dadosGerados").linkCasoDeuErrado("/admin/relatorio/dadosEstatisticos#dadosGerados").booleanoQueVerificaSeDeuCerto(true).finalizaMensagem(model);
				return "redirect:/admin/relatorio/dadosEstatisticos#dadosGerados";
			}
			
			listaTipificacoes = tipificacoesSelecionadas;
			
			if(tipificacoesSelecionadas.size()==1) {
				categoria = tipificacoesSelecionadas.get(0).getNome();
			}
			
		}else {
			listaTipificacoes = serviceCategoria.buscaPorId(rowId).get().getTiposOcorrencia();
			categoria = serviceCategoria.buscaPorId(rowId).get().getNome();
		}

		List<DadoEstatitico> itens = new ArrayList<>();
		
		if(parametrosDePesquisa.getMesInicial()==null) {
			parametrosDePesquisa.setMesInicial(1);
		}
		
		if(parametrosDePesquisa.getMesFinal() == null) {
			parametrosDePesquisa.setMesFinal(12);
		}

		if(parametrosDePesquisa.getIdCidade().size() == 0) {
				respostaBanco = serviceOcorrencia.buscaDadosEstatisticos(servicoEstabelecimento.buscaPorId(parametrosDePesquisa.getIdEstabelecimento()).get().getCidades(), listaTipificacoes,
				parametrosDePesquisa.getAno(), parametrosDePesquisa.getAno(), parametrosDePesquisa.getMesInicial(), parametrosDePesquisa.getMesFinal());
		}else{					
				respostaBanco = serviceOcorrencia.buscaDadosEstatisticos(parametrosDePesquisa.getIdCidade(), listaTipificacoes,
				parametrosDePesquisa.getAno(), parametrosDePesquisa.getAno(),  parametrosDePesquisa.getMesInicial(), parametrosDePesquisa.getMesFinal());
		}
		
		List<String> nome = new ArrayList<>();
		List<String> quantidade = new ArrayList<>();
	
		DadoEstatitico dadoComSomaDeCadaMes = new DadoEstatitico();
		
	for (Object[] resposta : respostaBanco) {

			DadoEstatitico novo = new DadoEstatitico();
			novo.setTipificacao((String) resposta[0]);
			novo.setJaneiro((Long) resposta[1]);
			novo.setFevereiro((Long) resposta[2]);
			novo.setMarco((Long) resposta[3]);
			novo.setAbril((Long) resposta[4]);
			novo.setMaio((Long) resposta[5]);
			novo.setJunho((Long) resposta[6]);
			novo.setJulho((Long) resposta[7]);
			novo.setAgosto((Long) resposta[8]);
			novo.setSetembro((Long) resposta[9]);
			novo.setOutubro((Long) resposta[10]);
			novo.setNovembro((Long) resposta[11]);
			novo.setDezembro((Long) resposta[12]);			
			
			dadoComSomaDeCadaMes.setJaneiro(dadoComSomaDeCadaMes.getJaneiro()+(Long) resposta[1]);
			dadoComSomaDeCadaMes.setFevereiro(dadoComSomaDeCadaMes.getFevereiro()+(Long) resposta[2]);
			dadoComSomaDeCadaMes.setMarco(dadoComSomaDeCadaMes.getMarco()+(Long) resposta[3]);
			dadoComSomaDeCadaMes.setAbril(dadoComSomaDeCadaMes.getAbril()+(Long) resposta[4]);
			dadoComSomaDeCadaMes.setMaio(dadoComSomaDeCadaMes.getMaio()+(Long) resposta[5]);
			dadoComSomaDeCadaMes.setJunho(dadoComSomaDeCadaMes.getJunho()+(Long) resposta[6]);
			dadoComSomaDeCadaMes.setJulho(dadoComSomaDeCadaMes.getJulho()+(Long) resposta[7]);
			dadoComSomaDeCadaMes.setAgosto(dadoComSomaDeCadaMes.getAgosto()+(Long) resposta[8]);
			dadoComSomaDeCadaMes.setSetembro(dadoComSomaDeCadaMes.getSetembro()+(Long) resposta[9]);
			dadoComSomaDeCadaMes.setOutubro(dadoComSomaDeCadaMes.getOutubro()+(Long) resposta[10]);
			dadoComSomaDeCadaMes.setNovembro(dadoComSomaDeCadaMes.getNovembro()+(Long) resposta[11]);
			dadoComSomaDeCadaMes.setDezembro(dadoComSomaDeCadaMes.getDezembro()+(Long) resposta[12]);

			nome.add(novo.getTipificacao());
			quantidade.add(Long.toString(novo.getTotal()));
			
			itens.add(novo);
		}
		dadoComSomaDeCadaMes.setTipificacao("TOTAL");
		itens.add(dadoComSomaDeCadaMes);
	
	
	
	 	ObjectMapper objectMapper = new ObjectMapper();
	 	String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(nome);
		String nomeLocalizacao = "";
		
		if(parametrosDePesquisa.getIdLocalidade() != null) {
			Localidade localidade = serviceLocalidade.buscaPorId(parametrosDePesquisa.getIdLocalidade()).get();
			nomeLocalizacao = localidade.getCidade().getNome()+" | "+localidade.getNome();
		} else if(parametrosDePesquisa.getIdCidade().size() != 0){
			
		for(Cidade cidade: parametrosDePesquisa.getIdCidade()) {
			
			nomeLocalizacao += ", "+cidade.getNome()+" ";
		}
			nomeLocalizacao = nomeLocalizacao.replaceFirst(",","");
			
		}else {
			nomeLocalizacao = servicoEstabelecimento.buscaPorId(parametrosDePesquisa.getIdEstabelecimento()).get().getNome();			
		}
		redirectAttributes.addFlashAttribute("nomeLocalizacao", nomeLocalizacao);
		redirectAttributes.addFlashAttribute("categoria", rowId);
		redirectAttributes.addFlashAttribute("resultados", itens);
		redirectAttributes.addFlashAttribute("resultadosGrafico",json);
		redirectAttributes.addFlashAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
		redirectAttributes.addFlashAttribute("dadosDePesquisa", parametrosDePesquisa);
		redirectAttributes.addFlashAttribute("tituloPagina", "ComunicaCICOM - Gerador de Dados Estatistico");
		redirectAttributes.addFlashAttribute("categoriaDaTipificacao", categoria);

		
		return "redirect:/admin/relatorio/dadosEstatisticos#dadosGerados";

	}
//
//	@GetMapping("**/gerarDadosViolenciaContraAMulher")
//	public ModelAndView gerarDadosviolenciaContraAMulher() {
//
//		ModelAndView model = new ModelAndView();
//
//		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
//
//		List<DadosEstatisticosPorMes> dadosVindosDoBanco = serviceOcorrencia.buscaDadosEstatisticosCategoriaPorMes(
//				user.getServidor().getEstabelecimento(),
//				serviceCategoria.buscarPorNome("CRIMES CONTRA A MULHER").get(0), LocalDateTime.now().getYear());
//		model.addObject("usuario", user);
//		model.addObject("resultados", dadosVindosDoBanco);
//		model.addObject("ocorrencia", new Ocorrencia());
//		model.addObject("tituloPagina", "ComunicaCICOM - Gerador de Dados Violencia Contra a Mulher");
//
//		model.setViewName("fragmentos/relatorio/violenciaContraAMulher");
//
//		return model;
//
//	}

//	@GetMapping("**/gerarDadosPerfilCvli")
//	public ModelAndView gerarDadosPerfilCvli() {
//
//		ModelAndView model = new ModelAndView();
//
//		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
//
//		List<DadosEstatisticosPorMes> dadosVindosDoBanco = serviceOcorrencia.buscaDadosEstatisticosCategoriaPorMes(
//				user.getServidor().getEstabelecimento(),
//				serviceCategoria.buscarPorNome("CVLI - CRIMES VIOLENTOS LETAIS INTENCIONAIS").get(0), LocalDateTime.now().getYear());
//		Map<Object, Object> dados = new HashMap<>();
//		List<String> nome = new ArrayList<>();
//		List<String> quantidade = new ArrayList<>();
//
//		for (DadosEstatisticosPorMes mes : dadosVindosDoBanco) {
//			nome.add(mes.getMes());
//			quantidade.add(Long.toString(mes.getTotalDoMes()));
//		}
//
//		dados.put("nome", nome);
//		dados.put("quantidade", quantidade);
//		
//		model.addObject("tipificacoes", dadosVindosDoBanco.get(0).getDados());
//		model.addObject("usuario", user);
//		model.addObject("resultados", dadosVindosDoBanco);
//		model.addObject("dados", dados);
//		model.addObject("tituloPagina", "ComunicaCICOM - Gerador de Dados Perfil CVLI");
//		model.setViewName("fragmentos/relatorio/perfilCvli");
//
//		return model;
//
//	}
	
//	@ResponseBody
//	@RequestMapping(value = "**/lista/dados/perfilCvli")
//	public Map<Object, Object> jsondadosPerfilCvli(ModelAndView mv) {
//		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
//
//		List<DadosEstatisticosPorMes> dadosVindosDoBanco = serviceOcorrencia.buscaDadosEstatisticosCategoriaPorMes(
//				user.getServidor().getEstabelecimento(),
//				serviceCategoria.buscarPorNome("CVLI - CRIMES VIOLENTOS LETAIS INTENCIONAIS").get(0), LocalDateTime.now().getYear());
//		Map<Object, Object> resultado = new HashMap<>();
//		List<String> nome = new ArrayList<>();
//		List<String> quantidade = new ArrayList<>();
//
//		Collections.reverse(dadosVindosDoBanco);
//		for (DadosEstatisticosPorMes mes : dadosVindosDoBanco) {
//			nome.add(mes.getMes());
//			quantidade.add(Long.toString(mes.getTotalDoMes()));
//		}
//
//		resultado.put("nome", nome);
//		resultado.put("quantidade", quantidade);
//
//		return resultado;
//	}
//	
	@ResponseBody
	@RequestMapping(value = "**/lista/dados/perfilCvli/especificos/{id}")
	public Map<Object, Object> jsonDadosPerfilCvliEspecificos(@PathVariable("id") Long id, ModelAndView mv) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());

		List<DadosEstatisticosPorMes> dadosVindosDoBanco = serviceOcorrencia.buscaDadosEstatisticosCategoriaPorMes(
				user.getServidor().getEstabelecimento(),new ArrayList<Tipificacao>(Arrays.asList(serviceTipificacao.buscaPorId(id).get())), LocalDateTime.now().getYear());
		Map<Object, Object> resultado = new HashMap<>();
		List<String> nome = new ArrayList<>();
		List<String> quantidade = new ArrayList<>();

		Collections.reverse(dadosVindosDoBanco);
		for (DadosEstatisticosPorMes mes : dadosVindosDoBanco) {
			nome.add(mes.getMes());
			quantidade.add(Long.toString(mes.getTotalDoMes()));
		}

		resultado.put("nome", nome);
		resultado.put("quantidade", quantidade);

		return resultado;
	}
	
}