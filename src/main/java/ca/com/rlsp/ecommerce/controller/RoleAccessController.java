package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.service.RoleAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class RoleAccessController {

    private static final String ERROR_GETTING_ROLE_ACCESS_BY_ID = "Error getting ROLE_ACESS by id/code [RLSP] : ";
    private static final String ERROR_ROLE_ACCESS_EXIST_ON_DB = "Role Access already exist on DB [RLSP] : ";
    private final RoleAccessService roleAccessService;

    public RoleAccessController(RoleAccessService roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/roleAccess")
    public ResponseEntity<Collection<RoleAccess>> getRoleAccess(){

        Collection<RoleAccess> allRolesSaved = roleAccessService.getAll();
        return new ResponseEntity<>(allRolesSaved, HttpStatus.CREATED);

    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/roleAccess/{roleAccessId}")
    public ResponseEntity<RoleAccess> getByIdRoleAccess(@PathVariable Long roleAccessId) throws EcommerceException {

        RoleAccess getOneRolesSaved = roleAccessService.getById(roleAccessId).orElse(null);

        if(getOneRolesSaved == null) {
            throw new EcommerceException(ERROR_GETTING_ROLE_ACCESS_BY_ID + roleAccessId);
        }
        return new ResponseEntity<>(getOneRolesSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveProductroleAccess")
    public ResponseEntity<RoleAccess> saveRoleAccess(@RequestBody RoleAccess roleAccess) throws EcommerceException {

        if(roleAccess.getId() == null) {
            List<RoleAccess> roleAccessList = roleAccessService.getByDescription(roleAccess.getDescription().toUpperCase());

            if(!roleAccessList.isEmpty()) {
                throw new EcommerceException(ERROR_ROLE_ACCESS_EXIST_ON_DB + roleAccess.getDescription());
            }
        }
        RoleAccess roleAccessSaved  = roleAccessService.save(roleAccess);
        return new ResponseEntity<>(roleAccessSaved, HttpStatus.OK);
    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/roleAccess")
    public ResponseEntity<?> deleteObjectRoleAccess(@RequestBody RoleAccess roleAccess){

        roleAccessService.deleteById(roleAccess.getId());
        return new ResponseEntity<>("Deleted Role Access By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/roleAccess/{roleAccessId}")
    public ResponseEntity<?> deleteByIdRoleAccess(@PathVariable Long roleAccessId){

        roleAccessService.deleteById(roleAccessId);

        return new ResponseEntity<>("Deleted Role Access By Id", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/roleAccess/desc/{description}")
    public ResponseEntity<List<RoleAccess>> getByDescriptionRoleAccess(@PathVariable String description){

        List<RoleAccess> roleAccessList = roleAccessService.getByDescription(description.toUpperCase());

        return new ResponseEntity<>(roleAccessList, HttpStatus.OK);
    }
}
