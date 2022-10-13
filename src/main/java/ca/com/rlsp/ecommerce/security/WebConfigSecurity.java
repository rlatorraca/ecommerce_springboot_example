package ca.com.rlsp.ecommerce.security;

import ca.com.rlsp.ecommerce.service.UserSystemDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;


/* WebSecurityConfigurerAdapter => para usar algumas classes do Spring Security */
/* HttpSessionListener => para implamentar interceptadores e alguns recursos de seguranca (ex: logout)*/


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    private UserSystemDetailsServiceImpl userSystemDetailsService;

    public WebConfigSecurity(UserSystemDetailsServiceImpl userSystemDetailsService) {
        this.userSystemDetailsService = userSystemDetailsService;
    }

    /* Ignora autenticacao das URLS abaixo para GET, DELETE e POST*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        /*
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/roleAccess/**")
                .antMatchers(HttpMethod.DELETE, "/roleAccess/**")
                .antMatchers(HttpMethod.POST, "/roleAccess/**");
         */
    }

    /* Parte de Seguranca da API*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* Ativa a o nivel de autenticao por meio de tokens, bloqueando usuarios sem autenticacao */
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable()
                    .authorizeRequests()
                        .antMatchers("/").permitAll()
                        .antMatchers("index").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // evita bloqueio de CORS no Browser

                    /* REDIRECIONA ou RETORNA ao INEDEX ao deslogadr do APP */
                        .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

                    /* Mapeia a parte de LOGOUT */
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                    /* Aponta os Filtros das requisoces de acesso para as classes de Filtros que foram criadas na APP */
                    .and()
                        .addFilterBefore(new JwtApiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                        .addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
                                            UsernamePasswordAuthenticationFilter.class)
        ;

        /* Ativa o ACESSO LIVRE para o contexto Principal do App - TELA DE LOGIN */



    }

    /* Irá consultar o UserSystem no DB usando o Spring Security*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSystemDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}
