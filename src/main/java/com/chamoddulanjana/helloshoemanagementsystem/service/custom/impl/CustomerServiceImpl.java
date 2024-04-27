package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CustomerDTO getSelectedCustomer(String customerCode) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return List.of();
    }

    @Override
    public void deleteCustomer(String customerCode) {

    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, String customerCode) {
        return null;
    }
}
