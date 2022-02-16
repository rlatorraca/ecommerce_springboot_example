package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.RoleAccessController;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// To show that I want to test EcommerceSpringbootExampleApplication.class (project)
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
class EcommercepringbootExampleApplicationTests {


    @Autowired
    private RoleAccessController roleAccessController;

    @Autowired
    private RoleAccessRepository roleAccessRepository;


    @Test
    public void testSaveRoleAccessController() {

        RoleAccess roleAccess_01 = new RoleAccess();

        roleAccess_01.setDescription("ROLE_MANAGER");

        roleAccessController.saveRoleAccess(roleAccess_01);

        roleAccess_01 = roleAccessController.saveRoleAccess(roleAccess_01).getBody();

        assertTrue(roleAccess_01.getId() > 0);

        /*Validar dados salvos da forma correta*/
        assertEquals("ROLE_MANAGER", roleAccess_01.getDescription());

        /*Teste de carregamento*/

        RoleAccess roleAccess_02 = roleAccessRepository.findById(roleAccess_01.getId()).get();

        assertEquals(roleAccess_01.getId(), roleAccess_02.getId());


        /*Teste de delete*/

        roleAccessRepository.deleteById(roleAccess_02.getId());

        roleAccessRepository.flush(); /*Roda esse SQL de delete no banco de dados*/

        RoleAccess roleAccess_03 = roleAccessRepository.findById(roleAccess_02.getId()).orElse(null);

        assertEquals(true, roleAccess_03 == null);


        /*Teste de query*/

        roleAccess_01 = new RoleAccess();

        roleAccess_01.setDescription("ROLE_STUDENT");

        roleAccess_01 = roleAccessController.saveRoleAccess(roleAccess_01).getBody();

        List<RoleAccess> roleAccessList = roleAccessRepository.findDescriptionAccess("STUDENT".trim().toUpperCase());

        assertEquals(1, roleAccessList.size());

        roleAccessRepository.deleteById(roleAccess_01.getId());


    }


}
