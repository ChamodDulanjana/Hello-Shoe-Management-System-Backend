package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class Employee {

    @GetMapping("/health")
    public String healthCheck() {
        return "Employee health check";
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {

    }
}
