package br.com.cicom.comunicacicom.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Cidade;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Hierarquia;
import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.localizacao.UfService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EmailService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EscolaridadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EstadoCivilService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.EtniaService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.FuncaoService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.NacionalidadeService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.ServidorService;
import br.com.cicom.comunicacicom.DSPrimary.service.rh.TipoSanguineoService;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.EstabelecimentoService;
import br.com.cicom.comunicacicom.DSPrimary.service.sisGeral.InstituicaoService;

@Controller
public class MenuServidoresController {

	@Autowired
	private ServidorService serviceServidor;

	@Autowired
	private UsuarioService servicoUsuario;

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
	private EmailService serviceEmail;

	@GetMapping("*/historico/servidores")
	public String telaHistorico(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		model.addAttribute("usuario", user);
		
		// Titulo da Página
		model.addAttribute("tituloPagina", "ComunicaCICOM - Relação de Servidores");

		return "fragmentos/historicos/historicoServidores";
	}	
	
	
	@GetMapping("*/servidores/cadastroRealizadoSucesso")
	public ModelAndView realizadoSucesso() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("linkRespostaCadastro", "/logout");
		mv.addObject("avisoLinha1", "Cadastro realizado com sucesso!");
		mv.addObject("avisoLinha2", "Procure o seu Coordenador CICOM ou Supervidor CICOC para ativação.");
		mv.setViewName("fragmentos/utilitarios/mensagemServidorCadastradoComSucesso");
		mv.addObject("usuario",
				servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		return mv;
	}

	@GetMapping("**/servidores/cadastroAlteradoSucesso")
	public ModelAndView alteradoSucesso() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("linkRespostaCadastro", "/logout");
		mv.addObject("avisoLinha1", "Cadastro alterado com sucesso!");
		mv.addObject("avisoLinha2", "O seu cadastro de Usuário e Servidor foi alterado com sucesso");
		mv.setViewName("fragmentos/utilitarios/mensagemServidorCadastradoComSucesso");
		mv.addObject("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		return mv;
	}

	@GetMapping("**/servidores/cadastroTerceirizadoRealizadoSucesso")
	public ModelAndView terceirizadoAlteradoSucesso() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("linkRespostaCadastro", "/admin/historico/servidores");
		mv.addObject("avisoLinha1", "Cadastro Realizado com sucesso!");
		mv.addObject("avisoLinha2", "O  Servidor foi cadastrado com sucesso");
		mv.setViewName("fragmentos/utilitarios/mensagemServidorCadastradoComSucesso");
		mv.addObject("usuario",
				servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		return mv;
	}

	// Primeiro Acesso do Usuário
	// Redireciona automatimente
	@GetMapping("*/cadastra/servidor")
	public String cadastroServidor(Model mv) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		mv.addAttribute("usuario", user);

		if (!mv.containsAttribute("servidor")) {

			if (user != null && user.getServidor() != null) {
				mv.addAttribute("edicao", true);
				mv.addAttribute("servidor", user.getServidor());
			} else {
				mv.addAttribute("edicao", false);
				mv.addAttribute("servidor", new Servidor());
			}

			Hierarquia hierarquia = new Hierarquia();
			hierarquia.setNome("Selecionar");
			mv.addAttribute("hierarquias", hierarquia);

			Cidade cidade = new Cidade();
			cidade.setNome("Selecionar");
			mv.addAttribute("cidades", cidade);
		}

		mv.addAttribute("linkCadastro", "/admin/salva/cadastro/servidor");
		mv.addAttribute("ufs", serviceUf.listarTodos());
		mv.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		mv.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		mv.addAttribute("funcoes", serviceFuncao.listarTodos());
		mv.addAttribute("estadoCivis", serviceEstadoCivil.listarTodos());
		mv.addAttribute("escolaridades", serviceEscolaridade.listarTodos());
		mv.addAttribute("tipoSanguineos", serviceTipoSanguineo.listarTodos());
		mv.addAttribute("etnias", serviceEtnia.listarTodos());
		mv.addAttribute("nacionalidades", serviceNacionalidade.listarTodos());
		mv.addAttribute("erros");
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor");

