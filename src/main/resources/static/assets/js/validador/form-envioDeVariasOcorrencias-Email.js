$('.selecionado').on("change", function(e){	

	var ocorrenciasSelecionadas = $("input[name='listaDeOcorrencias']:checked");
	var emailsSelecionados = $("input[name='listaDeEmails']:checked");
		
		if(this.checked){
			$('#'+$(this).attr('data-tr')).removeClass('table-default');
			$('#'+$(this).attr('data-tr')).addClass('table-info');
		}else{
			$('#'+$(this).attr('data-tr')).removeClass('table-info');
			$('#'+$(this).attr('data-tr')).addClass('table-default');
		}

	if(ocorrenciasSelecionadas.length == 0){
		$('#btn-enviarVariasEmail').attr("style","pointer-events: none;");
		document.getElementById('btn-enviarVariasEmail').disabled=true;
		$('#tolltip-btn-email').attr("data-original-title", "Marque ao menos uma ocorrência");
				
	}else if(emailsSelecionados.length == 0){
		$('#btn-enviarVariasEmail').attr("style","pointer-events: none;");
		document.getElementById('btn-enviarVariasEmail').disabled=true;
		$('#tolltip-btn-email').attr("data-original-title","Marque ao menos um e-mail");
	}else{
		$('#tolltip-btn-email').attr("data-original-title","");
		$('#btn-enviarVariasEmail').removeAttr("style");
		document.getElementById('btn-enviarVariasEmail').disabled=false;
	}
	
});


// the object will track mouse and call over/out


