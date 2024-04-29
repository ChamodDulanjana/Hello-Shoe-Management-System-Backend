package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface EmployeeService extends SuperService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(String code);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployeeById(String code);
    void updateEmployee(EmployeeDTO employeeDTO, String code);
}
