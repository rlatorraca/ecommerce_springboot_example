package ca.com.rlsp.ecommerce.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpSessionListener;


/* WebSecurityConfigurerAdapter => para usar algumas classes do Spring Security */
/* HttpSessionListener => para implamentar interceptadores e alguns recursos de seguranca (ex: logout)*/


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {


    /* Ignora autenticacao das URLS abaixo para GET, DELETE e POST*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/roleAccess/**")
                .antMatchers(HttpMethod.DELETE, "/roleAccess/**")
                .antMatchers(HttpMethod.POST, "/roleAccess/**");
    }

    /* Parte de Seguranca da API*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
