/**
 * 
 * INFORMAÇÃOES SOBRE MODALIDADE
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à INSTITUICAO
 * 
 * **/
$('.btn-nova-modalidade').on("click", function(){
	var titulo = $('.btn-nova-modalidade').data("titulo");
	$('#modal-modalidade').modal().find('.modal-title').text(titulo)
	$('.form-modalidade').attr("action","/admin/cadastraModalidade");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='descricao']").val('');
	$('#btn-salvar-modalidade').text("Salvar");
	$("#modal-modalidade").modal("show");
});

$('.btn-editar-modalidade').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-modalidade').data("titulo");
	var id = this.id;
	var href = "/admin/configuracao/"+ $(this).attr('href');
	
	$('#modal-modalidade').modal().find('.modal-title').text(titulo);
	$('.form-modalidade').attr("action", href);
	$('#btn-salvar-modalidade').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/configuracao/modalidade/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("input[name='qtdServidores']").val(data.qtdServidores);
          $("input[name='cor']").val(data.cor);
      }
	});
	$("#modal-modalidade").modal("show");
});

$('.btn-excluir-modalidade').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});



