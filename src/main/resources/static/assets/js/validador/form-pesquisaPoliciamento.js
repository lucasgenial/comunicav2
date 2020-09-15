
document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formPesquisaPoliciamento'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {		
            
			'comecoPlantao': {
                validators: {
                   date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
           
            'terminoPlantao': {
                validators: {
                         date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },

            	            
		},
		
		plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap(),
            defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
            submitButton: new FormValidation.plugins.SubmitButton(),
           
        },
	});

	
	jQuery(function($){
	
	$("#comecoPlantao").mask("99/99/9999 99:99:99");
	}).on('change', function(e) {
        fv.revalidateField('mesa.inicioPlantao');
    });
	
	jQuery(function($){
		$("#terminoPlantao").mask("99/99/9999 99:99:99");
		}).on('change', function(e) {
	        fv.revalidateField('mesa.fimPlantao');
	    });

});


$(document).ready(function() {
    $('#tabela-pesquisaPoliciamento').DataTable( {
    	
        "oLanguage": {
            "sProcessing":   "Processando...",
            "sLengthMenu":   "Mostrar _MENU_ registros",
            "sZeroRecords":  "Não foram encontrados resultados",
            "sInfo":         "Mostrando de _START_ até _END_ de _TOTAL_ registros",
            "sInfoEmpty":    "Mostrando de 0 até 0 de 0 registros",
            "sInfoFiltered": "",
            "sInfoPostFix":  "",
            "sSearch":       "Buscar:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "Primeiro",
                "sPrevious": "Anterior",
                "sNext":     "Seguinte",
                "sLast":     "Último"
            }
        },  	      	
    
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
    
});

today = new Date();
$('#comecoPlantao').datetimepicker({
	format: 'dd/mm/yyyy hh:ii:ss',
	language:  'pt-BR',
	ignoreReadonly: true,
	showClose: true,
	showTodayButton:true,
	toolbarPlacement: 'bottom',
	
});

$('#terminoPlantao').datetimepicker({
	format: 'dd/mm/yyyy hh:ii:ss',
	language:  'pt-BR',
	ignoreReadonly: true,
	showClose: true,
	showTodayButton:true,
	toolbarPlacement: 'bottom',
	
});

$('#estabelecimento').on("change", function(e) {
	document.getElementById("cidade").options.length = 1;
	document.getElementById("localidade").options.length = 1;
	document.getElementById("bairro").options.length = 1;
	
	var id = this.value;
	
	$.ajax({
		type : "GET",
		url : "admin/cadastros/estabelecimento/cidades/" + id,
		success : function(data) {			
			for (var i = 0; i < data.length; i++) {
				var option = new Option(data[i].nome, data[i].id);
				var select = document.getElementById("cidade");
				select.add(option);
			}
		}
	});

});

$('#cidade').on("change", function(e){
	document.getElementById("localidade").options.length = 1;
    $.ajax({
	      type : "GET",
	      url : "admin/cadastros/cidades/localidades/" + this.value,
	      success: function(data) {	   
	    	  for(var i=0;i<data.length;i++){
	    		  var option = new Option(data[i].nome, data[i].id);
	    		  var select = document.getElementById("localidade");
	    		  select.add(option);
	    	  }	
	      }
    });
});
	$('#localidade').on("change", function(e){
		document.getElementById("bairro").options.length = 1;
	    $.ajax({
		      type : "GET",
		      url : "admin/cadastros/localidades/bairrosSort/" + this.value,
		      success: function(data) {	   
		    	  for(var i=0;i<data.length;i++){
		    		  var option = new Option(data[i].nome, data[i].id);
		    		  var select = document.getElementById("bairro");
		    		  select.add(option);
		    	  }	
		      }
	    });
	});

