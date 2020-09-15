$(document).ready(function() {
	var tabela = $('#tabela-usuario-paginada').DataTable({				
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/usuarios/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
				},
				{
					data : 'login',
					name : 'Login',
				},
				{			
					data : 'servidor.nome',
		            name : 'Nome',
		            render: function dadosUsuarios(data, type, full, meta){   
		            	if(full.servidor === null){
		            		return '<div>Sem dados de Servidor</div>';
		            	}else if(full.servidor !== null){
		            		return '<div>' + full.servidor.nome + '</div>';
		            	}else{
		            		return '';
		            	}            	
		            	
		            }
		        },				
				{
					data : 'grupo.nome',
					name : 'GH',
				},	
				{
					data : 'servidor.estabelecimento.nome',
					name : 'estabelecimento',
				},
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "7%",
		            render: function teste(data, type, full, meta){		            	

		            	var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
						   '	<div class="btn-group" role="group">'+
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+				
					   	   '				<a class="dropdown-item" href="javascript:editarGrupo('+ full.id +')" data-titulo="Editar Usuário" id="'+ full.id +'" inline="text"><i class="far fa-edit"></i> Editar Grupo</a>'+	
						   					habilitarOuDesabilitar(full.ativo, full.id)+
						   					excluirUsuario(full.id)+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 4,
				"visible" : false
			}, 
			{
				"targets" : 5,
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
		return '<a class="dropdown-item" href="/admin/usuarios/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
		
	}else{		
		return '<a class="dropdown-item" href="javascript:alterarGrupo(' + id + ')"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Habilitar</a>';
	}
};

function editarGrupo(id){	
	
	
	$('#formUsuarioGrupo').attr("action","/admin/cadastro/grupos/alterarGrupo/" + id );
	$("#modal-usuario-grupo").modal("show");
	
}

function alterarGrupo(id){
	
	$('#formUsuarioGrupo').attr("action","/admin/cadastro/usuarios/editarGrupo/" + id );
	$("#modal-usuario-grupo").modal("show");
}

function excluirUsuario(id){
	var user;
	
	$.ajax({	
        type : "GET",
        url : "/admin/cadastros/usuario/buscar/logado",
        async: false,
        success: function(data) {          	
      	  user = data;        	
     }
	});
	
	if(user.grupo.nome === 'ADMINISTRADOR'){
		return '<a class="dropdown-item" href="/admin/usuarios/excluir/'+ id +'" inline="text" ><i class="far fa-trash-alt"></i> Excluir</a>';
	}else{
		return '';
	}
}
