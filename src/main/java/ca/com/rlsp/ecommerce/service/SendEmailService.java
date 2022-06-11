package ca.com.rlsp.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@Configuration
@PropertySource(value="classpath:email.properties")
public class SendEmailService {

    @Autowired
    private Environment env; // carrega o @PropertySource aqui dentro
    @Value("${email.username}")
    private String userName;

    @Value("${email.pwd}")
    private String pwd;

    @Async
    public void sendEmailHtml(String subject, String msg, String destination)
            throws MessagingException, UnsupportedEncodingException {

        // Parametros para enviar email por meio do gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth", "true");
;
                Session session = Session.getInstance(properties, new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                       // return new PasswordAuthentication(env.getRequiredProperty("email.username"), env.getRequiredProperty("email.pwd"));
                        System.out.println(userName);
                        System.out.println(pwd);
                        return new PasswordAuthentication(userName, pwd );
                    }

        });

        session.setDebug(true); // Mostra os dados no console em caso de erro no envio de email

        Address[] toUser = InternetAddress.parse(destination);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "RLSP - e-Commerce", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(subject);
        message.setContent(msg,"text/html; charset=utf-8");

        Transport.send(message);
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

}
