/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de cidades
 */

//document.addEventListener('DOMContentLoaded', function(e) {
//    FormValidation.formValidation(
//    		document.getElementById('formVisita'),
//    {
//		fields : {
//			nome : {
//				icon : true,
//				validators : {
//					notEmpty : {
//						message : 'O campo nome não pode está em branco'
//					},
//					stringLength : {
//						min : 1,
//						max : 2500,
//						message : 'até 2500 caracteres'
//					},
//				}
//			},
//			empresa : {
//				icon : true,
//				validators : {
//					notEmpty : {
//						message : 'O campo empresa não pode está em branco'
//					},
//					stringLength : {
//						min : 1,
//						max : 2500,
//						message : 'até 2500 caracteres'
//					},
//				}
//			},
//			historico : {
//				icon : true,
//				validators : {
//					notEmpty : {
//						message : 'O campo histórico não pode está em branco'
//					},
//					stringLength : {
//						min : 1,
//						max : 2500,
//						message : 'até 2500 caracteres'
//					},
//				}
//			},
//			inicioServico : {
//				icon : true,
//                validators: {
//                	notEmpty : {
//    					message : 'Campo Obrigatório',
//    				},
//                    date: {
//                        format: 'DD/MM/YYYY h:m:s',
//                        message: 'Data Inválida',
//                    }
//                }
//			},
//			fimServico : {
//				icon : true,
//                validators: {
//                	notEmpty : {
//    					message : 'Campo Obrigatório',
//    				},
//                    date: {
//                        format: 'DD/MM/YYYY h:m:s',
//                        message: 'Data Inválida',
//                    }
//                }
//			},
//		}
//	});
//});

//$('#tabela-visita').DataTable({
//	"columnDefs" : [{
//		"targets" : 7,
//		"orderable" : false
//	} ],
//	"order" : [ [ 0, "asc" ] ],
//	"responsive" : true,
//	"select" : true,
//	"language" : {
//		url : '/assets/traducao/pt_BR.json'
//	}
//});

/**
 * 
 * INFORMAÇÃOES SOBRE VISITAS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à TIPIFICACAO
 * 
 * **/
$('.btn-nova-visita').on("click", function(){
	var titulo = $('.btn-nova-visita').data("titulo");
	$('#modal-visita').modal().find('.modal-title').text(titulo)
	$('.form-visita').attr("action","/admin/cadastrarVisita");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='cpf']").val('');
	$("input[name='empresa']").val('');
	$("input[name='inicioServico']").val('');
	$("input[name='fimServico']").val('');
	$("textarea[name='historico']").val('');
	$('#btn-salvar-visita').text("Salvar");
	$("#modal-visita").modal("show");
});

$('.btn-editar-visita').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-visita').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
	
	$('#modal-visita').modal().find('.modal-title').text(titulo);
	$('.form-visita').attr("action", "/visitas/editar/");
	$('#btn-salvar-visita').text("Atualizar");
	$.ajax({
      type : "GET",
      url : "/admin/cadastros/visitas/consultar/"+id,
      success: function(data) {
          console.log(data);
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='cpf']").val(data.cpf);
          $("input[name='empresa']").val(data.empresa);
          $("#servico").val(data.servicoId);
          $("input[name='inicioServico']").val(data.inicioServico);
          $("input[name='fimServico']").val(data.fimServico);         
          $("textarea[name='historico']").val(data.historico);
      	}
	  });	
      	  $("#modal-visita").modal("show");
});

$('.btn-excluir-visita').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});

jQuery(function($){
	$(".inicioServico").mask("99/99/9999 99:99:99");
	$(".fimServico").mask("99/99/9999 99:99:99");
	$("#cpf").mask("999.999.999-99");
});


