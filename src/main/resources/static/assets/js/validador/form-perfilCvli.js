
function geradorDeGraficoCvli(idDaDiv, idDaTipificacao, tipoGrafico) {
	/*
	 * Inicio Configuração Gráfico
	 */
	let grafico1 = echarts.init(document.getElementById(idDaDiv));

	// Abre o LOADING
	grafico1.showLoading();   
	
	let link;
	
	
	if(idDaTipificacao == null){
		link = "/admin/cadastros/lista/dados/perfilCvli";
	}else{
		link ="/admin/cadastros/lista/dados/perfilCvli/especificos/" + idDaTipificacao;
	}
	
	$.getJSON(link, function(dados) {
		// FINALIZA O LADING
		grafico1.hideLoading();

		let dados2 = genData(dados);
		// RECEBE AS OPÇOES DO GRAFICO
		opcoes_grafico1 = {
			tooltip : {
				trigger : 'axis'
			},
			
			grid : {
				bottom : '7%'
			},
			dataZoom : [ {
				type : 'inside'
			} ],
			xAxis : {
				type : 'value',
				splitNumber : 5,
				axisLine : {
					show : true
				},
				axisTick : {
					show : true
				},
				axisLabel : {
					textStyle : {
						color : '#000'
					}
				},
			},
			toolbox: {
		        show: true,
		        showTitle: false, // hide the default text so they don't overlap each other
		        feature: {
		            saveAsImage: {
		                show: true,
		                title: 'Save As Image'
		            },
		         },
		        tooltip: { // same as option.tooltip
		            show: true,
		            formatter: function (param) {
		              return '<div>' + param.title + '</div>'; // user-defined DOM structure
		            },
		            backgroundColor: '#222',
		            textStyle: {
		                fontSize: 12,
		            },
		            extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);' // user-defined CSS styles
		        }

		},
			yAxis : {
				data : dados2.nomes,
				axisLabel : {
					inside : false,
					textStyle : {
						color : '#000',
						fontWeight: 'bold',
						fontSize: 15
					}
				},
				z : 25
				
			},
			 dataZoom: [
		            {
		                type: 'slider',
		                show: false,
		                start:0,
		                end: 100
		            }
		           
		        ],
			series : [ {
				type : 'bar',
				data : dados2.valores,
			} ]
		};
		
		// RECEBE AS OPÇOES DO GRAFICO
		grafico1.setOption(opcoes_grafico1);
		
	});
	
	
	
	function genData(data) {
		// Recebe os nomes das tipificações
		let nomes = data.nome;

		// Transforma em um json de Arrays de Objetos
		let valores = [];

		// Fica com os campos que foram selecionados
		let selected = {};
		
		for (let i = 0; i < nomes.length; i++) {
			valores.push({
				value : data.quantidade[i],
				itemStyle : {
					color : '#03a9d1'
				}
			});			
		}

		return {
			nomes : nomes,
			valores : valores
		}
	}
}




$('#categoria').multiselect();
$('#estabelecimentos').multiselect();

/* $('#cidadeDisponivel').multiselect({
	moveToRight: function(Multiselect, $options, event, silent, skipStack) {
		let $left_options = Multiselect.$left.find('> option:selected');
        Multiselect.$right.eq(0).append($left_options);     
	}
}); */

//$('#tipificacaoDisponivel').multiselect();
$('#tipificacaoDisponivel').multiselect({
	moveToRight: function(Multiselect, $options, event, silent, skipStack) {

	let $left_options = Multiselect.$left.find('> option:selected');
    Multiselect.$right.eq(0).append($left_options);
	}

});

/*
$('#todosTipos').click(function() {
    $('#tipificacao option').prop('selected', true);
});

 $('#selecionarCidades').click(function() {
    $('#tipificacaoDisponivel_to option').prop('selected', true);
}); */


/* $('#categoria').selectpicker();

$('#estabelecimentos').selectpicker();
$('#cidadeDisponivel').multiselect(); */


$('.categoriaDasTipificacoes').on("change", function(event){		 

if($('#categoria').val().length==0){
 let options = $('#tipificacaoDisponivel');
 options.find('option').remove();

}else{
$.ajax({
  type : "GET",
  url : "/admin/cadastros/tipificacoes/categorias/"+$('#categoria').val(),

  success: function(data) {
	  console.log(data);
	  let options = $('#tipificacaoDisponivel');
	  let optionsJaSelecionadas = $('#tipificacaoDisponivel_to');
	  optionsJaSelecionadas.find('option').remove();
		options.find('option').remove();

      $.each(data, function (key, value) {          	  
    		$('<option>').val(value.id).text(value.nome).appendTo(options);
      	});
  	}

 });
}
});

