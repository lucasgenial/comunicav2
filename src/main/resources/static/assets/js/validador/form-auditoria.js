$(document).ready(function() {
	$('#tabela-auditar').DataTable({
		"columnDefs" : [{
			"targets" : 3,
			"orderable" : false
		} ],
		"order" : [ [ 0, "desc" ] ],
		"responsive" : true,
		"paging":   false,
	    "info":     false,
	    "searching": false,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
});

