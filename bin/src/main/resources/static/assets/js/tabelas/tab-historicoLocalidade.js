$(document).ready(function() {
	var tabela = $('#tabela-localidade-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/localidades/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
					width: "7%",
				},
				{
					data : 'nome',
					name : 'Localidade',
				},
				{ 
					data : 'cidade.nome',
					name : 'Cidade',
				},
				{ 
					data : 'cidade.estabelecimento.nome',
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
						   '				<a class="dropdown-item" href="/admin/localidades/editar/'+ full.id +'" data-titulo="Editar Localidade"><i class="far fa-edit"></i> Editar</a>'+
						   					habilitarOuDesabilitar(full.ativo, full.id)+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 3,
				"visible" : false
			},
			{
				"targets" : 4,
				"orderable" : false
			} ],
			"createdRow": function(row, data, dataIndex){	
                if(!data.ativo){
                    $(row).addClass('table-danger');
                }
           }        
	});
	
});


/**
 * 
 * INFORMAÇÃOES SOBRE LOCALIDADES
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à LOCALIDADES
 * 
 * **/
$('.btn-nova-localidade').on("click", function(){
	var titulo = $('.btn-nova-localidade').data("titulo");	
	$('#formlocalidade').modal().find('.modal-title').text(titulo)
	$('.form-localidade').attr("action","/admin/cadastrarLocalidade");
});



$('.btn-editar-localidade').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-localidade').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');

	$.ajax({
      type : "GET",
      url : "admin/cadastros/localidades/consultar/"+id,
      success: function(data) {
          $("input[name='id']").val(data.id);
          $("input[name='nome']").val(data.nome);
        
      }
	});
	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/localidades/consultarCidade/"+id,
	      success: function(data) {	    	  	    	 
			   $("#cidade").val(data.id);
	      }
	});
	$("#modal-localidade").modal("show");

});

$('.btn-excluir-localidade').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#modal-excluir #delRef").attr("href",href);
	$("#modal-excluir").modal();
});


function habilitarOuDesabilitar(isAtivo, id){
	
	if(isAtivo == true){
		return '<a class="dropdown-item" href="/admin/cadastro/localidades/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastro/localidades/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}
