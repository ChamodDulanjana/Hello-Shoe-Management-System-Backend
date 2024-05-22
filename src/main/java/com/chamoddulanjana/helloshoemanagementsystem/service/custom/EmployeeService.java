package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService extends SuperService {
    EmployeeDTO saveEmployee(String employeeDTO, MultipartFile image) throws IOException;
    EmployeeDTO getEmployeeById(String code);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployeeById(String code);
    EmployeeDTO updateEmployee(String employeeDTO, String code, MultipartFile image) throws IOException;
}
