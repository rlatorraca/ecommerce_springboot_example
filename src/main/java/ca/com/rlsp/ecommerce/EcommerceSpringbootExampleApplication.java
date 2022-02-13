package ca.com.rlsp.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ca.com.rlsp.ecommerce.repository"}) // Gerencia e aponta para o base de pacotes de repositorios
@EnableTransactionManagement// Gerencia as transacoes com o BD
public class EcommerceSpringbootExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceSpringbootExampleApplication.class, args);
    }

}
