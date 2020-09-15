/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de cidades
 */

document.addEventListener('DOMContentLoaded', function(e) {
		const fv = FormValidation.formValidation(document.getElementById('formVisita'), {
    	locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {
			'nome' : {
				validators : {
					notEmpty : {
						message : 'O campo nome não pode está em branco'
					}					
				}
			},
			'empresa' : {
				validators : {
					notEmpty : {
						message : 'O campo empresa não pode está em branco'
					}
				}
			},
			'historico' : {
				validators : {
					notEmpty : {
						message : 'O campo histórico não pode está em branco'
					}
				}
			},
			'inicioServico' : {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY h:m:s',
                        message: 'Data Inválida',
                    }
                }
			},
			'fimServico' : {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY h:m:s',
                        message: 'Data Inválida',
                    }
                }
			},
			plugins: {
	            trigger: new FormValidation.plugins.Trigger(),
	            bootstrap: new FormValidation.plugins.Bootstrap(),
	            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
//	            submitButton: new FormValidation.plugins.SubmitButton(),
//	            icon: new FormValidation.plugins.Icon({
//	                valid: 'fa fa-check',
//	                invalid: 'fa fa-times',
//	                 validating: 'fa fa-refresh',
//	             }),
	        },
		}
	});


		
	jQuery(function($){
		$(".cpf").mask("999.999.999-99");
});
		
	jQuery(function($){	
		$(".inicioServico").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('inicioServico');
});
	
	jQuery(function($){	
		$(".fimServico").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('fimServico');
});
		

$('.inicioServico').datetimepicker({
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
    fv.revalidateField('inicioServico');
});


$('.fimServico').datetimepicker({
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
    fv.revalidateField('fimServico');
    
	});

});


$('#novoServico').hide();

$('#servico').change(function() {
    if ($(this).val() === '0') {
        $('#novoServico').show();
        $('#servico').hide();
    }
});

$('#cancelar').click(function(){
    $('#servico').show();
    $('#servico').val();
    $('#novoServico').hide();
});

$('.cpf').keyup(function() {
	let cpf =  $(this).val();
	let id= $(this).attr('data-identificador');
	if(cpf.length == 14 ){
		$.ajax({
	        type : "GET",
	        url : "/busca/visitante/porCPF/"+cpf,
	        success: function(data) {
			
	        	if(data != ""){
	        		$("#visitante-id-"+id).html('<input name="visitantes['+id+'].id" value="'+data.id+'"/>');
		            $("#nome-"+id).val(data.nome);
		            $("#nome-"+id).attr("readonly", "readonly");
		           
		    		$("#foto-3x4-"+id).html('<img data-toggle="modal" data-target="#exampleModalCenter" class="clicouNa3x4" style="width: 45px; height: 45px;" data-identificador="'+id+'" src="'+data.fotografiaUri+'"/>');
		    		$("#foto-id-"+id).html('<input readonly="readonly" hidden="hidden" name="visitantes['+id+'].fotografiaUri" value="'+data.fotografiaUri+'"/>');
				}
	        }
	  	});
	}
});
