/**
 * 
 * INFORMAÇÃOES SOBRE RECURSOS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-recurso').on("click", function(){
	var titulo = $('.btn-nova-recurso').data("titulo");
	$('#modal-recurso').modal().find('.modal-title').text(titulo)
	$('.form-recurso').attr("action","/admin/cadastraRecurso");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-recurso').text("Salvar");
	$("#modal-recurso").modal("show");
});

$('.btn-editar-recurso').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-recurso').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-recurso').modal().find('.modal-title').text(titulo);
	$('.form-recurso').attr("action", href);
	$('#btn-salvar-recurso').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/recurso/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
      }
	});
	$("#modal-recurso").modal("show");
});

$('.btn-excluir-recurso').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});


