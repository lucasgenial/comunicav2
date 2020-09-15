package br.com.cicom.comunicacicom.DSPrimary.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Permissao;
import br.com.cicom.comunicacicom.DSPrimary.model.seguranca.Usuario;
import br.com.cicom.comunicacicom.DSPrimary.service.seguranca.UsuarioService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioService service;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario user = service.buscaPeloLogin(login);
//		System.out.println(user);
		if (user == null) throw new UsernameNotFoundException(login); {
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Permissao role : user.getGrupo().getPermissoes()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getNome()));
			}
//			System.out.println("Permissões: " + grantedAuthorities);
			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), grantedAuthorities);
		}
	}
}