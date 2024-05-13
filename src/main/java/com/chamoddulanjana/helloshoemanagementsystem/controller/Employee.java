package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Gender;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import com.chamoddulanjana.helloshoemanagementsystem.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveEmployee(@Validated
                                              @RequestPart("employeeName") String employeeName,
                                              @RequestPart("employeeProfilePic") String employeeProfilePic,
                                              @RequestPart("gender") Gender gender,
                                              @RequestPart("status") String status,
                                              @RequestPart("designation") String designation,
                                              @RequestPart("role") Role role,
                                              @RequestPart("dob") Date dob,
                                              @RequestPart("dateOfJoin") Date dateOfJoin,
                                              @RequestPart("branch") String branch,
                                              @RequestPart("addressLine1") String addressLine1,
                                              @RequestPart("addressLine2") String addressLine2,
                                              @RequestPart("addressLine3") String addressLine3,
                                              @RequestPart("addressLine4") String addressLine4,
                                              @RequestPart("addressLine5") String addressLine5,
                                              @RequestPart("contactNumber") String contactNumber,
                                              @RequestPart("email") String email,
                                              @RequestPart("informInCaseOfEmergency") String informInCaseOfEmergency,
                                              @RequestPart("emergencyContactNumber") String emergencyContactNumber,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }

        //Build Base64 image
        String base64ProPic = ImageUtil.convertBase64(employeeProfilePic);

        //build object
        EmployeeDTO buildEmployeeDTO = new EmployeeDTO();
        buildEmployeeDTO.setEmployeeName(employeeName);
        buildEmployeeDTO.setEmployeeProfilePic(base64ProPic);
        buildEmployeeDTO.setGender(gender);
        buildEmployeeDTO.setStatus(status);
        buildEmployeeDTO.setDesignation(designation);
        buildEmployeeDTO.setRole(role);
        buildEmployeeDTO.setDob(dob);
        buildEmployeeDTO.setDateOfJoin(dateOfJoin);
        buildEmployeeDTO.setBranch(branch);
        buildEmployeeDTO.setAddressLine1(addressLine1);
        buildEmployeeDTO.setAddressLine2(addressLine2);
        buildEmployeeDTO.setAddressLine3(addressLine3);
        buildEmployeeDTO.setAddressLine4(addressLine4);
        buildEmployeeDTO.setAddressLine5(addressLine5);
        buildEmployeeDTO.setContactNumber(contactNumber);
        buildEmployeeDTO.setEmail(email);
        buildEmployeeDTO.setInformInCaseOfEmergency(informInCaseOfEmergency);
        buildEmployeeDTO.setEmergencyContactNumber(emergencyContactNumber);

        try {
            employeeService.saveEmployee(buildEmployeeDTO);
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
