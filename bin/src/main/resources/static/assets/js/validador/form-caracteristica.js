/**
 * 
 * INFORMAÇÃOES SOBRE CARACTERISTICA
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-caracteristica').on("click", function(){
	var titulo = $('.btn-nova-caracteristica').data("titulo");
	$('#modal-caracteristica').modal().find('.modal-title').text(titulo)
	$('.form-caracteristica').attr("action","/admin/cadastraCaracteristica");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-caracteristica').text("Salvar");
	$("#modal-caracteristica").modal("show");
});

$('.btn-editar-caracteristica').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-caracteristica').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-caracteristica').modal().find('.modal-title').text(titulo);
	$('.form-caracteristica').attr("action", href);
	$('#btn-salvar-caracteristica').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/caracteristica/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-caracteristica").modal("show");
});

$('.btn-excluir-caracteristica').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

