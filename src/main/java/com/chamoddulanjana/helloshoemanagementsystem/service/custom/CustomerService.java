package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.text.ParseException;
import java.util.List;

public interface CustomerService extends SuperService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(String customerCode);
    List<CustomerDTO> getAllCustomers();
    void deleteCustomer(String customerCode);
    CustomerDTO updateCustomer(CustomerDTO customerDTO, String customerCode);
}
