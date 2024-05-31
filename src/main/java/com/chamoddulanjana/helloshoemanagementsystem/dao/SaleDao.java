package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleDao extends JpaRepository<SaleEntity, String> {
    @Query(value = "SELECT DISTINCT COUNT(sale_id) FROM sale WHERE created_at LIKE %?1%", nativeQuery = true)
    List<Object[]> findBillCount(String date);

    @Query(value = "SELECT * FROM sale WHERE created_at LIKE %?1%", nativeQuery = true)
    Optional<List<SaleEntity>> getAllTodaySales(String date);

    @Query(value = "SELECT * FROM sale ORDER BY created_at DESC LIMIT 1;", nativeQuery = true)
    Optional<SaleEntity> findLatestInvoice();

    @Query(value = "SELECT * FROM sale WHERE sale_id LIKE %?1% OR cashier_name LIKE %?1% OR customer_id LIKE %?1% OR payment_description LIKE %?1% OR created_at LIKE %?1%", nativeQuery = true)
    List<SaleEntity> filterSales(String search);
}
