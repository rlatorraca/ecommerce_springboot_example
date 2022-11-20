package ca.com.rlsp.ecommerce.service;

import ca.com.rlsp.ecommerce.model.dto.report.ReportLowStockProductDTO;
import ca.com.rlsp.ecommerce.model.dto.report.ReportProductStatusSalesDTO;
import ca.com.rlsp.ecommerce.model.dto.report.ReportStockPurchaseInvoiceDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReportService  {


    private JdbcTemplate jdbcTemplate;

    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Title: Ecommerce Purchases report
     * This report is used to return Sales Status.
     * All products have connection with a StockPurchaseInvoice
     *    *
     * @return ReportStockPurchaseInvoiceDTO JSON (by jdbcTemplate.query)
     * @author RLSP
     */
    public List<ReportProductStatusSalesDTO> reportProductStatusSales(ReportProductStatusSalesDTO reportProductStatusSalesDTO){

        List<ReportProductStatusSalesDTO> response = new ArrayList<ReportProductStatusSalesDTO>();

        String sql = "select p.id as productCode, "
                + " p.name as productName, "
                + " p.valor_venda as productValueSale, "
                + " np.email as clientEmail, "
                + " np.telefone as clientPhone, "
                + " np.id as clientId, "
                + " np.name as clientName, "
                + " p.product_value as productValue, "
                + " pse.id as saleCode, "
                + " pse.status_sales_Ecommerce as statusSale "
                + " from  product_sales_ecommerce as pse "
                + " inner join item_sale_ecommerce as ise on  ise.ecommerce_company_id = pse.id "
                + " inner join product as p on p.id = ise.product_id "
                + " inner join natural_person as np on np.id = pse.person_id ";


        sql+= " where pse.saleDate >= '"+reportProductStatusSalesDTO.getInitialDate()
                +"' and pse.saleDate  <= '"+reportProductStatusSalesDTO.getFinalDate()+"' ";

        if(!reportProductStatusSalesDTO.getProductName().isEmpty()) {
            sql += " and upper(p.name) like upper('%"+reportProductStatusSalesDTO.getProductName()+"%') ";
        }

        if (!reportProductStatusSalesDTO.getStatusProductSale().isEmpty()) {
            sql+= " and pse.status_sales_Ecommerce in ('"+reportProductStatusSalesDTO.getStatusProductSale()+"') ";
        }

        if (!reportProductStatusSalesDTO.getClientName().isEmpty()) {
            sql += " and upper(np.nome) like upper('%"+reportProductStatusSalesDTO.getClientName()+"%') ";
        }


        response = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportProductStatusSalesDTO.class));

        return response;

    }

    /**
     * Title: Ecommerce Purchases report
     * This report is used to return all products purchased by a Ecommerce.
     * All products have connection with a StockPurchaseInvoice
     *    *
     * @return ReportStockPurchaseInvoiceDTO JSON (by jdbcTemplate.query)
     * @author RLSP
     */
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

    /**
     * Title: Ecommerce Low Stock Product report
     * This report is used to return all products low stock for a product by a Ecommerce.
     * All products have connection with a StockPurchaseInvoice
     * @return ReportLowStockProductDTO JSON (by jdbcTemplate.query)
     * @author RLSP
     */
    public List<ReportLowStockProductDTO> generateReportSLowStockProduct(
            ReportLowStockProductDTO reportLowStockProductDTO) {

        List<ReportLowStockProductDTO> response = new ArrayList<ReportLowStockProductDTO>();

        String sql = "select p.id as productCode, p.product_name as productName, "
                + " p.product_value as productValue, ise.quantity as quantityPurchased, "
                + " lp.id as ProviderCode, lp.name as providerName, spi.date_sale as dateSale,"
                + " p.stock_Quantity as stockQuantity, p.minimum_stock_quantity as minimumStockQuantity "
                + " from stock_purchase_invoice as spi "
                + " inner join item_sale_ecommerce as ise on  spi.id = stock_purchase_invoice_id "
                + " inner join product as p on p.id = ise.product_id "
                + " inner join legal_person as lp on lp.id = spi.legal_person_vendor_id where ";

        sql += " spi.date_sale >='"+reportLowStockProductDTO.getInitialDate()+"' and ";
        sql += " spi.date_sale <= '" + reportLowStockProductDTO.getFinalDate() +"' and ";
        sql += " p.alert_stock_quantity = true and p.stock_quantity <= minimum_stock_quantity ";

        if (!reportLowStockProductDTO.getInvoiceCode().isEmpty()) {
            sql += " and spi.id = " + reportLowStockProductDTO.getInvoiceCode() + " ";
        }


        if (!reportLowStockProductDTO.getProductCode().isEmpty()) {
            sql += " and p.id = " + reportLowStockProductDTO.getProductCode() + " ";
        }

        if (!reportLowStockProductDTO.getProductName().isEmpty()) {
            sql += " upper(p.nome) like upper('%"+reportLowStockProductDTO.getProductName()+"')";
        }

        if (!reportLowStockProductDTO.getProviderName().isEmpty()) {
            sql += " upper(lp.nome) like upper('%"+reportLowStockProductDTO.getProviderName()+"')";
        }

        response = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportLowStockProductDTO.class));

        return response;

    }
}
