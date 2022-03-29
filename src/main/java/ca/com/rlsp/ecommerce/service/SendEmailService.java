package ca.com.rlsp.ecommerce.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class SendEmailService {

    private String userName = "rlsprojects.ca@gmail.com";
    private String pwd = "";

    @Async
    public void sendEmailHtml(String subject, String msg, String destination)
            throws MessagingException, UnsupportedEncodingException {

        // Parametros para enviar email por meio do gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, pwd);
            }

        });

        session.setDebug(true); // Mostra os dados no console em caso de erro no envio de email

        Address[] toUser = InternetAddress.parse(destination);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "RLSP - e-Commerce", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(subject);
        message.setText(msg);

        Transport.send(message);
    }
}
