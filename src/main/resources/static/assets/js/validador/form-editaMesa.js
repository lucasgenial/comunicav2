function abrirModalEditando(){
	var titulo = "Selecionar servidores";
	$('#modal-adicionarServidorNaMesaEditando').modal().find('.modal-title').text(titulo)
	$('#btn-salvar-adicionarServidorMesa').text("Adicionar servidores");
	$("#modal-adicionarServidorNaMesaEditando").modal("show");
}

$('.clicador').on("click", function(e){
	
	if(!$("#"+$(this).attr('data-identificador')).is(':checked')){
		$("#"+$(this).attr('data-identificador')).prop('checked', true);
		$('.'+$(this).attr('data-identificador')).removeClass('table-default');
		$('.'+$(this).attr('data-identificador')).addClass('table-info');
		$('#servidoresSelects').append('<input name="listaServidoresSelecionados" type=text value='+$(this).attr('data-identificador')+' class="marcados servi-'+$(this).attr('data-identificador')+'" />');
	}else{
		$("#"+$(this).attr('data-identificador')).prop('checked', false);
		$('.servi-'+$(this).attr('data-identificador')).remove();
		$('.'+$(this).attr('data-identificador')).removeClass('table-info');
		$('.'+$(this).attr('data-identificador')).addClass('table-default');		
	}
	
	var servidoresSelecionados = new Array();
	
	$(".marcados").each(function(){
		servidoresSelecionados.push($(this).val());
	});
	
	if(servidoresSelecionados.length > 0){
		document.getElementById('adicionaServidorNaMesa').disabled=false;
	}else{
		document.getElementById('adicionaServidorNaMesa').disabled=true;
	}
});


jQuery(function($){
	$("#inicioPlantao").mask("99/99/9999 99:99:99");
	$("#fimPlantao").mask("99/99/9999 99:99:99");
	$(".servidorPausa1").mask("99:99:99");
	$(".servidorPausa2").mask("99:99:99");
	$(".servidorFimPlantao").mask("99/99/9999 99:99:99");
});

document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formEditaMesa'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
		fields : {		
            
			'inicioPlantao': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
           
            'fimPlantao': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                }
            },
           
            'lista': {
                validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
  			 
                }
            },

            'servidores.[*]inicioPlantao' : {
            	
         	   selector: '[data-stripe="number"]',
         	   validators: {
                	notEmpty : {
    					message : 'Campo Obrigatório',
    				},
                    date: {
                        format: 'DD/MM/YYYY HH:MM:SS',
                        message: 'Data Inválida',
                    }
                },
         },
         
         'servidores.[*]fimPlantao' : {
         	
       	   selector: '[data-stripe="number"]',
       	   validators: {
              	notEmpty : {
  					message : 'Campo Obrigatório',
  				},
                  date: {
                      format: 'DD/MM/YYYY HH:MM:SS',
                      message: 'Data Inválida',
                  }
              },
         },           
              	            
	},
				
		plugins: {
          trigger: new FormValidation.plugins.Trigger(),
           bootstrap: new FormValidation.plugins.Bootstrap(),
          defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
           //submitButton: new FormValidation.plugins.SubmitButton(),
            //icon: new FormValidation.plugins.Icon({
               //valid: 'fa fa-check',
               //invalid: 'fa fa-times',
                //validating: 'fa fa-refresh',
            //}),
        },
	});

	
	jQuery(function($){
	
	$("#inicioPlantao").mask("99/99/9999 99:99:99");
	}).on('keypress', function(e) {
        fv.revalidateField('inicioPlantao');
    });
	
	jQuery(function($){
	$(".servidorInicioPlantao").mask("99/99/9999 99:99:99");
	}).on('keypress', function(e) {
        fv.revalidateField('servidores[*].inicioPlantao');
    });
	jQuery(function($){
		$(".servidorFimPlantao").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('servidores[*].fimPlantao');
	    });
	
	jQuery(function($){
		
		$(".fimPlantaoMesa").mask("99/99/9999 99:99:99");
		}).on('keypress', function(e) {
	        fv.revalidateField('fimPlantao');
	    });

	$('.dataInicio').datetimepicker({
        language:  'pt-BR',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 0,
        minuteStep: 1,
        format: 'dd/mm/yyyy HH:ii:ss'
	}).on('change', function(e){
	    fv.revalidateField('inicioPlantao');
	});
	
	$('.dataFim').datetimepicker({
	    language:  'pt-BR',
	    weekStart: 1,
	    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
	    showMeridian: 0,
	    minuteStep: 1,
	    format: 'dd/mm/yyyy HH:ii:ss'
	}).on('change', function(e) {
	    fv.revalidateField('fimPlantao');
	});

});

$('#tabela-tabelaServidor').DataTable({
	"columnDefs" : [{
		"targets" : 4,
		"orderable" : false
	} ],
	"order" : [ [ 1, "asc" ] ],
//	"lengthMenu": [[-1], ["All"]],
	//"paging":   false,
	"responsive" : true,
	"select" : true,
	"className": 'dt-head-right',
	"language" : {
		url : '/assets/traducao/pt_BR.json'
	}
});