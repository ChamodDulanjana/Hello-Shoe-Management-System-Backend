package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import com.chamoddulanjana.helloshoemanagementsystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "customer")
@Entity
public class CustomerEntity implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerCode;
    @Column(nullable = false, length = 30)
    private String customerName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Gender gender;
    @Column(nullable = false, length = 10)
    private LocalDate joinDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Level level;
    @Column(nullable = false)
    private double totalPoints;
    @Column(nullable = false, length = 10)
    private LocalDate dob;
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
    @Column(nullable = false, length = 20)
    private LocalDateTime recentPurchaseDateAndTime;
}
