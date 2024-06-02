package com.chamoddulanjana.helloshoesapplicationsystem.service;

import com.chamoddulanjana.helloshoesapplicationsystem.dto.EmployeeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getEmployees(int page, int limit);

    EmployeeDTO getEmployee(String id);

    void saveEmployee(String dto, MultipartFile image) throws IOException;

    void updateEmployee(String id, String employee, MultipartFile Image) throws IOException;

    void deleteEmployee(String id);

    List<EmployeeDTO> filterEmployees(String pattern);
}
