package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    private String supplierCode;
    private String supplierName;
    private Category category;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressLine6;
    private String contactNumber1;
    private String contactNumber2;
    private String email;
}
