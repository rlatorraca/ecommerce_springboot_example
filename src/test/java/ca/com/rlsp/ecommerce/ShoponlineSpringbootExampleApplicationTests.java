package ca.com.rlsp.ecommerce;


import ca.com.rlsp.ecommerce.controller.RoleAccessController;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.management.relation.Role;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// To show that I want to test EcommerceSpringbootExampleApplication.class (project)
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class)
class EcommercepringbootExampleApplicationTests extends TestCase {


    @Autowired
    private RoleAccessController roleAccessController;

    @Autowired
    private RoleAccessRepository roleAccessRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testRestApiSaveRoleAccessController() throws JsonProcessingException, Exception {


        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING");

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/roleAccess")
                        .content(objectMapper.writeValueAsString(roleAccess))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());

        /*Conveter o retorno da API para um objeto de acesso*/

        RoleAccess objReturn = objectMapper.
                readValue(retornoApi.andReturn().getResponse().getContentAsString(),
                        RoleAccess.class);

        assertEquals(roleAccess.getDescription(), objReturn.getDescription());

    }


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
