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
			'funcao':{
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
			},/**
			'matricula':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},**/
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
    				},
                    date: {
                        format: 'DD/MM/YYYY',
                        message: 'Data Inválida',
                    }
                }
            },            
            'estadoNacionalidade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'naturalidade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'nacionalidade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'tipoSanguineo':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'etnia':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
/*
 * C O N T A T O
 */
            
			'celular1':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
//			'celular2':{
//				validators: {
//                    notEmpty : {
//    					message : '',
//    				}
//                }
//			},
			'email.enderecoDeEmail':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'situacao':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'estadoCivil':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'qtdFilhos':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'nomePai':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'nomeMae':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'escolaridade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'dataAdmissao': {
                validators: {
                	notEmpty : {
    					message : '',
    				},
                    date: {
                        format: 'DD/MM/YYYY',
                        message: 'Data Inválida',
                    }
                }
            },  
			
			'cartaoSUS':{
				validators: {
                    notEmpty : {
    					message : '',
    				}
                }
			},
			
			
			'UfServidor':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'cidade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'bairro':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'numero':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'rua':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'complemento':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			'cep':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
						
			'descricao' : {
				validators: {
					callback: {
                        message: 'O campo descrição poderá ter até 1.500 caracteres',
                        callback: function(value, validator, $field) {
                            // Get the plain text without HTML
                            var text = tinyMCE.activeEditor.getContent({
                                format: 'text'
                            });
                                                        
                            return text.length <= 1500 && text.length >= 1;
                        }                    },
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                }
			}
		},
		
		plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap(),
            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            submitButton: new FormValidation.plugins.SubmitButton()
        },
	});
	
//	today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
//	today = new Date();
	
	$('.dataNascimento').datetimepicker({
        language:  'pt-BR',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView:2,
		maxView:4,
		forceParse: 2,
        format: 'dd/mm/yyyy'
	}).on('change', function(e){
	    fv.revalidateField('dataNascimento');
	});
	jQuery(function($){
		
		$(".dataNascimento").mask("99/99/9999");
		}).on('keypress', function(e) {
	        fv.revalidateField('dataNascimento');
	    });
	
	$('.dataAdmissao').datetimepicker({
        language:  'pt-BR',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView:2,
		maxView:4,
		forceParse: 2,
        format: 'dd/mm/yyyy'
	}).on('change', function(e){
	    fv.revalidateField('dataAdmissao');
	});
	jQuery(function($){
		
		$(".dataAdmissao").mask("99/99/9999");
		}).on('keypress', function(e) {
	        fv.revalidateField('dataAdmissao');
	    });
	
	
	
	
	
	
//	$('[name="dataNascimento"]').datetimepicker({
//		format: 'DD/MM/YYYY',
//		locale:  'pt-br',
//		ignoreReadonly: true,
//		showClose: true,
//		showTodayButton:true,
//		toolbarPlacement: 'bottom',
//		//date: today,
//    }).on('changeDate', function(e) {
//    	
//        // Revalidate the date field
//        fv.revalidateField('dataNascimento');
//    });	
	
	
	
//	$('[name="dataAdmissao"]').datetimepicker({
//		format: 'DD/MM/YYYY',
//		locale:  'pt-br',
//		ignoreReadonly: true,
//		showClose: true,
//		showTodayButton:true,
//		toolbarPlacement: 'bottom',
//		//date: today,
//    }).on('changeDate', function(e) {
//    	
//        // Revalidate the date field
//        fv.revalidateField('dataAdmissao');
//    });
	
	
			
	tinymce.init({
		selector: 'textarea',
		language : 'pt_BR',
		width: '100%',
		entity_encoding : "raw",
		height : 230,
		elementpath : false,
		statusbar:  false,
		menubar : false,
		branding: false,
		plugins : 'print template advlist lists textcolor colorpicker textpattern',
		advlist_bullet_styles : "default",
		advlist_number_styles : "default",
		toolbar : "undo redo |  formatselect | bold italic strikethrough forecolor  | alignleft alignjustify | bullist numlist outdent indent | removeformat",
		
		setup: function(editor) {
            editor.on('keyup', function() {
                // Revalidate the comment field
                fv.revalidateField('descricao');
            });
        }
	});
	
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});


