package com.chamoddulanjana.helloshoemanagementsystem.util;

import com.chamoddulanjana.helloshoemanagementsystem.dto.*;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.*;
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


    public EmployeeDTO toEmployeeDTO(EmployeeEntity employee) {return mapper.map(employee, EmployeeDTO.class);}

    public EmployeeEntity toEmployeeEntity(EmployeeDTO employee) {return mapper.map(employee, EmployeeEntity.class);}

    public List<EmployeeDTO> toEmployeeDTOList(List<EmployeeEntity> employees) {return mapper.map(employees, List.class);}


    public InventoryDTO toInventoryDTO(InventoryEntity inventory) {return mapper.map(inventory, InventoryDTO.class);}

    public InventoryEntity toInventoryEntity(InventoryDTO inventory) {return mapper.map(inventory, InventoryEntity.class);}

    public List<InventoryDTO> toInventoryDTOList(List<InventoryEntity> inventoryList) {return mapper.map(inventoryList, List.class);}


    public UserDTO toUserDTO(UserEntity user) {return mapper.map(user, UserDTO.class);}

    public UserEntity toUserEntity(UserDTO user) {return mapper.map(user, UserEntity.class);}

    public List<UserDTO> toUserDTOList(List<UserEntity> users) {return mapper.map(users, List.class);}



}
