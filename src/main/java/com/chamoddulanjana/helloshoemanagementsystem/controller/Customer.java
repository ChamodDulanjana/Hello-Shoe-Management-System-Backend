package com.chamoddulanjana.helloshoemanagementsystem.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
public class Customer {

    @GetMapping("/health")
    public String healthCheck(){
        return "Customer health check";
    }

    @PostMapping
    public void saveCustomer(@RequestBody Customer customer){

    }

}
