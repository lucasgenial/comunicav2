$('#estabelecimento').on("change", function(e) {
	document.getElementById("cidade").options.length = 1;
	document.getElementById("instituicao").options.length = 1;
	
	var id = this.value;
	
	$.ajax({
		type : "GET",
		url : "admin/cadastros/estabelecimento/cidades/" + id,
		success : function(data) {			
			for (var i = 0; i < data.length; i++) {
				var option = new Option(data[i].nome, data[i].id);
				var select = document.getElementById("cidade");
				select.add(option);
			}
		}
	});
	$.ajax({
		type : "GET",
		url : "admin/cadastros/estabelecimentos/consultar/" + id,
		success : function(data) {
			for (var i = 0; i < data.instituicoes.length; i++) {
				var option = new Option(data.instituicoes[i].nome, data.instituicoes[i].id);
				var select = document.getElementById("instituicao");
				select.add(option);
			}
		}
	})
});

$('.btn-excluir-unidade').on("click", function(event) {
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href", href);
	$("#modal-excluir").modal();
});