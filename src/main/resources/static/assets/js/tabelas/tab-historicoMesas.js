$(document).ready(function() {
	var tabela = $('#tabela-historicoMesas').DataTable({
	   
		"responsive": true,
		"processing": true,
		"serverSide" : true,
		"select" : true,
		"language" : {url : '/assets/traducao/pt_BR.json'},
		"ajax": '/mesas/buscar/historico',
		"order" : [ [ 1, "desc" ] ],
		"columns": [
			 
				{					
					data :'nome',
					name: 'Nome',
					searchable: true,
				},
				{
				data : 'inicioPlantao',
				name: 'Início plantão',
				render: function converterData(data, type, row){

					var date = moment(row.inicioPlantao).format('DD/MM/YYYY HH:mm');
												
						options = {
						  year: 'numeric', month: 'numeric', day: 'numeric',
						  hour: 'numeric', minute: 'numeric', second: 'numeric',
						  hour12: false,
						};
						
						return date;
					}
				},	
				{
					data : 'fimPlantao',
					name: 'Fim plantão',
					render: function converterData(data, type, row){

						var date = moment(row.fimPlantao).format('DD/MM/YYYY HH:mm');
													
							options = {
							  year: 'numeric', month: 'numeric', day: 'numeric',
							  hour: 'numeric', minute: 'numeric', second: 'numeric',
							  hour12: false,
							};
							
							return date;
						}
					},	
				
				
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "10%",
		          render: function adicionaBotaoAcao(data, type, full, meta){
		        	  		if(full.ativo == true){
			        	  		var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
							   '	<div class="btn-group" role="group">'+
							   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
							   '			Ação'+
							   '			</button>'+
							   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">' +
							   '                    <a class="dropdown-item idDaMesa" href="/admin/cadastros/gestaoefetivo/diariodemesa/visualizar/mesaCompleta/'+ full.id +'" id="'+ full.id +'"><i class="fas fa-eye"></i> Detalhar</a>' +
							   '                <div class="dropdown-item disabled">Internos</div>' +
							   '                    <a class="dropdown-item idDaMesa" href="/admin/cadastros/gestaoefetivo/diariodemesa/editarMesa/'+ full.id +'" id="'+ full.id +'"><i class="fas fa-eye"></i> Editar</a>' +
							   '                <div class="dropdown-item disabled">Externos</div>' +
							   '				    <a class="dropdown-item idDaMesa" href="/admin/historico/policiamentos/'+ full.id +'" id="'+ full.id +'"><i class="fas fa-users"></i> Editar</a>' +
							   '			</div>' +
							   '		</div>' +
							   '	</div>'
							  return botaoAcao;
		        	  		}
		        	  		if(full.ativo == false){
			        	  		var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
							   '	<div class="btn-group" role="group">'+
							   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
							   '			Ação'+
							   '			</button>'+
							   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">' +
							   '                    <a class="dropdown-item idDaMesa" href="/admin/cadastros/gestaoefetivo/diariodemesa/visualizar/mesaCompleta/'+ full.id +'" id="'+ full.id +'"><i class="fas fa-eye"></i> Detalhar</a>' +
							   '			</div>' +
							   '		</div>' +
							   '	</div>'
							  return botaoAcao;
		        	  		}
		        	  		
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


function editar(isAtivo, id){
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/cadastros/gestaoefetivo/diariodemesa/editarMesa/'+ id +'" data-titulo="Editar Cidade" id="'+ id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>';
	}else{
		return '';
	}

}

function motrarCorDohabilitarOuDesabilitar(ativo){
	if(ativo == true){
		return 'table-default';
	}else{
		return 'table-danger';
	}
}
