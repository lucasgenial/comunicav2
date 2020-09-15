$(".btn-whatsapp").on("click", function(event){
	event.preventDefault();
	var id = this.id;
	
	$.ajax({
	      type : "GET",
	      url : "/admin/cadastros/ocorrencias/descricaoWhastapp/viaLink/"+id,
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
});