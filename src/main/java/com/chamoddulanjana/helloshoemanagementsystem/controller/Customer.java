package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class Customer {

    private final CustomerService customerService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Customer health check";
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customer){
        return customerService.saveCustomer(customer);
    }

}
