package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Caracteristica;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.CaracteristicaRepository;

@Service
public class CaracteristicaService {

	@Autowired
	private CaracteristicaRepository repositorio;

	public Caracteristica cadastrar(Caracteristica caracteristica) {
		return repositorio.saveAndFlush(caracteristica);
	}

	public Caracteristica alterar(Long id, Caracteristica caracteristica) {
		Caracteristica caracteristicaBanco = repositorio.findById(id).get();

		if (caracteristicaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(caracteristica, caracteristicaBanco, "id");

		return repositorio.saveAndFlush(caracteristicaBanco);
	}

	public void deletar(Long id) {
		repositorio.deleteById(id);
	}

	public Optional<Caracteristica> buscaPorId(Long id) {
		return (Optional<Caracteristica>) repositorio.findById(id);
	}

	public List<Caracteristica> listarTodos() {
		return repositorio.findAll();
	}

	public String pegarNome(Long id) {
		return repositorio.findById(id).get().getNome();
	}

	public Caracteristica desativar(Long id, boolean situacao) {
		Caracteristica gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}
	
	public DataTablesOutput<Caracteristica> buscarDadosDataTable(DataTablesInput input) {
		return repositorio.findAll(input);
	}
	
}
