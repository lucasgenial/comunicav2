$(document).ready(function() {
	var tabela = $('#tabela-funcao-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/funcoes/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
					width: "7%",
				},
				{
					data : 'nome',
					name : 'Funcão',
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
						   '				<a class="dropdown-item" href="/admin/funcoes/editar/'+ full.id +'" data-titulo="Editar Função"><i class="far fa-edit"></i> Editar</a>'+
						   '				<a class="dropdown-item" href="/admin/funcoes/excluir/'+ full.id +'" data-titulo="Excluir Função"><i class="far fa-edit"></i> Excluir</a>'+
						   					habilitarOuDesabilitar(full.ativo, full.id)+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 2,
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
		return '<a class="dropdown-item" href="/admin/cadastro/funcoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastro/funcoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}

