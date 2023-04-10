package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.enums.StatusPayable;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.*;
import ca.com.rlsp.ecommerce.model.dto.*;
import ca.com.rlsp.ecommerce.repository.AddressRepository;
import ca.com.rlsp.ecommerce.repository.SalesInvoiceRepository;
import ca.com.rlsp.ecommerce.service.*;
import okhttp3.OkHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

@RestController
public class ProductSalesEcommerceController {

    public static final String SALE_SUCCESSFULLY_DELETED_FROM_DATABASE_RLSP = "Sale successfully deleted from Database [RLSP]";
    public static final String SALE_SUCCESSFULLY_LOGICAL_DELETED_FROM_DATABASE_RLSP = "Sale successfully logically deleted from Database [RLSP]";
    public static final String SALE_SUCCESSFULLY_REVERTED_LOGICAL_DELETED_FROM_DATABASE_RLSP = "Sale successfully reverted logical deletion from Database [RLSP]";

    private ProductSalesEcommerceService productSalesEcommerceService;
    private TrackingStatusService trackingStatusService;

    private AddressRepository addressRepository;

    private PersonController personController;

    private SalesInvoiceRepository salesInvoiceRepository;

    private TradePayableService tradePayableService;

    private SendEmailService sendEmailService;

    public ProductSalesEcommerceController(ProductSalesEcommerceService productSalesEcommerceService,
                                           AddressRepository addressRepository,
                                           PersonController personController,
                                           SalesInvoiceRepository salesInvoiceRepository,
                                           TrackingStatusService trackingStatusService,
                                           TradePayableService tradePayableService) {
        this.productSalesEcommerceService = productSalesEcommerceService;
        this.addressRepository = addressRepository;
        this.personController = personController;
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.trackingStatusService = trackingStatusService;
        this.tradePayableService = tradePayableService;
    }

    @ResponseBody
    @GetMapping(value = "/queryShippingEcommerce")
    public ResponseEntity<List<TransportationCompanyDTO>>  queryShippingEcommerce(@RequestBody @Valid QueryShippingDTO queryShippingDTO)
            throws Exception {

        List<TransportationCompanyDTO> transportationCompanyDTOs = new ArrayList<TransportationCompanyDTO>();

        try (InputStream input = ProductSalesEcommerceService.class.getClassLoader().getResourceAsStream("tokensapi.properties")) {

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(queryShippingDTO);

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);

            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(prop.getProperty("url.melhorenvio.sandbox") + "/api/v2/me/shipment/calculate")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + prop.getProperty("token.melhorenvio.sandbox"))
                    .addHeader("User-Agent", "suporte@jdevtreinamento.com.br")
                    .build();


            okhttp3.Response response = client.newCall(request).execute();

            JsonNode jsonNode = new ObjectMapper().readTree(response.body() != null ? response.body().string() : "");

            Iterator<JsonNode> iterator = jsonNode.iterator();



