package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveEmployee(@Validated @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            employeeService.saveEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error! | Employee saved successfully!  \nMore Details\n"+e.getMessage());
        }

    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
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
