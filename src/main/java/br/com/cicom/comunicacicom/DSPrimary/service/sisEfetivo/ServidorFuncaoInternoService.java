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
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.ServidorFuncaoInternoRepository;

@Service
public class ServidorFuncaoInternoService {

	@Autowired
    private ServidorFuncaoInternoRepository repositorio;

    public ServidorFuncaoInterno cadastrar(ServidorFuncaoInterno servidorFuncaoInterno) {
        this.atualizaStatusAutomatico();
        return (ServidorFuncaoInterno)this.repositorio.saveAndFlush(servidorFuncaoInterno);
    }

    public List<ServidorFuncaoInterno> buscarPorMesa(Mesa mesa) {
        this.atualizaStatusAutomatico();
        return this.repositorio.findByMesa(mesa);
    }

    public ServidorFuncaoInterno alterar(Long id, ServidorFuncaoInterno servidorFuncaoInterno) {
        ServidorFuncaoInterno servidorFuncaoInternoBanco = (ServidorFuncaoInterno)this.repositorio.getOne(id);
        if (servidorFuncaoInternoBanco == null) {
            throw new EmptyResultDataAccessException(1);
        }
        BeanUtils.copyProperties((Object)servidorFuncaoInterno, (Object)servidorFuncaoInternoBanco, (String[])new String[]{"id"});
        return (ServidorFuncaoInterno)this.repositorio.saveAndFlush(servidorFuncaoInternoBanco);
    }

    public void deletar(Long id) {
        this.repositorio.deleteById(id);
    }

    public Optional<ServidorFuncaoInterno> buscaPorId(Long id) {
        this.atualizaStatusAutomatico();
        return this.repositorio.findById(id);
    }

    public List<ServidorFuncaoInterno> listarTodos() {
        this.atualizaStatusAutomatico();
        return this.repositorio.findAll();
    }

    public ServidorFuncaoInterno desativar(Long id, boolean situacao) {
        ServidorFuncaoInterno gp = (ServidorFuncaoInterno)this.repositorio.findById(id).get();
        if (gp != null) {
            gp.setAtivo(situacao);
        }
        return (ServidorFuncaoInterno)this.repositorio.save(gp);
    }

    public boolean isExiste(Long id) {
        return this.repositorio.findById(id) != null;
    }

    public List<ServidorFuncaoInterno> buscarServidoresAtivos(List<Mesa> mesas, LocalDateTime dataInicioMesa) {
        List<ServidorFuncaoInterno> lista = this.repositorio.findAllByMesaInAndFimPlantaoAfterAndMesaAtivo(mesas, dataInicioMesa, false);
        ArrayList<ServidorFuncaoInterno> lista2 = new ArrayList<ServidorFuncaoInterno>();
        for (ServidorFuncaoInterno servidor : lista) {
            if (servidor.getMesa().isAtivo()){
            	lista2.add(servidor);
            }
        }
        lista.removeAll(lista2);
        return lista;
    }

    public boolean verificarServidoresAtivos(List<Mesa> mesas, LocalDateTime dataInicioMesa) {
        this.atualizaStatusAutomatico();
        List<ServidorFuncaoInterno> listaDeServidores = this.repositorio.findAllByMesaInAndFimPlantaoAfterAndMesaAtivo(mesas, dataInicioMesa, false);
        return !listaDeServidores.isEmpty();
    }

    public List<ServidorFuncaoInterno> buscarPorListaDeId(List<Long> lista) {
        this.atualizaStatusAutomatico();
        ArrayList<ServidorFuncaoInterno> listaDeServidores = new ArrayList<ServidorFuncaoInterno>();
        for (int i = 0; i < lista.size(); ++i) {
            listaDeServidores.add((ServidorFuncaoInterno)this.repositorio.findById(lista.get(i)).get());
        }
        return listaDeServidores;
    }

    public void atualizaStatusAutomatico() {
        List<ServidorFuncaoInterno> servidorFuncaoInternos = this.repositorio.findAllByAtivo(Boolean.valueOf(true));
        for (ServidorFuncaoInterno servidorFuncaoInterno : servidorFuncaoInternos) {
            if (!servidorFuncaoInterno.isAtivo() || !servidorFuncaoInterno.getFimPlantao().isBefore(LocalDateTime.now())) continue;
            this.desativar(servidorFuncaoInterno.getId(), false);
        }
    }

    public List<ServidorFuncaoInterno> buscaPorServidores(List<Servidor> servidores) {
        if (servidores == null) {
            return null;
        }
        return this.repositorio.findAllByServidorIn(servidores);
    }

}