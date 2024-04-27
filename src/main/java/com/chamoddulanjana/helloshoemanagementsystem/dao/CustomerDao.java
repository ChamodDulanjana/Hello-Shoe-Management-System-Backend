package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<CustomerEntity, String> {
}
