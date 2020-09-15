$('#unidadeFederativa').on("change", function(e){
	document.getElementById("cidade").options.length = 1;
    $.ajax({
	      type : "GET",
	      url : "admin/cadastro/estados/cidades/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("cidade");
	    		  select.add(option);
	    	  }	
	      }
    });
});

$('#ufNaturalidade').on("change", function(e){
	document.getElementById("cidadeNaturalidade").options.length = 1;
    $.ajax({
	      type : "GET",
	      url : "admin/cadastro/estados/cidades/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("cidadeNaturalidade");
	    		  select.add(option);
	    	  }	
	      }
    });
});