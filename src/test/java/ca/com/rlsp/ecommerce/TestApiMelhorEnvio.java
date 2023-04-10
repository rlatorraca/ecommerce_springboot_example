package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.model.dto.TransportationCompanyDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestApiMelhorEnvio {


    public static void main(String[] args) throws IOException {

        try (InputStream input = TestApiMelhorEnvio.class.getClassLoader().getResourceAsStream("tokensapi.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("url.melhorenvio.sandbox"));

            /* Calculo de fretes (Produtos) */

            // Instancia o objecto da requisicao
            OkHttpClient client = new OkHttpClient().newBuilder().build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"from\": {\n        \"postal_code\": \"96020360\"\n    },\n    \"to\": {\n        \"postal_code\": \"01018020\"\n    },\n    \"products\": [\n        {\n            \"id\": \"x\",\n            \"width\": 11,\n            \"height\": 17,\n            \"length\": 11,\n            \"weight\": 0.3,\n            \"insurance_value\": 10.1,\n            \"quantity\": 1\n        },\n        {\n            \"id\": \"y\",\n            \"width\": 16,\n            \"height\": 25,\n            \"length\": 11,\n            \"weight\": 0.3,\n            \"insurance_value\": 55.05,\n            \"quantity\": 2\n        },\n        {\n            \"id\": \"z\",\n            \"width\": 22,\n            \"height\": 30,\n            \"length\": 11,\n            \"weight\": 1,\n            \"insurance_value\": 30,\n            \"quantity\": 1\n        }\n    ]\n}");
            Request request = new Request.Builder()
                    .url( prop.getProperty("url.melhorenvio.sandbox") + "/api/v2/me/shipment/calculate")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + prop.getProperty("token.melhorenvio.sandbox") )
                    .addHeader("User-Agent", "Application (RLSP -Ecommerce - Sandbox)")
                    .build();

            Response response = client.newCall(request).execute();
           //System.out.println(response.body().string());

            assert response.body() != null;
            JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());

            List<TransportationCompanyDTO> transportationCompanyDTOS = new ArrayList<TransportationCompanyDTO>();

            for (JsonNode node : jsonNode) {
                TransportationCompanyDTO transportationCompanyDTO = new TransportationCompanyDTO();

                if (node.get("id") != null) {
                    transportationCompanyDTO.setId(node.get("id").asText());
                }

                if (node.get("name") != null) {
                    transportationCompanyDTO.setName(node.get("name").asText());
                }

                if (node.get("price") != null) {
                    transportationCompanyDTO.setPrice(node.get("price").asText());
                }

                if (node.get("company") != null) {
                    transportationCompanyDTO.setCompany(node.get("company").get("name").asText());
                    transportationCompanyDTO.setCompanyLogo(node.get("company").get("picture").asText());
                }
                if(transportationCompanyDTO.dataOk()) {
                    transportationCompanyDTOS.add(transportationCompanyDTO);
                }
            }

            for( TransportationCompanyDTO t : transportationCompanyDTOS){
                System.out.println(t.getCompany() + " - " + t.getPrice());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
