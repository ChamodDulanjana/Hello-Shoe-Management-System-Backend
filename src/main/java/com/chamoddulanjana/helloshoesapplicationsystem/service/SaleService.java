package com.chamoddulanjana.helloshoesapplicationsystem.service;

import com.chamoddulanjana.helloshoesapplicationsystem.dto.OverViewDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.SaleDetailDTO;

import java.io.IOException;
import java.util.List;

public interface SaleService {
    void addSale(SaleDTO sale) throws IOException;

    SaleDTO getSale(String id);

    List<SaleDetailDTO> getSaleItem(String id, String itemId);

    void refundSaleItem(RefundDTO dto);

    OverViewDTO getOverview();


    List<SaleDTO> getSales(Integer page, Integer limit);

    List<SaleDTO> searchSales(String search);
}
