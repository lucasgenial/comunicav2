$(document).ready(function() {

	$('#tabela-variasOcorrenciaParaWhatsapp').DataTable({
		"columnDefs" : [ {
			"targets" :4,
			"orderable" : true
		} ],
		"order" : [ [ 0, "desc" ] ],
		"responsive" : true,
		"lengthMenu": [[-1], ["All"]],
		"paging":   false,
	    "info":     false,
	    "searching": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
});



$('.selecionado').on("change", function(e){	

	var ocorrenciasSelecionadas = $("input[name='listaDeOcorrencias']:checked");
	var contador = 0; 
	
		if(this.checked){
			$('#'+$(this).attr('data-tr')).removeClass('table-default');
			$('#'+$(this).attr('data-tr')).addClass('table-info');
		}else{
			$('#'+$(this).attr('data-tr')).removeClass('table-info');
			$('#'+$(this).attr('data-tr')).addClass('table-default');
		}
		
		var choices = [];
		var els = document.getElementsByName('listaDeOcorrencias');
		for (var i=0; i<els.length; i++){
		  if ( els[i].checked ) {
		    choices.push(els[i].value);
		  }
		}
		if(choices.length > 0){
			$.ajax({
				 type : "GET",
			     url : "/admin/cadastros/ocorrencias/enviarSelecionadas/whatsappEnviando/"+choices,
			     success: function(data) {
			    	 contador = data.length;
			     }
			  });	
		}
	if(ocorrenciasSelecionadas.length == 0){	
		
		$('#btn-enviarVariasWhatsapp').attr("style","pointer-events: none;");
		$('#btn-enviarVariasWhatsapp').addClass('disabled');
		document.getElementById('btn-enviarVariasWhatsapp').disabled=true;
		$('#tolltip-btn-enviarVariasWhatsapp').attr("data-original-title", "Marque ao menos uma ocorrência");
		
	}else if(contador > 16380){

		$('#btn-enviarVariasWhatsapp').attr("style","pointer-events: none;");
		$('#btn-enviarVariasWhatsapp').addClass('disabled');
		document.getElementById('btn-enviarVariasWhatsapp').disabled=true;
		$('#tolltip-btn-enviarVariasWhatsapp').attr("data-original-title", "Limite de ocorrencias excedido, desmarque uma");
		
		}else{
		$('#btn-enviarVariasWhatsapp').removeClass('disabled');
		$('#tolltip-btn-enviarVariasWhatsapp').attr("data-original-title","");
		$('#btn-enviarVariasWhatsapp').removeAttr("style");
		document.getElementById('btn-enviarVariasWhatsapp').disabled=false;
	}
	
});

$('#btn-enviarVariasWhatsapp').on("click", function(event){
	event.preventDefault();
	async: false;
	
	var choices = [];
	var els = document.getElementsByName('listaDeOcorrencias');
	for (var i=0; i<els.length; i++){
	  if ( els[i].checked ) {
	    choices.push(els[i].value);
	  }
	}
	
	$.ajax({
	     type : "GET",
	     url : "/admin/cadastros/ocorrencias/enviarSelecionadas/whatsappMensagemFormatada/"+choices,
	     success: function(data) {
		    	 $("#descricaoDaOcorrenciaWhatsapp").html(data);
	     }
    });

	
	$("#modal-VisualizaWhatsapp").modal("show");
	
});

function janelaSecundaria(){
	
	var choices = [];
	var els = document.getElementsByName('listaDeOcorrencias');
	for (var i=0; i<els.length; i++){
	  if ( els[i].checked ) {
	    choices.push(els[i].value);
	  }
	}
	if(choices.length > 0){
		$.ajax({
		     type : "GET",
		     url : "/admin/cadastros/ocorrencias/enviarSelecionadas/whatsappEnviando/"+choices,
		     success: function(data) {
		    	 window.open("https://web.whatsapp.com/send?text="+data, "janela1", "width=730%, height=600%, scrollbars=NO");
		     }
	  });
	}
	
	   
	$("#modal-VisualizaWhatsapp").modal("hide");
}
