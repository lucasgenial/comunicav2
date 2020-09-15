$(document).ready(function() {
	var tabela = $('#tabela-unidade-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"processing": true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/unidades/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
					width: "3%",
				},
				{
					data : 'nome',
					name : 'Unidade Operacional',
				},
				{
					data : 'estabelecimento.nome',
					name : 'Estabelecimento',
				},				
				{ 
					data : 'cidade.nome',
					name : 'Municipio',	
					
				},

								
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "7%",
		            render: function adicionaBotaoAcao(data, type, full, meta){
		            	
		            	var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
						   '	<div class="btn-group" role="group">'+
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+
						   '				<a class="dropdown-item" href="/admin/unidades/editar/'+ full.id +'" data-titulo="Editar Unidade" id="'+ full.id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 4,
				"orderable" : false
			} ],
			"createdRow": function(row, data, dataIndex){	
                if(!data.ativo){
                    $(row).addClass('table-danger');
                }
           }  
	});
	
});


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
