package com.chamoddulanjana.helloshoemanagementsystem.entity.custom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sale_details")
public class SaleDetailsEntity {
    @Id
    @Column(length = 20)
    private String saleDetailsId;

    private String name;

    private Integer qty;
    private Double price;
    private Double total;
    private String size;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private InventoryEntity inventory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id")
    private SaleEntity sale;
}
