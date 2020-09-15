package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorExterno;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.ServidorExternoRepository;

@Service
public class ServidorExternoService {

	@Autowired
	private ServidorExternoRepository repositorio;

	public ServidorExterno cadastrar(ServidorExterno servidorExterno) {
		return (ServidorExterno) this.repositorio.saveAndFlush(servidorExterno);
	}

	public ServidorExterno alterar(Long id, ServidorExterno servidorExterno) {
		ServidorExterno servidorExternoBanco = (ServidorExterno) this.repositorio.getOne(id);
		if (servidorExternoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(servidorExterno, servidorExternoBanco, new String[] { "id" });

		return (ServidorExterno) this.repositorio.saveAndFlush(servidorExternoBanco);
	}

	public void deletar(Long id) {
		this.repositorio.deleteById(id);
	}

	public Optional<ServidorExterno> buscaPorId(Long id) {
		return this.repositorio.findById(id);
	}

	public List<ServidorExterno> listarTodos() {
		return this.repositorio.findAll();
	}

	public List<ServidorExterno> buscarPorListaDeId(List<Long> lista) {
		List<ServidorExterno> listaDeServidores = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			listaDeServidores.add((ServidorExterno) this.repositorio.findById((Long) lista.get(i)).get());
		}
		return listaDeServidores;
	}

	public ServidorExterno buscarPorMatricula(String matricula, boolean ativo) {
		return this.repositorio.findByMatriculaAndAtivo(matricula, ativo);
	}

	public DataTablesOutput<ServidorExterno> listarServidoresExternos(@Valid DataTablesInput input, Usuario user){

		return repositorio.findAll(input);
	}
}
