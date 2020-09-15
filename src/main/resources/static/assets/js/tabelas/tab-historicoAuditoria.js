$(document).ready(function() {
	var tabela = $('#tabela-auditar-paginada').DataTable({				
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/auditoria/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data :  'id',
					name : 'ID',
					width: "7%",
				},
				{
					data : 'datahora',
					name : 'Data/Hora',
					render: function converterData(data, type, row){
						
						//Converte em data + hora
						var stringData = row.datahora;
						
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
					data : 'login',
					name : 'Usuario',
				},							
				{
					data : 'historico',
					name : 'Historico',	
				}
			],
			"columnDefs" : [ {
				"targets" : 0,
				"visible" : true
				
			},
			{
				"targets" : 3,
				"orderable" : false
			}],			
	});
	
	tabela.on('xhr', function(){
		var json = tabela.ajax.json();
	});
});