/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de notificação
 */
$.ajax({
	type: "POST",
	url: "/admin/notificacoes/nova/usuarios/",
	data: {
		grupos: [0],
	},
	success: function(dados) {
		for (var i = 0; i < dados.length; i++) {
			var option = new Option(dados[i].servidor.nome.toUpperCase(), dados[i].servidor.id);
			var select = document.getElementById("listaUsuarios");

			select.append(option);
		}
	},
	fail: function() {
		alert('Falha ao Buscar os Usuários');
	}
});

$.ajax({
	type: "POST",
	url: "/admin/notificacoes/nova/grupos/",
	data: {
		grupos: [1],
	},
	success: function(dados) {
		for (var i = 0; i < dados.length; i++) {
			var option = new Option(dados[i].nome.toUpperCase(), dados[i].id);
			var select = document.getElementById("listaGrupos");

			select.append(option);
		}
	},
	fail: function() {
		alert('Falha ao Buscar os Grupos');
	}
});
var selecionados = [];
var gruposSelecionados = [];
var usuariosAtivos = [];

$('#listaUsuarios').on("change", function() {
	
	
	if(gruposSelecionados.length > 0){
		//Selecionou um grupo
		//E adicinou um usuario Manualmente
		var aux2 = $('#listaUsuarios').val();
		
		//Recupera os Usuarios selecionados manualmente
		selecionados = selecionados.concat(atualizaSelecionados(aux2, usuariosAtivos));	
		
		//Concatena Lista de UsuariosAtivos e Selecionados Manualmente
		usuariosAtivos = aux2;
	}else{
		//Ainda não selecionou nenhum grupo
		//Apenas seleção manual de usuários
		selecionados = $('#listaUsuarios').val();
		console.log("seleção manual de usuarios: "+selecionados);
		
		usuariosAtivos = selecionados;
	}
	///ordena as listas "elas são ordenadas como array de String"
	selecionados.sort();
	usuariosAtivos.sort();
	
});

function atualizaSelecionados(array1, array2) {
    var r1 = array1.filter(function (element, index, array) {
        if(array2.indexOf(element) == -1)
            return element;
    });

    return r1;
}

$('#listaGrupos').on("change", function() {
	
	gruposSelecionados = $('#listaGrupos').val().map(Number);
	
	usuariosAtivos = $('#listaUsuarios').val();
	
	if(gruposSelecionados.length > 0){
		$.ajax({
			type: "POST",
			url: "/admin/notificacoes/nova/usuarios/",
			data: {
				grupos: gruposSelecionados
			},
			success: function(data) {
				var usuariosConsulta = [];
				
				for(var i = 0; i < data.length; i++) {
					usuariosConsulta.push(""+data[i].servidor.id);
				}
				
				var aux1 = Array.prototype.concat(aux1, usuariosConsulta);
				
				//Atualiza usuarios Ativos
				//tomando somente os usuarios que não estavam selecionados anteiormente
				//Passo necessário para evitar repetições na lista de usuarios ativos.
				usuariosAtivos = usuariosAtivos.concat(atualizaSelecionados(aux1, usuariosAtivos));
										
				$('#listaUsuarios').selectpicker('val', usuariosAtivos);
			},
			fail: function() {
				alert('Falha ao Buscar os Usuários');
				$('#listaUsuarios').selectpicker('val',selecionados);
			}
		});
	}else{
		
		var aux3 = $('#listaUsuarios').val();
		
		selecionados = selecionados.splice(0,Number.MAX_VALUE, usuariosAtivos);
		
		//Atualização dos Selecionados
		//Após nenhum grupo ser selecionado
		if(usuariosAtivos > aux3){
			selecionados = selecionados.concat(atualizaSelecionados(selecionados, atualizaSelecionados(usuariosAtivos, aux3)));
		}else{
			selecionados = selecionados.concat(atualizaSelecionados(aux3, usuariosAtivos));	
		}
		
		usuariosAtivos = selecionados;

		$('#listaUsuarios').selectpicker('deselect');
		$('#listaUsuarios').selectpicker('val',usuariosAtivos);
		
		alert('Nenhum grupo selecionado');
	}
	$('#listaUsuarios').selectpicker('refresh');
	
	///ordena as listas "elas são ordenadas como array de String"
	selecionados.sort();
	usuariosAtivos.sort();
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

