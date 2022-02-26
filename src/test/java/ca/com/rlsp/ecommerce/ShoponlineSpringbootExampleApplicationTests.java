package ca.com.rlsp.ecommerce;


import ca.com.rlsp.ecommerce.controller.RoleAccessController;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Profile("ut")
@SpringBootTest(classes = EcommerceSpringbootExampleApplication.class) // To show that we want to test EcommerceSpringbootExampleApplication.class (project)
class EcommercepringbootExampleApplicationTests extends TestCase {


    @Autowired
    private RoleAccessController roleAccessController;

    @Autowired
    private RoleAccessRepository roleAccessRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    /* Endpoint Save to Role Access */
    @Test
    public void testRestApiSaveRoleAccessController() throws JsonProcessingException, Exception {


        /* Responsaveis por efetuar os testes*/
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING_SAVE");

        /* Trabalhar com JSON */
        ObjectMapper objectMapper = new ObjectMapper();

        /* returnApi é um JSON*/
        ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.post("/roleAccess")
                        .content(objectMapper.writeValueAsString(roleAccess))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + returnApi.andReturn().getResponse().getContentAsString());

        /*Conveter o retorno da API para um objeto de acesso */

        RoleAccess objReturn = objectMapper.
                readValue(returnApi.andReturn().getResponse().getContentAsString(),
                        RoleAccess.class);

        assertEquals(roleAccess.getDescription(), objReturn.getDescription());

    }

    /* Endpoint Delete By Id to Role Access */
    @Test
    public void testRestApiDeleteByIdRoleAccessController() throws JsonProcessingException, Exception {


        /* Responsaveis por efetuar os testes*/
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING_DELETE_BY_ID");

        /* Save a entitu on Role Access */
        roleAccess = roleAccessRepository.save(roleAccess);

        /* Trabalhar com JSON */
        ObjectMapper objectMapper = new ObjectMapper();

        /* returnApi é um JSON*/
        ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.delete("/roleAccess/{roleAccessId}", roleAccess.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + returnApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status da API: " + returnApi.andReturn().getResponse().getStatus());

        /*Conveter o retorno da API para um objeto de acesso */
        assertEquals("Deleted Role Access By Id", returnApi.andReturn().getResponse().getContentAsString());
        assertEquals(200,  returnApi.andReturn().getResponse().getStatus());

    }

    /* Endpoint Get Object By Id to Role Access */
    @Test
    public void testRestApiGetObjectByIdRoleAccessController() throws JsonProcessingException, Exception {


        /* Responsaveis por efetuar os testes*/
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING_GET_BY_ID");

        /* Save a entitu on Role Access */
        roleAccess = roleAccessRepository.save(roleAccess);

        /* Trabalhar com JSON */
        ObjectMapper objectMapper = new ObjectMapper();

        /* returnApi é um JSON*/
        ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/roleAccess/{roleAccessId}", roleAccess.getId())
                .content(objectMapper.writeValueAsString(roleAccess))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + returnApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status da API: " + returnApi.andReturn().getResponse().getStatus());

        /*Conveter o retorno da API para um objeto de acesso */

        RoleAccess objReturn = objectMapper.
                readValue(returnApi.andReturn().getResponse().getContentAsString(),
                        RoleAccess.class);

        assertEquals(objReturn.getDescription(), roleAccess.getDescription());
        assertEquals(200,  returnApi.andReturn().getResponse().getStatus());

    }

    /* Endpoint Get Object By Description to Role Access */
    @Test
    public void testRestApiGetObjectByDescriptionRoleAccessController() throws JsonProcessingException, Exception {


        /* Responsaveis por efetuar os testes*/
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING_GET_BY_DESCRIPTION");

        /* Save a entitu on Role Access */
        roleAccess = roleAccessRepository.save(roleAccess);

        /* Trabalhar com JSON */
        ObjectMapper objectMapper = new ObjectMapper();

        /* returnApi é um JSON*/
        ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.get("/roleAccess/desc/{description}", "BY_DESCRIP")
                .content(objectMapper.writeValueAsString(roleAccess))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + returnApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status da API: " + returnApi.andReturn().getResponse().getStatus());

        /*Conveter o retorno da API para um objeto de acesso */

        List<RoleAccess> objReturnList = objectMapper.
                readValue(returnApi.andReturn().getResponse().getContentAsString(),
                        new TypeReference<List<RoleAccess>>() { });

        assertEquals(true,objReturnList.size() > 1);
        assertEquals(roleAccess.getDescription(), objReturnList.get(0).getDescription());
        assertEquals(200,  returnApi.andReturn().getResponse().getStatus());

        roleAccessRepository.deleteById(roleAccess.getId());

    }

    /* Endpoint Delete Object to Role Access */
    @Test
    public void testRestApiDeleteObjectRoleAccessController() throws JsonProcessingException, Exception {


        /* Responsaveis por efetuar os testes*/
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
        MockMvc mockMvc = builder.build();

        RoleAccess roleAccess = new RoleAccess();

        roleAccess.setDescription("ROLE_TESTING_DELETE");

        /* Save a entitu on Role Access */
        roleAccess = roleAccessRepository.save(roleAccess);

        /* Trabalhar com JSON */
        ObjectMapper objectMapper = new ObjectMapper();

        /* returnApi é um JSON*/
        ResultActions returnApi = mockMvc.perform(MockMvcRequestBuilders.delete("/roleAccess")
                .content(objectMapper.writeValueAsString(roleAccess))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + returnApi.andReturn().getResponse().getContentAsString());
        System.out.println("Status da API: " + returnApi.andReturn().getResponse().getStatus());

        /*Conveter o retorno da API para um objeto de acesso */
        assertEquals("Deleted Role Access By Object", returnApi.andReturn().getResponse().getContentAsString());
        assertEquals(200,  returnApi.andReturn().getResponse().getStatus());

    }


    @Test
    public void testSaveRoleAccessController() throws EcommerceException {

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
