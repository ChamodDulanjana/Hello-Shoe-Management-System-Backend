package com.chamoddulanjana.helloshoemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    private String itemCode;
    private String itemDescription;
    private String itemPicture;
    private String category;
    private int size;
    private String supplierCode;
    private String supplierName;
    private int quantityOnHand;
    private double unitPrice_sale;
    private double unitPrice_buy;
    private double expectedProfit;
    private double profitMargin;
    private String status;
}
