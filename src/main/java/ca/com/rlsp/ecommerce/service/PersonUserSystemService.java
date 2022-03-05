package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.repository.UserSystemRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonUserSystemService {

    private UserSystemRepository userSystemRepository;

    public PersonUserSystemService(UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

}
