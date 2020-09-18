package br.com.cicom.comunicacicom.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.service.AuditoriaService;
import br.com.cicom.comunicacicom.DSPrimary.POJO.Aviso;
import br.com.cicom.comunicacicom.DSPrimary.model.Formatador;
import br.com.cicom.comunicacicom.DSPrimary.model.GerenciadorDeEnvioPorEmail;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visita;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Email;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisComunica.Ocorrencia;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Policiamento;
import br.com.cicom.comunicacicom.DSPrimary.service.gestao.VisitaService;
import br.com.cicom.comunicacicom.DSPrimary.service.ocorrencia.OcorrenciaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.MesaService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.PoliciamentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.UnidadeService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReportController {

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private ServidorService servicoServidor;

	@Autowired
	private PoliciamentoService servicePoliciamento;

	@Autowired
	private MesaService serviceMesa;
	
	@Autowired
	private UnidadeService serviceUnidade;

	@Autowired
	private OcorrenciaService serviceOcorrencia;

	@Autowired
	private EmailService serviceEmail;

	@Autowired
	private AuditoriaService serviceAuditoria;

	@Autowired
	private VisitaService serviceVisita;

	@RequestMapping(value = "*/report/ocorrencia/{id}", method = RequestMethod.GET)
	public void ocorrencia(@PathVariable("id") Long id, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		final List<Ocorrencia> listReports = new ArrayList<Ocorrencia>();
		listReports.add(serviceOcorrencia.buscaPorId(id).get());

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Ocorrencia";
		Long Operador = user.getServidor().getId();

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());
		reportParam.put("Operador", Operador);

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/ocorrencia.jasper";

		JasperPrint impressao = null;
		try {

			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("Ocorrencia.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}

	@RequestMapping(value = "**/ocorrencias/enviarEmail/{id}", method = RequestMethod.POST)
	public ModelAndView envioporEmail(ModelAndView modelAndView, @PathVariable("id") Long id,
			@RequestParam("emailLista") List<String> emails, final HttpServletRequest request, ModelAndView model)
			throws IOException {
		String estabelecimento = servicoUsuario
				.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getServidor()
				.getEstabelecimento().getNome();

		final List<Ocorrencia> listReports = new ArrayList<Ocorrencia>();
		listReports.add(serviceOcorrencia.buscaPorId(id).get());

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Ocorrencia";
		Long Operador = user.getServidor().getId();

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());
		reportParam.put("Operador", Operador);

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/ocorrencia.jasper";

		String pdfPath = getClass().getClassLoader().getResource("").toString();
		pdfPath = pdfPath.substring(pdfPath.indexOf(':') + 1).replace("%20", " ") + "templates/principal/reports/";
		String nome = pdfPath + System.currentTimeMillis() + ".pdf";

		JasperPrint impressao = null;
		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);

			JasperExportManager.exportReportToPdfStream(impressao, new FileOutputStream(nome));
			serviceOcorrencia.buscaPorId(id).get().setEnviada(true);
			serviceOcorrencia.alterar(id, serviceOcorrencia.buscaPorId(id).get());

			for (String ids : emails) {
				Auditoria auditoria = new Auditoria();
				auditoria.setDatahora(LocalDateTime.now());
				auditoria.setUsuario(user.getId());
				auditoria.setHistorico("Enviada via E-Mail Ocorrencia Nº: "
						+ serviceOcorrencia.buscaPorId(id).get().getSic().toString() + " para "
						+ serviceEmail.buscaPorId(Long.valueOf(ids)).get().getEnderecoDeEmail());
				serviceAuditoria.cadastrar(auditoria);
			}

			new GerenciadorDeEnvioPorEmail().enviarEmail(nome, "OCORRÊNCIA - CICOM " + estabelecimento,
					new Formatador().formataParaEmail(serviceOcorrencia.buscaPorId(id).get()),
					new Formatador().listaDeEmails(emails, serviceEmail), serviceEmail.pegarRemetente(user),
					serviceEmail.pegarRemetenteSenha(user));

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}

		modelAndView.setViewName("redirect:/admin/ocorrencias/historico/emailEnviado");

		return modelAndView;
	}

	@RequestMapping(value = "**/ocorrencias/enviarSelecionadas",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView enviarVarias(@RequestParam(value = "listaDeOcorrencias", required = false) List<String> ocorrencias,
			@RequestParam(value = "listaDeEmails") List<String> listaDeEmails, ModelAndView model,
			final HttpServletRequest request) {

		String estabelecimento = servicoUsuario
				.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getServidor()
				.getEstabelecimento().getNome();

		final List<Ocorrencia> listReports = new Formatador().formataParaListaDeOcorrencias(ocorrencias,
				serviceOcorrencia);
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";
		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Ocorrencia";
		Long Operador = user.getServidor().getId();

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());
		reportParam.put("Operador", Operador);

		Thread.currentThread().getContextClassLoader();
		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/ocorrencia.jasper";

		String pdfPath = getClass().getClassLoader().getResource("").toString();
		pdfPath = pdfPath.substring(pdfPath.indexOf(':') + 1).replace("%20", " ") + "templates/principal/reports/";
		String nome = pdfPath + System.currentTimeMillis() + ".pdf";

		JasperPrint impressao = null;
		String erros="";
		Boolean ok = true;
		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);

			JasperExportManager.exportReportToPdfStream(impressao, new FileOutputStream(nome));

			String conta = serviceEmail.pegarRemetente(user);
			String senha = serviceEmail.pegarRemetenteSenha(user);
			for (String ids : ocorrencias) {
				serviceOcorrencia.buscaPorId(Long.valueOf(ids)).get().setEnviada(true);
				serviceOcorrencia.alterar(Long.valueOf(ids), serviceOcorrencia.buscaPorId(Long.valueOf(ids)).get());
				Auditoria auditoria = new Auditoria();
				auditoria.setDatahora(LocalDateTime.now());
				auditoria.setUsuario(user.getId());
				auditoria.setHistorico("Enviada via E-Mail Ocorrencia Nº: "
						+ serviceOcorrencia.buscaPorId(Long.valueOf(ids)).get().getSic().toString() + " para " + conta);
				serviceAuditoria.cadastrar(auditoria);

			}

			new GerenciadorDeEnvioPorEmail().enviarEmail(nome, estabelecimento,
					new Formatador().formataParaEmail(ocorrencias, serviceOcorrencia),
					new Formatador().listaDeEmails(listaDeEmails, serviceEmail), conta, senha);

		} catch (final Exception e) {
				
			erros = e.getMessage();
			ok = false;
		}
		
		
		  Aviso.criaAviso().mensagemCasoDeuCerto("E-mail enviado com sucesso")
		  .mensagemCasoDeuErrado("O email não foi enviado pelos seguintes erros -> "
		  +erros)
		  .linkCasoDeuCerto("/admin/historico/ocorrencias")
		  .linkCasoDeuErrado("/admin/historico/ocorrencias")
		  .booleanoQueVerificaSeDeuCerto(ok).
		  finalizaMensagem(model);
		 model.setViewName("fragmentos/historicos/historicoOcorrencias");
		 model.addObject("usuario", user);
		 model.addObject("tituloPagina", "ComunicaCICOM - Relação de Ocorrências");

			List<Email> emails = serviceUnidade.buscarEmailPorCidades(user.getServidor().getEstabelecimento().getCidades());
			if (serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br") != null) {
				emails.add(serviceEmail.buscarPeloEnderecoEmail("cicoc@ssp.ba.gov.br"));
			}
			model.addObject("emails", emails);
		return model;
	}
	
	@RequestMapping(value = "**/ocorrencias/imprimir/varias", method = {RequestMethod.GET,RequestMethod.POST})
	public String impimirVariasOcorrencias(@RequestParam(value = "listaDeOcorrencias", required = false) List<String> ocorrencias,
			ModelAndView model,
			final HttpServletRequest request, final HttpServletResponse response) {
		model.getModelMap().get("ocorrencias");
	
		final List<Ocorrencia> listReports = new Formatador().formataParaListaDeOcorrencias(ocorrencias,
				serviceOcorrencia);
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";
		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Ocorrencia";
		Long Operador = user.getServidor().getId();

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());
		reportParam.put("Operador", Operador);

		Thread.currentThread().getContextClassLoader();
		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/ocorrencia.jasper";

		String pdfPath = getClass().getClassLoader().getResource("").toString();
		pdfPath = pdfPath.substring(pdfPath.indexOf(':') + 1).replace("%20", " ") + "templates/principal/reports/";

		JasperPrint impressao = null;
		
		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("servidor.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}

		return "redirect:/admin/ocorrencias/historico/emailEnviado";
	}

	@RequestMapping(value = "**/report/servidor/{id}", method = RequestMethod.GET)
	public void servidor(@PathVariable("id") Long id, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		final List<Servidor> listReports = new ArrayList<Servidor>();
		listReports.add(servicoServidor.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";
		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Servidor";

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();
		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/servidor.jasper";

		JasperPrint impressao = null;
		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("servidor.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}

	@RequestMapping(value = "**/report/efetivoExterno/{id}", method = RequestMethod.GET)
	public void efetivoExterno(@PathVariable("id") Long id, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		final List<Policiamento> listReports = new ArrayList<Policiamento>();
		listReports.add(servicePoliciamento.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Policiamento";

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/efetivoExterno.jasper";

		JasperPrint impressao = null;

		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("efetivoExterno.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}

	@RequestMapping(value = "**/report/mesa_completa/{id}", method = RequestMethod.GET)
	public void mesa(@PathVariable("id") Long id, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		final List<Mesa> listReports = new ArrayList<Mesa>();
		listReports.add(serviceMesa.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);
		Mesa mesa = serviceMesa.buscaPorId(id).get();

		JRAbstractBeanDataSource ListaDeTeleVideoMonitoramento = new JRBeanCollectionDataSource(
				mesa.getServidoresVideoMonitoramento());
		JRAbstractBeanDataSource ListaDeCoordenadoresAdjuntos = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadoresAdjuntos());
		JRAbstractBeanDataSource ListaDeCoordenadores = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadores());
		JRAbstractBeanDataSource ListaDeTeleAtendimento = new JRBeanCollectionDataSource(
				mesa.getServidoresTeleAtendimento());
		JRAbstractBeanDataSource ListaDeTeledespacho = new JRBeanCollectionDataSource(mesa.getServidoresTeledespacho());
		Map<String, Object> reportParam = new HashMap<>();

		reportParam.put("imagem", imagem.getImage());
		reportParam.put("OperadoresDeVideoMonitoramento", ListaDeTeleVideoMonitoramento);
		reportParam.put("OperadoresDeTeleAtendimento", ListaDeTeleAtendimento);
		reportParam.put("listaDeCoordenadoresAdjuntos", ListaDeCoordenadoresAdjuntos);
		reportParam.put("listaDeCoordenadores", ListaDeCoordenadores);
		reportParam.put("OperadoresDeTeledespacho", ListaDeTeledespacho);

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/mesa_mesaCompleta.jasper";

		JasperPrint impressao = null;

		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("mesa.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}
	@RequestMapping(value = "**/report/mesa_efetivoExterno/{id}", method = RequestMethod.GET)
	public void mesaEfetivoExterno(@PathVariable("id") Long id, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		final List<Mesa> listReports = new ArrayList<Mesa>();
		listReports.add(serviceMesa.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);
		Mesa mesa = serviceMesa.buscaPorId(id).get();

		JRAbstractBeanDataSource ListaDeTeleVideoMonitoramento = new JRBeanCollectionDataSource(
				mesa.getServidoresVideoMonitoramento());
		JRAbstractBeanDataSource ListaDeCoordenadoresAdjuntos = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadoresAdjuntos());
		JRAbstractBeanDataSource ListaDeCoordenadores = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadores());
		JRAbstractBeanDataSource ListaDeTeleAtendimento = new JRBeanCollectionDataSource(
				mesa.getServidoresTeleAtendimento());
		JRAbstractBeanDataSource ListaDeTeledespacho = new JRBeanCollectionDataSource(mesa.getServidoresTeledespacho());
		Map<String, Object> reportParam = new HashMap<>();

		reportParam.put("imagem", imagem.getImage());
		reportParam.put("OperadoresDeVideoMonitoramento", ListaDeTeleVideoMonitoramento);
		reportParam.put("OperadoresDeTeleAtendimento", ListaDeTeleAtendimento);
		reportParam.put("listaDeCoordenadoresAdjuntos", ListaDeCoordenadoresAdjuntos);
		reportParam.put("listaDeCoordenadores", ListaDeCoordenadores);
		reportParam.put("OperadoresDeTeledespacho", ListaDeTeledespacho);

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/mesa_efetivoExterno.jasper";

		try {
			JasperPrint impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("mesa.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}
	
	@RequestMapping(value = "**/report/mesa_efetivoInterno/{id}", method = RequestMethod.GET)
	public void mesaEfetivoInterno(@PathVariable("id") Long id, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		final List<Mesa> listReports = new ArrayList<Mesa>();
		listReports.add(serviceMesa.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);
		Mesa mesa = serviceMesa.buscaPorId(id).get();

		JRAbstractBeanDataSource ListaDeTeleVideoMonitoramento = new JRBeanCollectionDataSource(
				mesa.getServidoresVideoMonitoramento());
		JRAbstractBeanDataSource ListaDeCoordenadoresAdjuntos = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadoresAdjuntos());
		JRAbstractBeanDataSource ListaDeCoordenadores = new JRBeanCollectionDataSource(
				mesa.getServidoresCoordenadores());
		JRAbstractBeanDataSource ListaDeTeleAtendimento = new JRBeanCollectionDataSource(
				mesa.getServidoresTeleAtendimento());
		JRAbstractBeanDataSource ListaDeTeledespacho = new JRBeanCollectionDataSource(mesa.getServidoresTeledespacho());
		Map<String, Object> reportParam = new HashMap<>();

		reportParam.put("imagem", imagem.getImage());
		reportParam.put("OperadoresDeVideoMonitoramento", ListaDeTeleVideoMonitoramento);
		reportParam.put("OperadoresDeTeleAtendimento", ListaDeTeleAtendimento);
		reportParam.put("listaDeCoordenadoresAdjuntos", ListaDeCoordenadoresAdjuntos);
		reportParam.put("listaDeCoordenadores", ListaDeCoordenadores);
		reportParam.put("OperadoresDeTeledespacho", ListaDeTeledespacho);

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/mesa_efetivoInterno.jasper";

		try {
			JasperPrint impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("mesa.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}

	@RequestMapping(value = "**/report/visita/{id}", method = RequestMethod.GET)
	public void visita(@PathVariable("id") Long id, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {

		final List<Visita> listReports = new ArrayList<Visita>();
		listReports.add(serviceVisita.buscaPorId(id).get());

		final JRDataSource datasource = new JRBeanCollectionDataSource(listReports);

		String relatorioPathImagem = getClass().getClassLoader().getResource("").toString();
		relatorioPathImagem = relatorioPathImagem.substring(relatorioPathImagem.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/logoCicomReport.jpg";

		ImageIcon imagem = new ImageIcon(relatorioPathImagem);

		String Titulo = "Visita";

		Map<String, Object> reportParam = new HashMap<>();
		reportParam.put("Titulo", Titulo);
		reportParam.put("imagem", imagem.getImage());

		Thread.currentThread().getContextClassLoader();

		String relatorioPath = getClass().getClassLoader().getResource("").toString();

		relatorioPath = relatorioPath.substring(relatorioPath.indexOf(':') + 1).replace("%20", " ")
				+ "templates/principal/reports/visita.jasper";

		JasperPrint impressao = null;
		try {
			impressao = JasperFillManager.fillReport(relatorioPath, reportParam, datasource);
			impressao.setName("visita.pdf");
			final byte[] bytes = JasperExportManager.exportReportToPdf(impressao);
			final byte[] arquivo = bytes;

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline");
			response.setContentLength(arquivo.length);

			final ServletOutputStream output = response.getOutputStream();
			output.write(arquivo, 0, arquivo.length);
			output.close();

		} catch (final Exception e) {
			throw new RuntimeException("Report: Erro ao gerar relatório", e);
		}
	}

}