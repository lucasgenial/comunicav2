package br.com.cicom.comunicacicom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Usuario.class.equals(aClass);
    }
  
    @Override
    public void validate(Object o, Errors errors) {
        Usuario user = (Usuario) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getLogin().length() < 6 || user.getLogin().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (usuarioService.buscaPeloLogin(user.getLogin()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    }
}