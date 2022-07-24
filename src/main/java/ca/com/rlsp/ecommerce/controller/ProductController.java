package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Product;
import ca.com.rlsp.ecommerce.service.ProductService;
import ca.com.rlsp.ecommerce.service.SendEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

@RestController
public class ProductController {

    private static final String ERROR_GETTING_PRODUCT_BY_ID = "Error getting Product by id/code [RLSP] : ";
    private static final String ERROR_PRODUCT_EXIST_ON_DB = "Product already exist on DB [RLSP] : ";
    private static final String ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED= "Ecommerce Company must be informed [RLSP] : ";
    private static final String ERROR_PRODUCT_BRAND_MUST_BE_INFORMED= "Product Brand doesnt must be informed [RLSP] : ";
    private static final String ERROR_PRODUCT_CATEGORY_MUST_BE_INFORMED = "Product Category must be informed [RLSP] : ";
    public static final String UNIT_TYPE_MUST_BE_INFORMED_RLSP = "Unit type must be informed [RLSP]";
    public static final String PRODUCT_NAME_MUST_BE_MORE_THAN_10_CHARACTERS_RLSP = "Product name must be more than 10 characters [RLSP]";
    public static final String PRODUCT_STOCK_QUANTITY_MUST_BE_AT_LEAST_OF_1 = "Product stock quantity must be at least of 1.";
    public static final String IMAGE_MUST_BE_INFORMED = "Image must be informed.";
    public static final String MUST_BE_INFORMED_AT_LEAST_3_IMAGES_FOR_EACH_PRODUCT = "Must be informed at least 3 images for each product.";
    public static final String MUST_BE_INFORMED_MAXIMUM_6_IMAGES_FOR_EACH_PRODUCT = "Must be informed maximum 6 images for each product.";

    private final ProductService productService;
    private final SendEmailService sendEmailService;

    public ProductController(ProductService productService, SendEmailService sendEmailService) {
        this.productService = productService;
        this.sendEmailService = sendEmailService;
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<Collection<Product>> getAllProducts(){

        Collection<Product> allProductsSave = productService.getAll();
        return new ResponseEntity<>(allProductsSave, HttpStatus.CREATED);

    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getProduct/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws EcommerceException {

        Product getOneProductSaved = productService.getById(productId).orElse(null);

        if(getOneProductSaved == null) {
            throw new EcommerceException(ERROR_GETTING_PRODUCT_BY_ID + productId);
        }
        return new ResponseEntity<>(getOneProductSaved, HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @PostMapping(path = "/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) throws EcommerceException, MessagingException, IOException {


        if (product.getUnitType() == null || product.getUnitType().toString().isEmpty()) {
            throw new EcommerceException(UNIT_TYPE_MUST_BE_INFORMED_RLSP);
        }

        if (product.getName().length() < 10) {
            throw new EcommerceException(PRODUCT_NAME_MUST_BE_MORE_THAN_10_CHARACTERS_RLSP);
        }
        if(product.getEcommerceCompany() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_ECOMMERCE_COMPANY_MUST_BE_INFORMED );
        }

        if(product.getId() == null) {
            List<Product> productList = productService.getByNameAndEcommerce(product.getName().toUpperCase(), product.getEcommerceCompany().getId());

            if(!productList.isEmpty()) {
                throw new EcommerceException(ERROR_PRODUCT_EXIST_ON_DB + product.getName());
            }
        }

        if(product.getProductBrand() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_PRODUCT_BRAND_MUST_BE_INFORMED );
        }

        if(product.getProductCategory() == null && product.getEcommerceCompany().getId() <= 0) {
            throw new EcommerceException(ERROR_PRODUCT_CATEGORY_MUST_BE_INFORMED);
        }

        if (product.getStockQuantity() < 1) {
            throw new EcommerceException(PRODUCT_STOCK_QUANTITY_MUST_BE_AT_LEAST_OF_1);
        }
        if (product.getImages() == null || product.getImages().isEmpty() || product.getImages().size() == 0) {
            throw new EcommerceException(IMAGE_MUST_BE_INFORMED);
        }
        if (product.getImages().size() < 3) {
            throw new EcommerceException(MUST_BE_INFORMED_AT_LEAST_3_IMAGES_FOR_EACH_PRODUCT);
        }
        if (product.getImages().size() > 6) {
            throw new EcommerceException(MUST_BE_INFORMED_MAXIMUM_6_IMAGES_FOR_EACH_PRODUCT);
        }

        if (product.getId() == null) {

            for (int x = 0; x < product.getImages().size(); x++) {
                product.getImages().get(x).setProduct(product);
                product.getImages().get(x).setEcommerceCompany(product.getEcommerceCompany());

                String base64Image = "";

                // Check if the image Ex: data:image/png;base64,IMAGE_ADDRESS has data:image (position 0) or just the
                // image address
                if (product.getImages().get(x).getSourceImage().contains("data:image")) {
                    base64Image = product.getImages().get(x).getSourceImage().split(",")[1];
                }else {
                    base64Image = product.getImages().get(x).getSourceImage();
                }

                // Converts the string argument ((base64)) into an array of bytes.
                byte[] imageBytes =  DatatypeConverter.parseBase64Binary(base64Image);

                // Returns a BufferedImage as the result of decoding a supplied InputStream with an ImageReader
                // chosen automatically from among those currently registered.
                // The InputStream is wrapped in an ImageInputStream.
                // If no registered ImageReader claims to be able to read the resulting stream, null is returned.
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

                if (bufferedImage != null) {

                    // Get Image extemsion )ex: png, jpg, etc)
                    // TYPE_INT_ARGB = 8-bitsRGBA
                    int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                    int width = Integer.parseInt("800");
                    int height = Integer.parseInt("600");

                    // Resize Image
                    BufferedImage resizedImage = new BufferedImage(width, height, type);
                    Graphics2D g = resizedImage.createGraphics();
                    g.drawImage(bufferedImage, 0, 0, width, height, null);
                    g.dispose(); // Print graphic in memory

                    // Output - save any image type to png
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage, "png", baos);

                    // (thumbnail => png and Base64)
                    String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

                    // Save thumbnail
                    product.getImages().get(x).setThumbnail(miniImgBase64);

                    bufferedImage.flush();
                    resizedImage.flush();
                    baos.flush();
                    baos.close();

                }
            }
        }

        Product productSaved  = productService.save(product);


        if (product.getAlertStockQuantity() && product.getStockQuantity() <= 1) {

            StringBuilder html = new StringBuilder();
            html.append("<h2>")
                    .append("Product: " + product.getName())
                    .append(" has a low stock quantity: " + product.getStockQuantity());
            html.append("<p>Product ID:").append(product.getId()).append("</p>");

            if (product.getEcommerceCompany().getEmail() != null) {
                sendEmailService.sendEmailHtml("Not Stock Product" , html.toString(), product.getEcommerceCompany().getEmail());
            }
        }

        return new ResponseEntity<>(productSaved, HttpStatus.OK);
    }


    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody Product product){

        productService.deleteById(product.getId());
        return new ResponseEntity<>("Deleted Role Access By Object", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @DeleteMapping(value = "/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long productId){

        productService.deleteById(productId);

        return new ResponseEntity<>("Deleted Product By Id", HttpStatus.OK);
    }

    @ResponseBody /* Retorno da api - de JSON para um objeto JAVA*/
    @GetMapping(value = "/getProduct/byDescription/{description}")
    public ResponseEntity<List<Product>> getByDescriptionRoleAccess(@PathVariable String description){

        List<Product> productList = productService.getByDescription(description.toUpperCase());

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
