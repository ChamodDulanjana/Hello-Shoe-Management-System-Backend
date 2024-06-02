package com.chamoddulanjana.helloshoesapplicationsystem.service;

import com.chamoddulanjana.helloshoesapplicationsystem.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getCustomers(int page, int limit);

    CustomerDTO getCustomer(String id);

    List<CustomerDTO> filterCustomers(String pattern);

    void updateCustomer(String id, CustomerDTO dto);

    void addCustomer(CustomerDTO dto);

    void deleteCustomer(String id);
}
