package br.com.cicom.comunicacicom.DSAudit.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;
import br.com.cicom.comunicacicom.DSAudit.repository.AuditoriaRepository;

@Service
public class AuditoriaService {

	@Autowired
	private AuditoriaRepository repositorio;

	public Auditoria cadastrar(Auditoria auditoria) {
		return repositorio.saveAndFlush(auditoria);
	}

	public Auditoria alterar(Long i, Auditoria t) {
		return null;
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Auditoria> buscaPorId(Long id) {
		return (Optional<Auditoria>) repositorio.findById(id);
	}

	public List<Auditoria> listarTodos() {
		return repositorio.findAll();
	}

//	@Override
//	public Page<Auditoria> buscaPaginada(String pesquisa, Pageable pageable) {
//
//		return repositorio.findByHistoricoLikeOrUsuarioLoginLikeOrderByIdDesc("%" + pesquisa + "%",
//				"%" + pesquisa + "%", pageable);
//	}
	
	public DataTablesOutput<Auditoria> listarAuditoria(@Valid DataTablesInput input){
		return repositorio.findAll(input);
	}

}
