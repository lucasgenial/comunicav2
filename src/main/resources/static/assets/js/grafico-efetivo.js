function graficoEfetivo(id){
	
	var imgPM = "/assets/imagens/pages/graficos/novasLogos/pm.fw.png";
	var imgPC = "/assets/imagens/pages/graficos/novasLogos/pc.fw.png";
	var imgDPT = "/assets/imagens/pages/graficos/novasLogos/dpt.fw.png";
	var imgBM = "/assets/imagens/pages/graficos/novasLogos/bm.fw.png";
	
	$.getJSON("/policiamento/buscar/dados/grafico/"+ id, function(dados) {

		for (var i=0; i < dados.length; i++) {
			var html = "";
			html += '<div class="col-12 item">'+
						'<div class="row">'+
							'<div class="col-12" style="text-align: center">'+
								'<div class="ribbon-wrapper card">'+
									'<h1>'+ dados[i].nomeCidade+'</h1>'+
								 '</div>'+
							'</div>'+
						'</div>'+
						'<div class="row" style="padding-bottom: 10px;">';
			
			var instituicoes = dados[i].instituicoes;
					
			for(var k=0; k< instituicoes.length; k++){
				html += 
					'<div class="col-sm-12 col-md-6 col-lg-6 col-xl-4">'+
						'<div class="ribbon-wrapper card h-100">'+
							'<div class="ribbon ribbon-info">EFETIVO - '+ instituicoes[k].nomeInstituicao +'</div>'+
							'<div class="row">'+
								'<div class="col-md-12">'+
			                        '<div class="card">'+
			                            '<div class="card-body">'+
			                                '<div class="d-flex">';
												var imgLogo = "";
												
												if(instituicoes[k].nomeInstituicao == "PM"){
														imgLogo = imgPM;
												}else if(instituicoes[k].nomeInstituicao == "PC"){
													imgLogo = imgPC;
												}else if(instituicoes[k].nomeInstituicao == "DPT"){
													imgLogo = imgDPT;
												}else if(instituicoes[k].nomeInstituicao == "BM"){
													imgLogo = imgBM;
												}
												
			                              		html +='<img src="'+ imgLogo +'" style="text-align: center; display: block; margin-left: auto; margin-right: auto; width: 50% ">'+
			                                '</div>'+
			                            '</div>'+
			                            '<div class="row">'+
											'<table class="table table-striped">'+
												'<thead>'+
			                                        '<tr>'+
			                                            '<th>TIPO</th>'+
			                                            '<th>QUANTIDADE</th>'+
			                                            '<th>SERVIDORES</th>'+
			                                        '</tr>'+
			                                    '</thead>'+
			                                    '<tbody id="dados-modalidade">';
					
					var modalidades = instituicoes[k].modalidades;
					
					for(j=0; j< modalidades.length; j++){
						
						html +=                         
                        '<tr>'+
                            '<td>'+ modalidades[j].nomeModalidade +'</td>'+
                            '<td class="text-center">'+ modalidades[j].qtdModalidade +'</td>'+
                            '<td class="text-center">'+ modalidades[j].qtdPoliciamento +'</td>'+
                        '</tr>';
					}
					
					html +=
												'</tbody>'+
											'</table>'+
										'</div>'+
			                    	'</div>'+
			                	'</div>'+
			             	'</div>'+
						'</div>'+
					'</div>';
			}
			html += '</div>'+'</div>';
			$("#painel-efetivo").append(html);
		}
		
		//Configura o OWL Carousel
		var painel = $('#painel-efetivo');
		
		painel.owlCarousel({
		    loop:true,
			items:1,
		    autoplay:true,
		    dots: false,
		    autoplayTimeout:3000,
		    autoplayHoverPause:true,
//		    owl2row: true, // enable plugin
	        owl2rowTarget: 'item',    // class for items in carousel div
//	        owl2rowContainer: 'owl2row-item', // class for items container
//	        owl2rowDirection: 'utd'
			responsive:{
			        0:{
			            items:1
			        },
			        600:{
			            items:1
			        },
			        1000:{
			            items:1
			        }
			}
		});	
		
		
	});
}