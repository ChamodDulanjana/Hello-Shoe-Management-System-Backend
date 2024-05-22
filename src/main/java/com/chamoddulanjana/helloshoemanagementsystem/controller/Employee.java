package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import com.chamoddulanjana.helloshoemanagementsystem.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Employee {

    private final EmployeeService employeeService;
    private final Logger logger = Logger.getLogger(Employee.class.getName());

    @GetMapping("/health")
    public String healthCheck() {
        return "Employee health check";
    }

    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveEmployee(@Validated
                                              @RequestParam(value = "image", required = false) MultipartFile image,
                                            @RequestPart(name = "dto") String dto,
                                            BindingResult bindingResult) {

        logger.info("Save Employee Request");

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        try {
            employeeService.saveEmployee(dto, image);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee saved successfully!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error! | Employee saved successfully!  \nMore Details\n"+e.getMessage());
        }

    }

    @Secured({"ADMIN", "USER"})
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeeById(@PathVariable String code) {

        logger.info("Get Employee Request: {}" + code);

        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(code));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found!");
        }
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/getAll")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Secured("ADMIN")
    @DeleteMapping("/{code}")
    public void deleteEmployeeById(@PathVariable String code) {
        employeeService.deleteEmployeeById(code);
    }

    @Secured("ADMIN")
    @PutMapping("/{code}")
    public ResponseEntity<?> updateEmployee(@Validated
                                                @PathVariable String code,
                                            @RequestPart(name = "dto") String dto,
                                            @RequestParam(value = "image", required = false) MultipartFile image,
                                            BindingResult bindingResult) throws IOException {

        logger.info("Update Employee Request: {}"+ code);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(code, dto, image);
        return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found!");
    }
}
