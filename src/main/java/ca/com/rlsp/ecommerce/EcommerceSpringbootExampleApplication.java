package ca.com.rlsp.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = {"ca.com.rlsp.ecommerce.repository"}) // Gerencia e aponta para o base de pacotes de repositorios
@EnableTransactionManagement// Gerencia as transacoes com o BD
public class EcommerceSpringbootExampleApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceSpringbootExampleApplication.class, args);
    }


    /* Para rodar os envio de email em batch*/
    @Override
    @Bean
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Assyncrono Thread");
        executor.initialize();

        return executor;
    }
}
