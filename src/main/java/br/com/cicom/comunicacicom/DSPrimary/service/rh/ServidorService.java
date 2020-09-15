package br.com.cicom.comunicacicom.DSPrimary.service.rh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Instituicao;
import br.com.cicom.comunicacicom.DSPrimary.repository.rh.ServidorRepository;

@Service
public class ServidorService {
	@Autowired
	private ServidorRepository repositorio;

	public Servidor cadastrar(Servidor value) {
		return repositorio.saveAndFlush(value);
	}

	public Servidor alterar(Long id, Servidor servidor) {
		Servidor servidorBanco = repositorio.getOne(id);

		if (servidorBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(servidor, servidorBanco, "id");

		return repositorio.saveAndFlush(servidorBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Servidor> buscaPorId(Long id) {
		return (Optional<Servidor>) repositorio.findById(id);
	}

	public List<Servidor> listarTodos() {
		return repositorio.findAll();
	}

	public List<Servidor> listarTodosPorEstabelecimentoEStatus(Estabelecimento estabelecimento, boolean staus) {
		return repositorio.findAll();
	}

	
	public List<Servidor> buscarPorListaDeId(List<Long> lista) {

		List<Servidor> listaDeServidores = new ArrayList<Servidor>();

		for (int i = 0; i < lista.size(); i++) {
			listaDeServidores.add(repositorio.findById(lista.get(i)).get());
		}

		return listaDeServidores;

	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Servidor desativar(Long id, boolean situacao) {
		Servidor gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public List<Servidor> buscarPorEstabelecimento(Estabelecimento estabelecimento) {
		return repositorio.findByEstabelecimento(estabelecimento);
	}

	public List<Servidor> buscarPorEstabelecimento(List<Estabelecimento> estabelecimento) {
		List<Servidor> listaServidor = new ArrayList<>();

		for (Estabelecimento est : estabelecimento) {
			listaServidor.addAll(repositorio.findByEstabelecimento(est));
		}

		return listaServidor;
	}

	public List<Servidor> buscarPorEstabelecimentoInstituicao(List<Estabelecimento> estabelecimento,
			List<Instituicao> instituicoes) {
		List<Servidor> listaServidor = new ArrayList<>();

		for (Estabelecimento est : estabelecimento) {
			for (Instituicao inst : instituicoes) {
				listaServidor.addAll(repositorio.findByEstabelecimentoAndInstituicao(est, inst));
			}
		}

		return listaServidor;
	}

	public List<Servidor> buscarPorEstabelecimentoInstituicao(Estabelecimento estabelecimento,
			Instituicao instituicao) {

		return repositorio.findByEstabelecimentoAndInstituicao(estabelecimento, instituicao);

	}

	public List<Servidor> buscarPorMatricula(String matricula) {
		return (List<Servidor>) repositorio.findByMatriculaLike(matricula + "%");

	}
	
	public List<Servidor> buscarPorEstabelecimentoInstituicoesENaoEstarNaMesa(Estabelecimento estabelecimento, Set<Instituicao> instituicoes, List<ServidorFuncaoInterno> ids) {
				List<Long> lista = new ArrayList<>();
				for(ServidorFuncaoInterno servidorInterno : ids){
					lista.add(servidorInterno.getServidor().getId());
				}
				if(lista.isEmpty()) {
					lista.add(repositorio.count()+1);
				}
		return repositorio.findAllByEstabelecimentoAndInstituicaoInAndIdNotInAndAtivoIsTrue(estabelecimento, instituicoes,lista);

	}

	public Servidor buscarMatricula(String matricula) {
		return (Servidor) repositorio.findByMatricula(matricula);

	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Map<String, Object>> listarTodosServidores(@Valid DataTablesInput input, Usuario user) {
		
		if(!user.getGrupo().getNome().contentEquals("ADMINISTRADOR")) {		
			input.getColumns().get(1).getSearch().setValue(String.valueOf(user.getServidor().getEstabelecimento().getNome()));			
		}
		
		DataTablesOutput<Map<String, Object>> dados = repositorio.findAll(input,null,null,new Function<Servidor, Map<String, Object>>(){
			@Override
			public Map<String, Object> apply(Servidor servidor) {
				Map<String, Object> mapa = new HashMap<>();
					mapa.put("instituicao",servidor.getInstituicao().getNome());
					mapa.put("estabelecimento",servidor.getEstabelecimento().getNome());
					
					if(servidor.getMatricula() != null) {
						mapa.put("matricula",servidor.getMatricula());
					}else {
						mapa.put("matricula","");
					}
					
					mapa.put("gh",servidor.getHierarquia().getNome());
			 		mapa.put("id", servidor.getId());
			 		mapa.put("nome", servidor.getNome());
			 		mapa.put("funcao", servidor.getFuncao().getNome());

			 		if(servidor.getUsuario() !=null) {
						mapa.put("tipoDeAcesso",servidor.getUsuario().getGrupo().getNome());
				 	}else {
						mapa.put("tipoDeAcesso","----------------");
				 	}
				
					mapa.put("ativo",servidor.isAtivo());
					
			 		
				 return mapa;
			}
		});

		return dados;
	}
	
	public DataTablesOutput<Servidor> listTodosServidoresDT(@Valid DataTablesInput input){
		return repositorio.findAll(input);
		
	}
}