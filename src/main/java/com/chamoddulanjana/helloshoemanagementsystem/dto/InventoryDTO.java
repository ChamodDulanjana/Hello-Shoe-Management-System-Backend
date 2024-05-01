package com.chamoddulanjana.helloshoemanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO {
    @Null(message = "item code is auto generated")
    private String itemCode;
    @NotNull(message = "itemDescription cannot be null")
    private String itemDescription;
    @NotNull(message = "itemPicture cannot be null")
    private String itemPicture;
    @NotNull(message = "category cannot be null")
    private String category;
    @NotNull(message = "size cannot be null")
    private int size;
    @NotNull(message = "supplierCode cannot be null")
    private String supplierCode;
    @NotNull(message = "supplierName cannot be null")
    private String supplierName;
    @NotNull(message = "quantityOnHand cannot be null")
    private int quantityOnHand;
    @NotNull(message = "unitPrice_sale cannot be null")
    private double unitPrice_sale;
    @NotNull(message = "unitPrice_buy cannot be null")
    private double unitPrice_buy;
    @NotNull(message = "expectedProfit cannot be null")
    private double expectedProfit;
    @NotNull(message = "profitMargin cannot be null")
    private double profitMargin;
    @NotNull(message = "status cannot be null")
    private String status;
}
