package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    @Null(message = "employee code is auto generated")
    private String employeeCode;
    @NotNull(message = "employeeName cannot be null")
    private String employeeName;
    @NotNull(message = "employeeProfilePic cannot be null")
    private String employeeProfilePic;
    @NotNull(message = "gender cannot be null")
    private Gender gender;
    @NotNull(message = "status cannot be null")
    private String status;
    @NotNull(message = "designation cannot be null")
    private String designation;
    @NotNull(message = "role cannot be null")
    private Role role;
    @NotNull(message = "dob cannot be null")
    private String dob;
    @NotNull(message = "dateOfJoin cannot be null")
    private String dateOfJoin;
    @NotNull(message = "branch cannot be null")
    private String branch;
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
    @Email(message = "Invalid email")
    private String email;
    @NotNull(message = "informInCaseOfEmergency cannot be null")
    private String informInCaseOfEmergency;
    @NotNull(message = "emergencyContactNumber cannot be null")
    private String emergencyContactNumber;
}
