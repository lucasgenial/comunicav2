/**
 * Configurações das Tabelas
 */
$(document).ready(function() {
	
	$('#tabela-permissoes').DataTable({
		"columnDefs" : [{
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 0, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-ocorrencia').DataTable({
		"columnDefs" : [ {
			"targets" : 6,
			"orderable" : false
		} ],
		"order" : [ [ 0, "desc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	
	$('#tabela-ocorrencia-estatistica').DataTable({
		"columnDefs" : [ {
			"targets" : 4,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	
	$('#tabela-servidor').DataTable({
		"columnDefs" : [ {
			"targets" : 7,
			"orderable" : false
		} ],
		"order" : [ [ 4, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-usuario').DataTable({
		"columnDefs" : [ {
			"targets" : 4,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-bairros').DataTable({
		"columnDefs" : [{
			"targets" : 4,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	
	$('#tabela-emails').DataTable({
		"columnDefs" : [{
			"targets" : 3,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-localidades').DataTable({
		"columnDefs" : [{
			"targets" : 3,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-tipificacao').DataTable({
		"columnDefs" : [{
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-usuarios').DataTable({
		"columnDefs" : [{
			"targets" : 4,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-permissao').DataTable({
		"columnDefs" : [{
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-grupos').DataTable({
		"columnDefs" : [{
			"targets" : 3,
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-permissao-grupo').DataTable({
		"columnDefs": [{
			"targets": 0,
			"orderable": false,
            "className": 'select-checkbox',
            'checkboxes': {
                'selectRow': true
             }
        }],
        "select":{
            "style": 'multi'
        },
		"order" : [[ 1, 'asc' ]],
		"responsive" : true,
		"select" : true,
		"language": {
			"url" : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-acoes').DataTable({
		"columnDefs" : [{
			"targets" : 5,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		"paging": false,
	    "searching": false,
	});
	
	$('#tabela-estabelecimentos').DataTable({
		"columnDefs" : [ {
			"targets" : 4,
			"orderable" : false
		}, {
			"targets" : 3,
			"width" : "30%",
			"orderable" : false
		} ],
		"order" : [ [ 1, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	

	$('#tabela-resultadoOcorrencia').DataTable({
		"columnDefs" : [ {
			"targets" : 5,
			"orderable" : false
		} ],
		"order" : [ [ 0, "asc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
/*	$('#tabela-enviarPorEmail').DataTable({
		"columnDefs" : [ {
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		  "searching": false,
		"responsive" : true,
		"select" : true,
		"paging":   true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});*/

	$('#tabela-enviarPorEmail').DataTable({
		"columnDefs" : [ {
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 1, "desc" ] ],
		"responsive" : true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
	$('#tabela-enviar-ocorrencias').DataTable({
		"columnDefs" : [ {
			"targets" : 5,
			"orderable" : false
		} ],
		"order" : [ [ 0, "desc" ] ],
		"responsive" : true,
		"lengthMenu": [[-1], ["All"]],
		"paging":   false,
	    "info":     false,
	    "searching": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});

	$('#tabela-emails-enviar-varias').DataTable({
		"columnDefs" : [ {
			"targets" : 2,
			"orderable" : false
		} ],
		"order" : [ [ 0, "desc" ] ],
		"responsive" : true,
		"lengthMenu": [[-1], ["All"]],
		"paging":   false,
	    "info":     false,
	    "searching": true,
		"select" : true,
		"language" : {
			url : '/assets/traducao/pt_BR.json'
		}
	});
	
$('#tabela-enviar-ocorrencias-whatsapp').DataTable({
	"columnDefs" : [ {
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 0, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-adicionar-servidor').DataTable({
	"columnDefs" : [ {
		"targets" : 4,
		"orderable" : false
	} ],
	"order" : [ [ 1, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-instituicoes').DataTable({
	"columnDefs" : [{
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-efetivoExterno').DataTable({
	"columnDefs" : [{
		"targets" : 6,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"lengthMenu": [[-1], ["All"]],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-funcoes').DataTable({
	"columnDefs" : [{
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});


$('#tabela-envolvidos').DataTable({
	"columnDefs" : [ {
		"targets" : 5,
		"orderable" : false
	} ],
	"order" : [ [ 0, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-objetosEnvolvidos').DataTable({
	"columnDefs" : [ {
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 0, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
$('#tabela-tabelaServidor').DataTable({
	"columnDefs" : [{
		"targets" : 4,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
$('#tabela-servidoresFuncao').DataTable({
	"columnDefs" : [{
		"targets" : 7,
		"orderable" : false
	} ],
	"lengthMenu": [[-1], ["All"]],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
$('#tabela-diarioMesa').DataTable({
	"columnDefs" : [{
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 0, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
$('#tabela-diarioMesa-administrador').DataTable({
	"columnDefs" : [{
		"targets" : 4,
		"orderable" : false
	} ],
	"order" : [ [ 0, "desc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});


$('#tabela-historicoEfetivoExterno').DataTable({
	"columnDefs" : [{
		"targets" : 5,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});


$('#tabela-arquivo').DataTable({
	"columnDefs" : [{
		"targets" : 5,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-servidorExterno').DataTable({
	"columnDefs" : [{
		"targets" : 5,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});

$('#tabela-prestacaoServico').DataTable({
	"columnDefs" : [{
		"targets" : 3,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});
});


