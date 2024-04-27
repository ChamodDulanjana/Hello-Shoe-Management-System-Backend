package com.chamoddulanjana.helloshoemanagementsystem.service.util;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {

    private final ModelMapper mapper;

    public CustomerDTO toCustomerDTO(CustomerEntity customer) {
        return mapper.map(customer, CustomerDTO.class);
    }

    public CustomerEntity toCustomerEntity(CustomerDTO customer){
        return mapper.map(customer, CustomerEntity.class);
    }

    public List<CustomerDTO> toCustomerDTOList(List<CustomerEntity> customers) {
        return mapper.map(customers, List.class);
    }
}
