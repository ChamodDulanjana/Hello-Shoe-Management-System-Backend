package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.CustomerDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
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
        return mapping.toCustomerDTO(customerDao.save(mapping.toCustomerEntity(customerDTO)));
    }

    @Override
    public CustomerDTO getCustomerById(String customerCode) {
        if (!customerDao.existsById(customerCode)) throw new NotFoundException("Customer not found!");
        return mapping.toCustomerDTO(customerDao.getReferenceById(customerCode));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapping.toCustomerDTOList(customerDao.findAll());
    }

    @Override
    public void deleteCustomer(String customerCode) {
        if (!customerDao.existsById(customerCode)) throw new NotFoundException("Customer not found!");
        customerDao.deleteById(customerCode);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO, String customerCode) {
        if (!customerDao.existsById(customerCode)) throw new NotFoundException("Customer is null!");
        var byId = customerDao.findById(customerCode);
        if (byId.isPresent()) {
            byId.get().setCustomerName(customerDTO.getCustomerName());
            byId.get().setGender(customerDTO.getGender());
            byId.get().setJoinDate(customerDTO.getJoinDate());
            byId.get().setLevel(customerDTO.getLevel());
            byId.get().setTotalPoints(customerDTO.getTotalPoints());
            byId.get().setDob(customerDTO.getDob());
            byId.get().setAddressLine1(customerDTO.getAddressLine1());
            byId.get().setAddressLine2(customerDTO.getAddressLine2());
            byId.get().setAddressLine3(customerDTO.getAddressLine3());
            byId.get().setAddressLine4(customerDTO.getAddressLine4());
            byId.get().setAddressLine5(customerDTO.getAddressLine5());
        }
    }
}
