package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<EmployeeEntity, String> {
}
