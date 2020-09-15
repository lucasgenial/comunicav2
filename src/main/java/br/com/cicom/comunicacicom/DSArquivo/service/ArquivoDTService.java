package br.com.cicom.comunicacicom.DSArquivo.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import br.com.cicom.comunicacicom.DSArquivo.model.Arquivo;
import br.com.cicom.comunicacicom.DSArquivo.model.ArquivoDT;
import br.com.cicom.comunicacicom.DSArquivo.repository.ArquivoDTRepository;
import br.com.cicom.comunicacicom.DSArquivo.specification.ArquivoEspecification;

@Service
public class ArquivoDTService {
	
	@Autowired
	private ArquivoDTRepository repository;
	
	public DataTablesOutput<ArquivoDT> listDTArquivo(@Valid DataTablesInput input){
		return repository.findAll(input);
	}

	public List<ArquivoDT> listArquivoDT(){
		return repository.findAll();
	}

	public List<ArquivoDT> buscarPorServidor(Long id){
	return repository.findByServidor(id);
	}
	
	public DataTablesOutput<ArquivoDT> listArquivosDTServidor(@Valid DataTablesInput input, Long idServidor) {
		return repository.findAll(input, ArquivoEspecification.arquivoDTporServidor(idServidor));
	}
}
