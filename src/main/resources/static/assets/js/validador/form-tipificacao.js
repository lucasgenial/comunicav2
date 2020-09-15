/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de tipificação
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(document.getElementById('formTipificacao'),{
		fields : {
			nome : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O campo nome não pode está em branco'
					},
					stringLength : {
						min : 4,
						max : 50,
						message : 'valores entre 4 e 50 caracteres'
					},
				}
			}
		}
	});
    
    /**
     * 
     * INFORMAÇÃOES SOBRE TIPIFICACAO
     * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à TIPIFICACAO
     * 
     * **/
    $('.btn-nova-tipificacao').on("click", function(){
    	var titulo = $('.btn-nova-tipificacao').data("titulo");
    	$('#modal-tipificacao').modal().find('.modal-title').text(titulo)
    	$('.form-tipificacao').attr("action","/admin/cadastraTipificacao");
    	$(".id").hide();
    	$("input[name='id']").val('');
    	$("input[name='nome']").val('');
    	$('#btn-salvar-tipificacao').text("Salvar");
    	$("#modal-tipificacao").modal("show");
    });

    $('.btn-editar-tipificacao').on("click", function(event){
    	event.preventDefault();
    	var titulo = $('.btn-editar-tipificacao').data("titulo");
    	var id = this.id;
    	var href = "/admin/cadastros"+ $(this).attr('href');
    	
    	$('#modal-tipificacao').modal().find('.modal-title').text(titulo);
    	$('.form-tipificacao').attr("action", href);
    	$('#btn-salvar-tipificacao').text("Editar");

    	$.ajax({
			  type : "GET",
			  url : "admin/cadastros/tipificacoes/consultar/" + id,
			  success: function(data) {
				  $("#categoria").val(data.categoria.id).change();
				  $("input[name='id']").val(data.id);
				  $("input[name='nome']").val(data.nome);
				  $("input[name='cor']").val(data.cor);
			  }
    	});
    	$("#modal-tipificacao").modal("show");
    });

    $('.btn-excluir-tipificacao').on("click", function(event){
    	event.preventDefault();
    	var href = $(this).attr('href');
    	$("#modal-excluir #delRef").attr("href",href);
    	$("#modal-excluir").modal();
    });
});