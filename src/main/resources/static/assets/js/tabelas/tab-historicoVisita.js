$(document).ready(function() {
var tabela = $('#tabela-visita-paginada').DataTable({
	"responsive": true,
	"serverSide" : true,
	"select" : true,
	"processing": true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	},
	"ajax": '/visitas/buscar/historico',
	"order" : [ [ 0, "desc" ] ],
	"columns": [
			{
				data : 'id',
				name : 'ID',
				width: "3%",
			},
			{
				data : 'empresa',
				name : 'Empresa',
			},				
			{ 
				data : 'servico.nome',
				name : 'Serviço',
				
				render: function converterData(data, type, row){
					return row.servico;
				}
				
			},
			{
				data : 'inicioServico',
				name : 'Entrada',
				width: "10%",
				
			 render: function converterData(data, type, row){
					
					//Converte em data + hora
					var stringData = row.inicioServico;
					
					var date = moment(stringData).format('DD/MM/YYYY HH:mm');
											
					options = {
					  year: 'numeric', month: 'numeric', day: 'numeric',
					  hour: 'numeric', minute: 'numeric', second: 'numeric',
					  hour12: false,
					};
					
					return date;
				}
			},
			{
				data : 'fimServico',
				name: 'Saída',
				width: "10%",
				
			 render: function converterData(data, type, row){
					
					//Converte em data + hora
					var stringData = row.fimServico;
					
					var date = moment(stringData).format('DD/MM/YYYY HH:mm');
											
					options = {
					  year: 'numeric', month: 'numeric', day: 'numeric',
					  hour: 'numeric', minute: 'numeric', second: 'numeric',
					  hour12: false,
					};
					
					return date;
				}
			},
			
			
			{
				data : 'historico',
				name : 'Historico',
				render: function toLimit(descricao, type, row){
					return descricao.substring(0,45);
				}
			},
			{
				data : 'estabelecimento.nome',
				name : 'Estabelecimento',
					render: function converterData(data, type, row){
					
					return row.estabelecimento;
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
					   '				<a class="dropdown-item" href="/admin/visualiza/visita/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-eye"></i> Visualizar</a>'+
					   '				<a class="dropdown-item" href="/admin/edita/visita/'+ full.id +'" data-titulo="Editar Visita" id="'+ full.id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>'+
//						   '				<a class="dropdown-item" href="/admin/visitas/excluir/'+ full.id +'" data-titulo="Excluir Visita" id="'+ full.id +'" th:inline="text" ><i class="far fa-delete"></i> Excluir</a>'+
					   '				<a class="dropdown-item" href="/admin/report/visita/'+ full.id +'" target="_blank" data-titulo="Imprimir Visita" id="'+ full.id +'" th:inline="text" ><i class="fa fa-print"></i> Imprimir</a>'+
					   '			</div>'+
					   '		</div>'+
					   '	</div>'

					   return botaoAcao;
	            }
	        }
		],
		"columnDefs" : [ {
			"targets" : 6,
			"visible" : false
		},
		{
			"targets" : 7,
			"orderable" : false
		
		} ],
});

var tabela = $('#ultimasVisitas').DataTable({
	"responsive": true,
	"serverSide" : true,
	"select" : true,
	"lengthMenu": [10],
	"processing": false,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	},
	"ajax": '/visitas/buscar/historico',
	"order" : [ [ 0, "desc" ] ],
	"columns": [
			
			{
				data : 'empresa',
				name : 'Empresa',
			},				
			{ 
				data : 'servico.nome',
				name : 'Serviço',
				render: function converterData(data, type, row){
					return row.servico;
				}
				
			},
			{
				data : 'inicioServico',
				name : 'Entrada',
				width: "11%",
				
			 render: function converterData(data, type, row){
					
					//Converte em data + hora
					var stringData = row.inicioServico;
					
					var date = moment(stringData).format('DD/MM/YYYY HH:mm');
											
					options = {
					  year: 'numeric', month: 'numeric', day: 'numeric',
					  hour: 'numeric', minute: 'numeric', second: 'numeric',
					  hour12: false,
					};
					
					return date;
				}
			},
			{
				data : 'fimServico',
				name: 'Saída',
				width: "11%",
				 render: function converterData(data, type, row){
						
						//Converte em data + hora
						var stringData = row.fimServico;
						
						var date = moment(stringData).format('DD/MM/YYYY HH:mm');
												
						options = {
						  year: 'numeric', month: 'numeric', day: 'numeric',
						  hour: 'numeric', minute: 'numeric', second: 'numeric',
						  hour12: false,
						};
						
						return date;
					}
			
			},
			
			{
				data : 'historico',
				name : 'Historico',
				render: function toLimit(descricao, type, row){
					return descricao.substring(0,45);
				}
			},
		],
	});
	
});

