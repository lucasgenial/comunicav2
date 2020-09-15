document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formEfetivoExterno'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {		
            
			'unidade': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

			'tipoServico': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

			'unidades': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

			'cidade': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

			'localidade': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

			'bairro': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
                	}
                }
            },

            'comecoPlantao': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
            
            'terminoPlantao': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
            
            'intervalo1': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
            	
            'intervalo2': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
            	
            	            
		},
		
		plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap(),
           // defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            //submitButton: new FormValidation.plugins.SubmitButton(),
            /*icon: new FormValidation.plugins.Icon({
                valid: 'fa fa-check',
                invalid: 'fa fa-times',
                validating: 'fa fa-refresh',
            }),*/
        },
	});
	
		
	//Se algum campo estiver oculto remove
	//$('[required]', event.field).removeAttr('required');
	$('#comecoPlantao').datetimepicker({
        language:  'pt-BR',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 0,
        minuteStep: 1,
        format: 'dd/mm/yyyy HH:ii:ss'
	}).on('change', function(e){
	    fv.revalidateField('comecoPlantao');
	});
	
	$('#terminoPlantao').datetimepicker({
        language:  'pt-BR',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 0,
        minuteStep: 1,
        format: 'dd/mm/yyyy HH:ii:ss'
	}).on('change', function(e){
	    fv.revalidateField('terminoPlantao');
	});
	
	
});

	
$('#cidade').on("change", function(e){
	document.getElementById("localidade").options.length = 1;
	if(this.value !== ""){
    $.ajax({
	      type : "GET",
	      url : "admin/cadastros/cidades/localidades/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("localidade");
	    		  select.add(option);
	    	  }	
	      }
    });
	}
});

$('#localidade').on("change", function(e){
	document.getElementById("bairro").options.length = 1;
    
	if(this.value !== ""){
	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/localidades/bairros/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("bairro");
	    		  select.add(option);
	    	  }	
	      }
    });
	}
});






$('#inserirServidorExterno').on("click", function(){
	var id = this.name;
	var lista = document.getElementsByTagName("input");
	var listaDeServidoresSelecionados = new Array();
		
	for (var i=0;i < lista.length;i++) {
		if (lista[i].type=="checkbox" && lista[i].name=="listaDeServidores") {
			
			if(lista[i].checked==true){
				listaDeServidoresSelecionados[listaDeServidoresSelecionados.length] = lista[i].value;
			}
		}
	}
	window.location.href= "/admin/cadastros/gestaoefetivo/cadastra-efetivoExterno/"+id+"/"+listaDeServidoresSelecionados;
});


	/**
	 * 
	 * INFORMAÇÃOES ADICIONAR SERVIDOR EXTERNO
	 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
	 * 
	 * **/
	function abrirModal(){
		var titulo = "Selecionar servidores";
		$('#modal-adicionarServidorExterno').modal().find('.modal-title').text(titulo)
		$('#btn-salvar-adicionarServidorExterno').text("Adicionar servidores");
		$("#modal-adicionarServidorExterno").modal("show");
	}
	
	jQuery(function($){
		$("#matriculaServidor").mask("99.999.999-9");
		$("#placaPolicial").mask("SSS - 9999");
		$("#terminoPlantao").mask("99/99/9999 99:99:99");
		$("#comecoPlantao").mask("99/99/9999 99:99:99");
		$("#telefone").mask("(99) 99999-9999");
		
		$(".efetivoGuarnicaoInicioPlantao").mask("99/99/9999 99:99:99");
		$(".efetivoGuarnicaoFimPlantao").mask("99/99/9999 99:99:99");
	});

	$("#placaPolicial").on("keypress", function(event){
		    this.value = this.value.toUpperCase();
	});
	
	
	$(".buscarPorMatricula").on("click", function(event){
		var matricula = document.getElementById("matriculaServidor").value;
		document.getElementById("nomeServidor").value = "";

		if(matricula.length == 12){
   
			$.ajax({
		    type : "GET",
		    url : "/admin/cadastro/gestaoefetivo/pesquisa/"+matricula,
		    
		    success: function(data) {   
		    	document.getElementById("nomeServidor").value = data.nome; 
		    	document.getElementById('inserirServidoresExternos').disabled=false;	
		    }
			
			});
    	}
		});
	
	$(".ativaBotaoDePesquisar").on("keydown", function(event){
		var matricula = document.getElementById("matriculaServidor").value;
		document.getElementById("nomeServidor").value = "";
		if(matricula.length >= 11){
			document.getElementById('pesquisarMatricula').disabled=false;
    	}else{
    		document.getElementById('pesquisarMatricula').disabled=true;
    	}
	});
	