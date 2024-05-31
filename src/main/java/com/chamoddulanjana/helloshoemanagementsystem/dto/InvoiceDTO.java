package com.chamoddulanjana.helloshoemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDTO {
    private String saleId;
    private String customerName;
    private List<SaleDetailsDTO> saleDetailsList;
    private String paymentDescription;
    private Double addedPoints;
    private Double totalPoints;
    private String cashierName;
    private Boolean rePrinted;
}
