/**
 * Arquivo de configuração da validação via Javascript do formulário de Cadastro e edição de emails
 */

document.addEventListener('DOMContentLoaded', function(e) {
    FormValidation.formValidation(document.getElementById('formEmail'),{
		fields : {
			nome : {
				icon : true,
				validators : {
					notEmpty : {
						message : 'O campo nome não pode está em branco'
					},
					stringLength : {
						min : 4,
						max : 50,
						message : 'valores entre 4 e 50 caracteres'
					},
				}
			},
		
			enderecoDeEmail : {
				icon : true,
				validators : {
					
					notEmpty : {
						message : 'O campo nome não pode está em branco'
					},
					regexp: {
	                      regexp: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/,
	                      message:'O formato do email é invalido'
	                  },
				}
			}
		}
	});
});

