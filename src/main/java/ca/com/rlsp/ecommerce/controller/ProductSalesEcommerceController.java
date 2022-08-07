package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.*;
import ca.com.rlsp.ecommerce.model.dto.ItemSaleEcommerceDTO;
import ca.com.rlsp.ecommerce.model.dto.ProductDTO;
import ca.com.rlsp.ecommerce.model.dto.ProductSalesEcommerceDTO;
import ca.com.rlsp.ecommerce.repository.AddressRepository;
import ca.com.rlsp.ecommerce.repository.SalesInvoiceRepository;
import ca.com.rlsp.ecommerce.service.ProductSalesEcommerceService;
import ca.com.rlsp.ecommerce.service.TrackingStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class ProductSalesEcommerceController {

    public static final String SALE_SUCCESSFULLY_DELETED_FROM_DATABASE_RLSP = "Sale successfully deleted from Database [RLSP]";
    private ProductSalesEcommerceService productSalesEcommerceService;

    private TrackingStatusService trackingStatusService;

    private AddressRepository addressRepository;

    private PersonController personController;

    private SalesInvoiceRepository salesInvoiceRepository;

    public ProductSalesEcommerceController(ProductSalesEcommerceService productSalesEcommerceService,
                                           AddressRepository addressRepository,
                                           PersonController personController,
                                           SalesInvoiceRepository salesInvoiceRepository,
                                           TrackingStatusService trackingStatusService) {
        this.productSalesEcommerceService = productSalesEcommerceService;
        this.addressRepository = addressRepository;
        this.personController = personController;
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.trackingStatusService=trackingStatusService;
    }

    @ResponseBody
    @PostMapping(value = "/saveSalesEcommerce")
    public ResponseEntity<ProductSalesEcommerceDTO> saveSalesEcommerce(@RequestBody @Valid ProductSalesEcommerce productSalesEcommerce) throws EcommerceException, MessagingException {



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
            productDTO.setId(item.getProduct().getId());
            productDTO.setDescription(item.getProduct().getDescription());
            productDTO.setName(item.getProduct().getName());
            productDTO.setStockQuantity(item.getProduct().getStockQuantity());

            itemSaleEcommerceDTO.setProduct(productDTO);

            productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
        }

        return new ResponseEntity<ProductSalesEcommerceDTO>(productSalesEcommerceDTO, HttpStatus.CREATED);
    }


    @ResponseBody
    @GetMapping(value = "/getSaleById/{id}")
    public ResponseEntity<ProductSalesEcommerceDTO> getSaleById(@PathVariable("id") Long saleId) {

        ProductSalesEcommerce productSalesEcommerce = productSalesEcommerceService.findById(saleId);

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
            productDTO.setId(item.getProduct().getId());
            productDTO.setDescription(item.getProduct().getDescription());
            productDTO.setName(item.getProduct().getName());
            productDTO.setStockQuantity(item.getProduct().getStockQuantity());

            itemSaleEcommerceDTO.setProduct(productDTO);

            productSalesEcommerceDTO.getItemsSaleEccommerceDTO().add(itemSaleEcommerceDTO);
        }

        return new ResponseEntity<ProductSalesEcommerceDTO>(productSalesEcommerceDTO, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteSaleFromDB/{saleId}")
    public ResponseEntity<String> deleteSaleFromDB(@PathVariable(value = "saleId") Long saleId) {

        trackingStatusService.deleteSaleFromDB(saleId);

        return new ResponseEntity<String>(SALE_SUCCESSFULLY_DELETED_FROM_DATABASE_RLSP,HttpStatus.OK);

    }

}
