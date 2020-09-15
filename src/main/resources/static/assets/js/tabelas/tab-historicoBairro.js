$(document).ready(function() {

	var tabela = $('#tabela-bairro-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/bairros/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
					width: "7%",
				 render: function retorno(data, type, full, meta){
				    	
		            return full.id
		         },
				},
				{
					data : 'nome',
					name : 'Bairro',
					 render: function retorno(data, type, full, meta){
					    	
			            	return full.nome
			            },
				},
				{ 
					data : 'localidade',
					name : 'Localidade',
					 render: function retorno(data, type, full, meta){
					    	
			            	return full.localidade
			            },
				},
				{ 
					data : 'localidade.cidade.nome',
					name: 'Cidade',
				    render: function retorno(data, type, full, meta){
				    	
		            	return full.cidade
		            },
				},
				{ 
					data : 'localidade.cidade.estabelecimento.nome',
					name : 'Estabelecimento',
				    render: function retorno(data, type, full, meta){
		            	return full.estabelecimento;
		            },
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
						   '				<a class="dropdown-item" href="/admin/bairros/editar/'+ full.id +'" data-titulo="Editar Bairro"><i class="far fa-edit"></i> Editar</a>'+
						   					habilitarOuDesabilitar(full.ativo, full.id)+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'

						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ {
				"targets" : 2,
				"visible" : true
			},
			{
				"targets" : 2,
				"orderable" : true
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
		return '<a class="dropdown-item" href="/admin/cadastro/bairros/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
	}else{
		return '<a class="dropdown-item" href="/admin/cadastro/bairros/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
	}
}


/**
 * 
 * INFORMAÇÃOES SOBRE BAIRROS
 * Aqui encontra-se as ações dos botões e telas (modais) do sistema referentes à BAIRROS
 * 
 * **/
$('.btn-novo-bairro').on("click", function(){
	var titulo = $('.btn-novo-bairro').data("titulo");
	$('#formBairro').modal().find('.modal-title').text(titulo)
	$('.form-bairro').attr("action","/admin/cadastraBairro");

});

$('.btn-editar-bairro').on("click", function(event){
	event.preventDefault();
	var titulo = $('.btn-editar-bairro').data("titulo");
	var id = this.id;
	var href = "/admin/cadastros/"+ $(this).attr('href');
		
	$.ajax({
      type : "GET",
      url : "admin/cadastros/bairros/consultar/"+id,
      success: function(data) {
    	   
	       $("input[name='id']").val(data.id);
	       $("input[name='nome']").val(data.nome);	      
	       
      }
	});
	

	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/bairros/consultarcidade/"+id,
	      success: function(data) {		      
		      	$("#cidade").val(data.id);		      
		      }
	});

	$.ajax({
	      type : "GET",
	      url : "admin/cadastros/bairros/consultarlocalidade/"+id,
	      success: function(data) {	    	  
			   $("#localidade").val(data.id);
	      }
	});

	$.ajax({
    type : "GET",
    url : "admin/cadastros/bairros/consultarcidadeporestabelecimento/"+id,
    async: false,
    success: function(data) {
  	    
        var options = $('#cidade');
        options.find('option').remove();
        $.each(data, function (key, value) {
                   $('<option>').val(value.id).text(value.nome).appendTo(options);
      	         //options += '</option>';
        	});
    	}
	});

	$.ajax({
	    type : "GET",
	    url : "admin/cadastros/bairros/consultarlocalidadesporcidade/"+id,
        async: false,
	    success: function(data) {
	  	  
	        var options = $('#localidade');
	        options.find('option').remove();
	        $.each(data, function (key, value) {
	                   $('<option>').val(value.id).text(value.nome).appendTo(options);
	      	         //options += '</option>';
	        	});
	    	}
	});//function editarArquivo(id){
//	
//	var user;
//	
//	$.ajax({
//        type : "GET",
//        url : "/admin/cadastros/usuario/buscar/logado",
//        async: false,
//        success: function(data) {          	
//      	  user = data;    
//     }
//});

	$("#modal-bairro").modal("show");
});	


$('.btn-excluir-bairro').on("click", function(event){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-excluir").modal();
});

$('#cidade').on("change", function(e){
	document.getElementById("localidade").options.length = 1;
	if(this.value !== ""){
    $.ajax({
	      type : "GET",
	      url : "admin/cadastros/cidades/localidades/" + this.value,
	      success: function(data) {	   
	    	  
              var options = $('#localidade');
              //options.find('option').remove();
              $.each(data, function (key, value) {
           	   		
                        $('<option>').val(value.id).text(value.nome).appendTo(options);
           	         //options += '</option>';
             });
	      }
    });
	}
});
