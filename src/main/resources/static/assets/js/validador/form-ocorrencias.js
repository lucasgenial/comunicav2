/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de ocorrências
 */

document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formOcorrencia'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {
			'sic' : {
				validators : {
					notEmpty : {
						message : 'Campo Obrigatório'
					},
					numeric: {
                        message: 'Somente números'
                    }
				}
			},
			
			'tipificacao':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'dataOcorrencia': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},

                }
            },
            
            'horaOcorrencia': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
    				regexp: {
                           regexp: /^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$/,
                           message: 'Digite um horário válido'
                    },
                   
                },
            },
            
            'estadoOcorrencia':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
            
            'endereco.cidade':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'endereco.bairro.localidade':{
				validators: {
					notEmpty : {
	    				message : 'Campo Obrigatório',
	    				}
	        	}
			},
			
			'endereco.bairro':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'endereco.rua':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				},
    				stringLength : {
						min : 5,
					},
                }
			},
			
			'endereco.numero':{
				validators : {
					number: {
                        message: 'Somente números'
                    }
				}
			},
			'endereco.referencia':{
				validators : {
					
				}
			},
			
			'guarnicao' : {
				validators : {
					notEmpty : {
						message : 'O identificador da guarnição não pode está em branco'
					},
					stringLength : {
						max : 100,
					},
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
                        }
                    },
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                }
			},

			'historico' : {
				validators: {
					callback: {
                        message: 'O campo historico poderá ter até 1.500 caracteres',
                        callback: function(value, validator, $field) {
                            // Get the plain text without HTML
                            var text = tinyMCE.activeEditor.getContent({
                                format: 'text'
                            });
                                                        
                            return text.length <= 1500 && text.length >= 1;
                        }
                    },
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
            submitButton: new FormValidation.plugins.SubmitButton()//,

        },
	});
	


	$('#cidade').on("change", function(e){
		document.getElementById("localidade").options.length = 1;
		document.getElementById("bairro").options.length = 1;
		document.getElementById("estabelecimento").options.length = 0;
		var id = this.value
				
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/cidades/localidades/" + id,
		      success: function(data) {
			           var options = $('#localidade');
			          // options.find('option').remove();
			           $.each(data, function (key, value) {
			                     $('<option>').val(value.id).text(value.nome).appendTo(options);
		    	  })
		      },
		      fail: function(){
		          alert('Falha ao Buscar as Localidades/Distritos');
		      }
	    });
	  $.ajax({
		      type : "GET",
		      url : "admin/cadastros/cidades/estabelecimento/" + id,
		      success: function(data1) {
		    	  var option = new Option(data1.nome, data1.id, true, true);
		    	  var select = document.getElementById("estabelecimento");
		    	  select.add(option);
		      },
		      fail: function(){
		    	  alert('Falha ao Buscar o Estabelecimento');
		      }
		});
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('localidade');
    });

	$('#localidade').on("change", function(e){
		document.getElementById("bairro").options.length = 1;
	    $.ajax({
		      type : "GET",
		      url : "admin/cadastros/localidades/bairros/" + this.value,
		      success: function(data) {
		           var options = $('#bairro');
		        //   options.find('option').remove();
		           $.each(data, function (key, value) {
		                     $('<option>').val(value.id).text(value.nome).appendTo(options);
			    	  })
		      },
		      fail: function(){
		          alert('Falha ao Buscar os Bairros');
		      }
	    });
	    
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('endereco.bairro');
    });
	
	tinymce.init({
		selector: 'textarea',
		language : 'pt_BR',
		width: '100%',
		entity_encoding : "raw",
		height : 230,
		encoding: 'raw',
		elementpath : false,
		statusbar:  false,
		menubar : false,
		branding: false,
		plugins : 'print template advlist lists textcolor colorpicker textpattern paste',
		paste_as_text: true,
		advlist_bullet_styles : "default",
		advlist_number_styles : "default",
		toolbar : "undo redo | bold italic strikethrough | removeformat",
		setup: function(editor) {
            editor.on('keyup', function() {
                // Revalidate the comment field
                fv.revalidateField('descricao');
                fv.revalidateField('historico');
            });
        }
	});
	
	jQuery(function($){
		$("#dataOcorrencia").mask("99/99/9999 99:99:99");
	}).on('changeDate', function(e) {
        fv.revalidateField('dataOcorrencia');
    });
	
	jQuery(function($){
		$("#horaOcorrencia").mask("99:99:99");
	}).on('changeDate', function(e) {

        fv.revalidateField('horaOcorrencia');
    });
	
	
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});

//today = new Date();
//$('[name="dataDaCriacao"]').datetimepicker({
//	format: 'DD/MM/YYYY HH:mm:ss',
//	locale:  'pt-br',
//	ignoreReadonly: false,
//	showClose: true,
//	showTodayButton:true,
//	toolbarPlacement: 'bottom',
//	date: today,
//});
//
//$('[name="dataUltimaModificao"]').datetimepicker({
//	format: 'DD/MM/YYYY HH:mm:ss',
//	locale:  'pt-br',
//	ignoreReadonly: false,
//	showClose: true,
//	showTodayButton:true,
//	toolbarPlacement: 'bottom',
//	date: today,
//});

$('.datetimepicker').datetimepicker({
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
});

$('.importaOcorrenciaCecoco').on("click", function(event){
	event.preventDefault();
	var sic = document.getElementById("sic").value;
	if( sic != null || sic != "" ){
		window.location.href="/admin/cadastro/ocorrencias/importarOcorrencia/"+sic;
	}
});

$('.atualizaOcorrenciaCecoco').on("click", function(event){
	event.preventDefault();
	var sic = document.getElementById("sic").value;
	console.log(sic);
	if( sic != null || sic != "" ){
		window.location.href="/admin/cadastro/ocorrencias/atualizarOcorrencia/"+sic;
	}
});

function validate(){
    if ( $('#sic').val().length > 0  ) {
        $('#importaOcorrenciaCecoco').prop("disabled", false);
    }
    else {
        $('#importaOcorrenciaCecoco').prop("disabled", true);
    }
}

$(document).ready(function (){
    validate();
    $('#sic').change(validate);
});

$('#tabela-log').DataTable({
	"columnDefs" : [{
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 0, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
