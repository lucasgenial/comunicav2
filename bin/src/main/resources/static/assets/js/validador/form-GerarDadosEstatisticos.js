/**
 * Arquivo de configuração da VALIDAÇÃO via Javascript do formulário que GERA OS DADOS ESTATISTICOS
 * ATENÇÃO ESTE JS GERENCIA A PÁGINA DE CADASTRO DE NOVOS REGISTROS DE OCORRÊNCIAS
 * 
 */

document.addEventListener('DOMContentLoaded', function(e) {
	var dados = [
		  {	"id":1,	"valor": "JANEIRO" },
		  {	"id":2,	"valor": "FEVEREIRO" },
		  {	"id":3,	"valor": "MARÇO"},
		  {	"id":4,	"valor": "ABRIL"},
		  {	"id":5,	"valor": "MAIO"},
		  {	"id":6,	"valor": "JUNHO"},
		  {	"id":7,	"valor": "JULHO"},
		  {	"id":8,	"valor": "AGOSTO"},
		  {	"id":9,	"valor": "SETEMBRO"},
		  {	"id":10, "valor": "OUTUBRO"},
		  {	"id":11, "valor": "NOVEMBRO"},
		  {	"id":12, "valor": "DEZEMBRO"}
		];
	
	var ano = 0;
	var hoje = new Date();
	var anoAtual = hoje.getFullYear();
	var mesAtual = hoje.getMonth();
	
	$('#anoEscolhido').change(function() {
		ano = this.value;
		
		if(ano == 0){
			//Remove todas as opções
			$("#mesInicial option[value!='0']").remove();
			$("#mesFinal option[value!='0']").remove();
			
			//Desabilita o campo Mes inicial
			$('#mesInicial').attr("disabled", true);
			$('#mesFinal').attr("disabled", true);
		} else{
			//Remove todas as opções
			$("#mesInicial option[value!='0']").remove();
			$("#mesFinal option[value!='0']").remove();
			
			//Habilita o campo Mes inicial
			$('#mesInicial').removeAttr("disabled");
			$('#mesFinal').attr("disabled", true);
			
			if(ano == anoAtual){
				if(mesAtual < 12){					
					for(var i=0; i < mesAtual; i++){
						var option = new Option(dados[i].valor, dados[i].id);
						var select = document.getElementById("mesInicial");
						select.add(option);
				  	}
				} else{
					for(var i=0; i < dados.length; i++){
						var option = new Option(dados[i].valor, dados[i].id);
						var select = document.getElementById("mesInicial");
						select.add(option);
				  	}
				}
			}else{				
				for(var i=0; i < dados.length; i++){
					var option = new Option(dados[i].valor, dados[i].id);
					var select = document.getElementById("mesInicial");
					select.add(option);
			  	}
			}
		}
	});
	
	$('#mesInicial').change(function() {
		var mesInicial = this.value;
		
		if(mesInicial == 0){
			//Remove todas as opções
			$("#mesFinal option[value!='0']").remove();
			
			//Desabilita o campo Mes inicial
			$('#mesFinal').attr("disabled", true);
		} else{
			//Remove todas as opções
			$("#mesFinal option[value!='0']").remove();
			
			//Habilita o campo Mes inicial
			$('#mesFinal').removeAttr("disabled");
			
			if(ano == anoAtual){
				if(mesAtual < 12){
					for(var i = mesInicial; i < mesAtual; i++){
						var option = new Option(dados[i].valor, dados[i].id);
						var select = document.getElementById("mesFinal");
						select.add(option);
					}
				}else{
					for(var i = mesInicial; i < dados.length; i++){
						var option = new Option(dados[i].valor, dados[i].id);
						var select = document.getElementById("mesFinal");
						select.add(option);
					}
				}
			}else{
				for(var i = mesInicial; i < dados.length; i++){
					var option = new Option(dados[i].valor, dados[i].id);
					var select = document.getElementById("mesFinal");
					select.add(option);
				}
			}
		}
	});
	
	
	//Trabalha com as categorias
	$('#categoria').on("change", function(e){
		document.getElementById("tipificacaoDisponivel").options.length = 1;
		
		var id = this.value
		
		if(id == 0){
			//Remove todas as opções
			$("#tipificacaoDisponivel option[value!='0']").remove();
			
			$('#tipificacaoDisponivel').attr("disabled", true);	
		}else{
			//Remove todas as opções
			$("#tipificacaoDisponivel option[value!='0']").remove();
			
			//Habilita o campo Mes inicial
			$('#tipificacaoDisponivel').removeAttr("disabled");	
						
			$.ajax({
			      type : "GET",
			      url : "admin/cadastros/categorias/tipificacoes/" + id,
			      success: function(data) {
			    	  for(var i=0;i<data.length;i++){
			    		  var option = new Option(data[i].nome, data[i].id);
			    		  var select = document.getElementById("tipificacaoDisponivel");
			    		  select.add(option);
			    	  }
			      },
			      fail: function(){
			          alert('Falha ao Buscar as Tipificações');
			      }
		    });
		}
    });
	
	//Trabalha com as Cidades
	$('#estabelecimentos').on("change", function(e){
		document.getElementById("tipificacaoDisponivel").options.length = 1;
		
		var id = this.value
		
		if(id == 0){
			//Remove todas as opções
			$("#cidadeDisponivel option[value!='0']").remove();
			
			$('#cidadeDisponivel').attr("disabled", true);	
		}else{
			//Remove todas as opções
			$("#cidadeDisponivel option[value!='0']").remove();
			
			//Habilita o campo Mes inicial
			$('#cidadeDisponivel').removeAttr("disabled");	
						
			$.ajax({
			      type : "GET",
			      url : "admin/cadastros/estabelecimento/cidades/" + id,
			      success: function(data) {
			    	  for(var i=0;i<data.length;i++){
			    		  var option = new Option(data[i].nome, data[i].id);
			    		  var select = document.getElementById("cidadeDisponivel");
			    		  select.add(option);
			    	  }
			      },
			      fail: function(){
			          alert('Falha ao Buscar as Cidades do Estabelecimento');
			      }
		    });
		}
    });
	
	
	
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});