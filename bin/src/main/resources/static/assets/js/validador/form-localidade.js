/**
 * 
 * INFORMAÇÃOES SOBRE LOCALIDADES
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à LOCALIDADES
 * 
 * **/
$('.btn-nova-localidade').on("click", function(){
	var titulo = $('.btn-nova-localidade').data("titulo");
	
	$('#modal-localidade').modal().find('.modal-title').text(titulo)
	$('.form-localidade').attr("action","/admin/cadastrarLocalidade");
	$("#cidade").val("");
	$("input[name='id']").val('');
	$("input[name='nome']").val('');
	$('#btn-salvar-localidade').text("Salvar");
	$("#modal-localidade").modal("show");
});

$('.btn-editar-localidade').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-localidade').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
	
	$('#modal-localidade').modal().find('.modal-title').text(titulo);
	$('.form-localidade').attr("action", href);	
	$('#btn-salvar-localidade').text("Editar");

	$.ajax({
      type : "GET",
      url : "admin/cadastros/localidades/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
          
      }
	});
	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/localidades/consultarCidade/"+id,
	      success: function(data) {
	    	  console.log(data);	    	 
			   $("#cidade").val(data.id);
	      }
	});
	$("#modal-localidade").modal("show");
});

$('.btn-excluir-localidade').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});
