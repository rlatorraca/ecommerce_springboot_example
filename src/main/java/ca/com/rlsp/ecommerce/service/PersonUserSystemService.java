package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.PersonRepository;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
            }

            usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPj.setEmpresa(juridica);
            usuarioPj.setPessoa(juridica);
            usuarioPj.setLogin(juridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPj.setSenha(senhaCript);

            usuarioPj = usuarioRepository.save(usuarioPj);

            usuarioRepository.insereAcessoUserPj(usuarioPj.getId());

            /*Fazer o envio de e-mail do login e da senha*/

        }

        return juridica;

    }

}
