package ca.com.rlsp.ecommerce.service;

/* Serva para autenticar com o UserSystemRepository */

import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserSystemDetailsServiceImpl implements UserDetailsService {

    private static final String MSG_NO_USER_FOUND = "User not found ! [RLSP]";

    private UserSystemRepository userSystemRepository;


    public UserSystemDetailsServiceImpl(UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    /* Ajuda na autenticao JWT */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSystem userSystem = userSystemRepository.findUserSystemByLogin(username); /* Recebe o Login para a consulta */

        if(userSystem == null) {
            throw new UsernameNotFoundException(MSG_NO_USER_FOUND);
        }

        /* Passamos como deve ser consultado o UserSystems*/
        return new User(userSystem.getLogin(),userSystem.getPassword(),userSystem.getAuthorities()); /* Retorna as info do User*/
    }
}
