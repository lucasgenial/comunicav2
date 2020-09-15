package br.com.cicom.comunicacicom.DSPrimary.service.seguranca;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Grupo;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.repository.seguranca.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repositorio;

	public Grupo cadastrar(Grupo grupo) {
		return repositorio.saveAndFlush(grupo);
	}

	public Grupo alterar(Long id, Grupo grupo) {
		Grupo grupoBanco = repositorio.getOne(id);

		if (grupoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(grupo, grupoBanco, "id");

		return repositorio.saveAndFlush(grupoBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Grupo> buscaPorId(Long id) {
		return (Optional<Grupo>) repositorio.findById(id);
	}

	public List<Grupo> listarTodos() {
		return repositorio.findAll();
	}

	public List<Permissao> listaPermissoesdoGrupo(Long id){
		return repositorio.findById(id).get().getPermissoes();
	}
	
	public Grupo deletarPermissaoDoGrupo(Long id, Grupo grupo) {
		List<Permissao> permissoes = repositorio.findById(grupo.getId()).get().getPermissoes();
		grupo.getPermissoes().clear();
		for(int i=0; i<permissoes.size(); i++){
			if(!permissoes.get(i).getId().equals(id)) {
				grupo.getPermissoes().add(permissoes.get(i));
			}
		}
		return grupo;
	}
	

	public Grupo desativar(Long id, boolean situacao) {
		Grupo gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public Grupo buscaPorNome(String value) {
		return (Grupo) repositorio.findByNome(value);
	}
	
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */
	 
	public DataTablesOutput<Grupo> listarTodosGrupos(@Valid DataTablesInput input) {
		return repositorio.findAll(input);
	}

}
