package br.com.cicom.comunicacicom.wrapper;

import java.util.List;

import br.com.cicom.comunicacicom.DSPrimary.model.estatistica.Pessoa;

public class EnvolvidosDto {
	private List<Pessoa> pessoas;
	
	public void addPessoa(Pessoa pessoa) {
        this.pessoas.add(pessoa);
    }
}
