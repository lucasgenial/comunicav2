
$(".mudaBotaoDePDF").on("mouseup", function(event){
	$("#190").removeAttr('hidden');
	if($("#efetivoInterno").hasClass("show") && $("#efetivoExterno").hasClass("show") && ($(this).attr('id')=='mostraEfetivoInterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_efetivoExterno/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}else if($("#efetivoInterno").hasClass("show") && $("#efetivoExterno").hasClass("show") && ($(this).attr('id')=='mostraEfetivoExterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_efetivoInterno/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}else if($("#efetivoInterno").hasClass("show") && (!$("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoInterno')){
		$(".botaoDeDownload").attr('href','/#');
		$("#divBotaoDeDownload").attr('hidden', 'hidden');
	}else if($("#efetivoInterno").hasClass("show") && (!$("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoExterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_completa/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}else if(!$("#efetivoInterno").hasClass("show") && ($("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoInterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_completa/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}else if(!$("#efetivoInterno").hasClass("show") && ($("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoExterno')){
		$(".botaoDeDownload").attr('href','/#');
		$("#divBotaoDeDownload").attr('hidden', 'hidden');
	}else if(!$("#efetivoInterno").hasClass("show") && (!$("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoInterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_efetivoInterno/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}else if(!$("#efetivoInterno").hasClass("show") && (!$("#efetivoExterno").hasClass("show")) && ($(this).attr('id')=='mostraEfetivoExterno')){
		$(".botaoDeDownload").attr('href','/admin/cadastro/report/mesa_efetivoExterno/'+$(".botaoDeDownload").attr('id'));
		$("#divBotaoDeDownload").removeAttr('hidden');
	}
});
