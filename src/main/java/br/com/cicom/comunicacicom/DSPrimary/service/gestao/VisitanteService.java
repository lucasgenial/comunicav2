package br.com.cicom.comunicacicom.DSPrimary.service.gestao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;
import br.com.cicom.comunicacicom.DSPrimary.repository.gestao.VisitanteRepository;
import br.com.cicom.comunicacicom.DSPrimary.specification.VisitanteEspecification;

@Service
public class VisitanteService {

	@Autowired
	private VisitanteRepository repositorio;

	public Visitante cadastrar(Visitante Visitante) {
		return repositorio.saveAndFlush(Visitante);
	}

	public Visitante alterar(Long id, Visitante Visitante) {
		
		Visitante VisitanteBanco = repositorio.getOne(id);
		if (VisitanteBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(Visitante, VisitanteBanco, "id");
		return repositorio.saveAndFlush(VisitanteBanco);
	}

	public void deletar(Long id) {
		
		repositorio.deleteById(id);
	}

	public Optional<Visitante> buscaPorId(Long id) {
		return (Optional<Visitante>) repositorio.findById(id);
	}

	public List<Visitante> listarTodos() {
		return repositorio.findAll();
	}
	
	 public Visitante buscarPorId(Long id) { 
		 return repositorio.findById(id).get(); 
	}
	 
	 public Visitante buscarPorCpf(String cpf) { 
		 return repositorio.findByCpf(cpf); 
	}

	public List<Visitante> filtrar(Visitante visitante) {
		return repositorio.findAll(VisitanteEspecification.filtro(visitante));
	}
}