            while (iterator.hasNext()) {
                JsonNode node = iterator.next();

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

                if (transportationCompanyDTO.dataOk()) {
                    transportationCompanyDTOs.add(transportationCompanyDTO);
                }
            }



        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<List<TransportationCompanyDTO>>(transportationCompanyDTOs, HttpStatus.OK);
    }



    @ResponseBody
    @PostMapping(value = "/saveSalesEcommerce")
    public ResponseEntity<ProductSalesEcommerceDTO> saveSalesEcommerce(@RequestBody @Valid ProductSalesEcommerce productSalesEcommerce) throws EcommerceException, MessagingException, UnsupportedEncodingException {



        productSalesEcommerce.getPerson().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        NaturalPerson naturalPerson = personController.saveNaturalPerson(productSalesEcommerce.getPerson()).getBody();
        productSalesEcommerce.setPerson(naturalPerson);

        productSalesEcommerce.getBillingAddress().setPerson(naturalPerson);
        productSalesEcommerce.getBillingAddress().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        Address billingAddress = addressRepository.save(productSalesEcommerce.getBillingAddress());
        productSalesEcommerce.setBillingAddress(billingAddress);

        productSalesEcommerce.getShippingAddress().setPerson(naturalPerson);
        productSalesEcommerce.getShippingAddress().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        Address deliveryAddress = addressRepository.save(productSalesEcommerce.getShippingAddress());
        productSalesEcommerce.setShippingAddress(deliveryAddress);

        productSalesEcommerce.getSalesInvoice().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());


        for (int i = 0; i < productSalesEcommerce.getItemsSaleEcommerce().size(); i++) {
            productSalesEcommerce.getItemsSaleEcommerce().get(i).setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
            productSalesEcommerce.getItemsSaleEcommerce().get(i).setProductSalesEcommerce(productSalesEcommerce);
        }
        /* First save Sale and all your data */
        productSalesEcommerce = productSalesEcommerceService.saveAndFlush(productSalesEcommerce);


        TrackingStatus trackingStatus = new TrackingStatus();
        trackingStatus.setDistributionHub("Loja Local");
        trackingStatus.setCity("Toronto");
        trackingStatus.setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        trackingStatus.setProvince("Ontario");
        trackingStatus.setCountry("Canada");
        trackingStatus.setStatus("Started Sales");
        trackingStatus.setProductSalesEcommerce(productSalesEcommerce);

        trackingStatusService.save(trackingStatus);

        /*Associa a venda gravada no banco com a nota fiscal*/
        productSalesEcommerce.getSalesInvoice().setProductSalesEcommerce(productSalesEcommerce);

        /*Persiste novamente as nota fiscal novamente pra ficar amarrada na venda*/
        salesInvoiceRepository.saveAndFlush(productSalesEcommerce.getSalesInvoice());

        ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();
        productSalesEcommerceDTO.setPerson(productSalesEcommerce.getPerson());

        productSalesEcommerceDTO.setTotalValue(productSalesEcommerce.getTotalValue());
        productSalesEcommerceDTO.setTotalDiscount(productSalesEcommerce.getTotalDiscount());

        productSalesEcommerceDTO.setBilling(productSalesEcommerce.getBillingAddress());
        productSalesEcommerceDTO.setDelivering(productSalesEcommerce.getShippingAddress());

        productSalesEcommerceDTO.setDeliveryValue(productSalesEcommerce.getDeliveryValue());
        productSalesEcommerceDTO.setDelivering(productSalesEcommerce.getShippingAddress());


        for (ItemSaleEcommerce item : productSalesEcommerce.getItemsSaleEcommerce()) {

            ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
            itemSaleEcommerceDTO.setQuantity(item.getQuantity());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(item.getProduct().getId().toString());
            productDTO.setDescription(item.getProduct().getDescription());
            productDTO.setName(item.getProduct().getName());
            productDTO.setStockQuantity(item.getProduct().getStockQuantity());

            itemSaleEcommerceDTO.setProduct(productDTO);

            productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
        }


        TradePayable tradePayable = new TradePayable();
        tradePayable.setDescription("Venda da loja virtual nº: " + productSalesEcommerce.getId());
        tradePayable.setPaymentDate(Calendar.getInstance().getTime());
        tradePayable.setDueDate(Calendar.getInstance().getTime());
        tradePayable.setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        tradePayable.setPerson(productSalesEcommerce.getPerson());
        tradePayable.setStatusDebtor(StatusPayable.PAID);
        tradePayable.setTotalDiscount(productSalesEcommerce.getTotalDiscount());
        tradePayable.setTotalValue(productSalesEcommerce.getTotalValue());

        tradePayableService.saveAndFlush(tradePayable);

        /*Email to buyer*/
        StringBuilder msgemail = new StringBuilder();
        msgemail.append("Hi, ").append(naturalPerson != null ? naturalPerson.getName() : "").append("</br>");
        msgemail.append("You made a purchase nº: ").append(productSalesEcommerce.getId()).append("</br>");
        msgemail.append("At store ").append(productSalesEcommerce.getEcommerceCompany().getComercialName());

        /* Subject, msg, to*/
        sendEmailService.sendEmailHtml("Purchase done", msgemail.toString(), naturalPerson != null ? naturalPerson.getEmail() : "");

        /*Email to seller*/
        msgemail = new StringBuilder();
        msgemail.append("You made a purchase nº: ").append(productSalesEcommerce.getId());
        sendEmailService.sendEmailHtml("Sale donea", msgemail.toString(), productSalesEcommerce.getEcommerceCompany().getEmail());

        return new ResponseEntity<ProductSalesEcommerceDTO>(productSalesEcommerceDTO, HttpStatus.CREATED);
    }


    @ResponseBody
    @GetMapping(value = "/getSaleBySaleId/{id}")
    public ResponseEntity<ProductSalesEcommerceDTO> getSaleBySaleId(@PathVariable("id") Long saleId) {

        ProductSalesEcommerce productSalesEcommerce = productSalesEcommerceService.findNotDeletedSaleById(saleId);


        ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();

        productSalesEcommerceDTO.setPerson(productSalesEcommerce.getPerson());

        productSalesEcommerceDTO.setTotalValue(productSalesEcommerce.getTotalValue());
        productSalesEcommerceDTO.setTotalDiscount(productSalesEcommerce.getTotalDiscount());

        productSalesEcommerceDTO.setBilling(productSalesEcommerce.getBillingAddress());
        productSalesEcommerceDTO.setDelivering(productSalesEcommerce.getShippingAddress());

        productSalesEcommerceDTO.setDeliveryValue(productSalesEcommerce.getDeliveryValue());
        productSalesEcommerceDTO.setDelivering(productSalesEcommerce.getShippingAddress());

        productSalesEcommerceDTO.setId(productSalesEcommerce.getId());

        for (ItemSaleEcommerce item : productSalesEcommerce.getItemsSaleEcommerce()) {

            ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
            itemSaleEcommerceDTO.setQuantity(item.getQuantity());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(item.getProduct().getId().toString());
            productDTO.setDescription(item.getProduct().getDescription());
            productDTO.setName(item.getProduct().getName());
            productDTO.setStockQuantity(item.getProduct().getStockQuantity());

            itemSaleEcommerceDTO.setProduct(productDTO);

            productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
        }

        return new ResponseEntity<ProductSalesEcommerceDTO>(productSalesEcommerceDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getSaleByProductId/{id}")
    public ResponseEntity<List<ProductSalesEcommerceDTO>> getSaleByProductId(@PathVariable("id") Long saleId) {

        List<ProductSalesEcommerce> listProductSalesEcommerce = productSalesEcommerceService.salesByProduct(saleId);

        if (listProductSalesEcommerce == null) {
            listProductSalesEcommerce = new ArrayList<ProductSalesEcommerce>();
        }

        List<ProductSalesEcommerceDTO> listProductSalesEcommerceDTO = new ArrayList<ProductSalesEcommerceDTO>();


        for (ProductSalesEcommerce sale : listProductSalesEcommerce) {

            ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();

            productSalesEcommerceDTO.setPerson(sale.getPerson());

            productSalesEcommerceDTO.setTotalValue(sale.getTotalValue());
            productSalesEcommerceDTO.setTotalDiscount(sale.getTotalDiscount());

            productSalesEcommerceDTO.setBilling(sale.getBillingAddress());
            productSalesEcommerceDTO.setDelivering(sale.getShippingAddress());

            productSalesEcommerceDTO.setDeliveryValue(sale.getDeliveryValue());
            productSalesEcommerceDTO.setDelivering(sale.getShippingAddress());

            productSalesEcommerceDTO.setId(sale.getId());

            for (ItemSaleEcommerce item : sale.getItemsSaleEcommerce()) {

                ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
                itemSaleEcommerceDTO.setQuantity(item.getQuantity());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(item.getProduct().getId().toString());
                productDTO.setDescription(item.getProduct().getDescription());
                productDTO.setName(item.getProduct().getName());
                productDTO.setStockQuantity(item.getProduct().getStockQuantity());

                itemSaleEcommerceDTO.setProduct(productDTO);

                productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
            }

            listProductSalesEcommerceDTO.add(productSalesEcommerceDTO);
        }

        return new ResponseEntity<List<ProductSalesEcommerceDTO>>(listProductSalesEcommerceDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getSalesByCustomerId/{id}")
    public ResponseEntity<List<ProductSalesEcommerceDTO>> getSalesByCustomerId(@PathVariable("id") Long customerId) {

        List<ProductSalesEcommerce> listSalesByCustomer = productSalesEcommerceService.salesByCustomerId(customerId);

        if (listSalesByCustomer == null) {
            listSalesByCustomer = new ArrayList<ProductSalesEcommerce>();
        }

        List<ProductSalesEcommerceDTO> listProductSalesEcommerceDTO = new ArrayList<ProductSalesEcommerceDTO>();


        for (ProductSalesEcommerce sale : listSalesByCustomer) {

            ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();

            productSalesEcommerceDTO.setPerson(sale.getPerson());

            productSalesEcommerceDTO.setTotalValue(sale.getTotalValue());
            productSalesEcommerceDTO.setTotalDiscount(sale.getTotalDiscount());

            productSalesEcommerceDTO.setBilling(sale.getBillingAddress());
            productSalesEcommerceDTO.setDelivering(sale.getShippingAddress());

            productSalesEcommerceDTO.setDeliveryValue(sale.getDeliveryValue());
            productSalesEcommerceDTO.setDelivering(sale.getShippingAddress());

            productSalesEcommerceDTO.setId(sale.getId());

            for (ItemSaleEcommerce item : sale.getItemsSaleEcommerce()) {

                ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
                itemSaleEcommerceDTO.setQuantity(item.getQuantity());

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(item.getProduct().getId().toString());
                productDTO.setDescription(item.getProduct().getDescription());
                productDTO.setName(item.getProduct().getName());
                productDTO.setStockQuantity(item.getProduct().getStockQuantity());

                itemSaleEcommerceDTO.setProduct(productDTO);

                productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
            }

            listProductSalesEcommerceDTO.add(productSalesEcommerceDTO);
        }

        return new ResponseEntity<List<ProductSalesEcommerceDTO>>(listProductSalesEcommerceDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/getSalesDynamicly/{parameter}/{querytype}")
    public ResponseEntity<List<ProductSalesEcommerceDTO>> getSalesDynamically (@PathVariable("parameter") String parameter,
                                                                            @PathVariable("querytype") String querytype) {

        List<ProductSalesEcommerce> salesEcommerce = null;

        if (querytype.equalsIgnoreCase("BY_ID_PROD")) {
            salesEcommerce =   productSalesEcommerceService.salesByProduct(Long.parseLong(parameter));

        }else if (querytype.equalsIgnoreCase("BY_NAME_PROD")) {
            salesEcommerce = productSalesEcommerceService.salesByProductName(parameter.toUpperCase().trim());
        }
        else if (querytype.equalsIgnoreCase("BY_CLIENT_NAME")) {
            salesEcommerce = productSalesEcommerceService.salesByCustomerName(parameter.toUpperCase().trim());
        }
        else if (querytype.equalsIgnoreCase("BY_BILLING_ADDRESS")) {
            salesEcommerce = productSalesEcommerceService.salesByBillingAddress(parameter.toUpperCase().trim());
        }
        else if (querytype.equalsIgnoreCase("BY_DELIVERING_ADDRESS")) {
            salesEcommerce = productSalesEcommerceService.salesByShippingAddress(parameter.toUpperCase().trim());
        }

        if (salesEcommerce == null) {
            salesEcommerce = new ArrayList<ProductSalesEcommerce>();
        }

        List<ProductSalesEcommerceDTO> productSalesEcommerceDTOList = new ArrayList<ProductSalesEcommerceDTO>();

        for (ProductSalesEcommerce se : salesEcommerce) {

            ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();

            productSalesEcommerceDTO.setTotalValue(se.getTotalValue());
            productSalesEcommerceDTO.setPerson(se.getPerson());

            productSalesEcommerceDTO.setDelivering(se.getShippingAddress());
            productSalesEcommerceDTO.setBilling(se.getBillingAddress());

            productSalesEcommerceDTO.setTotalDiscount(se.getTotalDiscount());
            productSalesEcommerceDTO.setDeliveryValue(se.getDeliveryValue());
            productSalesEcommerceDTO.setId(se.getId());

            for (ItemSaleEcommerce item : se.getItemsSaleEcommerce()) {

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(item.getProduct().getId().toString());
                productDTO.setDescription(item.getProduct().getDescription());
                productDTO.setName(item.getProduct().getName());
                productDTO.setStockQuantity(item.getProduct().getStockQuantity());


                ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
                itemSaleEcommerceDTO.setQuantity(item.getQuantity());

                itemSaleEcommerceDTO.setProduct(productDTO);

                productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
            }

            productSalesEcommerceDTOList.add(productSalesEcommerceDTO);

        }

        return new ResponseEntity<List<ProductSalesEcommerceDTO>>(productSalesEcommerceDTOList, HttpStatus.OK);
    }



    @ResponseBody
    @GetMapping(value = "/getSalesDynamicallyBetweenDates/{date1}/{date2}")
    public ResponseEntity<List<ProductSalesEcommerceDTO>> getSalesDynamicallyBetweenDates(
            @PathVariable("date1") String dateAfter,
            @PathVariable("date2") String dateBefore) throws ParseException {

        List<ProductSalesEcommerce> salesEcommerce = null;



        salesEcommerce = productSalesEcommerceService.getSalesDynamicallyBetweenDates(dateAfter, dateBefore);


        if (salesEcommerce == null) {
            salesEcommerce = new ArrayList<ProductSalesEcommerce>();
        }

        List<ProductSalesEcommerceDTO> productSalesEcommerceDTOList = new ArrayList<ProductSalesEcommerceDTO>();

        for (ProductSalesEcommerce se : salesEcommerce) {

            ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();

            productSalesEcommerceDTO.setTotalValue(se.getTotalValue());
            productSalesEcommerceDTO.setPerson(se.getPerson());

            productSalesEcommerceDTO.setDelivering(se.getShippingAddress());
            productSalesEcommerceDTO.setBilling(se.getBillingAddress());

            productSalesEcommerceDTO.setTotalDiscount(se.getTotalDiscount());
            productSalesEcommerceDTO.setDeliveryValue(se.getDeliveryValue());
            productSalesEcommerceDTO.setId(se.getId());

            for (ItemSaleEcommerce item : se.getItemsSaleEcommerce()) {

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(item.getProduct().getId().toString());
                productDTO.setDescription(item.getProduct().getDescription());
                productDTO.setName(item.getProduct().getName());
                productDTO.setStockQuantity(item.getProduct().getStockQuantity());


                ItemSaleEcommerceDTO itemSaleEcommerceDTO = new ItemSaleEcommerceDTO();
                itemSaleEcommerceDTO.setQuantity(item.getQuantity());

                itemSaleEcommerceDTO.setProduct(productDTO);

                productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
            }

            productSalesEcommerceDTOList.add(productSalesEcommerceDTO);

        }

        return new ResponseEntity<List<ProductSalesEcommerceDTO>>(productSalesEcommerceDTOList, HttpStatus.OK);

    }


    

    @ResponseBody
    @DeleteMapping(value = "/deleteSaleFromDB/{saleId}")
    public ResponseEntity<String> deleteSaleFromDB(@PathVariable(value = "saleId") Long saleId) {

        productSalesEcommerceService.deleteSaleFromDB(saleId);

        return new ResponseEntity<String>(SALE_SUCCESSFULLY_DELETED_FROM_DATABASE_RLSP,HttpStatus.OK);

    }

    @ResponseBody
    @PutMapping(value = "/deleteLogicalSaleFromDB/{saleId}")
    public ResponseEntity<String> deleteLogicalSaleFromDB(@PathVariable(value = "saleId") Long saleId) {

        productSalesEcommerceService.deleteLogicalSaleFromDB(saleId);

        return new ResponseEntity<String>(SALE_SUCCESSFULLY_LOGICAL_DELETED_FROM_DATABASE_RLSP,HttpStatus.OK);

    }

    @ResponseBody
    @PutMapping(value = "/revertDeleteLogicalSaleFromDB/{saleId}")
    public ResponseEntity<String> revertDeleteLogicalSaleFromDB(@PathVariable(value = "saleId") Long saleId) {

        productSalesEcommerceService.revertDeleteLogicalSaleFromDB(saleId);

        return new ResponseEntity<String>(SALE_SUCCESSFULLY_REVERTED_LOGICAL_DELETED_FROM_DATABASE_RLSP,HttpStatus.OK);

    }

}
