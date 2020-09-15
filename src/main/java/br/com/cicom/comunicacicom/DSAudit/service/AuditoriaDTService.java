package br.com.cicom.comunicacicom.DSAudit.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSAudit.model.AuditoriaDT;
import br.com.cicom.comunicacicom.DSAudit.repository.AuditoriaDTRepository;

@Service
public class AuditoriaDTService {
	
	@Autowired
	private AuditoriaDTRepository repositoryAuditoriaDT;

	public DataTablesOutput<AuditoriaDT> listDTAuditoria(@Valid DataTablesInput input) {
		return repositoryAuditoriaDT.findAll(input);
	}

	public List<AuditoriaDT> listAuditoriaDT(){
		return repositoryAuditoriaDT.findAll();
		
	}
}
