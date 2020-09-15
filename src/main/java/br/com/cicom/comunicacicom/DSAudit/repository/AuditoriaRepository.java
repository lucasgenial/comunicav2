package br.com.cicom.comunicacicom.DSAudit.repository;

import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cicom.comunicacicom.DSAudit.model.Auditoria;

public interface AuditoriaRepository  extends DataTablesRepository<Auditoria, Long>, JpaRepository<Auditoria, Long>{
	List<Auditoria> findAll();
	//Page<Auditoria> findByHistoricoLikeOrUsuarioLoginLikeOrderByIdDesc(String historico, String matriculaUsuario, Pageable page);
}
