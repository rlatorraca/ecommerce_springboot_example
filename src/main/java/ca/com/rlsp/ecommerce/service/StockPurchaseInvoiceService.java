package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.controller.StockPurchaseInvoiceController;
import ca.com.rlsp.ecommerce.model.ProductCategory;
import ca.com.rlsp.ecommerce.model.ProductImage;
import ca.com.rlsp.ecommerce.model.RoleAccess;
import ca.com.rlsp.ecommerce.model.StockPurchaseInvoice;
import ca.com.rlsp.ecommerce.model.dto.report.ReportStockPurchaseInvoiceDTO;
import ca.com.rlsp.ecommerce.repository.RoleAccessRepository;
import ca.com.rlsp.ecommerce.repository.StockPurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StockPurchaseInvoiceService {

    private StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository;
    private JdbcTemplate jdbcTemplate;

    public StockPurchaseInvoiceService(StockPurchaseInvoiceRepository stockPurchaseInvoiceRepository,
                                       JdbcTemplate jdbcTemplate) {
        this.stockPurchaseInvoiceRepository = stockPurchaseInvoiceRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<ReportStockPurchaseInvoiceDTO> generateReportStockPurchaseInvoice(
            ReportStockPurchaseInvoiceDTO reportStockPurchaseInvoiceDTO) {

        List<ReportStockPurchaseInvoiceDTO> response = new ArrayList<ReportStockPurchaseInvoiceDTO>();

        String sql = "select p.id as productCode, p.product_name as productName, "
                   + " p.product_value as productValue, ise.quantity as quantityPurchased, "
                   + " lp.id as ProviderCode, lp.name as providerName, spi.date_sale as dateSale "
                   + " from stock_purchase_invoice as spi "
                   + " inner join item_sale_ecommerce as ise on  spi.id = stock_purchase_invoice_id "
                   + " inner join product as p on p.id = ise.product_id "
                   + " inner join legal_person as lp on lp.id = spi.legal_person_vendor_id where ";

        sql += " spi.date_sale >='"+reportStockPurchaseInvoiceDTO.getInitialDate()+"' and ";
        sql += " spi.date_sale <= '" + reportStockPurchaseInvoiceDTO.getFinalDate() +"' ";

        if (!reportStockPurchaseInvoiceDTO.getInvoiceCode().isEmpty()) {
            sql += " and spi.id = " + reportStockPurchaseInvoiceDTO.getInvoiceCode() + " ";
        }


        if (!reportStockPurchaseInvoiceDTO.getProductCode().isEmpty()) {
            sql += " and p.id = " + reportStockPurchaseInvoiceDTO.getProductCode() + " ";
        }

        if (!reportStockPurchaseInvoiceDTO.getProductName().isEmpty()) {
            sql += " upper(p.nome) like upper('%"+reportStockPurchaseInvoiceDTO.getProductName()+"')";
        }

        if (!reportStockPurchaseInvoiceDTO.getProviderName().isEmpty()) {
            sql += " upper(lp.nome) like upper('%"+reportStockPurchaseInvoiceDTO.getProviderName()+"')";
        }

        response = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportStockPurchaseInvoiceDTO.class));

        return response;
    }



    public Collection<StockPurchaseInvoice> getAll() {

        return stockPurchaseInvoiceRepository.findAll();
    }

    public Optional<StockPurchaseInvoice> getById(Long roleAccessId) {

        return Optional.ofNullable(stockPurchaseInvoiceRepository.findById(roleAccessId).orElse(null));
    }

    public StockPurchaseInvoice save(StockPurchaseInvoice stockPurchaseInvoice) {

        return stockPurchaseInvoiceRepository.save(stockPurchaseInvoice);
    }

    public StockPurchaseInvoice saveAndFlush(StockPurchaseInvoice stockPurchaseInvoice) {

        return stockPurchaseInvoiceRepository.saveAndFlush(stockPurchaseInvoice);
    }


    public void deleteById(Long stockPurchaseInvoiceId) {

        stockPurchaseInvoiceRepository.deleteById(stockPurchaseInvoiceId);
    }

    public List<StockPurchaseInvoice> getByDescription(String description) {
        return stockPurchaseInvoiceRepository.getInvoiceByDescription((description));
    }

    public boolean isPresentIntoDB(StockPurchaseInvoice stockPurchaseInvoice) {
        return stockPurchaseInvoiceRepository.findById(stockPurchaseInvoice.getId()).isPresent() == true;
    }

    public Boolean isIntoDB(String description){
        return !stockPurchaseInvoiceRepository.getInvoiceByDescription(description).isEmpty();
    }

    public void deleteInvoiceItemProduct(Long purchaseInvoiceId){
        stockPurchaseInvoiceRepository.deleteInvoiceItemProduct(purchaseInvoiceId);
    }

}
