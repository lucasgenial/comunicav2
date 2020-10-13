function graficoEfetivo(id){
	
	var imgPM = "/assets/imagens/pages/graficos/novasLogos/pm.fw.png";
	var imgPC = "/assets/imagens/pages/graficos/novasLogos/pc.fw.png";
	var imgDPT = "/assets/imagens/pages/graficos/novasLogos/dpt.fw.png";
	var imgBM = "/assets/imagens/pages/graficos/novasLogos/bm.fw.png";
	
	$.getJSON("/policiamento/buscar/dados/grafico/"+ id, function(dados) {

		for (var i=0; i < dados.length; i++) {
			var html = "";
			var ativo = "";
			
			if(i==1){
				var ativo = "active";
			}
						
			html += '<div class="col-12 carousel-item '+ ativo +' " >'+
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
				var imgLogo = "";
				var nomeColuna = "";
				
				if(instituicoes[k].nomeInstituicao == "PM"){
					imgLogo = imgPM;
					nomeColuna = "POLICIAIS";
				}else if(instituicoes[k].nomeInstituicao == "PC"){
					imgLogo = imgPC;
					nomeColuna = "POLICIAIS";
				}else if(instituicoes[k].nomeInstituicao == "DPT"){
					imgLogo = imgDPT;
					nomeColuna = "POLICIAIS";
				}else if(instituicoes[k].nomeInstituicao == "BM"){
					imgLogo = imgBM;
					nomeColuna = "BOMBEIROS";
				}
				
				html += 
					'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 col-xl-3">'+
					
						'<div class="ribbon-wrapper card h-100">'+
							'<div class="ribbon ribbon-info">EFETIVO - '+ instituicoes[k].nomeInstituicao +'</div>'+
							'<div class="row">'+
								'<div class="col-12">'+
			                        '<div class="card">'+
			                            '<div class="card-body">'+
			                                '<div class="d-flex">'+
												'<img src="'+ imgLogo +'" style="text-align: center; display: block; margin-left: auto; margin-right: auto; width: 50% ">'+
			                                '</div>'+
			                            '</div>'+
			                            '<div class="col-12 col-md-12 col-lg-12 col-xl-12">'+
											'<table class="table table-striped">'+
												'<thead>'+
			                                        '<tr>'+
			                                            '<th class="text-center" style="padding: 5px;">MODALIDADE</th>'+
			                                            '<th style="padding: 5px;">QUANTIDADE</th>'+
			                                            '<th style="padding: 5px;">'+ nomeColuna +'</th>'+
			                                        '</tr>'+
			                                    '</thead>'+
			                                    '<tbody id="dados-modalidade">';
					
													var modalidades = instituicoes[k].modalidades;
													
													for(j=0; j< modalidades.length; j++){
														var nomeModalidade = modalidades[j].nomeModalidade;
														html +=                         
								                        '<tr>'+
								                            '<td class="">'+ nomeModalidade.replace("VIATURA","") +'</td>'+
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
			$("#painel-itens").append(html);
		}
		
		$('.carousel').carousel();
	});
}