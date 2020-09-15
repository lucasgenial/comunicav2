$(document).ready(function() {
	var tabela = $('#tabela-ocorrencia-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/ocorrencias/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'sic',
					name: 'SIC',
					width: "7%",
				},
				{
					data : 'dataOcorrencia',
					name: 'Data Ocorrência',
					width: "10%",
					render: function converterData(data, type, row){
						
						//Converte em data + hora
						var stringData = row.dataOcorrencia;
						
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
					data : 'tipificacao.nome',
					name: 'Tipificação'
				},
				{
					data : 'descricao',
					name: 'Descrição',
					render: function toLimit(descricao, type, row){
						return descricao.substring(0,45);
					}
				},
				{ 
					data : 'endereco.cidade.nome',
					name: 'Cidade'
				},
				{
					data : 'endereco.bairro.localidade.nome',
					name: 'Localidade'
				},
				{
					data : 'endereco.bairro.nome',
					name: 'Bairro'
				},
				{
					data : 'enviada',
					name: 'Enviada',
					render: function isEnviada(enviada, type, row){
						return enviada ? 'SIM':'NÃO';
					}
				},
				{
					data : 'estabelecimento.nome',
					name: 'Estabelecimento',
					width: "5%",
				},
				{
		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "8%",
		            render: function adicionaBotaoAcao(data, type, full, meta){
		            	
		            	var botaoAcao = '<div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
						   '	<div class="btn-group" role="group">'+
						   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
						   '			Ação'+
						   '			</button>'+
						   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+
						   '				<a class="dropdown-item" href="visualizar/'+ full.id +'" id="'+ full.id +'"><i class="glyphicon glyphicon-eye-open"></i> Visualizar</a>'+
						   '				<a class="dropdown-item" href="/admin/editar/ocorrencias/'+ full.id +'" data-titulo="Editar Ocorrência" id="'+ full.id +'" th:inline="text" ><i class="far fa-edit"></i> Editar</a>'+
						   '				<a class="dropdown-item" href="/admin/report/ocorrencia/'+ full.id +'" target="_blank" data-titulo="Imprimir Ocorrência" id="'+ full.id +'" th:inline="text" ><i class="fa fa-print"></i> Imprimir</a>'+

						   '				<a class="dropdown-item" href="javascript:enviarWhatsappocorrencia('+ full.id +')"><i class="fab fa-whatsapp"></i> WhatsApp</a>'+
						   '				<a class="dropdown-item" href="javascript:enviarEmailocorrencia('+ full.id +')" data-titulo="Enviar Ocorrência"><i class="far fa-envelope"></i> Email</a>'+
						   '			</div>'+
						   '		</div>'+
						   '	</div>'
						   return botaoAcao;
		            }
		        }
			],
			"columnDefs" : [ 
				{
					"targets" : 8 ,
					"visible" : false
				},
				{
					"targets" :  9 ,
					"orderable" : false
				}
			],
	});
	
	tabela.on( 'xhr', function () {
	    var json = tabela.ajax.json();
	    console.log(json);
	} );
	
});

function abrirmodal_alerta_paginainicial(){
	event.preventDefault();
	var href = $(this).attr('href');
	$("#delRef").attr("href",href);
	$("#modal-mensagem").modal();
}

/*função para marcar as ocorrencias selecionadas para email ou whatsapp*/

var ocorrenciasSelecionadasEmail= new Array();

$('.ocorrenciaSelecionada').on("change", function(e){
	
	var arrayTemporario = new Array();
	
	if(this.checked == true){
		ocorrenciasSelecionadasEmail[ocorrenciasSelecionadasEmail.length] = this.id;
	}else{
		for(var i=0; i < ocorrenciasSelecionadasEmail.length; i++){
			if(ocorrenciasSelecionadasEmail[i] !== this.id){
				arrayTemporario[arrayTemporario.length]=this.id;
			}
			
		}
		ocorrenciasSelecionadasEmail = arrayTemporario;
	}
	
	if(ocorrenciasSelecionadasEmail.length > 0){
		document.getElementById('btn-enviar-varias-por-email').disabled=false;
	}else{
		document.getElementById('btn-enviar-varias-por-email').disabled=true;
	}
});


var ocorrenciasSelecionadasWhatsapp = new Array();
    
$('.enviarVariasWhatsapp').on("change", function(e){

	var arrayTemporario = new Array();
	var contador = 0;
	
	if(this.checked == true){
		ocorrenciasSelecionadasWhatsapp[ocorrenciasSelecionadasWhatsapp.length] = this.id;
	}else{
		for(var i=0; i < ocorrenciasSelecionadasWhatsapp.length; i++){
			if(ocorrenciasSelecionadasWhatsapp[i] !== this.id){
				arrayTemporario[arrayTemporario.length]=this.id;
			}
			
		}
		ocorrenciasSelecionadasWhatsapp = arrayTemporario;
	}
	for(var i=0; i < ocorrenciasSelecionadasWhatsapp.length;i++){
			  	
		$.ajax({
			      type : "GET",
			      url : "/admin/cadastros/ocorrencias/descricaoWhastapp/"+ocorrenciasSelecionadasWhatsapp[i],
			      async: false,
			      success: function(data) {
			    	  contador = contador+data.length;
			   	   }
			  });	
		
	}
	if(contador > 16380){
    	alert("Limite de ocorrencias excedido, desmarque uma");
	}

	if( (ocorrenciasSelecionadasWhatsapp.length > 0) && contador < 16380){
		document.getElementById('btn-enviar-varias-por-whatsapp').disabled=false;
	}else{
		document.getElementById('btn-enviar-varias-por-whatsapp').disabled=true;
	}

});


