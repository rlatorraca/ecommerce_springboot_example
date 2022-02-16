package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.service.RoleAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class RoleAccessController {

    private final RoleAccessService roleAccessService;

    public RoleAccessController(RoleAccessService roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/roleAccess")
    public ResponseEntity<Collection<RoleAccess>> getRoleAccess(){

        Collection<RoleAccess> allRolesSaved = roleAccessService.getAll();
        return new ResponseEntity<>(allRolesSaved, HttpStatus.OK);

    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/roleAccess/{roleAccessId}")
    public ResponseEntity<RoleAccess> getByIdRoleAccess(@PathVariable Long roleAccessId){

        Optional<RoleAccess> getOneRolesSaved = roleAccessService.getById(roleAccessId);
        return new ResponseEntity(getOneRolesSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(value = "/roleAccess")
    public ResponseEntity<RoleAccess> saveRoleAccess(@PathVariable RoleAccess roleAccess){

        RoleAccess roleAccessSaved  = roleAccessService.save(roleAccess);
        return new ResponseEntity<>(roleAccessSaved, HttpStatus.OK);
    }



    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/roleAccess/{roleAccessId}")
    public ResponseEntity<?> deleteRoleAccess(@PathVariable Long roleAccessId){

        roleAccessService.deleteById(roleAccessId);

        return new ResponseEntity<>("Deleted Role Access", HttpStatus.OK);
    }
}
