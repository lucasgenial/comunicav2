$(document).ready(function() {
	var tabela = $('#tabela-historicoEfetivoExterno').DataTable({
	   
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {url : '/assets/traducao/pt_BR.json'},
		"ajax": '/efetivoExterno/buscar/historico/'+$(".idDaMesa").attr('id'),
		"order" : [ [ 0, "desc" ] ],
		"columns": [
			 
				{					
					data :'id',
					name: 'Id',
					searchable: true,
				},
				{					
					data :'mesa.nome',
					name: 'Mesa',
					searchable: true,
				},
				{
				data : 'comecoPlantao',
				name: 'Início plantão',
				render: function converterData(data, type, row){

					var date = moment(row.comecoPlantao).format('DD/MM/YYYY HH:mm:ss');
												
						options = {
						  year: 'numeric', month: 'numeric', day: 'numeric',
						  hour: 'numeric', minute: 'numeric', second: 'numeric',
						  hour12: false,
						};
						
						return date;
					}
				},	
				{
					data : 'terminoPlantao',
					name: 'Fim plantão',
				
					render: function converterData(data, type, row){

						var date = moment(row.terminoPlantao).format('DD/MM/YYYY HH:mm:ss');
													
							options = {
							  year: 'numeric', month: 'numeric', day: 'numeric',
							  hour: 'numeric', minute: 'numeric', second: 'numeric',
							  hour12: false,
							};
							
							return date;
						}
					},	
					{					
						data :'modalidade.nome',
						name: 'Modalidade',
						searchable: true,
					},
					{					
						data :'prefixo',
						name: 'Prefixo',
						searchable: true,
					},
					{					
						data :'unidade.nome',
						name: 'Unidade',
						searchable: true,
					},
					{	
					    name: 'Abrangência',
					    searchable: false,
			            orderable: false,
			            width: "20%",
			            render: function adicionaBotaoAcao(data, type, full, meta){
			        	   return full.abrangencia;
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
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+
						   					editar(full.ativo, full.id)+
						   '				<a class="dropdown-item" href="/admin/cadastros/admin/gestaoefetivo/policiamentos/visualizar/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-eye"></i> Visualizar</a>'+
						   '				<a class="dropdown-item" href="/admin/cadastros/report/efetivoExterno/'+ full.id +'" id="'+ full.id +'"><i class="fas fa-print"></i> Imprimir</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'
			   
						   return botaoAcao;
		            }
		        }
			],
			
			"columnDefs" : [ {
			"targets" : 8,
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
		return '<a class="dropdown-item" href="/admin/gestaoefetivo/edita-efetivo-externo/'+ id +'" data-titulo="Editar Cidade" id="'+ id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>';
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
