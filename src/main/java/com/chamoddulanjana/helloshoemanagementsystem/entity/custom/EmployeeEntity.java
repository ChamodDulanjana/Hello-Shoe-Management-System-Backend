package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "employee")
@Entity
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String employeeCode;
    @Column(nullable = false, length = 30)
    private String employeeName;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String employeeProfilePic;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Gender gender;
    @Column(nullable = false,length = 100)
    private String status;
    @Column(nullable = false,length = 100)
    private String designation;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Role role;
    @Column(nullable = false, length = 10)
    private Date dob;
    @Column(nullable = false, length = 10)
    private Date dateOfJoin;
    @Column(nullable = false, length = 20)
    private String branch;
    @Column(nullable = false, length = 50)
    private String addressLine1;
    @Column(length = 50)
    private String addressLine2;
    @Column(length = 50)
    private String addressLine3;
    @Column(length = 50)
    private String addressLine4;
    @Column(length = 50)
    private String addressLine5;
    @Column(nullable = false, length = 12)
    private String contactNumber;
    @Column(nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 100)
    private String informInCaseOfEmergency;
    @Column(nullable = false, length = 12)
    private String emergencyContactNumber;
}
