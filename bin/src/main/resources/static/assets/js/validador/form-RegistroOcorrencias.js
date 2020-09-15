/**
 * Arquivo de configuração da VALIDAÇÃO via Javascript do formulário de Cadastro e edição de Registros de ocorrências
 * ATENÇÃO ESTE JS GERENCIA A PÁGINA DE CADASTRO DE NOVOS REGISTROS DE OCORRÊNCIAS
 * 
 */

document.addEventListener('DOMContentLoaded', function(e) {
//	const fv = FormValidation.formValidation(document.getElementById('formRegistroOcorrencia'),{
//		locale: 'pt_BR',
//        localization: FormValidation.locales.pt_BR,
//		fields : {
//			
//			'dataDaOcorrencia': {
//                validators: {
//                	notEmpty : {
//    					message : 'Campo Obrigatório',
//    				},
//                    date: {
//                        format: 'DD/MM/YYYY h:m:s',
//                        message: 'Data Inválida',
//                    }
//                }
//            },
//            
//			'sic' : {
//				validators : {
//					notEmpty : {
//						message : 'Campo Obrigatório'
//					},
//					numeric: {
//                        message: 'Somente números'
//                    }
//				}
//			},
//			
//			'estabelecimento':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//			
//			'categoria':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//			
//			'tipificacao':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//			
//			
//            
//            'horaOcorrencia': {
//                validators: {
//                	notEmpty : {
//    					message : 'Campo Obrigatório',
//    				},
//                    options: {
//                    	format: '^(2[0-3]|[01]?[0-9]):([0-5]?[0-9]):([0-5]?[0-9])$',
////                        format: '^(1[0-2]|0?[1-9]):([0-5]?[0-9]):([0-5]?[0-9])$',
//                        message: 'Inválido',
//                    }
//                }
//            },
//            
//            'estadoOcorrencia':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//            
//            'endereco.cidade':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//			
//			'localidade':{
//				validators: {
//					notEmpty : {
//	    				message : 'Campo Obrigatório',
//	    				}
//	        	}
//			},
//			
//			'endereco.bairro':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				}
//                }
//			},
//			
//			'endereco.rua':{
//				validators: {
//                    notEmpty : {
//    					message : 'Campo Obrigatório',
//    				},
//    				stringLength : {
//						min : 5,
//					},
//                }
//			},
//			
//			'endereco.numero':{
//				validators : {
//					number: {
//                        message: 'Somente números'
//                    }
//				}
//			},
//		},
//		
//		plugins: {
//            trigger: new FormValidation.plugins.Trigger(),
//            bootstrap: new FormValidation.plugins.Bootstrap(),
//            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
//            submitButton: new FormValidation.plugins.SubmitButton(),
//            icon: new FormValidation.plugins.Icon({
//                valid: 'fa fa-check',
//                invalid: 'fa fa-times',
//                validating: 'fa fa-refresh',
//            }),
//        },
//	});
	
	today = new Date();
	
	$('[name="dataDaOcorrencia"]').datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		locale:  'pt-br',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		date: today,
//    }).on('changeDate', function(e) {
//    	
//        // Revalidate the date field
//        fv.revalidateField('dataOcorrencia');
    });
	
	$('[name="dataNascimento"]').datetimepicker({
		format: 'DD/MM/YYYY',
		locale:  'pt-br',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		date: today,
//    }).on('changeDate', function(e) {
//    	
//        // Revalidate the date field
//        fv.revalidateField('dataOcorrencia');
    });
	
	$('#categoria').on("change", function(e){
		document.getElementById("tipificacao").options.length = 1;
		var id = this.value
				
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/categorias/tipificacoes/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("tipificacao");
		    		  select.add(option);
		    	  }
		      },
		      fail: function(){
		          alert('Falha ao Buscar as Tipificações');
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('localidade');
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
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("localidade");
		    		  select.add(option);
		    	  }
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
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("bairro");
		    		  select.add(option);
		    	  }	
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
		height : 100,
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