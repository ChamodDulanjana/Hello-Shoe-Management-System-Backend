package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import com.chamoddulanjana.helloshoemanagementsystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customer")
@Entity
public class CustomerEntity implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerCode;
    private String customerName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinDate;
    @Enumerated(EnumType.STRING)
    private Level level;
    private int totalPoints;
    private Date dob;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
}
