/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de ocorrências
 */

document.addEventListener('DOMContentLoaded', function(e) {
	tinymce.init({
		selector: 'textarea',
		language : 'pt_BR',
		width: '100%',
		entity_encoding : "raw",
		height : 230,
		encoding: 'raw',
		elementpath : false,
		statusbar:  false,
		menubar : false,
		branding: false,
		plugins : 'print template advlist lists textcolor colorpicker textpattern paste',
		paste_as_text: true,
		advlist_bullet_styles : "default",
		advlist_number_styles : "default",
		toolbar : "undo redo | bold italic strikethrough | removeformat",
		setup: function(editor) {
            editor.on('keyup', function() {
                // Revalidate the comment field
                fv.revalidateField('#mensagem');
            });
        }
	});
	
	
	//Se algum campo estiver oculto remove
	$('[required]', event.field).removeAttr('required');
});

