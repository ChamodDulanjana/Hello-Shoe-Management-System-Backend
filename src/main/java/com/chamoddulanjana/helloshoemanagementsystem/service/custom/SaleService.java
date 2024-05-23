package com.chamoddulanjana.helloshoemanagementsystem.service.custom;


import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface SaleService extends SuperService {
    void addSale(SaleDTO sale);
    SaleDTO getSale(String id);
    List<SaleDetailsDTO> getSaleItem(String id, String itemId);
    void refundSaleItem(RefundDTO dto);
}
