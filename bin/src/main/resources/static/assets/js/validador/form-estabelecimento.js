/**
 * 
 * INFORMAÇÃOES SOBRE ESTABELECIMENTOS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à ESTABELECIMENTOS
 * 
 * **/
$('.btn-novo-estabelecimento').on("click", function(){
	var titulo = $('.btn-nova-estabelecimento').data("titulo");
	$('#modal-estabelecimento').modal().find('.modal-title').text(titulo)
	$('.form-estabelecimento').attr("action","/admin/cadastraEstabelecimento");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$("input[name='enderecoDeEmail']").val('');
	$("input[name='senhaDoEmail']").val('');
	$('#btn-salvar-estabelecimento').text("Salvar");
	$("#modal-estabelecimento").modal("show");
});

$('.btn-editar-estabelecimento').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-estabelecimento').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
	
    console.log(href);
	$('.form-estabelecimento').attr("action", href);
	$('#modal-estabelecimento').modal().find('.modal-title').text(titulo);
	$('#btn-salvar-estabelecimento').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/cadastros/estabelecimentos/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          $("#emailid").val(data.email.id);
          $("#enderecoDeEmail").val(data.email.enderecoDeEmail);
          $("#enderecoid").val(data.endereco.id);
      }
	});
	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/estabelecimentos/pegarSenhaEmail/"+id,
	      success: function(data) {
	          $("#senhaDoEmail").val(data);
	      }
		});

	$("#modal-estabelecimento").modal("show");
});

$('.btn-excluir-estabelecimento').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});

