package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Category;
import com.chamoddulanjana.helloshoemanagementsystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "supplier")
@Entity
public class SupplierEntity implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String supplierCode;
    @Column(nullable = false, length = 30)
    private String supplierName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 13)
    private Category category;
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
    @Column(length = 50)
    private String addressLine6;
    @Column(nullable = false, length = 12)
    private String contactNumber1;
    @Column(nullable = false, length = 12)
    private String contactNumber2;
    @Column(nullable = false, length = 20)
    private String email;

}
