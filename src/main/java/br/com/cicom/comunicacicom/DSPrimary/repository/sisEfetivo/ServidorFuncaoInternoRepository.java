package br.com.cicom.comunicacicom.DSPrimary.repository.sisEfetivo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.rh.Servidor;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.Mesa;
import br.com.cicom.comunicacicom.DSPrimary.model.sisEfetivo.ServidorFuncaoInterno;

@Repository
public interface ServidorFuncaoInternoRepository extends JpaRepository<ServidorFuncaoInterno, Long> {
	List<ServidorFuncaoInterno> findByMesa(Mesa mesa);

	List<ServidorFuncaoInterno> findAllByFimPlantaoAfter(LocalDateTime horaComecoMesa);

	List<ServidorFuncaoInterno> findAllByMesaInAndFimPlantaoAfterAndMesaAtivo(List<Mesa> mesas, LocalDateTime horaComecoMesa, boolean ativo);

	List<ServidorFuncaoInterno> findAllByAtivo(Boolean ativo); 
	
	List<ServidorFuncaoInterno> findAllByServidorIn( List<Servidor> servidores);
	
}