$('#estabelecimentos').on("change", function(event){		 

if($('#estabelecimentos').val().length==0){
 let options = $('#cidadeDisponivel');
 options.find('option').remove();

}else{
$.ajax({
  type : "GET",
  url : "/admin/cadastros/cidades/estabelecimentos/"+$('#estabelecimentos').val(),

  success: function(data) {
	  console.log(data);
	  let options = $('#cidadeDisponivel');
	  let optionsJaSelecionadas = $('#cidadeDisponivel_to');
	  optionsJaSelecionadas.find('option').remove();

      options.find('option').remove();
      $.each(data, function (key, value) {          	  
    		$('<option>').val(value.id).text(value.nome).appendTo(options);
      	});
  	}

 });
}
});

$('#anoInicial').on("change", function(event){		 

if($('#anoInicial').val() == ""){
 let options = $('#mesInicial');
 options.find('option').remove();
 $('<option>').val("").text('Escolha um ano inicial').appendTo(options);

 let anosFinais = $('#anoFinal');
	 anosFinais.find('option').remove();
 $('<option>').val("").text('Selecione um ano inicial').appendTo(anosFinais);

 let optionsJaSelecionadas = $('#mesFinal');
	 optionsJaSelecionadas.find('option').remove();
 $('<option>').val("").text('Selecione um ano inicial').appendTo(optionsJaSelecionadas);

}else{
$.ajax({
  type : "GET",
  url : "/admin/cadastros/ano/meses/"+$('#anoInicial').val(),

  success: function(data) {
	 
	  let options = $('#mesInicial');
	  options.find('option').remove();
      $('<option>').val("").text('Selecione um m�s inicial').appendTo(options);

	  let optionsAnoFinal = $('#anoFinal');
	  optionsAnoFinal.find('option').remove();
      $('<option>').val("").text('Selecione um m�s inicial').appendTo(optionsAnoFinal);

      let optionsJaSelecionadas = $('#mesFinal');
	  	optionsJaSelecionadas.find('option').remove();
      $('<option>').val("").text('Selecione um m�s inicial').appendTo(optionsJaSelecionadas);

      $.each(data, function (key, value) {          	  
    		$('<option>').val(value.valorInteiro).text(value.nome).appendTo(options);
      	});
  	}

 });

}
});

$('#mesInicial').on("change", function(event){		 
//alert("/admin/cadastros/ano/meses/"+$('#anoInicial').val()+'/'+$('#mesInicial').val());


if($('#mesInicial').val() == ""){
	 let options = $('#anoFinal');
     options.find('option').remove();
     $('<option>').val("").text('Escolha um m�s inicial').appendTo(options);

     let optionsJaSelecionadas = $('#mesFinal');
	  	 optionsJaSelecionadas.find('option').remove();
     $('<option>').val("").text('Selecione um mês inicial').appendTo(optionsJaSelecionadas);
 }else{
	 
	$.ajax({
      type : "GET",
      url : "/admin/cadastros/ano/anosValidosDeBusca/"+$('#anoInicial').val(),
	
      success: function(data) {
    	  let optionsJaSelecionadas = $('#mesFinal');
    	  optionsJaSelecionadas.find('option').remove();
          $('<option>').val("").text('Selecione um ano final').appendTo(optionsJaSelecionadas);

    	  let options = $('#anoFinal');
    	  options.find('option').remove();
          $('<option>').val("").text('Selecione um ano final').appendTo(options);

          $.each(data, function (key, value) {          	  
        		$('<option>').val(value).text(value).appendTo(options);
          	});
      	}
  
     });

 }
});

$('#anoFinal').on("change", function(event){		 

if($('#anoFinal').val() == ""){
     let optionsJaSelecionadas = $('#mesFinal');
	  	 optionsJaSelecionadas.find('option').remove();
     $('<option>').val("").text('Selecione um mês inicial').appendTo(optionsJaSelecionadas);

 }else{
	 
	$.ajax({
      type : "GET",
      url : "/admin/cadastros/ano/meses/"+$('#anoFinal').val()+'/'+$('#mesInicial').val(),
	
      success: function(data) {
    	   
    	  let options = $('#mesFinal');
    	  	options.find('option').remove();
          $('<option>').val("").text('Selecione um mês final').appendTo(options);

          
          $.each(data, function (key, value) {          	  
        		$('<option>').val(value.valorInteiro).text(value.nome).appendTo(options);
          	});
      	}
  
     });

 }
});

$('#cidadeDisponivel').on("change", function(e){
	document.getElementById("localidadeDisponivel").options.length = 1;
		if(this.value !== ""){
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/cidades/localidades/" + this.value,
		      success: function(data) {	   
		    	  console.log(data);
		          let options = $('#localidadeDisponivel');
		          //options.find('option').remove();
		          $.each(data, function (key, value) {
		       	   		console.log(value);
		                    $('<option>').val(value.id).text(value.nome).appendTo(options);
		       	         //options += '</option>';
		         });
		      }
		});
	}
});

	$('.datetimepicker').datetimepicker({
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
