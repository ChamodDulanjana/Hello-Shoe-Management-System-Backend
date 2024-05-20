package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService extends SuperService {
    EmployeeDTO saveEmployee(String employeeDTO, MultipartFile image) throws JsonProcessingException;
    EmployeeDTO getEmployeeById(String code);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployeeById(String code);
    void updateEmployee(String employeeDTO, String code, MultipartFile image);
}
