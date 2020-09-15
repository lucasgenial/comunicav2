$(document).ready(function() {
	var tabela = $('#tabela-servidorExterno').DataTable({				
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/servidoresexternos/buscar/historico',
		"order" : [ [ 3, "asc" ] ],
		"columns": [
			
				{
					data :  'id',
					name : 'ID',
				},
				{
					data : 'matricula',
					name : 'Matrícula',
				},
				{
					data : 'hierarquia.nome',
					name : 'GH',
				},							
				{
					data : 'nome',
					name : 'Nome',	
				},
				{
					data : 'instituicao.nome',
					name : 'Instituição',	
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
				           '				<a class="dropdown-item" href="/admin/edita/servidorExterno/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-eye"></i> Editar</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 1,
				"visible" : true
				
			}, 
			{			
				"targets" : 5,
				"orderable" : false
			}],			
	});
	
	tabela.on('xhr', function(){
		var json = tabela.ajax.json();
	});
});
