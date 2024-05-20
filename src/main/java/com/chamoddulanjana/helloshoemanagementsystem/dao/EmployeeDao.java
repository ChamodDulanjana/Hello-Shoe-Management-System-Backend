package com.chamoddulanjana.helloshoemanagementsystem.dao;

import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<EmployeeEntity, String> {
    Optional<EmployeeEntity> findEmployeeByEmail(String email);
    Optional<EmployeeEntity> findEmployeeByContactNumber(String contact);
}
