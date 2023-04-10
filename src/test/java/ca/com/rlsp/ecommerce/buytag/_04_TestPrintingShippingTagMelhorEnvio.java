package ca.com.rlsp.ecommerce.buytag;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class _04_TestPrintingShippingTagMelhorEnvio {

    /* COMPRA AS TAGs para o FRETE */
    public static void main(String[] args) throws IOException {
        try (InputStream input = _04_TestPrintingShippingTagMelhorEnvio.class.getClassLoader().getResourceAsStream("tokensapi.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"mode\": \"private\",\n    \"orders\": [\n        \"1f0029e1-c107-4142-888f-28740fb834d0\"\n    ]\n}");


            Request request = new Request.Builder()
                    .url(prop.getProperty("url.melhorenvio.sandbox") + "/api/v2/me/shipment/print")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + prop.getProperty("token.melhorenvio.sandbox"))
                    .addHeader("User-Agent", "Application (RLSP -Ecommerce - Sandbox)")
                    .build();


            Response response;
            response = client.newCall(request).execute();


            System.out.println(response.body() != null ? response.body().string() : "");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
