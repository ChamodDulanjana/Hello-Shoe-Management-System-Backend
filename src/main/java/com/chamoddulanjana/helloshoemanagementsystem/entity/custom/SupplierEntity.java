package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Category;
import com.chamoddulanjana.helloshoemanagementsystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "supplier")
@Entity
public class SupplierEntity implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String supplierCode;
    private String supplierName;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressLine6;
    private String contactNumber1;
    private String contactNumber2;
    private String email;

}
