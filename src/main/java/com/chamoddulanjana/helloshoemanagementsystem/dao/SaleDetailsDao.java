package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.SaleDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleDetailsDao extends JpaRepository<SaleDetailsEntity, String> {
    @Query(value = "SELECT item_id, COUNT(*) AS item_count FROM sale_details WHERE sale_id IN (SELECT sale_id FROM sale WHERE sale.created_at >= CURDATE() - INTERVAL ?1 DAY) GROUP BY item_id ORDER BY item_count DESC LIMIT 1", nativeQuery = true)
    Optional<List<Object[]>> findPopularItem(Integer range);
}