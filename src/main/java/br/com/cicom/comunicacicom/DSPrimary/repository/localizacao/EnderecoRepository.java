package br.com.cicom.comunicacicom.DSPrimary.repository.localizacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cicom.comunicacicom.DSPrimary.model.localizacao.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}