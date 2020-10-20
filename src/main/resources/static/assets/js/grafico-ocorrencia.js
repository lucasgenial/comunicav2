//Graifico de Cidades
var grafico1 = echarts.init(document.getElementById('grf_1'));

//Grafico de Horas
var grafico2 = echarts.init(document.getElementById('grf_2'));


function geradorDeGraficosDashboard(id) {
	
	
	geraGraficoTipificacao(id);
	
	geraGraficoCidades(id);
	
	geraGraficoHora(id);
	
	geraGraficoStatus(id);
	
	//Apaga painel 1
	$("#painel-2").toggle();
}

//Verifica caso a tela é Redimensionada
$(window).on('resize', function(){
	grafico1.resize();
	grafico2.resize();
});

function geraGraficoStatus(id){
	/*
	 * Inicio Configurações Grafico OCORRÊNCIAS POR STATUS
	 */	
	$.getJSON("/admin/cadastros/lista/ocorrencia/status/" + id, function(dados) {
		var coresStatus = [ '#00B23D', '#1E8DB2', '#FF9540', '#19A2FF' , '#89998C', '#FF1E17'];
		var content     = "";
		var nomes       = dados.nomes;

		for (var i = 0; i < dados.nome.length; i++) {

			var nome = dados.nome[i];
			var qtd = dados.quantidade[i];

			content += '<div class="item" style="min-height: 100%">'+
			'<div class="card text-center" style="background:'+ coresStatus[i] +'; align-center ">'+
				'<div class="card-body">'+
					'<h5 class="card-title">'+ nome +'</h5>'+
					'<div class="row text-center">'+
							'<div class="col-12" >'+
							    '<h1 class="card-title">'+ qtd +'</h1>'+
						    '</div>'+
					 '</div>'+
				 '</div>'+
              '</div>'+
            '</div>';
		}

		$("#apresentacaoPorStatus").append(content);
		
		$("#apresentacaoPorStatus").owlCarousel({
		    loop:false,
		    margin:10,
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
			    	items:3,
			    },
			    // ponto de interrupção de 768
			    1200 : {
			    	items:3,
			    }
		    }
		});
	});
};

function geraGraficoHora(id){
	/*
	 * Inicio Configurações Grafico OCORRÊNCIAS POR HORA
	 */
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
			grid: {
			    top: '6',
			    right: '0',
			    bottom: '17',
			    left: '25',
			},
//			grid : {
//				bottom : '10%'
//			},
			series : {
				type : 'line',
				showSymbol : true,
				data : dados.quantidade
			}
		};
		
		// RECEBE AS OPÇOES DO GRÁFICO
		grafico2.setOption(opcoes_grafico2);

	});
}

function geraGraficoCidades(id){
	/*
	 * Início Configuração Gráfico de OCORRÊNCIAS POR CIDADES
	 */

	// Abre o LOADING
	grafico1.showLoading();

	$.getJSON("/admin/cadastros/lista/ocorrencias/abrangencia/" + id, function(dados) {
		// FINALIZA O LADING
		grafico1.hideLoading();

		var dadosTratados = genData(dados);
		
		// RECEBE AS OPÇOES DO GRAFICO
		opcoes_grafico1 = {
			tooltip : {
				trigger : 'axis'
			},
			grid: {
			    top: '6',
			    right: '0',
			    bottom: '17',
			    left: '25',
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
				data : dadosTratados.nomes,
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
				data : dadosTratados.valores,
			} ]
		};
		
		// RECEBE AS OPÇÕES DO GRÁFICO
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
	
}

function geraGraficoTipificacao(id){
	/*
	 * Inicio Configurações Grafico OCORRÊNCIAS POR TIPIFICAÇÃO 
	 */
	$.getJSON("/admin/cadastros/lista/ocorrencias/" + id, function(dados) {
		var nomes = dados.nomes;
		var html = "";

		for (var i = 0; i < dados.nome.length; i++) {
			var nome  = dados.nome[i];
			var qtd   = dados.quantidade[i];
			var perc  = Number(dados.percentual[i]);
			var ativo = "";
			
			if(i == 1){
				var ativo = "active";
			}

			html += '<div class="card" style="margin-top:0px">'+
						'<div class="card-body">'+
							'<div style="height: 40px;">'+
								'<h6 class="card-title text-center">'+ nome +'</h6>'+
							'</div>'+
							'<div class="row">'+
								'<div class="d-flex no-block align-items-center">'+
									'<div class="col-9">'+
							   			'<div class="chart cart-pie-1" data-percent="'+ perc.toFixed(2) +'"> <span class="percent">'+ perc.toFixed(2) +'</span> </div>'+
							   		'</div>'+
									'<div class="col-12 text-center">'+
									    '<h1 class="text-success">'+ qtd +'</h1>'+
								    '</div>'+
							    '</div>'+
							 '</div>'+
						 '</div>'+
		              '</div>';
		}
		
		$("#apresentaPorClassificacao").append(html);
				
		$(".cart-pie-1").easyPieChart({
	        easing: 'easeOutBounce',
			barColor : '#13dafe',
			lineWidth: 7,
			animate: 500,
	        lineCap: 'square',
	        trackColor: '#e5e5e5',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(percent.toFixed(2));
			}
	    });

		$('#apresentaPorClassificacao').owlCarousel({
			loop:true,
		    margin:20,
			dots:false,
		    nav:false,
			autoplay:true,
    		autoplayTimeout:3000,
    		autoplayHoverPause:true,
		    responsive : {
			    0 : {
			    	items:1,
			    },
			    1150 : {
			    	items:3,
			    },
			    1200 : {
			    	items:4,
			    	owl2row: false,
			    },
				1400 : {
			    	items:4,
			    	owl2row: false,
			    },
				1600 : {
			    	items:5,
			    	owl2row: false,
			    }
		    }
		});
	});
}