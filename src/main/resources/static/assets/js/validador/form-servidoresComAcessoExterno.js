
/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de servidores
 */
/*
 * D A D O S   P E S S O A I S
 */
document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formServidor'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {
			'estabelecimento' : {
				validators : {
					notEmpty : {
						message : 'Campo Obrigatório'
					}					
				}
			},
			
			'instituicao':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'hierarquia':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'nome':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'sexo':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'cpf':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'rg':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'orgaoEmissor':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'dataNascimento': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				}                    
                }
            },
            
			'celular1':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
		  'celular2':{				
			},
			'email.enderecoDeEmail':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
		},
		
		plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap(),
            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            submitButton: new FormValidation.plugins.SubmitButton(),
        },
	});
	
	jQuery(function($){
		$("#dataNascimento").mask("99/99/9999");
		$("#telefone1").mask("(99) 99999-9999");
		$("#telefone2").mask("(99) 99999-9999");

	});

$('[required]', event.field).removeAttr('required');
});


$('#instituicao').on("change", function(e){
	document.getElementById("hierarquia").options.length = 1;
	if(this.value !== ""){
    $.ajax({
	      type : "GET",
	      url : "/admin/instituicoes/hierarquias/" + this.value,
	      success: function(data) {	   
	    	  console.log(data);
              var options = $('#hierarquia');
              //options.find('option').remove();
              $.each(data, function (key, value) {
           	   		console.log(value);
                        $('<option>').val(value.id).text(value.nome).appendTo(options);
           	         //options += '</option>';
             });
	      }
    	});
	}
	$(".instituicaoNoTerno").text($("#instituicao option:selected").text());
});


$('#funcao').on("change", function(e){
	$(".funcaoNoTerno").text($("#funcao option:selected").text());
});

$('#hierarquia').on("change", function(e){
	$(".hierarquiaNoTerno").text($("#hierarquia option:selected").text());
});

$('#estabelecimento').on("change", function(e){
	$(".estabelecimentoNoTerno").text($("#estabelecimento option:selected").text());
});

$('#nome').on("keyup", function(e){
	$(".nomeNoTerno").text($("#nome").val());
});

$("#aceitaTermo").on("change", function(event){
	$("#botaoSalvar").collapse('show');
});

$("#naoAceitaTermo").on("change", function(event){
	$("#botaoSalvar").collapse('hide');
});

document.addEventListener('DOMContentLoaded', function(e) {
	$('.data-picker').datepicker({
        language:  'pt-BR',
        format: 'dd/mm/yyyy',
        maxViewMode: 2,
        autoclose: true,
        todayHighlight: 1,
        defaultDate:'now'
	}).on('changeDate', function (ev) {
	    $(this).datepicker('hide');
	});
});
