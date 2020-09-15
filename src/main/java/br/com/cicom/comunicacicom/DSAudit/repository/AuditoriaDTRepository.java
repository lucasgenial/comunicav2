package br.com.cicom.comunicacicom.DSAudit.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSAudit.model.AuditoriaDT;



@Repository
public interface AuditoriaDTRepository extends DataTablesRepository<AuditoriaDT, Long>, JpaRepository<AuditoriaDT, Long>{

}
