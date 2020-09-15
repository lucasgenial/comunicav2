$(document).ready(function() {
	console.log(dados);
	
	var tabela = $('#tabela-dadosEstatisticos').DataTable({
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		},
		"data": dados,
		"dom": 'Bfrtip',
		"buttons": [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ],
        "columns": [
            { data: "categoria" },
            { data: "valores.janeiro" },
            { data: "valores.fevereiro" },
            { data: "valores.marco" },
            { data: "valores.abril" },
            { data: "valores.maio" },
            { data: "valores.junho" },
            { data: "valores.julho" },
            { data: "valores.agosto" },
            { data: "valores.setembro" },
            { data: "valores.outubro" },
            { data: "valores.novembro" },
            { data: "valores.dezembro" },
            { data: "total" },
        ]
	});
});