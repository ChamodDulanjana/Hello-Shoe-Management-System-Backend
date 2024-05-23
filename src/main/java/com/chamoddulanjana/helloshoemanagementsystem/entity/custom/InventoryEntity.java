package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import com.chamoddulanjana.helloshoemanagementsystem.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @JoinColumn(referencedColumnName = "supplier_name")
    @Column(nullable = false)
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
    @Column( length = 100)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_Id")
    private SupplierEntity supplier;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id")
    private StockEntity stock;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private List<SaleDetailsEntity> saleDetailsList;
}
