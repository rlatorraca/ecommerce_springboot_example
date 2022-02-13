package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.service.RoleAccessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoleAccessController {

    private final RoleAccessService roleAccessService;

    public RoleAccessController(RoleAccessService roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    @PostMapping(name = "/saveRoleAccess")
    public RoleAccess saveRoleAccess(RoleAccess roleAccess){

        return roleAccessService.save(roleAccess);
    }
}
