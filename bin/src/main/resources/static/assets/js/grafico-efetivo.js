function geradorDeGraficosDashboard(id, classificacao){
	$('#tbl-efetivos').DataTable({
	    ajax: {
	        url: "/admin/efetivos/externos/ativo/estabelecimento/cidade/" + id,
	        dataType: "json",
	        cache: false,
	        contentType: "application/json; charset=utf-8",
	        dataSrc: ''
	    },
	    columns: [
	      { data: "nomeCidade" }, 
	      { data: "modalidades.[1].qtd" },
	      { data: "modalidades.[2].qtd" },
	      { data: "modalidades.[3].qtd" },
	      { data: "modalidades.[4].qtd" },
	      { data: "modalidades.[5].qtd" },
	      { data: "modalidades.[6].qtd" },
	    ]
	});
	
//	if(classificacao == 0){
//		
//		
//		console.log(classificacao);
//				
//		$.getJSON("/admin/efetivos/externos/ativo/estabelecimento/cidade/" + id, function(dados) {
//			
//			var tabela = document.getElementById('tbl-efetivos');
//			
//			dados.forEach(function (obj) {
//			    var tr = document.createElement('tr');
//			    
//			    Object.keys(obj).forEach(function (chave) {
//			        var td = document.createElement('td');
//			        td.innerHTML = obj[chave];
//			        tr.appendChild(td);
//			    });
//			    
//			    tabela.appendChild(tr);
//			});
			
//			console.log(dados[0].nomeCidade);
			
//			var htmlCidade = "";
//
//			for (var i = 0; i < dados.length; i++) {
//				htmlCidade += '<div class="item-cidade"> '+
//									'<div class="x_panel">'+
//										'<div class="x_content">'+
//											'<div class="x_title center">'+ 
//												'<h4>'+ dados[i].nomeCidade + '</h4>'+
//												'<div class="clearfix">'+
//											'</div>'+
//										'</div>'+
//										'<div class="row"> ';
//				
//				var modalidades = dados[i].modalidades; 
//							
//				htmlModalidades = "";
//				
//				for(var j = 0; j < modalidades.length; j++){
//					htmlModalidades += '<div class="col-4 border item-modalidade" style="background: '+ modalidades[j].corModalidade +';">'+
//						'<h4 class="text-modalidade">'+ modalidades[j].nomeModalidade +'</h4> <hr>'+
//						'<h3 class="text-number">'+ modalidades[j].qtd +'</h3></div><div class="clearfix"></div>';
//				}
//				
//				htmlCidade += htmlModalidades + '</div></div></div></div>';		    
//			}
//			
//			$("#apresentaPorCidades").append(htmlCidade);
//			
//			//Configura o OWL Carousel
//			var painel1 = $('#apresentaPorCidades');
//			
//			painel1.owlCarousel({
//			    loop:true,
//			    margin:10,
//			    autoplay:true,
//			    dots: false,
//			    autoplayTimeout:10000,
//			    autoplayHoverPause:true,
//			    owl2row: true, // enable plugin
//		        owl2rowTarget: 'item-cidade',    // class for items in carousel div
//		        owl2rowContainer: 'owl2row-item', // class for items container
//		        owl2rowDirection: 'utd', // ltr : directions
//			    responsive : {
//				    0 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 480
//				    576 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    768 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    992 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    1200 : {
//				    	items:2,
//				    }
//			    }
//			});
//		});
//	}else if(classificacao == 1){
//		$.getJSON("/admin/efetivos/externos/ativo/estabelecimento/unidade/" + id, function(dados) {
//			console.log(classificacao);
//			
//			console.log(dados[0].nomeUnidade);
//			
//			var htmlUnidade = "";
//	
//			for (var i = 0; i < dados.length; i++) {
//				htmlUnidade += '<div class="item-unidade"> '+
//					'<div class="x_panel"> <div class="x_content"> <div class="x_title center"> <h4>'+ dados[i].nomeUnidade + '</h4>'+
//					'<div class="clearfix"></div> </div> <div class="row"> ';
//				
//				var modalidades = dados[i].modalidades; 
//							
//				htmlModalidades = "";
//				
//				for(var j = 0; j < modalidades.length; j++){
//					htmlModalidades += '<div class="col-4 border item-modalidade" style="background: '+ modalidades[j].corModalidade +';">'+
//						'<h4 class="text-modalidade">'+ modalidades[j].nomeModalidade +'</h4> <hr>'+
//						'<h3 class="text-number">'+ modalidades[j].qtd +'</h3></div><div class="clearfix"></div>';
//				}
//				
//				htmlUnidade += htmlModalidades + '</div></div></div></div>';		    
//			}
//			
//			$("#apresentaPorUnidade").append(htmlUnidade);
//			
//			//Configura o OWL Carousel
//			var painel2 = $('#apresentaPorUnidade');
//			
//			painel2.owlCarousel({
//			    loop:true,
//			    margin:10,
//			    autoplay:true,
//			    dots: false,
//			    autoplayTimeout:10000,
//			    autoplayHoverPause:true,
//			    owl2row: true, // enable plugin
//		        owl2rowTarget: 'item-unidade',    // class for items in carousel div
//		        owl2rowContainer: 'owl2row-item', // class for items container
//		        owl2rowDirection: 'utd', // ltr : directions
//			    responsive : {
//				    0 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 480
//				    576 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    768 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    992 : {
//				    	items:2,
//				    },
//				    // ponto de interrupção de 768
//				    1200 : {
//				    	items:2,
//				    }
//			    }
//			});
//		});
//	}
}