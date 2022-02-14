package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.service.RoleAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class RoleAccessController {

    private final RoleAccessService roleAccessService;

    public RoleAccessController(RoleAccessService roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    /* Retorno da api - de JSON para um objeto JAVA*/
    @ResponseBody
    @PostMapping(value = "/saveRoleAccess")
    public ResponseEntity<RoleAccess> saveRoleAccess(@RequestBody RoleAccess roleAccess){

        RoleAccess roleAccessSaved  = roleAccessService.save(roleAccess);
        return new ResponseEntity<>(roleAccessSaved, HttpStatus.OK);
    }
}
