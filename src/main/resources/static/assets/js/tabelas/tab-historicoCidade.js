$(document).ready(function() {
	var tabela = $('#tabela-cidade-paginada').DataTable({
	   
		"fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
		      if (aData.ativo == true)
		      {
		        $('td', nRow).css('background-color','#fff');
		      } 
		      else
		      {
		        $('td', nRow).css('background-color','#f7d5d9');
		      }
		    },
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'},
		"ajax": '/cidades/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
			 
				{
					data : 'id',
					name: 'ID',
					width: "9%",
				},
			
				{ 
					data : 'nome',
					name: 'Nome',					
				},
				{
					data : 'uf.nome',
					name: 'Estado',
				},
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "10%",
		          render: function adicionaBotaoAcao(data, type, full, meta){
		            	
		            	var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
						   '	<div class="btn-group" role="group">'+
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+						   
						                     habilitarOuDesabilitar(full.ativo, full.id)+
						   '				<a class="dropdown-item" href="/admin/cidades/editar/'+ full.id +'" data-titulo="Editar Cidade"><i class="far fa-edit"></i> Editar</a>'+
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
				console.log(data.uf.nome);
                if(!data.ativo){
                    $(row).addClass('table-danger');
                }
                
           }  
	});
	
});


function habilitarOuDesabilitar(isAtivo, id){
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/cadastros/cidades/status/'+id+'" th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastros/cidades/status/'+id+'" th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}


