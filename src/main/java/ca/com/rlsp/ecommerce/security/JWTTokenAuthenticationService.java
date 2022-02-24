package ca.com.rlsp.ecommerce.security;

import ca.com.rlsp.ecommerce.ApplicationContextLoad;
import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*
Essa classe terá Tem 2 FUNCOES:
 1) Criar Autenticacao JWT
 2) Retornar a Autenticao JWT
 */

@Service
public class JWTTokenAuthenticationService{

    /* Tempo de validade do Token  (30 dias) */
    private static final Long EXPIRATINN_TIME = 2592000000L;

    /* Chave de seguranca para ser usada na criptografia (Md5, base64 ,etc)*/
    private static final String SECRET = "db96ff26706a1a3d595ecb67266c2d94";

    /* Prefixo do TOKEN (Bearer ,API key )*/
    private static final String TOKEN_PREFIX = "Bearer";

    /* Prefixo do Cabecalho de Retorno do pedido de autenticacao (Header) */
    private static final String HEADER_STRING = "Authorization";

    private static final String INVALID_TOKEN = "TOKEN IS INVALID [RLSP]";
    private static final String EXPIRED_LOGIN = "LOGIN TIME IS EXPIRED. Get a new TOKEN! [RLSP]";

    /* Gera o Token e envia a resposta para o cliente com JWT*/
    /* Recebe o username mas a validacao e manda um retorno para o cliente*/
    public void addAuthentication(HttpServletResponse response, String username) throws Exception{

        /* Montagem do Token*/

        /* JWT part*/
        String jwt = Jwts
                        .builder() /* Chama o gerador de Token */
                        .setSubject(username)  /* Adiciona o user */
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATINN_TIME)) /* Tempo de Expiracao */
                        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /* Assina e compacta o token */

        /* Agrupa toda informacao do Token */
        /* Ex: Bearer ajdofijasoijfdaiojsdoajsodjaoisjdoaijsdasdoajsd */
        String token = TOKEN_PREFIX + " " + jwt;

        handleCORSErrorsByBrowser(response);

        /* Retorna para a TELA e para o CLIENTE (API, browser, app, JS, Java App, etc) */
        /* 1) No Cabecalho*/
        response.addHeader(HEADER_STRING, token);

        /* 2) No corpo da resposta  (USADO no POSTMAN */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    /* Retorna o USUARIO ja VERIFICADO e VAlIDADO ou caso nao seja valido Retornara NULL */
    public Authentication getAuthtentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = request.getHeader(HEADER_STRING);

        try{

            if(token != null) {
                String tokenCleaned = token.replace(TOKEN_PREFIX, "").trim();

                /* Extrai do token do USERNAME que veio pela requisicao (request) */
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenCleaned)
                        .getBody()
                        .getSubject(); /* O username esta no corpo da requisicao do token*/

                if(user != null) {
                    UserSystem userSystem = ApplicationContextLoad.getApplicationContext()
                            .getBean(UserSystemRepository.class)
                            .findUserSystemByLogin(user);
                    if(userSystem != null) {
                        return new UsernamePasswordAuthenticationToken(
                                userSystem.getLogin(),
                                userSystem.getPassword(),
                                userSystem.getAuthorities());

                    }
                }
            }
        } catch (SignatureException e){
                response.getWriter().write(INVALID_TOKEN);
        } catch (ExpiredJwtException e){
            response.getWriter().write(EXPIRED_LOGIN);
        } finally {
            handleCORSErrorsByBrowser(response);
        }

        return null;
    }


    /* Autoriza ou Libera acesso quando existe problema de CORS no browser */
    private void handleCORSErrorsByBrowser(HttpServletResponse response) {

        /* Verifica parametros de CORS */
        if(response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*"); /* * => acesso total */
        }

        if(response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*"); /* * => acesso total */
        }

        if(response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*"); /* * => acesso total */
        }

        if(response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*"); /* * => acesso total */
        }
    }


}
