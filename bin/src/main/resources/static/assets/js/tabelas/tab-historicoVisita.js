$(document).ready(function() {
	var tabela = $('#tabela-visita-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
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
					data : 'nome',
					name : 'Nome',
				},
				{
					data : 'empresa',
					name : 'Empresa',
				},				
				{ 
					data : 'servico.nome',
					name : 'Serviço',	
					
				},
				{
					data : 'inicioServico',
					name : 'Entrada',
					width: "7%",
					render: function converterData(data, type, row){
						
						return row.inicioServico;
					}
				},
				{
					data : 'fimServico',
					name: 'Saída',
					width: "7%",
					render: function converterData(data, type, row){
					
						return row.fimServico;
					}
				},
				
				{
					data : 'usuario.servidor.nome',
					name: 'Servidor',					
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
						   '				<a class="dropdown-item" href="/visitas/visualizar/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-eye"></i> Visualizar</a>'+
						   '				<a class="dropdown-item" href="/admin/visitas/editar/'+ full.id +'" data-titulo="Editar Visita" id="'+ full.id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>'+
						   '				<a class="dropdown-item" href="/admin/report/visita/'+ full.id +'" target="_blank" data-titulo="Imprimir Visita" id="'+ full.id +'" th:inline="text" ><i class="fa fa-print"></i> Imprimir</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 8,
				"visible" : false
			},
			{
				"targets" : 9,
				"orderable" : false
			
			} ],
	});
	
});
