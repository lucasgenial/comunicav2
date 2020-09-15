$(document).ready(function() {
	var tabela = $('#tabela-permissao-paginada').DataTable({
		"iDisplayLength": 100,
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/permissoes/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					 render: function adicionaBotaoAcao(data, type, full, meta){
			            	return '<input type="checkbox" class="form-control"/>';
			            }
				},
				{
					data : 'nome',
					name : 'Permissão',
				},
				{
					data : 'descricao',
					name : 'Descrição',
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
						   '				<a class="dropdown-item" href="/admin/permissoes/editar/'+ full.id +'" data-titulo="Editar Permissão"><i class="far fa-edit"></i> Editar</a>'+
/*						   '				<a class="dropdown-item" href="/admin/permissoes/excluir/'+ full.id +'" data-titulo="Excluir Permissão"><i class="far fa-edit"></i> Excluir</a>'+
*/						   					habilitarOuDesabilitar(full.ativo, full.id)+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 3,
				"orderable" : false
			} ],
			"createdRow": function(row, data, dataIndex){	
                if(!data.ativo){
                    $(row).addClass('table-danger');
                }
           }        
	});
	
});

function habilitarOuDesabilitar(isAtivo, id){
	
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/cadastro/permissoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastro/permissoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}

