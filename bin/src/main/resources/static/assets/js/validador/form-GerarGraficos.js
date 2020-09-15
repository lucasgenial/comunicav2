/**
 * Arquivo de configuração da VALIDAÇÃO via Javascript do formulário de Geração de Gráficos
 * ATENÇÃO ESTE JS GERENCIA A PÁGINA DE GERAÇÃO DE GRÁFICOS
 * 
 */

document.addEventListener('DOMContentLoaded', function(e) {
	
	today = new Date();
<<<<<<< HEAD
		
	$('#categoria').on("change", function(e){
		document.getElementById("tipificacao").options.length = 0;
=======
	
	$('[name="dataInicio"]').datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		locale:  'pt-br',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		date: today,
    }).on('changeDate', function(e) {
    	
        // Revalidate the date field
        fv.revalidateField('dataOcorrencia');
    });
	
	$('[name="dataFim"]').datetimepicker({
		format: 'DD/MM/YYYY',
		locale:  'pt-br',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		date: today,
    }).on('changeDate', function(e) {
    	
        // Revalidate the date field
        fv.revalidateField('dataOcorrencia');
    });
	
	$('#categoria').on("change", function(e){
		document.getElementById("tipificacao").options.length = 1;
>>>>>>> Mauricio
		var id = this.value
				
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/categorias/tipificacoes/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("tipificacao");
<<<<<<< HEAD
		    		  
=======
>>>>>>> Mauricio
		    		  select.add(option);
		    	  }
		      },
		      fail: function(){
		          alert('Falha ao Buscar as Tipificações');
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
<<<<<<< HEAD
        fv.revalidateField('tipificacao');
    });
	
	$('#estabelecimentos').on("change", function(e){
		document.getElementById("listaCidades").options.length = 0;
=======
        fv.revalidateField('localidade');
    });
			
	$('#cidade').on("change", function(e){
		document.getElementById("localidade").options.length = 1;
		document.getElementById("bairro").options.length = 1;
		document.getElementById("estabelecimento").options.length = 0;
>>>>>>> Mauricio
		var id = this.value
				
		$.ajax({
		      type : "GET",
<<<<<<< HEAD
		      url : "admin/estabelecimento/cidades/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("listaCidades");
		    		  
=======
		      url : "admin/cadastros/cidades/localidades/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("localidade");
>>>>>>> Mauricio
		    		  select.add(option);
		    	  }
		      },
		      fail: function(){
<<<<<<< HEAD
		          alert('Falha ao Buscar as Tipificações');
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('tipificacao');
    });
	
	$('#listaCidades').on("change", function(e){
		document.getElementById("listaLocalidades").options.length = 0;
		document.getElementById("listaBairros").options.length = 0;
		
		var cidade = [];
		
		$("#listaCidades :selected").each(function(){
			cidade.push(this.value);
	    });
		
		
		if(cidade.length > 1){
		    $('.listaLocalidades').prop('hidden', true);
		    $('.listaBairros').prop('hidden', true);
		    
		   
		}else{
			$('.listaLocalidades').prop('hidden', false);
		    $('.listaBairros').prop('hidden', false);
		    
		    $.ajax({
			      type : "GET",
			      url : "admin/cadastros/cidades/localidades/estabelecimento?cidade=" + cidade,
			      success: function(data) {
			    	  for(var i=0;i<data.length;i++){
			    		  var option = new Option(data[i].nome, data[i].id);
			    		  var select = document.getElementById("listaLocalidades");
			    		  select.add(option);
			    	  }
			      },
			      fail: function(){
			          alert('Falha ao Buscar as Localidades/Distritos');
			      }
		    });
		}
		
		
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('listaLocalidades');
    });

	$('#listaLocalidades').on("change", function(e){
		document.getElementById("listaBairros").options.length = 0;
=======
		          alert('Falha ao Buscar as Localidades/Distritos');
		      }
	    });
		
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/cidades/estabelecimento/" + id,
		      success: function(data1) {
		    	  var option = new Option(data1.nome, data1.id, true, true);
		    	  var select = document.getElementById("estabelecimento");
		    	  select.add(option);
		      },
		      fail: function(){
		    	  alert('Falha ao Buscar o Estabelecimento');
		      }
		});
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('localidade');
    });

	$('#localidade').on("change", function(e){
		document.getElementById("bairro").options.length = 1;
>>>>>>> Mauricio
	    $.ajax({
		      type : "GET",
		      url : "admin/cadastros/localidades/bairros/" + this.value,
		      success: function(data) {	   
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
<<<<<<< HEAD
		    		  var select = document.getElementById("listaBairros");
=======
		    		  var select = document.getElementById("bairro");
>>>>>>> Mauricio
		    		  select.add(option);
		    	  }	
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
<<<<<<< HEAD
        fv.revalidateField('listaBairros');
=======
        fv.revalidateField('bairro');
>>>>>>> Mauricio
    });
		
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});