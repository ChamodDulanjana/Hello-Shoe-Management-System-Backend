package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

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

    @GetMapping("/{code}")
    public CustomerDTO getCustomerById(@PathVariable String code){
        return customerService.getCustomerById(code);
    }

    @GetMapping("/getAll")
    public List<CustomerDTO> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/{code}")
    public void deleteCustomer(@PathVariable String code){
        customerService.deleteCustomer(code);
    }

    @PutMapping("/{code}")
    public void updateCustomer(@RequestBody CustomerDTO customer, @PathVariable String code){
        customerService.updateCustomer(customer, code);
    }

}
