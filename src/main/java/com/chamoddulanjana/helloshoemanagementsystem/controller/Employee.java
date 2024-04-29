package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class Employee {

    private final EmployeeService employeeService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Employee health check";
    }

    @PostMapping
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("/{code}")
    public EmployeeDTO getEmployeeById(@PathVariable String code) {
        return employeeService.getEmployeeById(code);
    }

    @GetMapping("/getAll")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{code}")
    public void deleteEmployeeById(@PathVariable String code) {
        employeeService.deleteEmployeeById(code);
    }

    @PutMapping("/{code}")
    public void updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable String code) {
        employeeService.updateEmployee(employeeDTO, code);
    }
}
