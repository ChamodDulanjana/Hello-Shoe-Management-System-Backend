package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.OverViewDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sales")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Sale {

    private final SaleService saleService;
    private final Logger logger = LoggerFactory.getLogger(Sale.class);

    @GetMapping("/health")
    public String healthCheck(){
        return "Sales Health Check";
    }

    @SneakyThrows
    @Secured({"ADMIN", "USER"})
    @PostMapping
    public ResponseEntity<String> addSale(@Validated @RequestBody SaleDTO sale) {
        logger.info("Sale request received");
        return saleService.addSale(sale);
    }

    @SneakyThrows
    @Secured({"ADMIN", "USER"})
    @GetMapping("/invoices/{id}")
    public ResponseEntity<String> getAInvoice(@PathVariable String id) {
        logger.info("Get a invoice request received");
        return saleService.getAInvoice(id);
    }

    @SneakyThrows
    @Secured({"ADMIN", "USER"})
    @GetMapping("/invoices/last")
    public ResponseEntity<String> getLastInvoice() {
        logger.info("Get last invoice request received");
        return saleService.getLastInvoice();
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/{id}")
    public SaleDTO getSale(@PathVariable String id) {
        logger.info("Get a sale request received");
        return saleService.getSale(id);
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/items")
    public List<SaleDetailsDTO> getSaleItem(@RequestParam(name = "itemId") String itemId, @RequestParam(name = "orderId") String orderId) {
        logger.info("Get sale item request received");
        return saleService.getSaleItem(orderId, itemId);
    }

    @Secured({"ADMIN", "USER"})
    @PostMapping("/refund")
    public void refundSaleItem(@Validated @RequestBody RefundDTO dto) {
        logger.info("Refund sale item request received");
        saleService.refundSaleItem(dto);
    }

    @Secured("ADMIN")
    @GetMapping("/overview")
    public OverViewDTO getDayOverview() {
        logger.info("Get day overview request received");
        return saleService.getOverview();
    }

    @Secured("ADMIN")
    @GetMapping
    public List<SaleDTO> getSales(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page,@RequestParam(name = "limit", required = false, defaultValue = "20") Integer limit, @RequestParam(name="search",required = false) String search) {
        if (search != null){
            return saleService.searchSales(search);
        }else{
            return saleService.getSales(page,limit);
        }
    }
}