		return "fragmentos/cadastros/servidor";
	}
	             
	@GetMapping("*/cadastra/servidorTerceirizado")
	public String cadastroServidorTerceirizado(Model mv) {

		mv.addAttribute("usuario",
				servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

		if (!mv.containsAttribute("servidor")) {
			mv.addAttribute("servidor", new Servidor());

			Hierarquia hierarquia = new Hierarquia();
			hierarquia.setNome("Selecionar");
			mv.addAttribute("hierarquias", hierarquia);

			Cidade cidade = new Cidade();
			cidade.setNome("Selecionar");
			mv.addAttribute("cidades", cidade);

			mv.addAttribute("EmailDuplicado", "");
		}
		mv.addAttribute("linkCadastro", "/admin/salva/cadastro/servidorTerceirizado");
		mv.addAttribute("servidorTerceirizado", "Ok");
		mv.addAttribute("ufs", serviceUf.listarTodos());
		mv.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		mv.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		mv.addAttribute("funcoes", serviceFuncao.listarTodos());
		mv.addAttribute("estadoCivis", serviceEstadoCivil.listarTodos());
		mv.addAttribute("escolaridades", serviceEscolaridade.listarTodos());
		mv.addAttribute("tipoSanguineos", serviceTipoSanguineo.listarTodos());
		mv.addAttribute("etnias", serviceEtnia.listarTodos());
		mv.addAttribute("nacionalidades", serviceNacionalidade.listarTodos());
		mv.addAttribute("erros");
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Novo Servidor");

		return "fragmentos/cadastros/cadastraServidor";
	}

	@GetMapping(value = "**/servidores/excluir/{id}")
	public String excluirServidor(@PathVariable("id") Long id, ModelAndView model) {
		
		serviceEmail.deletar(id);
		serviceServidor.deletar(id);

		return "redirect:/admin/historico/servidores";
	}

	@GetMapping(value = "**/servidores/status/{id}")
	public String modificarStatusServidor(@PathVariable("id") Long id, ModelAndView model) {
		Servidor servidorBanco = serviceServidor.buscaPorId(id).get();

		if (servidorBanco != null) {
			servidorBanco.setAtivo(!servidorBanco.isAtivo());
		}

		serviceServidor.alterar(id, servidorBanco);
		return "redirect:/admin/historico/servidores";
	}

	/* Se auto editar */
	@GetMapping("*/edita/servidor")
	public String editarServidor(Model mv) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		Map<String, Permissao> mapa = new HashMap<>();
		for (int i = 0; i < user.getGrupo().getPermissoes().size(); i++) {
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}

		mv.addAttribute("permissoes", mapa);
		mv.addAttribute("usuario", servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		mv.addAttribute("EmailDuplicado", "");
		mv.addAttribute("servidor", user.getServidor());
		mv.addAttribute("hierarquias", user.getServidor().getInstituicao().getHierarquias());
		mv.addAttribute("cidades", user.getServidor().getEstabelecimento().getCidades());
		mv.addAttribute("servidorProprio", "Ok");
		mv.addAttribute("linkCadastro", "/admin/cadastros/servidores/alterar/" + user.getServidor().getId());
		mv.addAttribute("ufs", serviceUf.listarTodos());
		
		if (mapa.containsKey("ADMINISTRADOR")) {
			mv.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		} else {
			mv.addAttribute("estabelecimentos", user.getServidor().getEstabelecimento());
		}

		mv.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		mv.addAttribute("funcoes", serviceFuncao.listarTodos());
		mv.addAttribute("estadoCivis", serviceEstadoCivil.listarTodos());
		mv.addAttribute("escolaridades", serviceEscolaridade.listarTodos());
		mv.addAttribute("tipoSanguineos", serviceTipoSanguineo.listarTodos());
		mv.addAttribute("etnias", serviceEtnia.listarTodos());
		mv.addAttribute("nacionalidades", serviceNacionalidade.listarTodos());
		mv.addAttribute("erros");
		
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servidor");

		return "fragmentos/edicoes/autoEditar";
	}

	/* coordenador editar terceirizado */
	@GetMapping("*/servidores/editar/{id}")
	public String editarServidor(@PathVariable("id") Long id, Model mv) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = servicoUsuario.buscaPeloLogin(auth.getName());
		Map<String, Permissao> mapa = new HashMap<>();
		for (int i = 0; i < user.getGrupo().getPermissoes().size(); i++) {
			mapa.put(user.getGrupo().getPermissoes().get(i).getNome(), user.getGrupo().getPermissoes().get(i));
		}
		mv.addAttribute("permissoes", mapa);
		mv.addAttribute("usuario", user);

		if (!mv.containsAttribute("servidor")) {
			// Atualizar o estabelecimento do servidor
			Servidor servidor = serviceServidor.buscaPorId(id).get();

			mv.addAttribute("servidor", servidor);
			Hierarquia hierarquia = new Hierarquia();
			hierarquia.setNome("Selecionar");
			mv.addAttribute("hierarquias", hierarquia);
			Cidade cidade = new Cidade();
			cidade.setNome("Selecionar");
			mv.addAttribute("cidades", cidade);
			mv.addAttribute("EmailDuplicado", "");
		}

		mv.addAttribute("linkCadastro", "/admin/cadastros/servidores/alterar/" + id);
		mv.addAttribute("servidorTerceirizado", "Ok");
		mv.addAttribute("servidorProprio", "Nao");
		mv.addAttribute("ufs", serviceUf.listarTodos());
		mv.addAttribute("cidades", serviceServidor.buscaPorId(id).get().getEndereco().getUfServidor().getCidades());
//		mv.addAttribute("estabelecimentos", user.getServidor().getEstabelecimento());
		if (mapa.containsKey("ADMINISTRADOR")) {
			mv.addAttribute("estabelecimentos", serviceEstabelecimento.listarTodos());
		} else {
			mv.addAttribute("estabelecimentos", user.getServidor().getEstabelecimento());
		}
		mv.addAttribute("instituicoes", serviceInstituicao.listarTodos());
		mv.addAttribute("hierarquias", serviceServidor.buscaPorId(id).get().getInstituicao().getHierarquias());
		mv.addAttribute("funcoes", serviceFuncao.listarTodos());
		mv.addAttribute("estadoCivis", serviceEstadoCivil.listarTodos());
		mv.addAttribute("escolaridades", serviceEscolaridade.listarTodos());
		mv.addAttribute("tipoSanguineos", serviceTipoSanguineo.listarTodos());
		mv.addAttribute("etnias", serviceEtnia.listarTodos());
		mv.addAttribute("nacionalidades", serviceNacionalidade.listarTodos());
		mv.addAttribute("erros");
		// Titulo da Página
		mv.addAttribute("tituloPagina", "ComunicaCICOM - Editar Servidor");

		return "fragmentos/cadastros/cadastraServidor";
	}

	/* salvar terceirizado editado no banco */
	@PostMapping(value = "**/servidores/alterar/{id}")
	public String editarServidor(@PathVariable("id") Long id, @Valid Servidor servidor, BindingResult result,
			RedirectAttributes redirectAttributes) {

		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.servidor", result);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.email", result);
			redirectAttributes.addFlashAttribute("servidor", servidor);
			redirectAttributes.addFlashAttribute("estabelecimento", servidor.getEstabelecimento());
			redirectAttributes.addFlashAttribute("instituicao", servidor.getInstituicao());
			redirectAttributes.addFlashAttribute("hierarquias", servidor.getHierarquia());
			redirectAttributes.addFlashAttribute("funcoes", servidor.getFuncao());
			redirectAttributes.addFlashAttribute("cidades", servidor.getEndereco().getCidade());
			redirectAttributes.addFlashAttribute("sexo", servidor.getSexo());
			return "redirect:/admin/cadastros/servidores/editar";
		}

		Servidor servidorBanco = serviceServidor.buscaPorId(id).get();
		servidor.getEmail().setEnderecoDeEmail(servidor.getEmail().getEnderecoDeEmail());
		servidor.getEmail().setNome(servidor.getNome());
		servidor.getEmail().setId(servidorBanco.getEmail().getId());
		servidor.getEndereco().setId(servidorBanco.getEndereco().getId());
		servidor.setHierarquia(servidor.getHierarquia());

		if (servidorBanco.getUsuario()!=null) {
			servidor.setUsuario(servidorBanco.getUsuario());
			servidor.getUsuario().setGrupo(servidorBanco.getUsuario().getGrupo());
		}

		if (servidor.getUsuario()!=null) {
			servidor.getUsuario().getEstabelecimento().clear();
			servidor.getUsuario().getEstabelecimento().add(servidor.getEstabelecimento());
		}

		if (servidor.getUsuario() != null 
			&& !servidor.getUsuario().getSenha().equals(servidorBanco.getUsuario().getSenha())) {
				servicoUsuario.alterar(servidor.getUsuario().getId(), servidor.getUsuario());
		}
		
		
		serviceServidor.alterar(id, servidor);

		if (user.getGrupo().getNome().equals("PRIMEIRO-LOGIN")) {

			servicoUsuario.desativar(user.getId(), false);

			return "redirect:/admin/servidores/cadastroRealizadoSucesso";
		}

		return "redirect:/admin/cadastro/servidores/cadastroAlteradoSucesso";

	}

	@GetMapping("**/servidores/visualizarTodos")
	public String telaVisualiza(Model model) {
		Usuario user = servicoUsuario.buscaPeloLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("usuario", user);
		model.addAttribute("servidores", serviceServidor.listarTodos());
		model.addAttribute("servidores",
				serviceServidor.buscarPorEstabelecimento(user.getServidor().getEstabelecimento()));

		return "fragmentos/visualizacoes/visualizaGrupoServidores";

	}

}
