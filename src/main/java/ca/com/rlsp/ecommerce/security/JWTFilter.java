package ca.com.rlsp.ecommerce.security;

import ca.com.rlsp.ecommerce.model.UserSystem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* Filtro para usar o servico da aplicacao  */
public class JWTFilter extends AbstractAuthenticationProcessingFilter {


    /* Configura o gerenciador de Autenticacao */
    protected JWTFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {

        /* Obriga a autenticacao da URL */
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));

        /* Seta o gerenciador de Autenticacao */
        setAuthenticationManager(authenticationManager);
    }



    /* Retorna o USUARIO ao processar a autenticacao */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /* Obtem o Usuario vindo da requisicao*/
        UserSystem userSystem = new ObjectMapper().readValue(request.getInputStream(), UserSystem.class);


        /* Retorna USER com LOGIN e SENHA */
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(userSystem.getLogin(),userSystem.getPassword()));
    }

    /* Cria um objeto de JWTTokenAuthenticationService (classe que foir criada) */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        try {
            new JWTTokenAuthenticationService().addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
