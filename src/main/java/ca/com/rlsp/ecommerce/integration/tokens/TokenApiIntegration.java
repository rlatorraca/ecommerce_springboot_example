package ca.com.rlsp.ecommerce.integration.tokens;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource(value="classpath:tokensapi.properties")
public class TokenApiIntegration {

    @Value("${token.melhorenvio.prod}")
    public static String TOKEN_API_INTEGRATION_MELHORENVIO_PROD;

    @Value("${token.melhorenvio.sandbox}")
    public static String TOKEN_API_INTEGRATION_MELHORENVIO_SANDBOX;

    @Value("${url.melhorenvio.sandbox}")
    public static String URL_MELHORENVIO_SANDBOX;




}
