package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    @Null(message = "Customer code is auto generated")
    private String customerCode;
    @NotNull(message = "customerName cannot be null")
    private String customerName;
    @NotNull(message = "gender cannot be null")
    private Gender gender;
    @NotNull(message = "joinDate cannot be null")
    private Date joinDate;
    @NotNull(message = "level cannot be null")
    private Level level;
    @NotNull(message = "totalPoints cannot be null")
    private int totalPoints;
    @NotNull(message = "dob cannot be null")
    private Date dob;
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
    @NotNull(message = "contactNumber cannot be null")
    private String contactNumber;
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "recentPurchaseDateAndTime cannot be null")
    private String recentPurchaseDateAndTime;
}
