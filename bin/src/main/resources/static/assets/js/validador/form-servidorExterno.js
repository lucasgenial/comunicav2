/**
 * Arquivo de configuração da VALIDAÇÃO via Javascript do formulário de Geração
 * de Gráficos ATENÇÃO ESTE JS GERENCIA A PÁGINA DE CADASTRO DE EFETIVOS
 * EXTERNOS
 * 
 */

document.addEventListener('DOMContentLoaded', function(e) {

	$('#instituicao').on("change", function(e) {
		document.getElementById("hierarquia").options.length = 1;
		$.ajax({
			type : "GET",
			url : "admin/cadastro/instituicoes/hierarquias/" + this.value,
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var option = new Option(data[i].nome, data[i].id);
					var select = document.getElementById("hierarquia");
					select.add(option);
				}
			}
		});
	}).on('changeDate', function(e) {
		// Revalidate the date field
		fv.revalidateField('listaBairros');
	});

	$("#matricula").mask("99.999.999-9");

	// Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});