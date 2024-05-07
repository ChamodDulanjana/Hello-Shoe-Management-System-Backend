package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDTO {
    @Null(message = "employee code is auto generated")
    private String employeeCode;

    @NotNull(message = "employeeName cannot be null")
    @Length(min = 3, max = 50, message = "employeeName too short or too long")
    private String employeeName;

    @NotNull(message = "employeeProfilePic cannot be null")
    @Length(min = 1, message = "Invalid employeeProfilePic")
    private String employeeProfilePic;

    @NotNull(message = "gender cannot be null")
    @Length(min = 4, max = 6, message = "Invalid gender")
    private Gender gender;

    @NotNull(message = "status cannot be null")
    @Length(min = 1, max = 100, message = "status too short or too long")
    private String status;

    @NotNull(message = "designation cannot be null")
    @Length(min = 1, max = 100, message = "designation too short or too long")
    private String designation;

    @NotNull(message = "role cannot be null")
    @Length(min = 4, max = 5, message = "Invalid role")
    private Role role;

    @NotNull(message = "dob cannot be null")
    @Length(min = 8, max = 10, message = "Invalid date of birth")
    private Date dob;

    @NotNull(message = "dateOfJoin cannot be null")
    @Length(min = 8, max = 10, message = "Invalid date of join")
    private Date dateOfJoin;

    @NotNull(message = "branch cannot be null")
    @Length(min = 2, max = 20, message = "branch too short or too long")
    private String branch;

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

    @NotNull(message = "informInCaseOfEmergency cannot be null")
    @Length(min = 3, max = 100, message = "inform in case of emergency too short or too long")
    private String informInCaseOfEmergency;

    @NotNull(message = "emergencyContactNumber cannot be null")
    @Length(min = 10, max = 12, message = "Not Validate emergency contact number")
    private String emergencyContactNumber;
}
