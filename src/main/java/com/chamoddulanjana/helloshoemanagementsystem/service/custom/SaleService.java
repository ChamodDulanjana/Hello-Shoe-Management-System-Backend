package com.chamoddulanjana.helloshoemanagementsystem.service.custom;


import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;

import java.util.List;

public interface SaleService {
    void addSale(SaleDTO sale);
    SaleDTO getSale(String id);
    List<SaleDetailsDTO> getSaleItem(String id, String itemId);
    void refundSaleItem(RefundDTO dto);
}
