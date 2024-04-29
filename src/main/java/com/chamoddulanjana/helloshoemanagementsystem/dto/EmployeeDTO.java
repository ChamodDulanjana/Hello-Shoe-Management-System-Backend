package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private String employeeCode;
    private String employeeName;
    private String employeeProfilePic;
    private Gender gender;
    private String status;
    private String designation;
    private Role role;
    private Date dob;
    private Date dateOfJoin;
    private String branch;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNumber;
    private String email;
    private String informInCaseOfEmergency;
    private String emergencyContactNumber;
}
