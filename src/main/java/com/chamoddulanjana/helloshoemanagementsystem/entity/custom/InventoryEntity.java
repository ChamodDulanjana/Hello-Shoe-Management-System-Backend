package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

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
@Table(name = "inventory")
@Entity
public class InventoryEntity implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String itemCode;
    @Column(nullable = false, length = 30)
    private String itemDescription;
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String itemPicture;
    @Column(nullable = false, length = 20)
    private String category;
    @Column(nullable = false)
    private int size;
    @Column(nullable = false)
    private String supplierCode;
    @Column(nullable = false, length = 50)
    private String supplierName;
    @Column(nullable = false)
    private int quantityOnHand;
    @Column(nullable = false)
    private double unitPrice_sale;
    @Column(nullable = false)
    private double unitPrice_buy;
    @Column(nullable = false)
    private double expectedProfit;
    @Column(nullable = false)
    private double profitMargin;
    @Column(nullable = false, length = 100)
    private String status;
}
