package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.controller.RoleAccessController;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// To show that I want to test EcommerceSpringbootExampleApplication.class (project)
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
class EcommercepringbootExampleApplicationTests {


    @Autowired
    private RoleAccessController roleAccessController;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSaveRoleAccessController() {

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_MANAGER");

        roleAccessController.saveRoleAccess(roleAccess);

    }


}
