$(document).ready(function() {
	var tabela = $('#tabela-registro-ocorrencia').DataTable({
		"responsive": true,
		"serverSide" : true,
		"processing": true,
		"select" : true,
		"language" : {	url : '/assets/traducao/pt_BR.json'	},
		"ajax": '/registros/ocorrencias/buscar/historico/roubosEFurtosNaoRegistrados',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'sic',
					name: 'SIC',
					width: "7%",
					searchable: false,
				},
				{
					data : 'dataOcorrencia',
					name: 'Data Ocorrência',
					width: "11%",
                       render: function converterData(data, type, row){
						
						//Converte em data + hora
						var stringData = row.dataOcorrencia;
						
						var date = moment(stringData).format('DD/MM/YYYY HH:mm');
												
						options = {
						  year: 'numeric', month: 'numeric', day: 'numeric',
						  hour: 'numeric', minute: 'numeric', second: 'numeric',
						  hour12: false,
						};
						
						return date;
					}
				},
				{
					data : 'tipificacao.nome',
					name: 'Tipificação'
				},
				{ 
					data : 'endereco.cidade.nome',
					name: 'Cidade'
				},
				{
					data : 'endereco.bairro.localidade.nome',
					name: 'Localidade'
				},
				{
					data : 'endereco.bairro.nome',
					name: 'Bairro'
				},
				{		            targets: -1,
		            searchable: false,
		            orderable: false,
		            width: "8%",
		            render: function adicionaBotaoAcao(data, type, full, meta){
		            	
		            	var botaoAcao = '<a class="btn btn-info" href="/relatorio/ocorrencias/cadastro/'+ full.id +'">Registrar <i class="fa fa-arrow-right"></i></a>';
						   						 
						   return botaoAcao;
            }
        } ]	
				
	});

});
