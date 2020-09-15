/**
 * 
 * INFORMAÇÃOES SOBRE GRUPOS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à GRUPOS
 * 
 * **/
$('.btn-novo-grupo').on("click", function(){
	var titulo = $('.btn-novo-grupo').data("titulo");
	$('#modal-grupo').modal().find('.modal-title').text(titulo)
	$('.form-grupo').attr("action","/admin/cadastraGrupo");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-grupo').text("Salvar");
	$("#modal-grupo").modal("show");
});

$('.btn-editar-grupo').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-grupo').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-grupo').modal().find('.modal-title').text(titulo);
	$('.form-grupo').attr("action", href);
	$('#btn-salvar-grupo').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/grupos/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='descricao']").val(data.descricao);
      }
	});
	$("#modal-grupo").modal("show");
});

$('.btn-excluir-grupo').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});


