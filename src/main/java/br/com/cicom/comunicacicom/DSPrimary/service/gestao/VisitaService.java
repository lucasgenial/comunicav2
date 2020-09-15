package br.com.cicom.comunicacicom.DSPrimary.service.gestao;

import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visita;
import br.com.cicom.comunicacicom.DSPrimary.model.gestao.Visitante;
import br.com.cicom.comunicacicom.DSPrimary.model.sisGeral.Estabelecimento;
import br.com.cicom.comunicacicom.DSPrimary.repository.gestao.VisitaRepository;
import br.com.cicom.comunicacicom.DSPrimary.specification.VisitaEspecification;

@Service
public class VisitaService {

	@Autowired
	private VisitaRepository repositorio;

	public Visita cadastrar(Visita visita) {
		return repositorio.save(visita);
	}

	public Visita alterar(Long id, Visita visita) {
		
		Visita visitaBanco = repositorio.getOne(id);
		if (visitaBanco == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		// Copia de um objeto para outro!
		BeanUtils.copyProperties(visita, visitaBanco, "id");
		return repositorio.saveAndFlush(visitaBanco);
	}

	public void deletar(Long id) {
		
		repositorio.deleteById(id);
	}

	public Optional<Visita> buscaPorId(Long id) {
		return (Optional<Visita>) repositorio.findById(id);
	}

	public List<Visita> listarTodos() {
		return repositorio.findAll();
	}

	 public Visita buscarPorId(Long id) { 
		 return repositorio.findById(id).get(); 
	}
	 
	/**
	 * IMPLEMENTAÇÕES DA NOVA TEMPLATE
	 */

	public DataTablesOutput<Map<String, Object>> listarTodasVisitas(@Valid DataTablesInput input) {
		
		return repositorio.findAll(input, new Function<Visita, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(Visita visita) {
				Map<String, Object> mapa = new HashMap<>();
			 		mapa.put("empresa", visita.getEmpresa());
					mapa.put("servico",visita.getServico().getNome());
					mapa.put("inicioServico", visita.getInicioServico());
					mapa.put("fimServico",visita.getFimServico());
			 		mapa.put("estabelecimento", visita.getEstabelecimento().getNome());
					mapa.put("historico",visita.getHistorico());
					mapa.put("id",visita.getId());

				 return mapa;
			}
		});
		
	}
	
	
	public DataTablesOutput<Map<String, Object>> listarTodasVisitasPorEstabelecimento(@Valid DataTablesInput input,Estabelecimento estabelecimento) {		
		return repositorio.findAll(input, Specification.where(VisitaEspecification.porEstabelecimento(estabelecimento)),null, new Function<Visita, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(Visita visita) {
				Map<String, Object> mapa = new HashMap<>();
			 		mapa.put("empresa", visita.getEmpresa());
			 		mapa.put("estabelecimento", visita.getEstabelecimento().getNome());
					mapa.put("servico",visita.getServico().getNome());
					mapa.put("inicioServico", visita.getInicioServico());
					mapa.put("fimServico",visita.getFimServico());
					mapa.put("historico",visita.getHistorico());
					mapa.put("id",visita.getId());

				 return mapa;
			}
		});
	}
	
	  
    public List<Visita> filtrar(Visita visita, Estabelecimento estabelecimento) {
    
		return repositorio.findAll(VisitaEspecification.filtro(visita, estabelecimento));
				
    }
    
    public Long quantidadedeVisitasPorVisitante(Visitante visitante) {
		return repositorio.count(VisitaEspecification.quandoContemOVisitante(visitante));
    }
    
    
    public String empresasPorVisitante(Visitante visitante) {
	
    	Set<String> empresas = new HashSet<>();
    	for(Visita visita : repositorio.findAll(VisitaEspecification.quandoContemOVisitante(visitante))) {
			empresas.add(visita.getEmpresa());
		}
    	return empresas.toString().replace("[", "").replace("]","");
    }
    
}
