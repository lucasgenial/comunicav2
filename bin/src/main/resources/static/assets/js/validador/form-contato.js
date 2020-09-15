/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de login
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(document.getElementById('formContato'),
    {
		fields : {
			nome : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo nome não pode está em branco'
					}
				}
			},
			email : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo email não pode está em branco'
					}					
				}
			},
			estabelecimento : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo estabelecimento não pode está em branco'
					}					
				}
			},
			mensagem : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O Campo mensagem não pode está em branco'
					}					
				}
			},
		}
	});
});



