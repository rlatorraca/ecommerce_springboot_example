package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleAccessService {

    //@Autowired
    private final RoleAccessRepository roleAccessRepository;


    public RoleAccessService(RoleAccessRepository roleAccessRepository) {
        this.roleAccessRepository = roleAccessRepository;
    }

    public Collection<RoleAccess> getAll() {

        return roleAccessRepository.findAll();
    }

    public Optional<RoleAccess> getById(Long roleAccessId) {

        return Optional.ofNullable(roleAccessRepository.findById(roleAccessId).orElse(null));
    }

    public RoleAccess save(RoleAccess roleAccess) {

        return roleAccessRepository.save(roleAccess);
    }


    public void deleteById(Long roleAccessId) {

        roleAccessRepository.deleteById(roleAccessId);
    }

    public List<RoleAccess> getByDescription(String description) {
        return roleAccessRepository.findDescriptionAccess((description));
    }
}
