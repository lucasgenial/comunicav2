document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(document.getElementById('formGrupo'),{
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
    
});

$(document).ready(function() {

	$('#tabela-grupo-permissoes').DataTable({
		"order" : [ [ 0, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
});

$('.btn-excluir-grupo').on("click", function(event){
	//event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

$('.btn-novo-grupo-permissao').on("click", function(){
	var titulo = $('.btn-novo-grupo').data("titulo");
	$('#modal-grupo-permissao').modal().find('.modal-title').text(titulo)
	$('.form-grupo').attr("action","/admin/grupos/adiconarpermissao");
	$('#btn-grupopermissaoEditar').text("Salvar");
	$("#modal-grupo-permissao").modal("show");
});

$('.btn-editar-grupo-permissao').on("click", function(){
	var titulo = $('.btn-novo-grupo').data("titulo");
	$('#modal-grupo-permissao').modal().find('.modal-title').text(titulo)
	$('.form-grupo').attr("action","/admin/grupos/editarPermissao");
    $.ajax({
	      type : "GET",
	      url : "/admin/cadastros/grupos/listarTodasPermissoes",
	      success: function(data) {	   
	    	  console.log(data);
            var options = $('#grupoPermissoes');
            $.each(data, function (key, value) {
         	   		console.log(value);
                      $('<option>').val(value.id).text(value.nome).appendTo(options);
           });
	      }
    });
	$('#btn-grupopermissaoEditar').text("Salvar");
	$("#modal-grupo-permissao").modal("show");
});

