/**
 * Arquivo de configuração da VALIDAÇÃO via Javascript do formulário de Geração de Gráficos
 * ATENÇÃO ESTE JS GERENCIA A PÁGINA DE GERAÇÃO DE GRÁFICOS
 * 
 */

document.addEventListener('DOMContentLoaded', function(e) {
	
	today = new Date();
		
	$('#categoria').on("change", function(e){
		document.getElementById("tipificacao").options.length = 0;
		var id = this.value
				
		$.ajax({
		      type : "GET",
		      url : "admin/cadastros/categorias/tipificacoes/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("tipificacao");
		    		  
		    		  select.add(option);
		    	  }
		      },
		      fail: function(){
		          alert('Falha ao Buscar as Tipificações');
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('tipificacao');
    });
	
	$('#estabelecimentos').on("change", function(e){
		document.getElementById("listaCidades").options.length = 0;
		var id = this.value
				
		$.ajax({
		      type : "GET",
		      url : "admin/estabelecimento/cidades/" + id,
		      success: function(data) {
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("listaCidades");
		    		  
		    		  select.add(option);
		    	  }
		      },
		      fail: function(){
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
	    $.ajax({
		      type : "GET",
		      url : "admin/cadastros/localidades/bairros/" + this.value,
		      success: function(data) {	   
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("listaBairros");
		    		  select.add(option);
		    	  }	
		      }
	    });
	}).on('changeDate', function(e) {
        // Revalidate the date field
        fv.revalidateField('listaBairros');
    });
		
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});