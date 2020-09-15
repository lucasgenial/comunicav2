	 $(document).ready(function() {
	    $('#tabela').DataTable( {
	        dom: 'Bfrtip',
	        buttons: [
	        	'excel'// 'csv', 'excel', 'pdf'
	        ],
	        "paging": false,
	        "ordering": false,
	        "info": false,
	        "filter": false
	    } );
	} );
 
	 $('#mesInicial').on("change", function(e){
		 let meses= ["","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"]
			document.getElementById("mesFinal").options.length = 0;
			if(this.value !== ""){
	              var options = $('#mesFinal');
	              for(let i = this.value; i <= 12; i++){
	            	  $('<option>').val(i).text(meses[i]).appendTo(options);
	              }
			}
	 });
	 	 
	 
	 $('#tipoGrafico').on("change", function(e){
		document.getElementById('graficod').innerHtml='';
		
		let myChart = echarts.init(document.getElementById('graficod'));
		myChart.clear();
		let maiorTotal = 0;
		let nomeTipificacoesCompleto = new Array();
		let tituloGrafico = document.getElementById('tituloGrafico').value;
		let categoriaDaTipificacao = document.getElementById('categoriaDaTipificacao').value;
		let totais = new Array();
		let valoresComNomes = new Array();
		let valoresComNomesResumidos = new Array();
		

		
		if( $('#apresentacaoDosDados').val() == "mesesPorAno" ){
			 $("[name='mes']").each(function(index, element){
				 valoresComNomes.push({value: 0, name: element.textContent, max:0});
			 });
			  
			let tabela = $('#tabela tbody tr');
			let contado=0;
		
				for(let i=0; i < tabela.length-1; i++){
					for(let j=0; j < valoresComNomes.length; j++){
			 		valoresComNomes[j].value += parseInt(tabela[i].children[j+1].textContent)
				}
			}
	 		
	 		 for(let i=0; i < valoresComNomes.length; i++){
				 	totais.push(valoresComNomes[i].value)
				 	nomeTipificacoesCompleto.push(valoresComNomes[i].name)
				 	
				 	if(valoresComNomes[i].value > maiorTotal){
						maiorTotal = valoresComNomes[i].value;
					}
			}
	 		 
	 		 for(let i=0; i < valoresComNomes.length; i++){
				 	valoresComNomes[i].max = maiorTotal;
			}
			 
		}else if($('#apresentacaoDosDados').val() == "totaisDoAno"){
			
			$("[name='nomes']").each(function(index, element){
				nomeTipificacoesCompleto.push(element.textContent);
			});
			nomeTipificacoesCompleto.pop();
			
			
			$("[name='totais']").each(function(index, element){
				totais.push(parseInt(element.textContent));
			});
			
			totais.pop();
			
			for(let i=0; i < totais.length; i++){
				if(totais[i] > maiorTotal){
					maiorTotal = totais[i] ;
				}
				console.log("totais -> "+totais[i]+" maior -> "+maiorTotal)
			}
			

			for(let i=0; i<totais.length; i++){
				valoresComNomes.push({value:totais[i], name:nomeTipificacoesCompleto[i], max:maiorTotal});
			}
		}
		
		montaGrafico(myChart,tituloGrafico,this.value,nomeTipificacoesCompleto,totais,valoresComNomes, valoresComNomes,categoriaDaTipificacao)
	 
});
	 
