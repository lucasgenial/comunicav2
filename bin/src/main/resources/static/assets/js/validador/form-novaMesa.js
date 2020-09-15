

/**
 * 
 * INFORMAÇÃOES ADICIONAR SERVIDOR NA MESA
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
 * 
 * **/
function abrirModal(){
	var titulo = "Selecionar servidores";
	$('#modal-adicionarServidorTabela').modal().find('.modal-title').text(titulo);
	$('#btn-salvar-adicionarServidorTabela').text("Adicionar servidores");
	$("#modal-adicionarServidorTabela").modal("show");
};

jQuery(function($){
	$(".servidorPausa1").mask("99:99:99");	
	$(".servidorPausa2").mask("99:99:99");
	$(".servidorFimPlantao").mask("99/99/9999 99:99:99");
});


document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formMesaNova'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {		
            
			'inicioPlantao': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message:'Data Inválida',
                    }
                }
            },
           
            'fimPlantao': {
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
           
            'lista': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
  			 
                }
            },

            'servidores.[*]inicioPlantao' : {
            	
         	   selector: '[data-stripe="number"]',
         	   validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                },
         },
         
         'servidores.[*]fimPlantao' : {
         	
       	   selector: '[data-stripe="number"]',
       	   validators: {
              	notEmpty : {
  					message : 'Campo Obrigatório',
  				},
                  date: {
                      format: 'DD/MM/YYYY HH:MM:SS',
                      message: 'Data Inválida',
                  }
              },
         },
            /* 'listaDeServidoresNaMesa': {
                     validators: {
                      	notEmpty : {
          					message : 'Campo Obrigatório',
          				},
                          date: {
                              format: 'DD/MM/YYYY HH:MM:SS',
                              message: 'Data Inválida',
                          }
                      }
                  },*/
              	            
		},
				
		plugins: {
          trigger: new FormValidation.plugins.Trigger(),
           bootstrap: new FormValidation.plugins.Bootstrap(),
          defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
           //submitButton: new FormValidation.plugins.SubmitButton(),
            //icon: new FormValidation.plugins.Icon({
               //valid: 'fa fa-check',
               //invalid: 'fa fa-times',
                //validating: 'fa fa-refresh',
            //}),
        },
	});

	
	jQuery(function($){
	
	$("#inicioPlantao").mask("99/99/9999 99:99:99");
	}).on('keypress', function(e) {
        fv.revalidateField('inicioPlantao');
    });
	
	jQuery(function($){
	$(".servidorInicioPlantao").mask("99/99/9999 99:99:99");
	}).on('keypress', function(e) {
        fv.revalidateField('servidores[*].inicioPlantao');
    });
	jQuery(function($){
		$(".servidorFimPlantao").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('servidores[*].fimPlantao');
	    });
	
	jQuery(function($){
		
		$(".fimPlantaoMesa").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('fimPlantao');
	    });

	$('.dataInicio').datetimepicker({
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
	    fv.revalidateField('inicioPlantao');
	});
	
	$('.dataFim').datetimepicker({
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
	}).on('change', function(e) {
	    fv.revalidateField('fimPlantao');
	});


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

$("#inicioPlantao").on("change", function(event){
	var dataInicio = document.getElementById("inicioPlantao").value;
		if(dataInicio.length == 19){
	
		$.ajax({
	    type : "GET",
	    url : "/admin/cadastro/gestaoefetivo/pesquisaEfetivosAtivos/"+dataInicio.replace(/\//g,'-'),
	    
	    success: function(data) {   
	    	if(data == true && !document.getElementById('atualizar-efetivo')){
	    		 $("#botaoDeAtualizacao").append("<button type='submit' class='btn btn-danger offset-3 col-6' name='atualizar-efetivo'  id='atualizar-efetivo'>click para adicionar servidores remanescentes da última mesa</button>");
	    		 $("#adicionar-efetivo").remove();
	    		 $("#salvar-efetivo").remove();
	    		 $("#cancelar-efetivo").remove();
	    		 //window.location.href= "/admin/cadastros/gestaoefetivo/mesaComEfetivosAtivos/"+dataInicio.replace(/\//g,'-');
	    	}
	    	
	    }
		
		});
	   }
});

 
$('.clicador').on("click", function(e){
		
	if(!$("#"+$(this).attr('data-identificador')).is(':checked')){
		$("#"+$(this).attr('data-identificador')).prop('checked', true);
		$('.'+$(this).attr('data-identificador')).removeClass('table-default');
		$('.'+$(this).attr('data-identificador')).addClass('table-info');
	
	}else{
		$("#"+$(this).attr('data-identificador')).prop('checked', false);

		$('.'+$(this).attr('data-identificador')).removeClass('table-info');
		$('.'+$(this).attr('data-identificador')).addClass('table-default');	
	}
	
	var servidoresSelecionados = new Array();
	
	$("input[type=checkbox][name='listaDeServidores']:checked").each(function(){
		servidoresSelecionados.push($(this).val());
	});
		
	if(servidoresSelecionados.length > 0){
		document.getElementById('inserirServidorNaMesa').disabled=false;
	}else{
		document.getElementById('inserirServidorNaMesa').disabled=true;
	}
});

$('#inserirServidorNaMesa').on("click", function(){
	
	var servidoresSelecionados = new Array();
	
	$("input[type=checkbox][name='listaDeServidores']:checked").each(function(){
		servidoresSelecionados.push($(this).val());
	});

	window.location.href= "/admin/cadastros/gestaoefetivo/adicionaServidorNaMesaNova/"+servidoresSelecionados;
});

$('#instituicao').on("change", function(e){
	document.getElementById("unidade").options.length = 1;
    $.ajax({
	      type : "GET",
	      url : "admin/cadastros/instituicoes/unidades/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("unidade");
	    		  select.add(option);
	    	  }	
	      }
    });
});




