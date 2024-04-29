package com.chamoddulanjana.helloshoemanagementsystem.service.util;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomerDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SupplierDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.CustomerEntity;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.SupplierEntity;
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

    public SupplierDTO toSupplierDTO(SupplierEntity supplier) {return mapper.map(supplier, SupplierDTO.class);}

    public SupplierEntity toSupplierEntity(SupplierDTO supplier){return mapper.map(supplier, SupplierEntity.class);}

    public List<SupplierDTO> toSupplierDTOList(List<SupplierEntity> suppliers) {return mapper.map(suppliers, List.class);}
}
