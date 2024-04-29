package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<SupplierEntity, String> {
}
