package ca.com.rlsp.ecommerce;

/* Usada para solucionar problemas / carregar / recuperar Objetos ou Services do Contexto gerenciado pelo Spring*/
/* Principamente, em casos  em que a recuperacao de objetos com a injecao de dependencia possa falhar */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/* ApplicationContextAware => traz o contexto do Spring*/
@Component
public class ApplicationContextLoad implements ApplicationContextAware {

    /* Objeto estatico de contexto */
    private ApplicationContext applicationContext;

    public ApplicationContextLoad(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
