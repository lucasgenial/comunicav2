$(document).ready(function() {
	var tabela = $('#tabela-arquivo-paginada').DataTable({
		"responsive": true,
		"serverSide" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"ajax": '/arquivos/buscar/historico',
		"order" : [ [ 0, "desc" ] ],
		"columns": [
				{
					data : 'id',
					name : 'ID',
				},
				{
					data : 'nome',
					name : 'Nome',
				},
				{
					data : 'tipo',
					name : 'Tipo',
				},
				{
					data : 'descricao',
					name : 'Descrição',
					render: function toLimit(descricao, type, row){
						return descricao.substring(0,45);
					}
				},
				{
					data : 'dataEnvio',
					name : 'Data de Envio',
					render: function converterData(data, type, row){
						
						//Converte em data + hora
						var stringData = row.dataEnvio ;
						
						var date = moment(stringData).format('DD/MM/GGGG');
												
						options = {
						  year: 'numeric', month: 'numeric', day: 'numeric',
						};
						
						return date;
					}
				},
				{
					data : 'nome',
					name : 'Download',
					render : function baixarArquivo(data, type, full, meta){
						return '<button  type="none" class="downloadArquivo btn btn-info btn-sm"><a class="btn-info btn-sm" href="/admin/cadastro/arquivo/download/'+ full.id + '"/"' + full.nome +'">Baixar</a></button>';
					} 
				},

			],
			"columnDefs" : [ {
				"targets" : 5,
				"orderable" : false
			} ],
	});
	
});