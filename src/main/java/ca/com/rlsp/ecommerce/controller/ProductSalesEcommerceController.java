package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.model.ProductSalesEcommerce;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoice;
import ca.com.rlsp.ecommerce.model.dto.ProductSalesEcommerceDTO;
import ca.com.rlsp.ecommerce.repository.AddressRepository;
import ca.com.rlsp.ecommerce.repository.ProductSalesEcommerceRepository;
import ca.com.rlsp.ecommerce.repository.SalesInvoiceRepository;
import ca.com.rlsp.ecommerce.service.ProductSalesEcommerceService;
import ca.com.rlsp.ecommerce.service.StockPurchaseInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class ProductSalesEcommerceController {

    private ProductSalesEcommerceRepository productSalesEcommerceRepository;

    private AddressRepository addressRepository;

    private PersonController personController;

    private SalesInvoiceRepository salesInvoiceRepository;

    public ProductSalesEcommerceController(ProductSalesEcommerceRepository productSalesEcommerceRepository, AddressRepository addressRepository, PersonController personController, SalesInvoiceRepository salesInvoiceRepository) {
        this.productSalesEcommerceRepository = productSalesEcommerceRepository;
        this.addressRepository = addressRepository;
        this.personController = personController;
        this.salesInvoiceRepository = salesInvoiceRepository;
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

        productSalesEcommerce.getBillingAddress().setPerson(naturalPerson);
        productSalesEcommerce.getBillingAddress().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());
        Address deliveryAddress = addressRepository.save(productSalesEcommerce.getBillingAddress());
        productSalesEcommerce.setShippingAddress(deliveryAddress);

        productSalesEcommerce.getSalesInvoice().setEcommerceCompany(productSalesEcommerce.getEcommerceCompany());

        /*Salva primeiro a venda e os demais dados*/
        productSalesEcommerce = productSalesEcommerceRepository.saveAndFlush(productSalesEcommerce);

        /*Associa a venda gravada no banco com a nota fiscal*/
        productSalesEcommerce.getSalesInvoice().setProductSalesEcommerce(productSalesEcommerce);

        /*Persiste novamente as nota fiscal novamente pra ficar amarrada na venda*/
        salesInvoiceRepository.saveAndFlush(productSalesEcommerce.getSalesInvoice());

        ProductSalesEcommerceDTO productSalesEcommerceDTO = new ProductSalesEcommerceDTO();
        productSalesEcommerceDTO.setTotalValue(productSalesEcommerce.getTotalValue());

        return new ResponseEntity<ProductSalesEcommerceDTO>(productSalesEcommerceDTO, HttpStatus.OK);
    }
}
