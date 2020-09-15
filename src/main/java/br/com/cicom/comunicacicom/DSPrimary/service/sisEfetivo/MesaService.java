package br.com.cicom.comunicacicom.DSPrimary.service.sisEfetivo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo.MesaRepository;
import br.com.cicom.comunicacicom.DSPrimary.specification.MesaEspecification;

@Service
public class MesaService {

  @Autowired
  private MesaRepository repositorio;
  
  public MesaService() {}
  
  public Mesa cadastrar(Mesa mesa)
  {
    return (Mesa)repositorio.saveAndFlush(mesa);
  }
  
  public Mesa alterar(Long id, Mesa mesa)
  {
    Mesa mesaBanco = (Mesa)repositorio.findById(id).get();
    
    if (mesaBanco == null) {
      throw new EmptyResultDataAccessException(1);
    }
    
    BeanUtils.copyProperties(mesa, mesaBanco, new String[] { "id" });
    
    return (Mesa)repositorio.saveAndFlush(mesaBanco);
  }
  
  public void deletar(Long id)
  {
    repositorio.deleteById(id);
  }
  
  public Optional<Mesa> buscaPorId(Long id)
  {
	  return repositorio.findById(id);
  }
  
  public List<Mesa> listarTodos()
  {
    return repositorio.findAll();
  }
  
  public List<Mesa> buscarListaPorNomeEstabelecimento(String nome)
  {  
    return repositorio.findAllByNomeLike(nome + "%");
  }
  
  public int buscarQuantidadeDeMesasPorNomeEstabelecimento(String nome)
  {  
    return repositorio.buscarQuantidadeDeMesasPorNomeEstabelecimento(nome+"%");
  }
  
  public Mesa buscarUltimaAtivaPorNomeEstabelecimento(String nome)
  {
    List<Mesa> mesas = repositorio.findAllByAtivoAndNomeLike(true, nome+"%");
    List<Mesa> mesasRetornar = new ArrayList<>();
    for (Mesa mesa : mesas) {
      if (mesa.getFimPlantao().isAfter(LocalDateTime.now())) {
    	  mesasRetornar.add(mesa);
      }
    }if(mesasRetornar.size()>0) {
    	return mesasRetornar.get(mesasRetornar.size()-1);
    }
    	return null;
  }
  
  public String pegarNome(Long id) {
    return ((Mesa)repositorio.findById(id).get()).getNome();
  }
  
  public Mesa desativar(Long id, boolean situacao) {
    Mesa gp = (Mesa)repositorio.findById(id).get();
    
    if (gp != null) {
      gp.setAtivo(situacao);
    }
    
    return (Mesa)repositorio.save(gp);
  }
  

  public List<Mesa> findAllByMesa(Mesa mesaExampler, LocalDate dataInicio, LocalDate dataFim)
  {
    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
      .withIgnorePaths(new String[] {"ativo" });
    
    Example<Mesa> example = Example.of(mesaExampler, exampleMatcher);
    
    List<Mesa> listaDeMesas = repositorio.findAll(example);
    
    if (dataFim == null) {
      dataFim = LocalDate.now();
    }
    
    if (dataInicio != null) {
      List<Mesa> listaPorData = new ArrayList<>();
      Iterator<Mesa> mesaAsIterator = listaDeMesas.iterator();
      
      while (mesaAsIterator.hasNext()) {
        Mesa mesa = (Mesa)mesaAsIterator.next();
        
        if (((mesa.getInicioPlantao().toLocalDate().isAfter(dataInicio)) || 
          (mesa.getInicioPlantao().toLocalDate().isEqual(dataInicio))) && (
          (mesa.getInicioPlantao().toLocalDate().isBefore(dataFim)) || 
          (mesa.getInicioPlantao().toLocalDate().isEqual(dataFim)))) {
          listaPorData.add(mesa);
        }
      }
      listaDeMesas = listaPorData;
    } else {
      List<Mesa> listaPorData = new ArrayList<>();
      Iterator<Mesa> mesaAsIterator = listaDeMesas.iterator();
      
      while (mesaAsIterator.hasNext()) {
        Mesa mesa = (Mesa)mesaAsIterator.next();
        
        if ((mesa.getInicioPlantao().toLocalDate().isBefore(dataFim)) || 
          (mesa.getInicioPlantao().toLocalDate().isEqual(dataFim))) {
          listaPorData.add(mesa);
        }
      }
      listaDeMesas = listaPorData;
    }
    return listaDeMesas;
  }
  
  
  public List<Mesa> filtrar(Mesa mesa, List<Estabelecimento> estabelecimentos, List<ServidorFuncaoInterno> servidores)
  {
    if (servidores != null)
    {
      return repositorio.findByEstabelecimentoInAndInicioPlantaoGreaterThanEqualAndFimPlantaoLessThanEqualAndNomeLikeAndServidoresIn(estabelecimentos, mesa.getInicioPlantao(), mesa.getFimPlantao(), "%" + mesa.getNome() + "%", servidores);
    }    

    return repositorio.findByEstabelecimentoInAndInicioPlantaoGreaterThanEqualAndFimPlantaoLessThanEqualAndNomeLike(estabelecimentos, mesa.getInicioPlantao(), mesa.getFimPlantao(), "%" + mesa.getNome() + "%");
  }

  
  public Page<Mesa> buscaPaginada(String pesquisa, Pageable pageable)
  {
    return null;
  }
  
  public DataTablesOutput<Mesa> buscardadosDataTable(DataTablesInput input)
  {
	  return repositorio.findAll(input);

  }
  
  public DataTablesOutput<Mesa> buscardadosDataTable(DataTablesInput input, Estabelecimento estabelecimento)
  {
	  return repositorio.findAll(input, Specification
				.where(MesaEspecification.porEstabelecimento(estabelecimento)));
	
  }
  
  public Mesa buscarMesaAtivaComOServidorNela(String nomeEstabelecimento, Long idServidor)
  {
	  List<Mesa> mesas = repositorio.findAllByNomeLikeAndAtivoAndServidoresServidorId(nomeEstabelecimento+"%",true, idServidor); 
	  if(mesas.size() > 0) {
		  return mesas.get(mesas.size()-1);
	  }else {
		  return null;
	  }
	 
  }
}
