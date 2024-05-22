package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Customer {

    private final CustomerService customerService;
    private final Logger logger = LoggerFactory.getLogger(Customer.class);

    @GetMapping("/health")
    public String healthCheck(){
        return "Customer health check";
    }

    @Secured("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCustomer(@Validated @RequestBody CustomerDTO customer, BindingResult bindingResult){

        logger.info("Save Customer Request: {}", customer);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            customerService.saveCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer saved successfully!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error! | Customer saved successfully!  \nMore Details\n"+e.getMessage());
        }
    }

    @Secured({"ADMIN","USER"})
    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerById(@PathVariable String code){

        logger.info("Get Customer Request: {}", code);

        try {
            return ResponseEntity.ok(customerService.getCustomerById(code));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
        }
    }

    @Secured({"ADMIN","USER"})
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomer(){

        logger.info("Get All Customers Request");

        return customerService.getAllCustomers();
    }

    @Secured("ADMIN")
    @DeleteMapping(value = "/{code}")
    public void deleteCustomer(@PathVariable String code){

        logger.info("Delete Customer Request: {}", code);

        customerService.deleteCustomer(code);
    }

    @Secured("ADMIN")
    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@Validated @RequestBody CustomerDTO customer, @PathVariable String code, BindingResult bindingResult){

        logger.info("Update Customer Request: {}", customer);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        CustomerDTO updatedCustomer = customerService.updateCustomer(customer, code);
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found!");
    }

}
