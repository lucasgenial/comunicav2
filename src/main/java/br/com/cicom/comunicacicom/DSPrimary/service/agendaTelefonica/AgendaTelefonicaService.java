package br.com.cicom.comunicacicom.DSPrimary.service.agendaTelefonica;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.agendaTelefonica.AgendaTelefonica;
import br.com.cicom.comunicacicom.DSPrimary.repository.agendaTelefonica.AgendaTelefonicaRepository;

@Service
public class AgendaTelefonicaService {
	
	@Autowired
	private AgendaTelefonicaRepository repositorio;

	public AgendaTelefonica cadastrar(AgendaTelefonica tel) {		
		return repositorio.saveAndFlush(tel);
	}

	public AgendaTelefonica alterar(Long id, AgendaTelefonica agenda) {
		AgendaTelefonica agendaBanco = repositorio.getOne(id);
		
		if(agendaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(agenda, agendaBanco, "id");
		
		return repositorio.saveAndFlush(agendaBanco);
	}

	public void deletar(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Optional<AgendaTelefonica> buscaPorId(Long id) {
		
		return (Optional<AgendaTelefonica>) repositorio.findById(id);
	}

	public List<AgendaTelefonica> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}


	public DataTablesOutput<AgendaTelefonica> listarTodosTelefones(@Valid DataTablesInput input) {
		DataTablesOutput<AgendaTelefonica> dados = repositorio.findAll(input);
		
		return dados;
	}

}
