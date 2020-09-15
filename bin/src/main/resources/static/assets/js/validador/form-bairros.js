/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de bairro
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(
    		document.getElementById('formBairro'),
    {
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

/**
 * 
 * INFORMAÇÃOES SOBRE BAIRROS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à BAIRROS
 * 
 * **/
$('.btn-novo-bairro').on("click", function(){
	var titulo = $('.btn-novo-bairro').data("titulo");
	$('#modal-bairro').modal().find('.modal-title').text(titulo)
	$('.form-bairro').attr("action","/admin/cadastraBairro");
	$("#cidade").val("");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$('#btn-salvar-bairro').text("Salvar");
	$("#modal-bairro").modal("show");
});

$('.btn-editar-bairro').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-bairro').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
		
	$('.form-bairro').attr("action", href);
	$('#btn-salvar-bairro').text("Editar");
	$('#modal-bairro').modal().find('.modal-title').text(titulo);

	$.ajax({
      type : "GET",
      url : "admin/cadastros/bairros/consultar/"+id,
      success: function(data) {
    	   console.log(data);
	       $("input[name='id']").val(data.id);
	       $("input[name='nome']").val(data.nome);
      }
	});

	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/bairros/consultarcidade/"+id,
	      success: function(data) {
		      console.log(data);
		      $("#cidade").val(data.id);
		      }
	});

	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/bairros/consultarlocalidade/"+id,
	      success: function(data) {
	    	  console.log(data);
			   $("#localidade").val(data.id);
	      }
	});

	$.ajax({
    type : "GET",
    url : "admin/cadastros/bairros/consultarcidadeporestabelecimento/"+id,
    async: false,
    success: function(data) {
  	    console.log(data);
        var options = $('#cidade');
        options.find('option').remove();
        $.each(data, function (key, value) {
                   $('<option>').val(value.id).text(value.nome).appendTo(options);
      	         //options += '</option>';
        	});
    	}
	});

	$.ajax({
	    type : "GET",
	    url : "admin/cadastros/bairros/consultarlocalidadesporcidade/"+id,
        async: false,
	    success: function(data) {
	  	  console.log(data);
	        var options = $('#localidade');
	        options.find('option').remove();
	        $.each(data, function (key, value) {
	                   $('<option>').val(value.id).text(value.nome).appendTo(options);
	      	         //options += '</option>';
	        	});
	    	}
	});

	$("#modal-bairro").modal("show");
});

$('.btn-excluir-bairro').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

$('#cidade').on("change", function(e){
	document.getElementById("localidade").options.length = 1;
	if(this.value !== ""){
    $.ajax({
	      type : "GET",
	      url : "admin/cadastros/cidades/localidades/" + this.value,
	      success: function(data) {	   
	    	  console.log(data);
              var options = $('#localidade');
              //options.find('option').remove();
              $.each(data, function (key, value) {
           	   		console.log(value);
                        $('<option>').val(value.id).text(value.nome).appendTo(options);
           	         //options += '</option>';
             });
	      }
    });
	}
});