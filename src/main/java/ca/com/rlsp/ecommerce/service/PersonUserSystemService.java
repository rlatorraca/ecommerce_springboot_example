package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.PersonRepository;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PersonUserSystemService {

    private UserSystemRepository userSystemRepository;

    public PersonUserSystemService(UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public LegalPerson salvarPessoaJuridica(LegalPerson legalPerson) {

        //juridica = pesssoaRepository.save(juridica);

        for (int i = 0; i< legalPerson.getAddresses().size(); i++) {
            legalPerson.getAddresses().get(i).setPerson(legalPerson);
            legalPerson.getAddresses().get(i).setEcommerceCompany(legalPerson);
        }

        legalPerson = personRepository.save(legalPerson);

        UserSystem userLegalPerson = userSystemRepository.findUserSystemByPerson(legalPerson.getId(), legalPerson.getEmail());

        if (userLegalPerson == null) {

            String constraint = personRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
            }

            userLegalPerson = new UserSystem();
            userLegalPerson.setLastPasswordDate(Calendar.getInstance().getTime());
            userLegalPerson.setEcommerceCompany(legalPerson);
            userLegalPerson.setPerson(legalPerson);
            userLegalPerson.setLogin(legalPerson.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            userLegalPerson.setPassword(senhaCript);

            userLegalPerson = userSystemRepository.save(userLegalPerson);

            userSystemRepository.insereAcessoUserPj(userLegalPerson.getId());

            /*Fazer o envio de e-mail do login e da senha*/

        }

        return legalPerson;

    }

}
