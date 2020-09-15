function gerarTabela(idCidade, idMesa){
$(document).ready(function() {
	var tabela = $('#tabela-historicoEfetivoExterno').DataTable({
	   
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"processing": true,
		"language" : {url : '/assets/traducao/pt_BR.json'},
		"ajax": '/policiamento/buscar/historico/ativo/'+idMesa+"/"+idCidade,
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{		
					width: "7%",
					data :'id',
					name: 'Id',
					searchable: true,

				},
				{					
					data :'mesa.nome',
					name: 'Mesa',
					render: function converter(data, type, row){
						return row.mesa;
					},
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
					className: "visualizador",
				
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
						render: function converter(data, type, row){
							return row.modalidade;
						},
						searchable: true,
					},
					{					
						data :'prefixo',
						name: 'Prefixo',
						searchable: true,
					},
					{					
						data :'unidade.nome',
						name: 'Unidade',render: function converter(data, type, row){
							return row.unidade;
						},
						searchable: true,
					},
					{	
						width: "20%",
					    name: 'Abrangência',
					    searchable: false,
			            orderable: false,
			            name: 'Unidade',render: function converter(data, type, row){
							return row.abrangencia;
						},
			           
					}/*,
				
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
		        }*/
			],
			
			"columnDefs" : [ {
			
			className: "visualizador",
			"targets" : 7,
			"orderable" : false
			} ],
			"createdRow": function(row, data, dataIndex){
               
                    $(row).addClass('visualizador');
                
           }			
           
		
		
	});

	
	$('#tabela-historicoEfetivoExterno tbody').on('click', 'tr', function () {
	    var data = tabela.row( this ).data();


		$.ajax({
		    url: "/admin/policiamento/buscar/" + data.id,
		    async: false
		}).done(function (policiamento) {
	    
			console.log(policiamento	)
			$("#unidadeNome").html(policiamento.unidade.nome);
			
			$("#modalidadeNome").html(policiamento.modalidade.nome);
			
			$("#prefixo").html(policiamento.prefixo);
			
			$("#comecoPlantao").html(policiamento.comecoPlantao);
			
			$("#terminoPlantao").html(policiamento.terminoPlantao);
						
			$("#cidadeNome").html(policiamento.cidade.nome);
			
			$("#localidadeNome").html(policiamento.modalidade.nome);
			
			$("#modalidadeNome").html(policiamento.localidade.nome);
			
			$("#bairroNome").html(policiamento.bairro.nome);
			
			$("#telefone").html(policiamento.telefone);
					
			let texto="";
			
			for(let i =0 ; i < policiamento.guarnicao.length; i++ ){
				 texto+=`<div th:each="_guarnicao : ${policiamento.guarnicao}" class="table-default">
					<div class="form-row text-center border-bottom border-dark">
						<div class="width-15-PorCento border-right border-dark  pt-1 pb-1">`+policiamento.guarnicao[i].funcao.nome+`</div>
						<div class="width-20-PorCento border-right border-dark pt-1 pb-1" >`+policiamento.guarnicao[i].servidor.hierarquia.nome+`</div>
						<div class="width-45-PorCento border-right border-dark pt-1 pb-1">`+policiamento.guarnicao[i].servidor.nome+`</div>
						<div class="width-20-PorCento pt-1 pb-1">`+policiamento.guarnicao[i].servidor.matricula+`</div>
					</div>												
				</div>`
			}
			$("#guarnicao").html(texto);
			
			$("#modal-visualizarServidorExternoAtivo").modal("show");
		
		});

	});
	
});

}

function editar(isAtivo, id){
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/edita/policiamento/'+ id +'" data-titulo="Editar Cidade" id="'+ id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>';
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

