package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Category;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO {
    @Null(message = "supplier code is auto generated")
    private String supplierCode;
    @NotNull(message = "supplierName cannot be null")
    private String supplierName;
    @NotNull(message = "category cannot be null")
    private Category category;
    @NotNull(message = "addressLine1 cannot be null")
    private String addressLine1;
    @Nullable
    private String addressLine2;
    @Nullable
    private String addressLine3;
    @Nullable
    private String addressLine4;
    @Nullable
    private String addressLine5;
    @Nullable
    private String addressLine6;
    @NotNull(message = "contactNumber1 cannot be null")
    private String contactNumber1;
    @NotNull(message = "contactNumber2 cannot be null")
    private String contactNumber2;
    @NotNull(message = "email cannot be null")
    private String email;
}
