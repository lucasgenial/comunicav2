function geradorDeGraficosDashboard(id) {
	/*
	 * Inicio Configuração Gráfico 3
	 */
	var grafico1 = echarts.init(document.getElementById('grf_1'));

	// Abre o LOADING
	grafico1.showLoading();

	$.getJSON("/admin/cadastros/lista/ocorrencias/abrangencia/" + id, function(dados) {
		// FINALIZA O LADING
		grafico1.hideLoading();

		var dados2 = genData(dados);
		
		// RECEBE AS OPÇOES DO GRAFICO
		opcoes_grafico1 = {
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				bottom : '10%'
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
				}
			},
			yAxis : {
				data : dados2.nomes,
				axisLabel : {
					inside : true,
					textStyle : {
						color : '#000',
						fontWeight: 'bold',
						fontSize: 14
					}
				},
				z : 25
			},
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
		var nomes = data.nome;

		// Transforma em um json de Arrays de Objetos
		var valores = [];

		// Fica com os campos que foram selecionados
		var selected = {};
		
		for (var i = 0; i <= nomes.length; i++) {
			valores.push({
				value : data.quantidade[i],
				itemStyle : {
					color : data.cores[i]
				}
			});
		}

		return {
			nomes : nomes,
			valores : valores
		}
	}

	/*
	 * Inicio Configurações Grafico Ocorrencias por Hora
	 */
	var grafico2 = echarts.init(document.getElementById('grf_2'));

	grafico2.showLoading();

	$.getJSON("/admin/cadastros/lista/ocorrencias/hora/" + id, function(dados) {

		grafico2.hideLoading();

		opcoes_grafico2 = {
			// Make gradient line here
			visualMap : {
				show : true,
				type : 'continuous',
				seriesIndex : 0,
				min : 0,
				max : 10
			},
			tooltip : {
				trigger : 'axis'
			},
			xAxis : {
				data : dados.nome
			},
			yAxis : {
				splitLine : {
					show : false
				}
			},
			grid : {
				bottom : '10%'
			},
			series : {
				type : 'line',
				showSymbol : true,
				data : dados.quantidade
			}
		};
		
		// RECEBE AS OPÇOES DO GRAFICO
		grafico2.setOption(opcoes_grafico2);

	});	
	
	$.getJSON("/admin/cadastros/lista/ocorrencias/" + id, function(dados1) {
		var nomes = dados1.nomes;
		console.log(dados1.nomes)
		
		var html = "";

		for (var i = 0; i < dados1.nome.length; i++) {
			var nome = dados1.nome[i];
			var qtd = Number(dados1.quantidade[i]);
			var perc = Number(dados1.percentual[i]);
						
			html += '<div class="item">'+
						'<div class="card">'+
							'<div class="card-body">'+
								'<h5 class="card-title">'+ nome +'</h5>'+
								'<div class="row">'+
									'<div class="d-flex no-block align-items-center">'+
										'<div class="col-12">'+
								   			'<div class="chart cart-pie-1" data-percent="'+ perc.toFixed(2) +'"> <span class="percent">'+ perc.toFixed(2) +'</span> </div>'+
								   		'</div>'+
										'<div class="col-12">'+
										    '<h1 class="text-success"><span class="counter">'+ qtd +'</span></h1>'+
									    '</div>'+
								    '</div>'+
								 '</div>'+
							 '</div>'+
		                  '</div>'+
		                '</div>';
			    
		}
		
		$("#apresentaPorClassificacao").append(html).before(function(){
				var EasyPieChart = function() {};
	
			    EasyPieChart.prototype.init = function() {
					$('.cart-pie-1').easyPieChart({
						easing: 'easeOutBounce',
						barColor : '#13dafe',
						lineWidth: 7,
						animate: 1000,
				        lineCap: 'square',
				        trackColor: '#e5e5e5',
						onStep: function(from, to, percent) {
							$(this.el).find('.percent').text(percent.toFixed(2));
						}
					});
			    };
			    
			    //init
			    $.EasyPieChart = new EasyPieChart, $.EasyPieChart.Constructor = EasyPieChart
			    $.EasyPieChart.init()
			}
		);
		
		//Configura o OWL Carousel
		var painel1 = $('#apresentaPorClassificacao');
		
		painel1.owlCarousel({
			items:6,
		    loop:true,
		    margin:30,
		    autoplay:true,
		    dots: false,
		    autoplayTimeout:3000,
		    autoplayHoverPause:true,
		    owl2row: true, // enable plugin
	        owl2rowTarget: 'item',    // class for items in carousel div
	        owl2rowContainer: 'owl2row-item', // class for items container
	        owl2rowDirection: 'utd', // ltr : directions
		    responsive : {
			    0 : {
			    	items:1,
			    },
			    // ponto de interrupção de 480
			    576 : {
			    	items:2,
			    },
			    // ponto de interrupção de 768
			    768 : {
			    	items:3,
			    },
			    // ponto de interrupção de 768
			    992 : {
			    	items:4,
			    },
			    // ponto de interrupção de 768
			    1200 : {
			    	items:5,
			    	owl2row: false,
			    }
		    }
		});
	});
	
	//Apaga painel 1
	
	
//	$.getJSON("/admin/cadastros/lista/ocorrencia/status/" + id, function(dados1) {
//		var coresStatus = [ '#00B23D', '#1E8DB2', '#FF9540', '#19A2FF' , '#89998C', '#cf9bcc'];
//		
//		var content = "";
//		
//		var nomes = dados1.nomes;
//		
//		for (var i = 0; i < dados1.nome.length; i++) {
//		
//			var nome = dados1.nome[i];
//			var qtd = dados1.quantidade[i];
//			
//			content += '<div class="item">'+
//			'<div class="card" style="background:'+ coresStatus[i] +'">'+
//				'<div class="card-body">'+
//					'<h5 class="card-title">'+ nome +'</h5>'+
//					'<div class="row">'+
//						'<div class="d-flex no-block align-items-center">'+
//							'<div class="col-12">'+
//							    '<h1 class="card-title">'+ qtd +'</h1>'+
//						    '</div>'+
//					    '</div>'+
//					 '</div>'+
//				 '</div>'+
//              '</div>'+
//            '</div>';
//
////			content += '<div class="item border" style="background:'+ coresStatus[i] +'"><h4 class="text-light">'+ nome +'</h4><hr><h3 class="text-light">' + qtd +'</h3></div>';
//
//		}
//		
//		$("#apresentacaoPorStatus").append(content);
//		
//		//Configura o OWL Carousel
//		var painel2 = $('#apresentacaoPorStatus');
//		
//		painel2.owlCarousel({
//		    loop:false,
//		    margin:10,
//		    autoplay:true,
//		    dots: false,
//		    autoplayTimeout:3000,
//		    autoplayHoverPause:true,
//		    owl2row: true, // enable plugin
//	        owl2rowTarget: 'item',    // class for items in carousel div
//	        owl2rowContainer: 'owl2row-item', // class for items container
//	        owl2rowDirection: 'utd', // ltr : directions
//		    responsive : {
//		    	0 : {
//			    	items:1,
//			    },
//			    // ponto de interrupção de 480
//			    576 : {
//			    	items:2,
//			    },
//			    // ponto de interrupção de 768
//			    768 : {
//			    	items:3,
//			    },
//			    // ponto de interrupção de 768
//			    992 : {
//			    	items:3,
//			    },
//			    // ponto de interrupção de 768
//			    1200 : {
//			    	items:3,
//			    }
//		    }
//		});	
//	});
	
	
}