/*Valida senha*/

$('#conferirSenha').on('keyup', function(event){
	
	if(this.value == document.getElementById('novaSenha').value){
		return true;
	}	
	return false;
	
	
});


/*CONSULTA POR CEP*/
$("#buscar").on('click', function(event) {

    /*function limpa_formulário_cep() {
        // Limpa valores do formulário de cep.
        $("#rua").val("");
        $("#bairro").val("");
        $("#cidade").val("");
        $("#uf").val("");
        $("#ibge").val("");
    }*/
    
    //Quando o campo cep perde o foco.
   // $("#cep").blur(function() {

        //Nova variável "cep" somente com dígitos.
        var cep = $("#cep").val().replace(/\D/g, '');
        
        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(validacep.test(cep)) {
            	
            	//Preenche os campos com "..." enquanto consulta webservice.
                $("#rua").val("...");
                $("#bairro").val("...");
               
                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#rua").val(dados.logradouro);
                        $("#bairro").val(dados.bairro);
                        document.getElementById("cidade").options.length = 0;
                        $.ajax({
                		      type : "GET",
                		      url : "/admin/cadastros/cidades/consultarPorNome/" + dados.localidade,
                		      success: function(data) {
                		    	  var option = new Option(data.nome, data.id);
                		    	  var select = document.getElementById("cidade");
                		    	  select.add(option);
                		      },
                   		});
                        document.getElementById("unidadeFederativa").options.length = 0;
                        $.ajax({
              		      type : "GET",
              		      url : "/admin/cadastros/estados/consultarPorNome/" + converteSigla(dados.uf),
              		      success: function(data1) {
              		    	  var option = new Option(data1.nome, data1.id);
              		    	  var select = document.getElementById("unidadeFederativa");
              		    	  select.add(option);
              		      },
                 		});
                    } //end if.
                    else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulário_cep();
                        alert("CEP não encontrado.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                limpa_formulário_cep();
                alert("Formato de CEP inválido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    //});
});

function converteSigla(sigla){
	switch (sigla) {
	    case "AC": return "ACRE"; break;
	    case "AL": return "ALAGOAS"; break;
	    case "AP": return "AMAPÁ"; break;
	    case "AM": return "AMAZONAS"; break;
	    case "BA": return "BAHIA"; break;
	    case "CE": return "CEARÁ"; break;
	    case "DF": return "DISTRITO FEDERAL"; break;
	    case "ES": return "ESPÍRITO SANTO"; break;
	    case "GO": return "GOIÁS"; break;
	    case "MA": return "MARANHÃO"; break;
	    case "MT": return "MATO GROSSO"; break;
	    case "MS": return "MATO GROSSO DO SUL"; break;
	    case "MG": return "MINAS GERAIS"; break;
	    case "PA": return "PARÁ"; break;
	    case "PB": return "PARAÍBA"; break;
	    case "PR": return "PARANÁ"; break;
	    case "PE": return "PERNAMBUCO"; break;
	    case "PI": return "PIAUÍ"; break;
	    case "RJ": return "RIO DE JANEIRO"; break;
	    case "RN": return "RIO GRANDE DO NORTE"; break;
	    case "RS": return "RIO GRANDE DO SUL"; break;
	    case "RO": return "RONDÔNIA"; break;
	    case "RR": return "RORAIMA"; break;
	    case "SC": return "SANTA CATARINA"; break;
	    case "SP": return "SÃO PAULO"; break;
	    case "SE": return "SERGIPE"; break;
	    case "TO": return "TOCANTINS"; break;
		} 

}
