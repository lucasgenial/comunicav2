//    $('.btn-excluir-instituicao').on("click", function(event){
//    	event.preventDefault();
//    	var href = $(this).attr('href');
//    	$("#delRef").attr("href",href);
//    	$("#modal-excluir").modal();
//    });
//});

$('.btn-estabelecimento-instituicao-incluir').on("click", function(){
	var titulo = $('.btn-estabelecimento-instituicao-incluir').data("titulo");
	$('#modal-estabelecimento-instituicao').modal().find('.modal-title').text(titulo)
	//	$('.formEstabelecimentoInstituicao').attr("action","@{~/admin/cadastros/estabelecimento/instituicoes/adicionar/{id}(id=${estabelecimento.id})}");
	//    $.ajax({
	//	      type : "GET",
	//	      url : "/admin/cadastros/instituicoes/listarTodas",
	//	      success: function(data) {
	//            var options = $('#instituicoesList');
	//            $.each(data, function (key, value) {
	//         	   		console.log(value);
	//                      $('<option>').val(value.id).text(value.nome).appendTo(options);
	//           });
	//	      }
	//    });
	$('#btn-instituicoesAdicionar').text("Adicionar");
	$("#modal-estabelecimento-instituicao").modal("show");
});