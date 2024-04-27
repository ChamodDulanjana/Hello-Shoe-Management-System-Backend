package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.CustomerDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.CustomerService;
import com.chamoddulanjana.helloshoemanagementsystem.service.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final Mapping mapping;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return mapping.toCustomerDTO(customerDao.save(mapping.customerEntity(customerDTO)));
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
