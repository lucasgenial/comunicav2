
		
	jQuery(function($){
		$(".cpf").mask("999.999.999-99");
	});
		
	jQuery(function($){	
		$(".inicioServico").mask("99/99/9999 99:99:99");
	});
	
	jQuery(function($){	
		$(".fimServico").mask("99/99/9999 99:99:99");
	});
		

$('.inicioServico').datetimepicker({
	language:  'pt-BR',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
    showMeridian: 0,
    minuteStep: 1,
    format: 'dd/mm/yyyy HH:ii:ss'
});

$('.fimServico').datetimepicker({
	language:  'pt-BR',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
    showMeridian: 0,
    minuteStep: 1,
    format: 'dd/mm/yyyy HH:ii:ss'
});
