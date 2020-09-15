$(document).ready(function() {
	var tabela = $('#tabela-telefone-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/telefones/buscar/historico',
		"order" : [ [ 0, "asc" ] ],
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
					data : 'numero',
					name : 'Número',
				},
				{ 
					data : 'ramal',
					name : 'Ramal',
					render: function semRamal(data, type, full, meta){
						//console.log(full.ramal);
						return semRamalFunction(full.ramal);
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
						   '				<a class="dropdown-item" href="/admin/telefones/editar/'+ full.id +'" data-titulo="Editar Telefone"><i class="far fa-edit"></i> Editar</a>'+
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
			}],      
			
	});
	
});

function semRamalFunction(r){	
	console.log(r.ramal);
	if(r === null){
		return '<div>------------------</div>';
	}else{
		return '<div>' + r + '</div>';
	}
}


