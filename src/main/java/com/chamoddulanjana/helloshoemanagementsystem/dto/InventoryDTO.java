package com.chamoddulanjana.helloshoemanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InventoryDTO {
    @Null(message = "item code is auto generated")
    private String itemCode;

    @NotNull(message = "itemDescription cannot be null")
    @Length(min = 3, max = 50, message = "itemDescription too short or too long")
    private String itemDescription;

    @NotNull(message = "itemPicture cannot be null")
    @Length(min = 1, message = "Invalid itemPicture")
    private String itemPicture;

    @NotNull(message = "category cannot be null")
    @Length(min = 3, max = 20, message = "category too short or too long")
    private String category;

    @NotNull(message = "size cannot be null")
    @Length(min = 1, message = "Invalid shoe size")
    private int size;

    @NotNull(message = "supplierCode cannot be null")
    @Length(min = 3, message = "supplierCode too short or too long")
    private String supplierCode;

    @NotNull(message = "supplierName cannot be null")
    @Length(min = 3, max = 50, message = "supplierName too short or too long")
    private String supplierName;

    @NotNull(message = "quantityOnHand cannot be null")
    @Length(min = 1, message = "Invalid quantity on hand")
    private int quantityOnHand;

    @NotNull(message = "unitPrice_sale cannot be null")
    @Length(min = 1, message = "Invalid unitPrice sale")
    private double unitPrice_sale;

    @NotNull(message = "unitPrice_buy cannot be null")
    @Length(min = 1, message = "Invalid unitPrice buy")
    private double unitPrice_buy;

    @NotNull(message = "expectedProfit cannot be null")
    @Length(min = 1, message = "Invalid expectedProfit")
    private double expectedProfit;

    @NotNull(message = "profitMargin cannot be null")
    @Length(min = 1, message = "Invalid profitMargin")
    private double profitMargin;

    @NotNull(message = "status cannot be null")
    @Length(min = 1, max = 100, message = "status too short or too long")
    private String status;
}
