/**
 * 
 * INFORMAÇÃOES SOBRE PESQUISA DE OCORRENCIAS
 * Aqui encontra-se as funçoes de pesquisar ocorrencias
 * 
 * **/

//Verifica se o usuário clicou em outra field do formulário de pesquisa de ocorrência
// e habilita os campos se o num do SIC não foi informado, caso contrário exibe um erro
$('.liberar').on('click', function(event){
	value = document.getElementById('sic').value;	
	if(value == ""){
		// Habilitar todos os campos
		$('#tipificacao').removeAttr("readonly");
		$('#dataInicio').removeAttr("readonly");
		$('#dataFim').removeAttr("readonly");
		$('#cidade').removeAttr("readonly");
		$('#localidade').removeAttr("readonly");
		$('#bairro').removeAttr("readonly");
		$('#sic').attr("readonly", true);
		
		$('body').on('click', function(event){			
			var listaIn = document.getElementsByTagName("input");
			var listaSe = document.getElementsByTagName("select");
			document.getElementById('sic').disabled=false;
			for (var i=0;i <= listaIn.length-1; i++) {
						if (listaIn[i].id !== "sic" && listaIn[i].value !== "") {
							document.getElementById('sic').disabled=true;
				}
			}
				
		   for(var i=0; i <= listaSe.length-1;i++){
			   if (listaSe[i].name !== "tabela-resultadoOcorrencia_length" && listaSe[i].value !== "") {
						document.getElementById('sic').disabled=true;
				}
			}

		});

	}else{
		alert("Não é possivel realizar pesquisas de ocorrências com essa configuração!");
		$('#dataInicio').val("");
		$('#dataFim').val("");
	}
});

/*$('#sic').on('keyup', function(event){
	event.preventDefault();
	
	var valor = this.value;
				
	if(valor == ""){
		
		document.getElementById('tipificacao').disabled=false;
		document.getElementById('dataInicio').disabled=false;
		document.getElementById('dataFim').disabled=false;
		document.getElementById('cidade').disabled=false;
		document.getElementById('localidade').disabled=false;
		document.getElementById('bairro').disabled=false;
	
	}else{
		
		document.getElementById('tipificacao').disabled=true;
		document.getElementById('dataInicio').disabled=true;
		document.getElementById('dataFim').disabled=true;
		document.getElementById('cidade').disabled=true;
		document.getElementById('localidade').disabled=true;
		document.getElementById('bairro').disabled=true;
	}

});*/

document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formPesquisaOcorrencia'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {
			
			'dataInicio': {
                validators: {
                    date: {
                        format: 'DD/MM/YYYY HH:mm',
                        message: 'Data Inválida',
                    }
                }
            },
			'dataFim': {
                validators: {
                    date: {
                        format: 'DD/MM/YYYY HH:mm',
                        message: 'Data Inválida',
                    }
                }
            },
		},
            
		plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap(),
            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            submitButton: new FormValidation.plugins.SubmitButton(),
            icon: new FormValidation.plugins.Icon({
                valid: 'fa fa-check',
                invalid: 'fa fa-times',
                validating: 'fa fa-refresh',
            }),
        },
	});
	
	jQuery(function($){
		$("#dataInicio").mask("99/99/9999 99:99");
	}).on('changeDate', function(e) {

        fv.revalidateField('dataInicio');
    });

	jQuery(function($){
		$("#dataFim").mask("99/99/9999 99:99");
	}).on('changeDate', function(e) {

        fv.revalidateField('dataFim');
    });
});


// Caso o button limpar seja selecionado volta o formulário ao primeiro instante da página carregada.
$('#limpar').on('click', function(event){
	$('#tipificacao').attr("readonly", false);
	$('#dataInicio').attr("readonly", false);
	$('#dataFim').attr("readonly", false);
	$('#cidade').attr("readonly", false);
	$('#localidade').attr("readonly", false);
	$('#bairro').attr("readonly", false);
	$('#sic').removeAttr("readonly");
});


$('#cidade').on("change", function(e){
	document.getElementById("localidade").options.length = 1;
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
});
	$('#localidade').on("change", function(e){
		document.getElementById("bairro").options.length = 1;
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
	});

	
