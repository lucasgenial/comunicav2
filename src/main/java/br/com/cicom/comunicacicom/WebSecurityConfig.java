package br.com.cicom.comunicacicom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.cicom.comunicacicom.DSPrimary.service.security.CustomUserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired(required=true)
	private CustomUserDetailsService ssUserDetailsService;

	private BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(ssUserDetailsService).configure(auth);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(ssUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().csrf().disable()
				.formLogin().loginPage("/").permitAll().usernameParameter("login").passwordParameter("senha").defaultSuccessUrl("/redirecionaUsuario", true)
				.failureUrl("/").failureForwardUrl("/?error").and().exceptionHandling().accessDeniedPage("/accesso_negado")
				
				.and().authorizeRequests()
				//Acessível a quem não tem usuário ou não está logado
				.antMatchers("/").permitAll()
				.antMatchers("/registro").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/recupera").permitAll()
				.antMatchers("/recuperaSenhaUsuario").permitAll()
				.antMatchers("/cadastrarUsuario").permitAll()
				.antMatchers("/enviado").permitAll()
				.antMatchers("/contato").permitAll()
				.antMatchers("/cadastrarContato").permitAll()
				.antMatchers("/contatos/cadastro").permitAll()
				.antMatchers("/accesso_negado").permitAll()
				.antMatchers("/index").permitAll()
				.antMatchers("/.well-known/pki-validation/*").permitAll()
					
				//Acessos a ocorrências
				.antMatchers("/*/cadastra/ocorrencia/**").hasAuthority("CADASTRAR_OCORRENCIA")
				.antMatchers("/*/visualiza/ocorrencia/**").hasAuthority("VISUALIZAR_OCORRENCIA")
				.antMatchers("/*/historico/ocorrencias/**").hasAuthority("HISTORICO_OCORRENCIAS")
				.antMatchers("/*/pesquisa/ocorrencias/**").hasAuthority("PESQUISAR_OCORRENCIAS")
				.antMatchers("/*/edita/ocorrencia/**").hasAuthority("EDITAR_OCORRENCIA")
				.antMatchers("/*/ocorrencia/visualizar/comunica/**","/*/ocorrencias/visualizar/externo/***").hasAnyAuthority("VISUALIZAR_OCORRENCIA_EXTERNO")
				.antMatchers("/*/ocorrencia/visualizar/comunica/**","/*/ocorrencias/visualizar/externo/***").hasIpAddress("0.0.0.0/0")
				
				//Acessos a mesas
				.antMatchers("/*/cadastra/mesa/**").hasAuthority("CADASTRAR_MESA")
				.antMatchers("/*/visualiza/mesa/**").hasAuthority("VISUALIZAR_MESA")
				.antMatchers("/*/historico/mesas/**").hasAuthority("HISTORICO_MESAS")
				.antMatchers("/*/edita/mesa/**").hasAuthority("EDITAR_MESA")
				.antMatchers("/*/pesquisa/mesas/**").hasAuthority("PESQUISAR_MESAS")
				
				
				//Acessos a policiamentos
				.antMatchers("/*/cadastra/policiamento/**").hasAuthority("CADASTRAR_POLICIAMENTO")
				.antMatchers("/*/visualiza/policiamento/**").hasAuthority("VISUALIZAR_POLICIAMENTO")
				.antMatchers("/*/historico/policiamentos/**").hasAuthority("HISTORICO_POLICIAMENTOS")
				.antMatchers("/*/pesquisa/policiamentos/**").hasAuthority("PESQUISAR_POLICIAMENTOS")
				.antMatchers("/*/historico/policiamentos/ativos/**").hasAuthority("HISTORICO_POLICIAMENTOS_ATIVOS")
				.antMatchers("/*/edita/policiamento/**").hasAuthority("EDITAR_POLICIAMENTO")
						
				//Acessos a visitas
				.antMatchers("/*/cadastra/visita").hasAuthority("CADASTRAR_VISITA")
				.antMatchers("/*/visualiza/visita/**").hasAuthority("VISUALIZAR_VISITA")
				.antMatchers("/*/historico/visitas/**").hasAuthority("HISTORICO_VISITAS")
				.antMatchers("/*/edita/visita/**").hasAuthority("EDITAR_VISITA")				
				
				//Acessos a usuarios
				.antMatchers("/*/cadastra/usuario").hasAuthority("CADASTRAR_USUARIO")
				.antMatchers("/*/visualiza/usuario/**").hasAuthority("VISUALIZAR_USUARIO")
				.antMatchers("/*/historico/usuarios/**").hasAuthority("HISTORICO_USUARIOS")
				.antMatchers("/*/edita/usuario/**").hasAuthority("EDITAR_USUARIO")				
					
				//Acessos a servidores
				.antMatchers("/*/cadastra/servidor").hasAuthority("CADASTRAR_SERVIDOR")
				.antMatchers("/*/visualiza/servidor/**").hasAuthority("VISUALIZAR_SERVIDOR")
				.antMatchers("/*/historico/servidores/**").hasAuthority("HISTORICO_SERVIDORES")
				.antMatchers("/*/edita/servidor/**").hasAuthority("EDITAR_SERVIDOR")		
				
				//Acessos a servidores externos
				.antMatchers("/*/cadastra/servidorExterno").hasAuthority("CADASTRAR_SERVIDOR_EXTERNO")
				.antMatchers("/*/visualiza/servidorExterno/**").hasAuthority("VISUALIZAR_SERVIDOR_EXTERNO")
				.antMatchers("/*/historico/servidoresExternos/**").hasAuthority("HISTORICO_SERVIDORES_EXTERNOS")
				.antMatchers("/*/edita/servidorExterno/**").hasAuthority("EDITAR_SERVIDOR_EXTERNO")				
				
				//Acessos a relatórios estatísticos
				.antMatchers("/*/registroOcorrencias/cvli/**").hasAuthority("RELATORIO_DADOS_PERFIL_CVLI")
				.antMatchers("/*/registroOcorrencias/roubosEFurtos/**").hasAuthority("RELATORIO_DADOS_PERFIL_ROUBOS_E_FURTOS")
				.antMatchers("/*/relatorio/gerarDadosViolenciaContraAMulher/**").hasAuthority("RELATORIO_DADOS_VIOLENCIA_CONTRA_MULHER")
				.antMatchers("/*/relatorio/dadosEstatisticos**").hasAuthority("RELATORIO_DADOS_ESTATISTICOS")
				.antMatchers("/*/historico/registroOcorrencias**").hasAuthority("HISTORICO_REGISTRO_OCORRENCIAS")
				
				
				//###########################################################//
				//               Acessado por administradores                //
				//###########################################################//
				

				//Acessos a auditoria
				.antMatchers("/*/historico/auditorias/**").hasAuthority("HISTORICO_AUDITORIAS")
				
				//Acessos a permissoes
				.antMatchers("/*/historico/permissoes/**").hasAuthority("HISTORICO_PERMISSOES")
				.antMatchers("/*/edita/permissoes/**").hasAuthority("EDITAR_PERMISSAO")
				.antMatchers("/*/visualiza/permissoes/**").hasAuthority("VISUALIZAR_PERMISSAO")
				.antMatchers("/*/cadastra/permissoes/**").hasAuthority("CADASTRAR_PERMISSAO")
				
				//Acessos a grupos
				.antMatchers("/*/historico/grupos/**").hasAuthority("HISTORICO_GRUPOS")
				.antMatchers("/*/edita/grupo/**").hasAuthority("EDITAR_GRUPO")
				.antMatchers("/*/visualiza/grupo/**").hasAuthority("VISUALIZAR_GRUPO")
				.antMatchers("/*/cadastra/grupo/**").hasAuthority("CADASTRAR_GRUPO")

				//Acessos a emails
				.antMatchers("/*/historico/emails/**").hasAuthority("HISTORICO_EMAILS")
				.antMatchers("/*/edita/email/**").hasAuthority("EDITAR_EMAIL")
				.antMatchers("/*/visualiza/email/**").hasAuthority("VISUALIZAR_EMAIL")
				.antMatchers("/*/cadastra/email/**").hasAuthority("CADASTRAR_EMAIL")
				
				//Acessos a tipificacoes
				.antMatchers("/*/historico/tipificacoes/**").hasAuthority("HISTORICO_TIPIFICACOES")
				.antMatchers("/*/edita/tipificacao/**").hasAuthority("EDITAR_TIPIFICACAO")
				.antMatchers("/*/visualiza/tipificacao/**").hasAuthority("VISUALIZAR_TIPIFICACAO")
				.antMatchers("/*/cadastra/tipificacao/**").hasAuthority("CADASTRAR_TIPIFICACAO")		
				
				//Acessos a servicos
				.antMatchers("/*/historico/servicos/**").hasAuthority("HISTORICO_SERVICOS")
				.antMatchers("/*/edita/servico/**").hasAuthority("EDITAR_SERVICO")
				.antMatchers("/*/visualiza/servico/**").hasAuthority("VISUALIZAR_SERVICO")
				.antMatchers("/*/cadastra/servico/**").hasAuthority("CADASTRAR_SERVICO")
				
				//Acessos a estabelecimentos
				.antMatchers("/*/historico/estabelecimentos/**").hasAuthority("HISTORICO_ESTABELECIMENTOS")
				.antMatchers("/*/edita/estabelecimento/**").hasAuthority("EDITAR_ESTABELECIMENTO")
				.antMatchers("/*/visualiza/estabelecimento/**").hasAuthority("VISUALIZAR_ESTABELECIMENTO")
				.antMatchers("/*/cadastra/estabelecimento/**").hasAuthority("CADASTRAR_ESTABELECIMENTO")
				
				
				//Acessos a unidades
				.antMatchers("/*/historico/unidades/**").hasAuthority("HISTORICO_UNIDADES")
				.antMatchers("/*/edita/unidade/**").hasAuthority("EDITAR_UNIDADE")
				.antMatchers("/*/visualiza/unidade/**").hasAuthority("VISUALIZAR_UNIDADE")
				.antMatchers("/*/cadastra/unidade/**").hasAuthority("CADASTRAR_UNIDADE")
				
				//Acessos a instituicoes
				.antMatchers("/*/historico/instituicoes/**").hasAuthority("HISTORICO_INSTITUICOES")
				.antMatchers("/*/edita/instituicao/**").hasAuthority("EDITAR_INSTITUICAO")
				.antMatchers("/*/visualiza/instituicao/**").hasAuthority("VISUALIZAR_INSTITUICAO")
				.antMatchers("/*/cadastra/instituicao/**").hasAuthority("CADASTRAR_INSTITUICAO")
				
				//Acessos a modalidades
				.antMatchers("/*/historico/modalidades/**").hasAuthority("HISTORICO_MODALIDADES")
				.antMatchers("/*/edita/modalidade/**").hasAuthority("EDITAR_MODALIDADE")
				.antMatchers("/*/visualiza/modalidade/**").hasAuthority("VISUALIZAR_MODALIDADE")
				.antMatchers("/*/cadastra/modalidade/**").hasAuthority("CADASTRAR_MODALIDADE")
				
				
				//Acessos a funcoes
				.antMatchers("/*/historico/funcoes/**").hasAuthority("HISTORICO_FUNCOES")
				.antMatchers("/*/edita/funcao/**").hasAuthority("EDITAR_FUNCAO")
				.antMatchers("/*/visualiza/funcao/**").hasAuthority("VISUALIZAR_FUNCAO")
				.antMatchers("/*/cadastra/funcao/**").hasAuthority("CADASTRAR_FUNCAO")
				
				
				//Acessos a caracteristicas
				.antMatchers("/*/historico/caracteristicas/**").hasAuthority("HISTORICO_CARACTERISTICAS")
				.antMatchers("/*/edita/caracteristica/**").hasAuthority("EDITAR_CARACTERISTICA")
				.antMatchers("/*/visualiza/caracteristica/**").hasAuthority("VISUALIZAR_CARACTERISTICA")
				.antMatchers("/*/cadastra/caracteristica/**").hasAuthority("CADASTRAR_CARACTERISTICA")
				
				//Acessos a recursos
				.antMatchers("/*/historico/recursos/**").hasAuthority("HISTORICO_RECURSOS")
				.antMatchers("/*/edita/recurso/**").hasAuthority("EDITAR_RECURSO")
				.antMatchers("/*/visualiza/recurso/**").hasAuthority("VISUALIZAR_RECURSO")
				.antMatchers("/*/cadastra/recurso/**").hasAuthority("CADASTRAR_RECURSO")
				
				//Acessos a tipos serviços
				.antMatchers("/*/historico/tipoServicos/**").hasAuthority("HISTORICO_TIPO_SERVICOS")
				.antMatchers("/*/edita/tipoServico/**").hasAuthority("EDITAR_TIPO_SERVICO")
				.antMatchers("/*/visualiza/tipoServico/**").hasAuthority("VISUALIZAR_TIPO_SERVICO")
				.antMatchers("/*/cadastra/tipoServico/**").hasAuthority("CADASTRAR_TIPO_SERVICO")
				

				//###########################################################//
				//                          Localização                      //
				//###########################################################//
				//Acessos a cidades
				.antMatchers("/*/cadastra/cidade/**").hasAuthority("CADASTRAR_CIDADE")
				.antMatchers("/*/edita/cidades/**").hasAuthority("EDITAR_CIDADES")
				.antMatchers("/*/visualiza/cidade/**").hasAuthority("VISUALIZAR_CIDADE")
				.antMatchers("/*/historico/cidades/**").hasAuthority("HISTORICO_CIDADES")
				.antMatchers("/*/pesquisa/cidades/**").hasAuthority("PESQUISAR_CIDADES")
				
				//Acessos a localidades
				.antMatchers("/*/cadastra/localidade/**").hasAuthority("CADASTRAR_LOCALIDADE")
				.antMatchers("/*/edita/localidade/**").hasAuthority("EDITAR_LOCALIDADE")
				.antMatchers("/*/visualiza/localidade/**").hasAuthority("VISUALIZAR_LOCALIDADE")
				.antMatchers("/*/historico/localidades/**").hasAuthority("HISTORICO_LOCALIDADES")
				.antMatchers("/*/pesquisa/localidades/**").hasAuthority("PESQUISAR_LOCALIDADES")
				
				//Acessos a bairros
				.antMatchers("/*/cadastra/bairro/**").hasAuthority("CADASTRAR_BAIRRO")
				.antMatchers("/*/edita/bairro/**").hasAuthority("EDITAR_BAIRRO")
				.antMatchers("/*/visualiza/bairro/**").hasAuthority("VISUALIZAR_BAIRRO")
				.antMatchers("/*/historico/bairros/**").hasAuthority("HISTORICO_BAIRROS")
				.antMatchers("/*/pesquisa/bairros/**").hasAuthority("PESQUISAR_BAIRROS")				
				.anyRequest().access("( isAuthenticated() and hasIpAddress('10.48.0.0/16') )"
				           + " OR ( hasAnyAuthority('OCORRENCIAS VIA LINK','COORDENADOR-SUPERVISOR','ADMINISTRADOR','MASTER') and hasIpAddress('0.0.0.0/0') )")
				
				
				//Configurações finais
				//Força Conexão SSL 443
				//Comentar para executar o projeto no TomCat Embarcado
				//.and().requiresChannel().anyRequest().requiresSecure()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				.and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/");

	}

	@Override
	public void configure(WebSecurity web){
		web.ignoring().antMatchers("/assets/**", "/static/**", "/css/**", "/js/**", "/resources/**", "/imagens/**");
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Until https://jira.spring.io/browse/SEC-2855 is closed, we need to have this
	 * custom sessionRegistry
	 */
	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

}
