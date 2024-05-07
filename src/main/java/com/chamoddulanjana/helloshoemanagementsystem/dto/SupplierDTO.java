package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Category;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
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
public class SupplierDTO {
    @Null(message = "supplier code is auto generated")
    private String supplierCode;

    @NotNull(message = "supplierName cannot be null")
    @Length(min = 3, max = 50, message = "supplierName too short or too long")
    private String supplierName;

    @NotNull(message = "category cannot be null")
    @Length(min = 5, max = 13, message = "Invalid category")
    private Category category;

    @NotNull(message = "addressLine1 cannot be null")
    @Length(min = 3, max = 50, message = "addressLine1 too short or too long")
    private String addressLine1;

    @Nullable
    @Length(max = 50, message = "addressLine2 too long")
    private String addressLine2;

    @Nullable
    @Length(max = 50, message = "addressLine3 too long")
    private String addressLine3;

    @Nullable
    @Length(max = 50, message = "addressLine4 too long")
    private String addressLine4;

    @Nullable
    @Length(max = 50, message = "addressLine5 too long")
    private String addressLine5;

    @Nullable
    @Length(max = 50, message = "addressLine6 too long")
    private String addressLine6;

    @NotNull(message = "contactNumber1 cannot be null")
    @Length(min = 10, max = 12, message = "contactNumber1 is Invalid")
    private String contactNumber1;

    @NotNull(message = "contactNumber2 cannot be null")
    @Length(min = 10, max = 12, message = "contactNumber2 is Invalid")
    private String contactNumber2;

    @NotNull(message = "email cannot be null")
    @Email(message = "Invalid email")
    private String email;
}
