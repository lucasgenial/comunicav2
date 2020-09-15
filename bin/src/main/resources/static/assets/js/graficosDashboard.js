/* 
*
* Inicio Configurações Grafico 1
*
**/
function geradorDeGraficosDashboard(id, url){
		
		var myChart1 = echarts.init(document.getElementById('grf_1'));

		myChart1.showLoading();
		
		$.getJSON("/lista/ocorrencias/" + id, function(dados) {
			var data2 = genData(dados);

			myChart1.hideLoading();

			myChart1.setOption({
				tooltip : {
					formatter : "{a} <br/>{b} : ({d}%)"
				},
				toolbox : {
					show : true,
					feature : {
						restore : {
							title : 'Reset'
						},
						saveAsImage : {
							title : 'Salvar'
						}
					}
				},
				legend : {
					type : 'scroll',
					orient : 'horizontal',
					bottom : '1%',
					data : dados.nome,
					selected : data2.selecionadas
				},
				series : [ {
					name : 'Tipificação',
					type : 'pie',
					radius : '50%',
					center : [ '50%', '50%' ],
					data : data2.valores,
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				} ]
			});
		});

		function genData(data) {
			// Recebe os nomes das tipificações
			var nomes = data.nome;

			// Recebe a quantidade das tipificações
			var quantidade = data.quantidade;

			// Transforma em um json de Arrays de Objetos
			var valores = [];

			// Fica com os campos que foram selecionados
			var selected = {};

			// Preenche o Json e marca os campos selecionados por padrão
			for (var i = 0; i < nomes.length; i++) {
				valores.push({
					name : data.nome[i],
					value : data.quantidade[i]
				});
				selected[data.nome[i]] = i < 10;
			}

			return {
				selecionadas : selected,
				valores : valores
			}
		}

		/*
		 * Inicio Configurações Grafico 2
		 */
		var myChart2 = echarts.init(document.getElementById('grf_2'));

		myChart2.showLoading();

		$.getJSON("/lista/ocorrencias/hora/"+ id, function(dados) {

			myChart2.hideLoading();

			// var teste = dados.data;

			// var max = 0

			myChart2.setOption({
				// Make gradient line here
				visualMap : {
					show : false,
					type : 'continuous',
					seriesIndex : 0,
					min : 0,
					max : 30
				},
				toolbox : {
					show : true,
					feature : {
						restore : {
							title : 'Reset'
						},
						saveAsImage : {
							title : 'Salvar'
						}
					}
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
					showSymbol : false,
					data : dados.quantidade
				}
			});

		});

		/*
		 * Inicio Configuração Gráfico 3
		 */
		var myChart3 = echarts.init(document.getElementById('grf_3'));

		myChart3.setOption({
			tooltip : {},
			xAxis : {
				data : []
			},
			yAxis : {},
			series : [ {
				name : 'QUANTIDADE',
				type : 'bar',
				data : []
			} ]
		});

		myChart3.showLoading();
		$.getJSON("/lista/ocorrencias/abrangencia/"+ id, function(dados) {
					myChart3.hideLoading();

					myChart3.setOption({
						legend : {
							data : dados.nome
						},
						toolbox : {
							show : true,
							feature : {
								restore : {
									title : 'Reset'
								},
								saveAsImage : {
									title : 'Salvar'
								}
							}
						},
						xAxis : {
							data : dados.nome
						},
						series : [ {
							name : "QUANTIDADE",
							data : dados.quantidade
						} ]
					});
				});
}