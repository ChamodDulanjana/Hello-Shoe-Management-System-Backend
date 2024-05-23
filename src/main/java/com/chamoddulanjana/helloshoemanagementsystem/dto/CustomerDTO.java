package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDTO {
    @Null(message = "Customer code is auto generated")
    private String customerCode;

    @NotNull(message = "customerName cannot be null")
    @Length(min = 3, max = 50, message = "customerName too short or too long")
    private String customerName;

    @NotNull(message = "gender cannot be null")
    @Length(min = 4, max = 6, message = "Invalid gender")
    private Gender gender;

    @NotNull(message = "joinDate cannot be null")
    @Length(min = 6, message = "Invalid join date")
    private LocalDate joinDate;

    @NotNull(message = "level cannot be null")
    @Length(min = 3, max = 6, message = "Invalid level")
    private Level level;

    @NotNull(message = "totalPoints cannot be null")
    @Length(min = 1, message = "Invalid total points")
    private double totalPoints;

    @NotNull(message = "dob cannot be null")
    @Length(min = 8, max = 10, message = "Invalid date of birth")
    private LocalDate dob;

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

    @NotNull(message = "contactNumber cannot be null")
    @Length(min = 10, max = 12, message = "Not Validate Number")
    private String contactNumber;

    @NotNull(message = "email cannot be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "recentPurchaseDateAndTime cannot be null")
    @Length(min = 2, message = "Invalid date & time")
    private LocalDateTime recentPurchaseDateAndTime;
}
