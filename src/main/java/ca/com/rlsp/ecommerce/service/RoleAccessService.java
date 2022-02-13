package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleAccessService {

    //@Autowired
    private final RoleAccessRepository roleAccessRepository;

    public RoleAccessService(RoleAccessRepository roleAccessRepository) {
        this.roleAccessRepository = roleAccessRepository;
    }

    public RoleAccess save(RoleAccess roleAccess) {

        return roleAccessRepository.save(roleAccess);
    }
}
