/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de cidades
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation( document.getElementById('formCidade'), {
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
     * INFORMAÇÃOES SOBRE CIDADES
     * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à CIDADES
     * 
     * **/
    $('.btn-nova-cidade').on("click", function(){
    	var titulo = $('.btn-nova-cidade').data("titulo");
    	$('#modal-cidade').modal().find('.modal-title').text(titulo);
    	
    	$('.form-cidade').attr("action","/admin/cadastraCidade");
    	
    	$(".id").hide();
    	$("input[name='id']").val('');
    	$("input[name='nome']").val('');
    	$("input[name='cor']").val('');
    	$('#btn-salvar-cidade').text("Salvar");
    	$("#modal-cidade").modal("show");
    });

    $('.btn-editar-cidade').on("click", function(event){
    	event.preventDefault();
    	var titulo = $('.btn-editar-cidade').data("titulo");
    	
    	var id = this.id;
    	var href = "/admin/cadastros"+ $(this).attr('href');
    	
    	$('#modal-cidade').modal().find('.modal-title').text(titulo);
    	$('.form-cidade').attr("action", href);
    	$('#btn-salvar-cidade').text("Editar");

    	$.ajax({
          type : "GET",
          url : "admin/cadastros/cidades/consultar/"+id,
          success: function(data) {
        	  console.log(data);
              $("input[name='id']").val(data.id);
              $("#uf").val(data.uf.id).change();
              $("input[name='nome']").val(data.nome);
              $("input[name='cor']").val(data.cor);
          }
    	});
    	$("#modal-cidade").modal("show");
    });

    $('.btn-excluir-cidade').on("click", function(event){
    	event.preventDefault();
    	var href = $(this).attr('href');
    	$("#modal-excluir #delRef").attr("href",href);
    	$("#modal-excluir").modal();
    });
});

$(document).ready(function() {
	$('#tabela-cidades').DataTable({
		"columnDefs" : [{
			"targets" : 3,
			"orderable" : false
		} ],
		"order" : [ [ 0, "asc" ] ],
		"responsive" : true,
		"paging":   false,
	    "info":     false,
	    "searching": false,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
});