//	today = new Date();
//	$('#dataInicio').datetimepicker({
//		format: 'DD/MM/YYYY',
//		locale:  'pt-br',
//		ignoreReadonly: true,
//		showClose: true,
//		showTodayButton:true,
//		toolbarPlacement: 'bottom',
//		
//    });
//
//    $('#dataFim').datetimepicker({
//		format: 'DD/MM/YYYY',
//		locale:  'pt-br',
//		ignoreReadonly: true,
//		showClose: true,
//		showTodayButton:true,
//		toolbarPlacement: 'bottom',
//		
//    });



   
    $(".btn-whatsapp").on("click", function(event){
    	event.preventDefault();
    	var id = this.id;
    	
    	$.ajax({
    	      type : "GET",
    	      url : "/admin/cadastros/ocorrencias/descricaoWhastapp/"+id,
    	      success: function(data) {
    	    	  if(navigator.userAgent.indexOf("Firefox") != -1 ) 
    		  	    {
    		  	    	 var myWindow =  window.open('https://web.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
    			    	  myWindow.moveBy(400, 100);
    			    	  myWindow.resizeTo(800, 700);
    		  	    } else{    	  
    	    	  
    	    			 var myWindow =  window.open('https://api.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
    		    	     myWindow.moveTo(-1200, 100);
    		    	     myWindow.resizeTo(800, 700);
    	  	        }	  	   
    	   	     }
    		});
    	 $('#modal-enviarWhatsapp').modal('hide');
    });

    $('.btn-enviar-varias').on("click", function(event){
    	event.preventDefault();
    	
    	var titulo = $('.btn-enviar-varias').data("titulo");
    	
    	$('#modal-envio-varias-ocorrencias').modal().find('.modal-title').text(titulo);
    	
    	$('.form-enviarVariasPorEmail').attr("action","admin/cadastros/ocorrencias/enviarSelecionadas");
    	
    	$("#modal-envio-varias-ocorrencias").modal("show");
    	
    });



    /**
     * 
     * Aciona barra de progresso
     * 
     * 
     * **/
    $('#btn-enviarEmails_Selecionados').on("click", function(event){
    	document.getElementById('progress').innerHTML = '<div class="pace"><div class="pace-progress" data-progress="50" data-progress-text="50%" style="-webkit-transform: translate3d(50%, 0px, 0px); -ms-transform: translate3d(50%, 0px, 0px); transform: translate3d(50%, 0px, 0px);"><div class="pace-progress-inner"></div></div><div class="pace-activity"></div></div>';

    });

    $('#btn-enviar-varias-por-email').on("click", function(event){
    	document.getElementById('progresso').innerHTML = '<div class="pace"><div class="pace-progress" data-progress="50" data-progress-text="50%" style="-webkit-transform: translate3d(50%, 0px, 0px); -ms-transform: translate3d(50%, 0px, 0px); transform: translate3d(50%, 0px, 0px);"><div class="pace-progress-inner"></div></div><div class="pace-activity"></div></div>';
    });



    $('.btn-enviarEmail-ocorrencia').on("click", function(event){
    	event.preventDefault();
    	
    	var id = this.id;
    	
    	
    		$.ajax({
    		      type : "GET",
    		      url : "admin/cadastros/ocorrencias/descricaoHtml/"+id,
    		      success: function(data) {
    		      $('#btn-enviarEmail').attr("name", id);
    		      document.getElementById("descricaoDaOcorrenciaEmail").innerHTML = data;
    		      $('.form-enviarEmail').attr("action", "/admin/cadastros/ocorrencias/enviarEmail/"+id,);
    		      }
    			});
    		
    	$("#modal-enviarEmail").modal("show");
    });
    
    
    var ocorrenciasSelecionadasEmail = new Array();

    $('.ocorrenciaSelecionada').on("change", function(e){
    	
    	var arrayTemporario = new Array();
    	
    	if(this.checked == true){
    		ocorrenciasSelecionadasEmail[ocorrenciasSelecionadasEmail.length] = this.id;
    	}else{
    		for(var i=0; i < ocorrenciasSelecionadasEmail.length; i++){
    			if(ocorrenciasSelecionadasEmail[i] !== this.id){
    				arrayTemporario[arrayTemporario.length]=this.id;
    			}
    			
    		}
    		ocorrenciasSelecionadasEmail = arrayTemporario;
    	}
    	
    	if(ocorrenciasSelecionadasEmail.length >= 0){
	    	document.getElementById('btn-enviar-varias-por-email').disabled=false;
			document.getElementById('btn-imprimir-varias').disabled=false;
	    	document.getElementById('btn-enviar-varias-por-whatsapp').disabled=false;
    		
    	}else{
    		document.getElementById('btn-imprimir-varias').disabled=true;
    		document.getElementById('btn-enviar-varias-por-whatsapp').disabled=true;
    		document.getElementById('btn-enviar-varias-por-email').disabled=true;
    	}
    });
    
	$('#tabela-pesquisaOcorrencia').DataTable({
		"columnDefs" : [ {
			"targets" : 5,
			"orderable" : false
		} ],
		"order" : [ [ 0, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-enviarPorEmail').DataTable({
		"columnDefs" : [ {
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
