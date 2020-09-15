$(document).ready(function() {
	var tabela = $('#tabela-estabelecimento-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/estabelecimentos/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
					width: "7%",
				},
				{
					data : 'nome',
					name : 'Nome',
				},
				{ 
					data : 'endereco.cidade.nome',
					name : 'Cidade',
				},
				{ 
					data : 'cidades.length',
					name : 'Qtd Cidades',
					width: "10%",
					render: function qtd(data, type, full, meta){    
		            	return qtdCidades(full);
		            }
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
						   '				<a class="dropdown-item" href="/admin/cadastros/estabelecimentos/editar/'+ full.id +'" data-titulo="Editar Estabelecimento" id="'+full.id+'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'
//
						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 4,
				"orderable" : false
			} ],       
	});
	
});

function qtdCidades(cidade){	
	if(cidade === null){
		return '<div>***********</div>';
	}else{
		return '<div>'+ cidade.cidades.length +'</div>';
	}
}

