/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de notificação
 */
var id = [];

$(document).ready(function() {
	$('#listaGrupos').selectpicker();
	$('#listaUsuarios').selectpicker();

	$('#listaGrupos').on("change", function() {
		
		var gruposSelecionados = $('#listaGrupos').val();

		if (gruposSelecionados.length >= 1) {

			console.log(gruposSelecionados);
			
			//document.getElementById("listaUsuarios").options.length = 0;
			$("#listaUsuarios").empty();
			
			$.ajax({
				type: "POST",
				url: "/admin/notificacoes/nova/usuarios/" + id,
				data:{
					grupos: JSON.parse(gruposSelecionados)
				},
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						var option = new Option(data[i].nome, data[i].id);
						var select = document.getElementById("listaUsuarios");

						select.add(option);
					}
				},
				fail: function() {
					alert('Falha ao Buscar os Usuários');
				}
			});
		} else {
			$.ajax({
				type: "POST",
				url: "/admin/notificacoes/nova/usuarios/",
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						var option = new Option(data[i].nome, data[i].id);
						var select = document.getElementById("listaUsuarios");

						select.add(option);
					}
				},
				fail: function() {
					alert('Falha ao Buscar as Usuários');
				}
			});
		}


	});

});

document.addEventListener('DOMContentLoaded', function(e) {
	tinymce.init({
		selector: 'textarea',
		language: 'pt_BR',
		width: '100%',
		entity_encoding: "raw",
		height: 230,
		encoding: 'raw',
		elementpath: false,
		statusbar: false,
		menubar: false,
		branding: false,
		plugins: 'print template advlist lists textcolor colorpicker textpattern paste',
		paste_as_text: true,
		advlist_bullet_styles: "default",
		advlist_number_styles: "default",
		toolbar: "undo redo | bold italic strikethrough | removeformat",
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

