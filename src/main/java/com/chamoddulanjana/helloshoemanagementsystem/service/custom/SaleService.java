package com.chamoddulanjana.helloshoemanagementsystem.service.custom;


import com.chamoddulanjana.helloshoemanagementsystem.dto.OverViewDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface SaleService extends SuperService {
    ResponseEntity<String> addSale(SaleDTO sale) throws IOException;

    SaleDTO getSale(String id);

    List<SaleDetailsDTO> getSaleItem(String id, String itemId);

    void refundSaleItem(RefundDTO dto);

    OverViewDTO getOverview();

    ResponseEntity<String> getAInvoice(String id) throws IOException;

    ResponseEntity<String> getLastInvoice() throws IOException;

    List<SaleDTO> getSales(Integer page, Integer limit);

    List<SaleDTO> searchSales(String search);
}
