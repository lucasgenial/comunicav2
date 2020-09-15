$(document).ready(function() {
	var tabela = $('#tabela-servidor-paginada').DataTable({				
		"responsive": true,
		"processing": true,
		"serverSide" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/servidores/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
			
				{
					data :  'instituicao.nome',
					name : 'Instituicão',
					render: function tipoAcesso(data, type, full, meta){            		            	
						return full.instituicao;
		            }
				},
				{
					data : 'estabelecimento.nome',
					name : 'Estabelecimento',
					render: function tipoAcesso(data, type, full, meta){            		            	
						return full.estabelecimento;
		            }
				},
				{
					data : 'matricula',
					name : 'Matrícula',	
				},
				{
					data : 'hierarquia.nome',
					name : 'GH',
	
					render: function tipoAcesso(data, type, full, meta){            		            	
		            	return full.gh;
		            }
				},							
				{
					data : 'nome',
					name : 'Nome',
				},	
				{
					data : 'funcao.nome',
					name : 'Função',
					render: function tipoAcesso(data, type, full, meta){            		            	
		            	return full.funcao;
		            }
				},				
				{			
					data : 'usuario.grupo.nome',
		            name : 'Tipo de Acesso',
		            render: function tipoAcesso(data, type, full, meta){            		            	
		            	return full.tipoDeAcesso;
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
											visualizarArquivo(full)+
											editarUsuario(full)+
							   				habilitarOuDesabilitar(full.ativo, full.id)+
				           '				<a class="dropdown-item" href="visualizar/'+ full.id +'" id="'+ full.id +'"><i class="fa fa-eye"></i> Visualizar</a>'+
						   '				<a class="dropdown-item" href="/admin/report/servidor/'+ full.id +'" target="_blank" data-titulo="Imprimir Servidor" id="'+ full.id +'" th:inline="text" ><i class="fa fa-print"></i> Imprimir</a>'+
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
				"targets" : 7,
				"orderable" : false
			}],			
			"createdRow": function(row, data, dataIndex){	
                if(!data.ativo){
                    $(row).addClass('table-danger');
                }
           }           
	});
	
	tabela.on('xhr', function(){
		var json = tabela.ajax.json();
	});
//	
});

function habilitarOuDesabilitar(isAtivo, id){
	
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/cadastro/servidores/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastro/servidores/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}
function editarUsuario(_servidor){
	
	var user;
	
	$.ajax({
        type : "GET",
        url : "/admin/cadastros/usuario/buscar/logado",
        async: false,
        success: function(data) {          	
      	  user = data;    
      	  
     }
});	
	
if(user.servidor !== null){
	if((user.servidor.id != _servidor.id) && (_servidor.usuario == null)){		
		return '<a class="dropdown-item" href="/admin/servidores/editar/' + _servidor.id + '"inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Editar</a>';

		}else if(user.servidor.id !== _servidor.id && user.grupo.nome === 'ADMINISTRADOR'){		
			return '<a class="dropdown-item" href="/admin/servidores/editar/' + _servidor.id + '"inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Editar</a>';

		}else{
			return '';
		}	
	}
	return '';
}


function trocarNomeTipoAcesso(acesso){	
	
	if(acesso === null){
		return '<div>------------------</div>';
	}else{
		return '<div>' + acesso.grupo.nome + '</div>';
	}
}

function visualizarArquivo(_servidor){

	return '<a class="dropdown-item" href="/admin/historico/arquivosDT/'+ _servidor.id +' "data-titulo="Adcionar Solicitação" id="'+ _servidor.id +'" inline="text" ><i class="far fa-edit"></i> Arquivo</a>'
}