$('.btn-enviar-varias-whatsapp').on("click", function(event){
	event.preventDefault();
	
	var titulo = $('.btn-enviar-varias-whatsapp').data("titulo");
	
	$('#modal-envio-varias-ocorrencias-whatsapp').modal().find('.modal-title').text(titulo);
	
	$('.form-enviarVariasPor-whatsapp').attr("action","/admin/cadastros/ocorrencias/enviar-varias-whatsapp");
	
	$("#modal-envio-varias-ocorrencias-whatsapp").modal("show");
	
});

$('.btn-enviar-varias-por-whatsapp').on("click", function(e){

var lista = document.getElementsByTagName("input");
	
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/ocorrencias/variasOcorrencias/"+ocorrenciasSelecionadasWhatsapp,
	      success: function(data) {
	    	  if(navigator.userAgent.indexOf("Firefox") != -1) 
		  	    {
		  	    	 var myWindow =  window.open('https://web.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
			    	  myWindow.moveBy(400, 100);
			    	  myWindow.resizeTo(800, 700);
		  	    } else{    	  
	    	  
	    			 var myWindow =  window.open('https://api.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
		    	     myWindow.moveTo(-1200, 100);
		    	     myWindow.resizeTo(800, 700);
	  	        }	  	   
	   	     }
		});

	
	
	 $('#modal-envio-varias-ocorrencias-whatsapp').modal('hide');
});


/**
 * 
 * Aciona barra de progresso
 * 
 * 
 * **/


function btnenviarEmailsSelecionados(){
	document.getElementById('progress').innerHTML = '<div class="pace"><div class="pace-progress" data-progress="50" data-progress-text="50%" style="-webkit-transform: translate3d(50%, 0px, 0px); -ms-transform: translate3d(50%, 0px, 0px); transform: translate3d(50%, 0px, 0px);"><div class="pace-progress-inner"></div></div><div class="pace-activity"></div></div>';
};

$('#btn-enviar-varias-por-email').on("click", function(event){
	document.getElementById('progresso').innerHTML = '<div class="pace"><div class="pace-progress" data-progress="50" data-progress-text="50%" style="-webkit-transform: translate3d(50%, 0px, 0px); -ms-transform: translate3d(50%, 0px, 0px); transform: translate3d(50%, 0px, 0px);"><div class="pace-progress-inner"></div></div><div class="pace-activity"></div></div>';
});


function enviarWhatsappocorrencia(id){
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/ocorrencias/descricaoWhastapp/"+id,
	      success: function(data) {
	    	  if(navigator.userAgent.indexOf("Firefox") != -1 ) 
		  	    {
		  	    	 var myWindow =  window.open('https://web.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
			    	  myWindow.moveBy(400, 100);
			    	  myWindow.resizeTo(800, 700);
		  	    } else{    	  
	    	  
	    			 var myWindow =  window.open('https://api.whatsapp.com/send?text='+data,"_blank",true,'width=800, height=600');
		    	     myWindow.moveTo(-1200, 100);
		    	     myWindow.resizeTo(800, 700);
	  	        }	  	   
	   	     }
		});
	 $('#modal-enviarWhatsapp').modal('hide');
};


function enviarEmailocorrencia(id){
	
	var id = this.id;
	
		$.ajax({
		      type : "GET",
		      url : "/admin/cadastros/ocorrencias/descricaoHtml/"+id,
		      success: function(data) {
		      $('#btn-enviarEmail').attr("name", id);
		      document.getElementById("descricaoDaOcorrenciaEmail").innerHTML = data;
		      $('.form-enviarEmail').attr("action", "/admin/cadastros/ocorrencias/enviarEmail/"+id);
		      }
			});
		
	$("#modal-enviarEmail").modal("show");

};

$('.listaDeEmails').on("change", function(e){	

	var emailsSelecionados = $("input[name='emailLista']:checked");
		
//		if(this.checked){
//			$('#'+$(this).attr('data-tr')).removeClass('table-default');
//			$('#'+$(this).attr('data-tr')).addClass('table-info');
//		}else{
//			$('#'+$(this).attr('data-tr')).removeClass('table-info');
//			$('#'+$(this).attr('data-tr')).addClass('table-default');
//		}

	if(emailsSelecionados.length == 0){
		document.getElementById('btn-enviarEmails_Selecionados').disabled=true;
	}else{
		document.getElementById('btn-enviarEmails_Selecionados').disabled=false;
	}
	
});