function mostrarGrafico(){	
	
	document.getElementById('graficod').innerHtml='';
	
	let myChart = echarts.init(document.getElementById('graficod'));
	myChart.clear();
	
	let tituloGrafico = document.getElementById('tituloGrafico').value;
	let categoriaDaTipificacao = document.getElementById('categoriaDaTipificacao').value;
	let valoresComNomes = new Array();
	
	
	 $("[name='mes']").each(function(index, element){
		 valoresComNomes.push({value: 0, name: element.textContent, max:0});
	 });
	  
	let tabela = $('#tabela tbody tr');

		for(let i=0; i < tabela.length-1; i++){
			for(let j=0; j < valoresComNomes.length; j++){
	 		valoresComNomes[j].value += parseInt(tabela[i].children[j+1].textContent)
		}
	}
		
	myChart.setOption({
		 title: {
		        subtext: tituloGrafico,
		        text: categoriaDaTipificacao,
		        left: '50%',
		        textAlign: 'center'
		    },
		 dataset: {
		        source: valoresComNomes
		    },
		       toolbox: {
		            show: true,
		            feature: {
		                restore: {show: true},
		                saveAsImage: {show: true}
		            }
		        },
		    textStyle:{
		    	fontSize:12
		    },
		    grid: {containLabel: true},
		    xAxis: {name: 'value'},
		    yAxis: {type: 'category'},

		    series: [{
		    	 barWidth: '25%',				            
		    	 label: {
		             normal: {
		                 position: 'right',
		                 show: true
		             }
		         },    
		    	type: 'bar',
		            encode: {
		                // Map the "amount" column to X axis.
		                x: 'amount',
		                // Map the "product" column to Y axis
		                y: 'product'
		            }
		        }
		    ]});
	
}


	function resumeTipificacao(nomes){
		let nomesResumidos = new Array(); 
		let nomeProvisorio = "";
		for(let i = 0; i < nomes.length; i++){
			nomeProvisorio = nomes[i].replace(/\s+/gi, ' ').trim();
			nomeProvisorio = nomeProvisorio.split(' ').map((parte, index, nomes) => (index == (0 )) ? parte : `${parte[0]}.`).join(' ');
			nomesResumidos.push(nomeProvisorio);
		}
		return nomesResumidos;
	}
	
	function montaGrafico(myChart,tituloGrafico,tipo, listaNomesIndividuais,listaValoresIndividuais, listaValoresComNomes, listaArrayDeValoresComNomes,categoriaDaTipificacao){
		
		
		switch (tipo) {
		case "barraHorizontal": {
			myChart.setOption({
				 title: {
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				    },
				 dataset: {
				        source:listaValoresComNomes
				    },
				       toolbox: {
				            show: true,
				            feature: {
				                restore: {show: true},
				                saveAsImage: {show: true}
				            }
				        },
				    textStyle:{
				    	fontSize:12
				    },
				    grid: {containLabel: true},
				    xAxis: {name: 'value'},
				    yAxis: {type: 'category'},

				    series: [{
				    	 barWidth: '25%',				            
				    	 label: {
				             normal: {
				                 position: 'right',
				                 show: true
				             }
				         },    
				    	type: 'bar',
				            encode: {
				                // Map the "amount" column to X axis.
				                x: 'amount',
				                // Map the "product" column to Y axis
				                y: 'product'
				            }
				        }
				    ]});
			break;
		}
		
		case "barraHorizontalComplementar": {
			let serie = [];
			serie = listaValoresComNomes;
			for(let i = 0; i < serie.length; i++){
				serie[i].type = 'bar';
				serie[i].label = {show: true,  position: 'inside'};
				serie[i].barWidth= '25%';	
				serie[i].stack= 'teste';
				serie[i].data= [serie[i].value];
			}
	
			myChart.setOption({
				 title: {
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				      
				    },
			
				       toolbox: {
				            show: true,
				            feature: {
				                restore: {show: true},
				                saveAsImage: {show: true}
				            }
				        },
				        legend: {
				        	orient: 'horizontal',
				            right: 0,
				            top: 45,
				            bottom: 20,
				            data: listaNomesIndividuais

				        },
				        
				        tooltip: {
				            trigger: 'axis',
				            axisPointer: {            
				                type: 'shadow'        
				            }
				        },
				   
				        grid: {
				            left: '3%',
				            right: '4%',	
				            bottom: '3%',
				            containLabel: true
				        },
				        xAxis: {
				            type: 'value'
				        },
				        yAxis: {
				            type: 'category',
				            data: [categoriaDaTipificacao]
				        },
				        series: serie
				    });
			break;
		}
		
		case "linha":{
			myChart.setOption({
				 title: {	 
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				      
				    },
			       toolbox: {
			            show: true,
			            feature: {
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        lineHeight: 10,
				    yAxis: {
				        type: 'value'

				    },
				    xAxis: {
				    	 type: 'category',
					     data: listaNomesIndividuais
				    },
				    series: [{
				    	 label: {
				             normal: {
				                 position: 'right',
				                 show: true
				             }
				         },
				        data: listaValoresIndividuais,
				        type: 'line',
				        smooth: true
				    }]
				});
			break;
		}	
		
		case "pizza":{
			myChart.setOption({
				 title: {	 
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				      
				    },
				 toolbox: {
			            show: true,
			            feature: {
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        
			        series: [{
			            type: 'pie',
			            radius: '50%',
			            data: listaValoresComNomes,
			            animation: true,
			           
			            label: {
			            	formatter: '{b}: {@[' + listaValoresComNomes + ']} ({d}%)',
			                bleedMargin: 5
			            },
			          
			        },]
		      
		   
			});
			break;
		}
		
		case "barraVertical":{
	
			myChart.setOption({
				 title: {	 
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				      
				    },
				 toolbox: {
			            show: true,
			            feature: {
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
				 color: ['#3398DB'],
				  legend: {
				        left: 'center',
				        top: 'bottom',
				        data: listaNomesIndividuais
				    },
				 
				    xAxis: [
				        {
				        	  axisLabel: {
					                interval: 0,
					                rotate: 16
					            },
					            splitLine: {
					                show: false
					            },
				            type: 'category',
				            data: listaNomesIndividuais,
				            axisTick: {
				                alignWithLabel: true
				            }
				        }
				      
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				        	 label: {
					             normal: {
					                 position: 'top',
					                 show: true
					             }
					         },
				            type: 'bar',
				            barWidth: '20%',
				            data: listaValoresIndividuais
				        }
				    ]
			});
			
			break;
		}
		
		case "dispersao": {
			myChart.setOption({
				 title: {	 
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				      
				    },
				 dataset: {
				        source:listaValoresComNomes
				    },
				       toolbox: {
				            show: true,
				            feature: {
				                restore: {show: true},
				                saveAsImage: {show: true}
				            }
				        },
				    grid: {containLabel: true},
				    xAxis: {name: 'value'},
				    yAxis: {type: 'category'},

				    series: [
				        {
				        	 label: {
					             normal: {
					                 position: 'right',
					                 show: true
					             }
					         },
				            type: 'scatter',
				            encode: {
				                // Map the "amount" column to X axis.
				                x: 'amount',
				                // Map the "product" column to Y axis
				                y: 'product'
				            }
				        }
				    ]});
			break;
		}
		
		case "radar":{
			myChart.setOption({
				 title: {	 
					  subtext: tituloGrafico,
				        text: categoriaDaTipificacao,
				        left: '50%',
				        textAlign: 'center'
				    },
			  
			    legend: {
			        data: listaNomesIndividuais
			    },
			    toolbox: {
		            show: true,
		            feature: {
		                restore: {show: true},
		                saveAsImage: {show: true}
		            }
		        },
			    tooltip: {
			        trigger: 'item',
			        axisPointer: {
			            type: 'shadow'
			        }
			    },
			    radar: {
			        // shape: 'circle',
			        name: {
			            textStyle: {
			                color: '#fff',
			                backgroundColor: '#999',
			                borderRadius: 3,
			                padding: [3, 5]
			            }
			        },
			         
			        indicator: listaValoresComNomes
			    },
			    series: [{
			    	 label: {
			             normal: {
			                 position: 'right',
			                 show: true
			             }
			         },
			        name: '预算 vs 开销（Budget vs spending）',
			        type: 'radar',
			        // areaStyle: {normal: {}},
			        data: [
			            {
			                value: listaValoresIndividuais,
			                name: 'Quantidade de Ocorrências'
			            },
			           
			        ]
			    }]
				    });
			break;
		}
	
	}
		
	}
	