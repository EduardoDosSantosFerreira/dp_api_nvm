package com.github.wesleyav.cloudparking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Classe de configuração de segurança da web
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Configuração de segurança HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests()
	            // Permite acesso a várias rotas sem autenticação
	            .antMatchers("/swagger-ui/index.html").permitAll()
	            .antMatchers("/swagger-resources/**").permitAll()
	            .antMatchers("/webjars/**").permitAll()
	            .antMatchers("/v2/api/docs/**").permitAll()
	            .antMatchers("/").permitAll()
	            .antMatchers("/csrf").permitAll()
	            .antMatchers("/*.js").permitAll()
	            .antMatchers("/*.css").permitAll()
	            .antMatchers("/*.ico").permitAll()
	            .antMatchers("/*.png").permitAll()	       
	            // Requer autenticação para qualquer outra requisição
	            .anyRequest().authenticated()
	            .and().httpBasic() // Usa autenticação básica HTTP
	            .and()
	            // Define a política de criação de sessão como STATELESS
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	// Configuração de autenticação em memória
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
                .withUser("user") // Define um usuário em memória
                .password(passwordEncoder().encode("123456789")) // Define a senha do usuário
                .roles("USER") // Define o papel do usuário
                .and()
                .passwordEncoder(passwordEncoder()); // Define o codificador de senha
    }
	
	// Bean para codificador de senha
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Retorna uma instância de BCryptPasswordEncoder
	}
}
