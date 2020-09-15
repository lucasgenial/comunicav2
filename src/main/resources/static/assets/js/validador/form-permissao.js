$('#grupo').on("change", function(){
	let id = this.value;	
	
	//let table = $('#products-table').DataTable();
	 
	//let rows = table.clear();
	$("#products-table td").remove();
	$("#id").val(id);
	
	$.ajax({
    type : "GET",
    url : "/admin/buscar/permissoes/listarTodas",
    success: function(permissoes) {
    	$.ajax({
    	    type : "GET",
    	    url : "/admin/buscar/grupo/listarOsIdsDasPermissoes/"+id,
    	    success: function(permissoesDoGrupo) {
    	    	
    	    	for(var i=0;i<permissoes.length;i++){
    	    		let linha;
    	    		let readonly;
	    	   		
    	    		if(permissoes[i].ativo){
    	    			linha = '<tr>';
    	    			readonly='';
	    	   		 }else{
	    	   			 linha = '<tr class="table-danger">'
	    	   			 readonly = 'disabled';
 	    	   		 }
    	   		 
    	  		  var newRow = $(linha);
    	  		  
    	  		    var cols = "";
    	  		        	  		    
    	  		    if(permissoesDoGrupo.includes(permissoes[i].id)){
    	  			    cols += '<td style="width: 10%;" ><input '+readonly+' type="checkbox" checked name="permissoes" value="'+permissoes[i].id+'" class="form-control"/></td>';
    	  		    }else{
    	  			    cols += '<td><input type="checkbox" '+readonly+' name="permissoes" value="'+permissoes[i].id+'" class="form-control"/></td>';
    	  		    }
    	  		    cols += '<td>'+permissoes[i].nome+'</td>';
    	  		    cols += '<td>'+permissoes[i].descricao+'</td>';
    	  		    cols += '<td><div class="btn-group" role="group" aria-label="Button group with nested dropdown">'+
					   '	<div class="btn-group" role="group">'+
					   '		<button id="btnGroupDrop1" type="button" class="btn btn-secondary  btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >'+
					   '			Ação'+
					   '			</button>'+
					   '			<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">'+
/*						   '				<a class="dropdown-item" href="/admin/permissoes/excluir/'+ full.id +'" data-titulo="Excluir Permissão"><i class="far fa-edit"></i> Excluir</a>'+
*/						   					habilitarOuDesabilitar(permissoes[i].ativo, permissoes[i].id)+
					   '			</div>'+
					   '		</div>'+
					   '	</div></td>';
    	  		    
    	  		    newRow.append(cols);
    	  		    
    	  		    $("#products-table").append(newRow);	
    	    	  }
    	  		  
    	    	

    	    	function habilitarOuDesabilitar(isAtivo, id){
    	    		
    	    		if(isAtivo == true){
    	    			
    	    			return '<a class="dropdown-item" href="/admin/permissoes/editar/'+ id +'" data-titulo="Editar Permissão"><i class="far fa-edit"></i> Editar</a>'+
    	    				   '<a class="dropdown-item" href="/admin/cadastro/permissoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-down" aria-hidden="true"></i> Desabilitar</a>';
    	    		}else{
    	    			return '<a class="dropdown-item" href="/admin/cadastro/permissoes/status/' + id + '"th:inline="text"><i class="far fa-thumbs-up" aria-hidden="true"></i> Habilitar</a>';
    	    		}
    	    	}
    	       }
    	    });
       }
    });
	
	
});
