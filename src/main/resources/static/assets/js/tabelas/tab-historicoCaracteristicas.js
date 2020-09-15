$(document).ready(function() {
	var tabela = $('#tabela-caracteristicas').DataTable({
	   
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {url : '/assets/traducao/pt_BR.json'},
		"ajax": '/caracteristicas/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
			 
				{					
					data :'id',
					name: 'Id',
					searchable: true,
				},
				{					
					data :'nome',
					name: 'Nome',
					searchable: true,
				},		
				{
					data : 'ativo',
					name: 'Ativo',
				
					render: function converterStatus(data, type, full, meta){
							if(full.ativo){
								return "Ativa";
							}else{
								return "Inativa";
							}						
						}
					},	
							
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "9%",
		            render: function adicionaBotaoAcao(data, type, full, meta){
		            	
		            	var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
						   '	<div class="btn-group" role="group">'+
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+
						   '				<a class="dropdown-item dropdown-item-sm" href="/admin/edita/caracteristica/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-edit"></i> Editar</a>'+
						   '				<a class="dropdown-item dropdown-item-sm" href="/admin/cadastros/caracteristicas/status/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-edit"></i>'+habilitar(full.ativo)+'</a>'
						   '			</div>'+
						   '		</div>'+
						   '	</div>'
			   
						   return botaoAcao;
		            }
		        }
			],			
			"columnDefs" : [ {
			"targets" : 3,
			"orderable" : true
			} ],
			"createdRow": function(row, data, dataIndex){
                if(!data.ativo){
                    $(row).addClass('table-danger');
            }
        }	
           
	});

});

function habilitar(isAtivo){
	if(isAtivo){
		return " Desabilitar";
	}else{
		return " Habilitar";
	}

}