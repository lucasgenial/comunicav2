package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncao;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.ServidorFuncaoRepository;

@Service
public class ServidorFuncaoService {

	@Autowired
	private ServidorFuncaoRepository repositorio;

	public ServidorFuncao cadastrar(ServidorFuncao servidorFuncao) {
		this.atualizaStatusAutomatico();
		return repositorio.saveAndFlush(servidorFuncao);
	}

	public ServidorFuncao alterar(Long id, ServidorFuncao servidorFuncao) {
		this.atualizaStatusAutomatico();
		ServidorFuncao servidorFuncaoBanco = repositorio.findById(id).get();

		if (servidorFuncaoBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(servidorFuncao, servidorFuncaoBanco, "id");

		return repositorio.saveAndFlush(servidorFuncaoBanco);
	}

	public void deletar(Long id) {
		this.atualizaStatusAutomatico();
		repositorio.deleteById(id);
	}

	public Optional<ServidorFuncao> buscaPorId(Long id) {
		this.atualizaStatusAutomatico();
		return (Optional<ServidorFuncao>) repositorio.findById(id);
	}

	public List<ServidorFuncao> listarTodos() {
		this.atualizaStatusAutomatico();
		return repositorio.findAll();
	}

	public List<ServidorFuncao> transformarServidorEmServidorFuncao(List<Servidor> servidores) {

		this.atualizaStatusAutomatico();

		List<ServidorFuncao> listaDeServidorFuncao = new ArrayList<ServidorFuncao>();
		for (int i = 0; i < servidores.size(); i++) {

			listaDeServidorFuncao.add(new ServidorFuncao());

		}
		return listaDeServidorFuncao;
	}

	public ServidorFuncao desativar(Long id, boolean situacao) {
		ServidorFuncao gp = repositorio.findById(id).get();

		if (gp != null) {
			gp.setAtivo(situacao);
		}

		return repositorio.save(gp);
	}

	public void atualizaStatusAutomatico() {
		List<ServidorFuncao> servidoresFuncao = repositorio.findAllByAtivo(true);
		for (ServidorFuncao servidorFuncao : servidoresFuncao) {
			if (servidorFuncao.isAtivo() && servidorFuncao.getFimPlantao().isBefore(LocalDateTime.now())) {
				this.desativar(servidorFuncao.getId(), false);
			}
		}
	}
}
