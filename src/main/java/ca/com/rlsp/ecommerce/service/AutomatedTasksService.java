package ca.com.rlsp.ecommerce.service;


import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class AutomatedTasksService {

    private UserSystemRepository userSystemRepository;
    private SendEmailService sendEmailService;

    public AutomatedTasksService(UserSystemRepository userSystemRepository, SendEmailService sendEmailService) {
        this.userSystemRepository = userSystemRepository;
        this.sendEmailService = sendEmailService;
    }

    // Inicia apos 2 segundos do projeto deployed [initialDelay]
    // Roda a cada 24h (em milissegundos) [fixedDelay]

    //@Scheduled(cron = "0 0 11 * * * ", zone = "America/Halifax") - com o CRON
    @Scheduled(initialDelay = 2000, fixedDelay = 86400000)
    public void notifyUserChangePassword() throws UnsupportedEncodingException, InterruptedException, MessagingException {

        List<UserSystem> allUsersMore90Days = userSystemRepository.userPasswordMoreThan90Days();

        for (UserSystem userSystem : allUsersMore90Days) {
            StringBuilder msg = new StringBuilder();
            msg.append("Hi, ").append(userSystem.getPerson().getName()).append("<br/ >");
            msg.append("Passed 90 days of your last password update, That is time to change it for your security").append("<br/>");
            msg.append("A reminder of RLSP - eCommerce");

            sendEmailService.sendEmailHtml("Change your password", msg.toString(), userSystem.getLogin());

            Thread.sleep(500);

        }
    }
}
