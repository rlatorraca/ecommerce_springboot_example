package ca.com.rlsp.ecommerce.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* FILTRA todos os REQUEST, capturando para ser usado na Autenticacao */
public class JwtApiAuthenticationFilter extends GenericFilterBean {
    private static final String SYSTEM_ERROR_ADMINISTRATOR_MESSAGE = "SYSTEM ERROR CONTACT AN ADMINSITRATOR [RLSP]";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        /* Try/Catch para tratar as excecoes nao pegas no ExceptionController.class */

        try {
            /* Estabelece a Autenticacao do USUARIO */
            Authentication authentication = new JWTTokenAuthenticationService()
                    .getAuthtentication(
                            (HttpServletRequest) servletRequest,
                            (HttpServletResponse)  servletResponse
                    );

            /* Faz a conexao das classes criadas pelo APP com o Spring Security*/
            SecurityContextHolder.getContext().setAuthentication(authentication);

            /* Continua a cadeia de conexao junto a aplicacao */
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            e.getStackTrace();
            servletResponse.getWriter().write(SYSTEM_ERROR_ADMINISTRATOR_MESSAGE + "\n " + e.getMessage());
        }

    }
}
