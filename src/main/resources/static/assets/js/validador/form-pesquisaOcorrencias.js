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
		$('#estabelecimento').removeAttr("readonly");
		$('#cidade').removeAttr("readonly");
		$('#localidade').removeAttr("readonly");
		$('#bairro').removeAttr("readonly");
		$('#sic').attr("readonly", true);
		$('#estadoOcorrencia').removeAttr("readonly");

		document.getElementById('sic').disabled=true;
	}else{
		alert("Não é possivel realizar pesquisas de ocorrências com essa configuração!");
		$('#dataInicio').val("");
		$('#dataFim').val("");
	}
});


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
         /*   defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            submitButton: new FormValidation.plugins.SubmitButton(),
*/
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
$('#limparCampos').on('click', function(event){
	
	$('#formPesquisaOcorrencia').each (function(){
		  this.reset();
	});	
	
	$('#tipificacao').attr("readonly", true);
	$('#dataInicio').attr("readonly", true);
	$('#dataFim').attr("readonly", true);
	$('#estabelecimento').attr("readonly", true);
	$('#cidade').attr("readonly", true);
	$('#localidade').attr("readonly", true);
	$('#bairro').attr("readonly", true);
	$('#sic').prop("disabled", false);
	$('#estadoOcorrencia').attr("readonly", true);
	$('#sic').removeAttr("readonly");
});

$('#estabelecimento').on("change", function(e) {
	document.getElementById("cidade").options.length = 1;
	document.getElementById("localidade").options.length = 1;
	document.getElementById("bairro").options.length = 1;
	
	var id = this.value;
	
	$.ajax({
		type : "GET",
		url : "admin/cadastros/estabelecimento/cidades/" + id,
		success : function(data) {			
			for (var i = 0; i < data.length; i++) {
				var option = new Option(data[i].nome, data[i].id);
				var select = document.getElementById("cidade");
				select.add(option);
			}
		}
	});

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
		      url : "admin/cadastros/localidades/bairrosSort/" + this.value,
		      success: function(data) {	   
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("bairro");
		    		  select.add(option);
		    	  }	
		      }
	    });
	});

	
	today = new Date();
	$('#dataInicio').datetimepicker({
		format: 'dd/mm/yyyy hh:ii',
		language:  'pt-BR',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		
    });

    $('#dataFim').datetimepicker({
		format: 'dd/mm/yyyy hh:ii',
		language:  'pt-BR',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		
    });

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

    $('.listaDeEmails').on('change', function(event){
		
		 var check = $(".listaDeEmails :checked"); 
		  	let contador = 0;
		    for (var i=0; i < check.length;i++){ 
		        if (check[i].checked == true){
		        	contador++;
		            break;
		        }
		    }
		    if(contador == 0){
		    	$('#btn-enviarEmails_Selecionados').prop("disabled", false);
		    }
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
    
  
    $(document).ready(function() {
        $('#tabela-pesquisa-paginada').DataTable( {
        	
            "oLanguage": {
                "sProcessing":   "Processando...",
                "sLengthMenu":   "Mostrar _MENU_ registros",
                "sZeroRecords":  "Não foram encontrados resultados",
                "sInfo":         "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty":    "Mostrando de 0 até 0 de 0 registros",
                "sInfoFiltered": "",
                "sInfoPostFix":  "",
                "sSearch":       "Buscar:",
                "sUrl":          "",
                "oPaginate": {
                    "sFirst":    "Primeiro",
                    "sPrevious": "Anterior",
                    "sNext":     "Seguinte",
                    "sLast":     "Último"
                }
            },  	      	
        
            initComplete: function () {
                this.api().columns().every( function () {
                    var column = this;
                    var select = $('<select><option value=""></option></select>')
                        .appendTo( $(column.footer()).empty() )
                        .on( 'change', function () {
                            var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );
     
                            column
                                .search( val ? '^'+val+'$' : '', true, false )
                                .draw();
                        } );
     
                    column.data().unique().sort().each( function ( d, j ) {
                        select.append( '<option value="'+d+'">'+d+'</option>' )
                    } );
                } );
            }
        } );
        
    } );
