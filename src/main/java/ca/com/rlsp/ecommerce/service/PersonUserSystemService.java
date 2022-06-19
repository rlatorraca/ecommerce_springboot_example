package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.model.Person;
import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.model.dto.PostalCodeDTO;
import ca.com.rlsp.ecommerce.model.dto.QueryBusinessNumberDTO;
import ca.com.rlsp.ecommerce.repository.LegalPersonRepository;
import ca.com.rlsp.ecommerce.repository.NaturalPersonRepository;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.util.Calendar;

@Service
public class PersonUserSystemService {


    private UserSystemRepository userSystemRepository;
    private LegalPersonRepository legalPersonRepository;

    private NaturalPersonRepository naturalPersonRepository;
    private JdbcTemplate jdbcTemplate;
    private SendEmailService sendEmailService;

    public PersonUserSystemService(UserSystemRepository userSystemRepository,
                                   LegalPersonRepository legalPersonRepository,
                                   NaturalPersonRepository naturalPersonRepository,
                                   JdbcTemplate jdbcTemplate,
                                   SendEmailService sendEmailService) {
        this.userSystemRepository = userSystemRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.naturalPersonRepository = naturalPersonRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.sendEmailService = sendEmailService;
    }

    public LegalPerson saveLegalPerson(LegalPerson legalPerson) throws MessagingException {


        // Salva empresa no BD
        //legalPerson = personRepository.save(legalPerson);

        for (int i = 0; i< legalPerson.getAddresses().size(); i++) {
           legalPerson.getAddresses().get(i).setPerson(legalPerson);
           legalPerson.getAddresses().get(i).setEcommerceCompany(legalPerson);
        }

        legalPerson = legalPersonRepository.save(legalPerson);

        // Verifica se existe usuario com mesmo ID and Email
        UserSystem userLegalPerson = userSystemRepository.findUserSystemByPerson(
                                                                legalPerson.getId(),
                                                                legalPerson.getEmail());

        // Se NAO existir o Usuario de Sistema criado
        // Cria um Usuario de Sistema se a Empresa nao tiver usuario criado
        if (userLegalPerson == null) {

            // Verifica se existe uma constraint no BD que nao permite a gravacao de Access_id na tabela de conexao
            //    entra as tabelas User_System e role_access (chamada de user_role_access)
            String constraintErrorUserLegalPersonRoleAccess = userSystemRepository.queryConstraintUserRoleAcessoTable();
            if (constraintErrorUserLegalPersonRoleAccess != null) {
                jdbcTemplate.execute("begin; " +
                                         "alter table user_role_access drop constraint "
                                                + constraintErrorUserLegalPersonRoleAccess +
                                         "; commit;");
            }

            // Cria o novo usuario para Empresa que foi criada e cadastrada
            userLegalPerson = new UserSystem();
            userLegalPerson.setLastPasswordDate(Calendar.getInstance().getTime());
            userLegalPerson.setEcommerceCompany(legalPerson);
            userLegalPerson.setPerson(legalPerson);
            userLegalPerson.setLogin(legalPerson.getEmail());

            String password = "" + Calendar.getInstance().getTimeInMillis();
            String passwordEncoded = new BCryptPasswordEncoder().encode(password);

            userLegalPerson.setPassword(passwordEncoded);

            // Save a UserSystem
            userLegalPerson = userSystemRepository.save(userLegalPerson);

            // Cria um Usuario STANDARD (para acessar o sistema)
            userSystemRepository.insertStandardUserNatualAndLegalPerson(userLegalPerson.getId());
            userSystemRepository.insertStandardUserLegalPerson(userLegalPerson.getId(), "ROLE_ADMIN");

            StringBuilder messageHtml = new StringBuilder();
            /*Fazer o envio de e-mail do login e da senha*/

            try {
                sendEmailService.sendEmailHtml("Generated access to e-Commerce", createEmailHtml(legalPerson, password), legalPerson.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return legalPerson;

    }

    public String createEmailHtml(Person person, String password) {
        StringBuilder messageHtml = new StringBuilder();
        messageHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
        messageHtml.append("<b>Login: </b>"+person.getEmail()+"<br/>");
        messageHtml.append("<b>Senha: </b>").append(password).append("<br/><br/>");
        messageHtml.append("Obrigado!");

        return messageHtml.toString();
    }

    public NaturalPerson saveNaturalPerson(NaturalPerson naturalPerson) {

        for (int i = 0; i< naturalPerson.getAddresses().size(); i++) {
            naturalPerson.getAddresses().get(i).setPerson(naturalPerson);
            //naturalPerson.getAddresses().get(i).setEcommerceCompany(naturalPerson);
        }

        naturalPerson = naturalPersonRepository.save(naturalPerson);

        // Verifica se existe usuario com mesmo ID and Email
        UserSystem userNaturalPerson = userSystemRepository.findUserSystemByPerson(
                naturalPerson.getId(),
                naturalPerson.getEmail());

        // Se NAO existir o Usuario de Sistema criado
        // Cria um Usuario de Sistema se a Empresa nao tiver usuario criado
        if (userNaturalPerson == null) {

            // Verifica se existe uma constraint no BD que nao permite a gravacao de Access_id na tabela de conexao
            //    entra as tabelas User_System e role_access (chamada de user_role_access)
            String constraintErrorUserNaturalPersonRoleAccess = userSystemRepository.queryConstraintUserRoleAcessoTable();
            if (constraintErrorUserNaturalPersonRoleAccess != null) {
                jdbcTemplate.execute("begin; " +
                        "alter table user_role_access drop constraint "
                        + constraintErrorUserNaturalPersonRoleAccess +
                        "; commit;");
            }

            // Cria o novo usuario para Empresa que foi criada e cadastrada
            userNaturalPerson = new UserSystem();
            userNaturalPerson.setLastPasswordDate(Calendar.getInstance().getTime());
            userNaturalPerson.setEcommerceCompany(naturalPerson.getEcommerceCompany());
            userNaturalPerson.setPerson(naturalPerson);
            userNaturalPerson.setLogin(naturalPerson.getEmail());

            String password = "" + Calendar.getInstance().getTimeInMillis();
            String passwordEncoded = new BCryptPasswordEncoder().encode(password);

            userNaturalPerson.setPassword(passwordEncoded);

            // Save a UserSystem
            userNaturalPerson = userSystemRepository.save(userNaturalPerson);

            // Cria um Usuario STANDARD (para acessar o sistema)
            userSystemRepository.insertStandardUserNatualAndLegalPerson(userNaturalPerson.getId());

            StringBuilder messageHtml = new StringBuilder();
            /*Fazer o envio de e-mail do login e da senha*/

            try {
                sendEmailService.sendEmailHtml("Generated access to e-Commerce", createEmailHtml(naturalPerson, password), naturalPerson.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return naturalPerson;
    }

    public PostalCodeDTO fetchPostalCode(String postalCode) {
        return new RestTemplate()
                        .getForEntity("https://viacep.com.br/ws/"+ postalCode +"/json/", PostalCodeDTO.class)
                        .getBody();
    }

    public QueryBusinessNumberDTO fetchBusinnesNumberReceitaFederalWS(String businnessNumber) {
        return new RestTemplate()
                .getForEntity("https://receitaws.com.br/v1/cnpj/"+ businnessNumber, QueryBusinessNumberDTO.class)
                .getBody();
    }
}
