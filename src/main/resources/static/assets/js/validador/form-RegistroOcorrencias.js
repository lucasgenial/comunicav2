document.addEventListener('DOMContentLoaded', function(e) {
	const fv = FormValidation.formValidation(document.getElementById('formRegistroOcorrencia'),{
		locale: 'pt_BR',
        localization: FormValidation.locales.pt_BR,
        fields : {		

			'nome':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'sexo':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'envolvimento':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
			},
			
			'tipoPessoa':{
				validators: {
                    notEmpty : {
    					message : 'Campo Obrigatório',
    				}
                }
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
	
	tinymce.init({
	    readonly : 1,
		selector: 'textarea',
		language : 'pt_BR',
		width: '100%',
		entity_encoding : "raw",
		height : 100,
		elementpath : false,
		statusbar:  false,
		menubar : false,
		branding: false,
		plugins : 'print template advlist lists textcolor colorpicker textpattern',
		advlist_bullet_styles : "default",
		advlist_number_styles : "default",
		toolbar :false ,
		setup: function(editor) {
            editor.on('keyup', function() {
                // Revalidate the comment field
                fv.revalidateField('descricao');
            });
        }
	});
	
	
	jQuery(function($){
	
	$("#dataNascimento").mask("99/99/9999");
	}).on('keypress', function(e) {
        fv.revalidateField('dataNascimento');
    });
	

	$('.dataNascimento').datetimepicker({
        language:  'pt-BR',
        autoclose: 1,
		startView: 2,
        format: 'dd/mm/yyyy'
	}).on('change', function(e){
	    fv.revalidateField('dataNascimento');
	});
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});