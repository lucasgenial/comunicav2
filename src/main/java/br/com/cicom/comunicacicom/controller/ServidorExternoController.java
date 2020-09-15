package br.com.cicom.comunicacicom.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.swing.text.MaskFormatter;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorExterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.HierarquiaService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo.ServidorExternoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;

@Controller
public class ServidorExternoController {

	private static final Logger logger = LoggerFactory.getLogger(ServidorExternoController.class);

	@Autowired
	private UsuarioService servicoUsuario;

	@Autowired
	private ServidorExternoService servicoServidorExterno;

	@Autowired
	private InstituicaoService servicoInstituicoes;

	@Autowired
	private HierarquiaService servicoHierarquia;


	
	@GetMapping("*/historico/servidoresExternos")
	public String telaHistorico(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Servidores Externos");

		return "fragmentos/historicos/historicoServidorExterno";
	}
	

	@ResponseBody
	@RequestMapping(value = "/servidoresexternos/buscar/historico" ,method = { RequestMethod.POST,RequestMethod.GET })
	public DataTablesOutput<ServidorExterno> listPOST(DataTablesInput input) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	return servicoServidorExterno.listarServidoresExternos(input, user) ;
	}
	
	
	@RequestMapping(value = "*/edita/servidorExterno/{id}", method = RequestMethod.GET)
	public String telaEditarServidorExterno(@PathVariable("id") Long id, Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
	
		model.addAttribute("usuario", user);

		ServidorExterno serv = servicoServidorExterno.buscaPorId(id).get();
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servidor Externo");
		model.addAttribute("servidor", serv);
		model.addAttribute("edicao", true);
		model.addAttribute("instituicoes", servicoInstituicoes.listarTodos());
		model.addAttribute("hierarquias", serv.getInstituicao().getHierarquias());

		return "fragmentos/edicoes/editaServidorExterno";
	}

	@RequestMapping(value = "*/cadastra/servidorExterno", method = RequestMethod.GET)
	public String telaCadastroServidorExterno(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor Externo");
		model.addAttribute("usuario",user);
		model.addAttribute("servidor", new ServidorExterno());
		model.addAttribute("instituicoes", servicoInstituicoes.listarTodos());

		return "fragmentos/cadastros/servidorExterno";
	}

	@RequestMapping(value = "**/admin/importar/servidor/externo", method = RequestMethod.GET)
	public String telaImportacaoServidorExterno(Model model) {
		
		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addAttribute("tituloPagina", "ComunicaCICOM - Importa Servidor Externo");

		return "fragmentos/utilitarios/importar-servidorExterno";
	}

	@RequestMapping(value = "**/admin/importar/servidor/externo", params = { "enviar" })
	public String importarServidores(Model model, MultipartFile arquivo) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario",user);

		ArrayList<String> linhasErro = new ArrayList<>();

		// Contador de Servidores Desativados: Status >>> False
		int qtdSerDesativado = 0;

		// Contador de Novos Servidores Adicionados:
		int qtdSerNovosAdicionado = 0;

		// Cria um novo arquivo na pasta temporária
		final String label = UUID.randomUUID().toString() + ".csv";
		final String filepath = "/tmp/" + label;

		// Recebe os bytes do Arquivo Original
		byte[] bytes = null;
			
		
		try {
			bytes = arquivo.getBytes();
		} catch (IOException e) {
			logger.error("Falha ao processar os bytes do arquivo", e);
			e.printStackTrace();
		}

		File fh = new File("/tmp/");

		if (!fh.exists()) {
			fh.mkdir();
		}

		try {
			// Grava os dados no novo arquivo
			FileOutputStream writer = new FileOutputStream(filepath);
			writer.write(bytes);
			writer.close();
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));

			String linha = "";

			Set<ServidorExterno> listaServidor = new HashSet<>();

			while ((linha = reader.readLine()) != null) {
				String[] parts = linha.split(";");
				String matricula = "";

				try {
					MaskFormatter mf = new MaskFormatter("AA.AAA.AAA-A");
					mf.setValueContainsLiteralCharacters(false);
					matricula = mf.valueToString(parts[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				ServidorExterno servidorBanco = servicoServidorExterno.buscarPorMatricula(matricula, true);

				// Verifica se o usuario já está cadastrado no banco
				if (servidorBanco == null) {
					// Verifica a Instituição
					Instituicao instituicao = servicoInstituicoes.buscaPorNome(parts[0]);

					// Verifica a Hierarquia
					Hierarquia hierarquia = servicoHierarquia.buscaPorNome(parts[3]);

					if (instituicao == null || hierarquia == null) {
						// Mostra qual a linha que contém erros
						linhasErro.add(linha);

						model.addAttribute("erro", true);
						model.addAttribute("msgErro", linhasErro);

					} else {
						// Cria um novo Usuário
						servidorBanco = new ServidorExterno();

						servidorBanco.setAtivo(true);
						servidorBanco.setNome(parts[2]);
						servidorBanco.setMatricula(matricula);
						servidorBanco.setInstituicao(instituicao);
						servidorBanco.setHierarquia(hierarquia);

						// Adiciona o Servidor No Banco
						servicoServidorExterno.cadastrar(servidorBanco);

						qtdSerNovosAdicionado++;
					}
				}

				listaServidor.add(servidorBanco);
			}
			int i = 0;
			for (ServidorExterno servExt : servicoServidorExterno.listarTodos()) {
				
//				System.out.println("Esta em -> "+i);
				i++;
				if (!listaServidor.contains(servExt)) {
					servExt.setAtivo(false);

					servicoServidorExterno.alterar(servExt.getId(), servExt);

					qtdSerDesativado++;

				}
			}
		} catch (IOException ex) {
			logger.error("Falha ao processar o arquivo", ex);
		}
		model.addAttribute("qtdAdicionados", qtdSerNovosAdicionado);
		model.addAttribute("qtdDesativados", qtdSerDesativado);
		model.addAttribute("finalizado", true);

		return "principal/importar-servidorExterno";
	}

	@PostMapping("*/salva/edicao/ServidorExterno")
	public String atualizaServidorExterno(@Valid ServidorExterno servidor, BindingResult result, RedirectAttributes redirect, Model model) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);

		if (result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);

			// Retorna o servidor com o erro
			redirect.addFlashAttribute("servidor", servidor);

			model.addAttribute("instituicoes", servicoInstituicoes.listarTodos());
			model.addAttribute("hierarquias", servidor.getInstituicao().getHierarquias());
			
			model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servidor Externo");

			return "fragmentos/edicoes/editaServidorExterno";

		} else {

			try {
				// Atualiza o Servidor
				servicoServidorExterno.alterar(servidor.getId(), servidor);

			} catch (Exception e) {
				// Sinaliza o erro
				redirect.addFlashAttribute("aviso", true); 

				if (e.getCause() instanceof ConstraintViolationException) {
					redirect.addFlashAttribute("mensagem", "Matricula já está em uso!");
				}
				redirect.addFlashAttribute("servidor", servidor);
				return "redirect:/admin/edicao/servidorExterno/" + servidor.getId();
			}
		}

		// Lista todos os servidores Externos
		model.addAttribute("servidores", servicoServidorExterno.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servidor Externo");

		return "redirect:/admin/historico/servidoresExternos";
	}

	@PostMapping("*/salva/cadastro/ServidorExterno")
	public String cadastraServidorExterno(@Valid ServidorExterno servidor, BindingResult result, RedirectAttributes redirect, Model model) {

		model.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

		if (result.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);

			// Retorna a pessoa com o erro
			redirect.addFlashAttribute("servidor", servidor);
			model.addAttribute("instituicoes", servicoInstituicoes.listarTodos());
			model.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor Externo");

			return "fragmentos/cadastros/servidorExterno";

		} else {
			// Atualiza o Servidor
			try {
				servicoServidorExterno.cadastrar(servidor);
			} catch (Exception e) {
				// Sinaliza o erro
				model.addAttribute("aviso", true); 

				if (e.getCause() instanceof ConstraintViolationException) {
					model.addAttribute("mensagem", "Matricula já está em uso!");
				}

				model.addAttribute("servidor", servidor);
				model.addAttribute("instituicoes", servicoInstituicoes.listarTodos());
				model.addAttribute("hierarquias", servicoInstituicoes.buscaPorId(servidor.getInstituicao().getId()).get().getHierarquias());
				model.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor Externo");

				return "fragmentos/cadastros/servidorExterno";
			}
		}

		// Lista todos os servidores Externos
		model.addAttribute("servidores", servicoServidorExterno.listarTodos());
		model.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor Externo");
		
		return "redirect:/admin/cadastra/servidorExterno";
	}
}
